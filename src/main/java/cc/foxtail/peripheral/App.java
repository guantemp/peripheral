package cc.foxtail.peripheral;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.TooManyListenersException;

import cc.foxtail.peripheral.communication.RS232;
import cc.foxtail.peripheral.customerdisplay.CustomerDisplay;
import cc.foxtail.peripheral.customerdisplay.StandardLed;

/**
 * Hello world!
 * 
 */
public class App {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		RS232 serial = null;
		try {
			serial = new RS232("COM6", 2400, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			CustomerDisplay customerDisplay = new StandardLed(serial,
					"gb2312");
			customerDisplay.demo();
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			serial.close();
		}
		System.out.println("Execute time:"
				+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		connect("COM6");
		System.out.println("Execute time:"
				+ (System.currentTimeMillis() - start));
	}

	public static void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open("conect", 2000);
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(2400, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				InputStream in = serialPort.getInputStream();
				//OutputStream out = serialPort.getOutputStream();
				//(new Thread(new SerialWriter(out))).start();
				serialPort.addEventListener(new SerialReader(in));
				serialPort.notifyOnDataAvailable(true);
			} else {
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	/**
	 * Handles the input coming from the serial port. A new line character is
	 * treated as the end of a block in this example.
	 */
	public static class SerialReader implements SerialPortEventListener {
		private InputStream in;
		private byte[] buffer = new byte[1024];

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void serialEvent(SerialPortEvent arg0) {
			int data;

			try {
				int len = 0;
				while ((data = in.read()) > -1) {
					if (data == '\n') {
						break;
					}
					buffer[len++] = (byte) data;
				}
				System.out.print(new String(buffer, 0, len));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

	}

	/** */
	public static class SerialWriter implements Runnable {
		private Writer writer;

		public SerialWriter(OutputStream out) {
			try {
				writer = new PrintWriter(new OutputStreamWriter(
						out, "gb2312"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run() {
			try {
				writer.write(new char[]{ 0x1b, 0x73, 0x33 });
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}
