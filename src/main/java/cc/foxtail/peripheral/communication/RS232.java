/*
 * @(#}Serial.java
 *
 * Copyright 2013 www.pos4j.com All rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.foxtail.peripheral.communication;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import java.util.TooManyListenersException;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @since JDK7.0
 * @version 0.0.1 2014年10月4日
 */
public class RS232 extends Observable {
	private class SerialReader implements SerialPortEventListener {
		private byte[] buffer = new byte[1024];

		@Override
		public void serialEvent(SerialPortEvent event) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			switch (event.getEventType()) {
			case SerialPortEvent.BI: // 10
			case SerialPortEvent.OE: // 7
			case SerialPortEvent.FE: // 9
			case SerialPortEvent.PE: // 8
			case SerialPortEvent.CD: // 6
			case SerialPortEvent.CTS: // 3
			case SerialPortEvent.DSR: // 4
			case SerialPortEvent.RI: // 5
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2
				break;
			case SerialPortEvent.DATA_AVAILABLE: // 1
				try {
					int numBytes = serialPort.getInputStream().read(buffer);
					System.arraycopy(buffer, 0, readBuffer, 0, numBytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	private static final Pattern BAUD_RATE_PATTERN = Pattern
			.compile("300|600|1200|2400|4800|9600|19200|38400|43000|56000|57600|115200");
	// Windows:com1-com128,Linux:/dev/ttyS0-/dev/ttyS128,MacOS:/dev/tty.*
	private static final Pattern PORT_PATTERN = Pattern
			.compile("COM(1[0-2][0-7]|\\d|[1-9]\\d)|/dev/ttyS(1[0-2][0-7]|\\d|[1-9]\\d)|/dev/tty.");

	/**
	 * @return The machine all serial port name
	 */
	public static String[] listAllSerialPort() {
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		Set<String> serial = new HashSet<String>();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL)
				serial.add(portIdentifier.getName());
		}
		return serial.toArray(new String[0]);
	}

	/**
	 * @return The machine available serial port name
	 */
	public static String[] listAvailableSerialPort() {
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		Set<String> serial = new HashSet<String>();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				try {
					CommPort thePort = portIdentifier.open("CommUtil", 50);
					thePort.close();
					serial.add(portIdentifier.getName());
				} catch (PortInUseException e) {
					serial.add(portIdentifier.getName() + ", is in use.");
				} catch (Exception e) {
					System.err.println("Failed to open port "
							+ portIdentifier.getName());
					e.printStackTrace();
				}
			}
		}
		return serial.toArray(new String[0]);
	}

	private int baudRate;
	private int dataBits;
	private int parity;
	private String port;
	private byte[] readBuffer;
	private SerialPort serialPort;
	private int stopBits;
	private int timeout;

	/**
	 * @throws IOException
	 * @throws TooManyListenersException
	 * @throws UnsupportedCommOperationException
	 * @throws PortInUseException
	 * @throws NoSuchPortException
	 * @see RS232(String port, int baudRate, int dataBits, int stopBits, int
	 *      parity, int timeout)
	 */
	public RS232(String port, int baudRate, int dataBits, int stopBits,
			int parity) throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, TooManyListenersException,
			IOException {
		this(port, baudRate, dataBits, stopBits, parity, 50);
	}

	/**
	 * @param port
	 * @param baudRate
	 * @param dataBits
	 * @param stopBits
	 * @param parity
	 * @param timeout
	 *            is Millisecond
	 * @throws IOException
	 * @throws TooManyListenersException
	 * @throws UnsupportedCommOperationException
	 * @throws PortInUseException
	 * @throws NoSuchPortException
	 */
	public RS232(String port, int baudRate, int dataBits, int stopBits,
			int parity, int timeout) throws NoSuchPortException,
			PortInUseException, UnsupportedCommOperationException,
			TooManyListenersException, IOException {
		setPort(port);
		setBaudRate(baudRate);
		setDataBits(dataBits);
		setStopBits(stopBits);
		setParity(parity);
		setTimeout(timeout);
		init();
	}

	public void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * @throws NoSuchPortException
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws TooManyListenersException
	 * @throws IOException
	 */
	private void init() throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, TooManyListenersException,
			IOException {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(port);
		serialPort = (SerialPort) portIdentifier.open("serial", timeout);
		serialPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
		serialPort.addEventListener(new SerialReader());
		serialPort.notifyOnDataAvailable(true);
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public OutputStream openOutputStream() throws IOException {
		return serialPort.getOutputStream();
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws TooManyListenersException
	 * @throws UnsupportedCommOperationException
	 * @throws PortInUseException
	 * @throws NoSuchPortException
	 */
	public byte[] read() throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, TooManyListenersException,
			IOException {
		return readBuffer;
	}

	private void setBaudRate(int baudRate) {
		if (!BAUD_RATE_PATTERN.matcher(String.valueOf(baudRate)).matches())
			throw new IllegalArgumentException(
					"BauRate must be 300|600|1200|2400|4800|9600|19200|38400|43000|56000|57600|115200");
		this.baudRate = baudRate;
	}

	private void setDataBits(int dataBits) {
		if (dataBits != SerialPort.DATABITS_5
				&& dataBits != SerialPort.DATABITS_6
				&& dataBits != SerialPort.DATABITS_7
				&& dataBits != SerialPort.DATABITS_8)
			throw new IllegalArgumentException("DataBits must be 5,6,7,8");
		this.dataBits = dataBits;
	}

	private void setParity(int parity) {
		if (parity != SerialPort.PARITY_EVEN
				&& parity != SerialPort.PARITY_MARK
				&& parity != SerialPort.PARITY_NONE
				&& parity != SerialPort.PARITY_ODD
				&& parity != SerialPort.PARITY_SPACE)
			throw new IllegalArgumentException("parity must be 0,1,2,3,4");
		this.parity = parity;
	}

	private void setPort(String port) {
		if (!PORT_PATTERN.matcher(port).matches())
			throw new IllegalArgumentException(
					"Port must be COM*(1-127) with windows or /dev/ttyS*(1-127) with Linux or /dev/tty.* with MacOS");
		this.port = port;
	}

	private void setStopBits(int stopBits) {
		if (stopBits != SerialPort.STOPBITS_1
				&& stopBits != SerialPort.STOPBITS_1_5
				&& stopBits != SerialPort.STOPBITS_2)
			throw new IllegalArgumentException("StopBits must be 1,2,3");
		this.stopBits = stopBits;
	}

	private void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
