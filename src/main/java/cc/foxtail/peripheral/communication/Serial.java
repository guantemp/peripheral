/*
 * @(#}Serial.java
 *
 * Copyright 2017 www.foxtail.cc All rights Reserved.
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


import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.2 20171027
 * @since JDK8.0
 */
public final class Serial extends Observable {
    private static final Pattern BAUD_RATE_PATTERN = Pattern
            .compile("300|600|1200|2400|4800|9600|19200|38400|43000|56000|57600|115200");
    // Windows:com[1-128],Linux:/dev/ttyS[0-128],MacOS:/dev/tty.*
    private static final Pattern PORT_PATTERN = Pattern
            .compile("COM(1[0-2][0-8]|\\d|[1-9]\\d)|/dev/ttyS(1[0-2][0-8]|\\d|[1-9]\\d)|/dev/tty.");


    private int baudRate;
    private int dataBits;
    private int parity;
    private String port;
    private SerialPort serialPort;
    private int stopBits;
    private int timeout;
    private int delay;
    private boolean open = false;
    private volatile boolean interrupted;
    private OutputStream os;

    /**
     * @param port
     * @param baudRate
     * @param dataBits
     * @param stopBits
     * @param parity   rang is 0-4
     * @param timeout  is Millisecond
     * @param delay    is Millisecond
     */
    public Serial(String port, int baudRate, int dataBits, int stopBits,
                  int parity, int timeout, int delay) {
        setPort(port);
        setBaudRate(baudRate);
        setDataBits(dataBits);
        setStopBits(stopBits);
        setParity(parity);
        setTimeout(timeout);
        setDelay(delay);
    }

    /**
     * @return The machine all serial port name
     */
    public static String[] listAllSerialPort() {
        @SuppressWarnings("unchecked")
        Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
                .getPortIdentifiers();
        Set<String> serial = new HashSet<>();
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
        Set<String> serial = new HashSet<>();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                try {
                    CommPort thePort = portIdentifier.open("CommUtil", 500);
                    thePort.close();
                    serial.add(portIdentifier.getName());
                } catch (PortInUseException e) {
                    serial.add(portIdentifier.getName() + ", is in use.");
                } catch (Exception e) {
                    serial.add("Failed to open port "
                            + portIdentifier.getName());
                }
            }
        }
        return serial.toArray(new String[0]);
    }

    public boolean isOpen() {
        return open;
    }

    public void close() {
        if (open && serialPort != null) {
            //interrupt read thread
            interrupted = true;
            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
            serialPort.close();
            open = false;
        }
    }

    /**
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     * @throws TooManyListenersException
     * @throws IOException
     */
    public void open() throws NoSuchPortException, PortInUseException,
            UnsupportedCommOperationException, TooManyListenersException,
            IOException {
        if (open)
            return;
        CommPortIdentifier portIdentifier = CommPortIdentifier
                .getPortIdentifier(port);
        serialPort = (SerialPort) portIdentifier.open("serial", timeout);
        serialPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
        SerialReader serialReader = new SerialReader();
        serialPort.addEventListener(serialReader);
        serialPort.notifyOnDataAvailable(true);
        os = serialPort.getOutputStream();
        Thread thread = new Thread(serialReader);
        thread.start();
        open = true;
    }


    /**
     * @param mess
     * @param encoding
     * @throws IOException
     */
    public void write(String mess, String encoding) throws IOException {
        write(mess.getBytes(encoding));
    }


    /**
     * @param mess
     * @throws IOException
     */
    public void write(String mess) throws IOException {
        os.write(mess.getBytes("UTF-8"));
    }

    /**
     * @param ch
     * @throws IOException
     */
    public void write(char ch) throws IOException {
        write(new char[]{ch});
    }


    /**
     * @param chars
     * @throws IOException
     */
    public void write(char[] chars) throws IOException {
        byte[] bytes = new String(chars).getBytes(StandardCharsets.UTF_8);
        os.write(bytes);
    }


    /**
     * @param bytes
     * @throws IOException
     */
    public void write(byte[] bytes) throws IOException {
        os.write(bytes);
    }


    /**
     * @param aByte
     * @throws IOException
     */
    public void write(byte aByte) throws IOException {
        os.write(aByte);
    }

    public void flush() throws IOException {
        os.flush();
    }

    /**
     * @param baudRate
     */
    private void setBaudRate(int baudRate) {
        if (!BAUD_RATE_PATTERN.matcher(String.valueOf(baudRate)).matches())
            throw new IllegalArgumentException(
                    "BauRate must be 300|600|1200|2400|4800|9600|19200|38400|43000|56000|57600|115200");
        this.baudRate = baudRate;
    }

    /**
     * @param dataBits
     */
    private void setDataBits(int dataBits) {
        if (dataBits != SerialPort.DATABITS_5
                && dataBits != SerialPort.DATABITS_6
                && dataBits != SerialPort.DATABITS_7
                && dataBits != SerialPort.DATABITS_8)
            throw new IllegalArgumentException("DataBits must be 5,6,7,8");
        this.dataBits = dataBits;
    }

    /**
     * @param parity
     */
    private void setParity(int parity) {
        if (parity != SerialPort.PARITY_EVEN
                && parity != SerialPort.PARITY_MARK
                && parity != SerialPort.PARITY_NONE
                && parity != SerialPort.PARITY_ODD
                && parity != SerialPort.PARITY_SPACE)
            throw new IllegalArgumentException("parity must be 0,1,2,3,4");
        this.parity = parity;
    }

    /**
     * @param port
     */
    private void setPort(String port) {
        if (!PORT_PATTERN.matcher(port).matches())
            throw new IllegalArgumentException(
                    "Port must be COM*(1-128) with windows or /dev/ttyS*(1-128) with Linux or /dev/tty.* with MacOS");
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

    private void setDelay(int delay) {
        this.delay = delay;
    }

    private class SerialReader implements SerialPortEventListener, Runnable {
        @Override
        public void serialEvent(SerialPortEvent event) {
            try {
                Thread.sleep(delay);
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
                        InputStream is = serialPort.getInputStream();
                        int numBytes = is.available();
                        byte[] buffer = new byte[numBytes];
                        while (is.available() > 0) {
                            is.read(buffer);
                        }
                        setChanged();
                        notifyObservers(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
                while (!interrupted) {
                    Thread.sleep(350);
                    //nothing
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
