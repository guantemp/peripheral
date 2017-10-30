/*
 * @(#}Parallel.java
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

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.TooManyListenersException;
import java.util.regex.Pattern;

/**
 * @author <a href="www.foxtail.cc/author/guan xianghuang">guan xiangHuan</a>
 * @version 0.0.1 20141006
 * @since JDK7.0
 */
public class Parallel {
    private static final Pattern pattern = Pattern.compile("LPT(\\d?\\d)");
    private ParallelPort parallelPort;
    private String port;
    private byte[] readBuffer;
    private int timeout;
    private boolean open;

    public Parallel(String port, int timeout) throws NoSuchPortException,
            PortInUseException, TooManyListenersException {
        setPort(port);
        setTimeout(timeout);
    }

    /**
     * @return The machine all parallel name
     */
    public static String[] listAllParallel() {
        @SuppressWarnings("unchecked")
        Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
                .getPortIdentifiers();
        Set<String> parallel = new HashSet<>();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_PARALLEL)
                parallel.add(portIdentifier.getName());
        }
        return parallel.toArray(new String[0]);
    }

    /**
     *
     */
    public void close() {
        if (open && parallelPort != null) {
            parallelPort.removeEventListener();
            parallelPort.close();
            open = false;
        }
    }

    /**
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws TooManyListenersException
     */
    private void open() throws NoSuchPortException, PortInUseException,
            TooManyListenersException {
        if (open)
            return;
        CommPortIdentifier portIdentifier = CommPortIdentifier
                .getPortIdentifier(port);
        if (portIdentifier.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
            parallelPort = (ParallelPort) portIdentifier.open("parallel",
                    timeout);
            parallelPort.addEventListener(new ParallelReader());
            parallelPort.notifyOnError(true);
            open = true;
        }
    }

    /**
     * @return
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @return
     */
    public boolean isPaperOut() {
        return parallelPort.isPaperOut();
    }

    /**
     * @return
     * @throws IOException
     * @throws NoSuchPortException
     * @throws UnsupportedCommOperationException
     * @throws PortInUseException
     */
    public InputStream openInputStream() throws IOException,
            NoSuchPortException, UnsupportedCommOperationException,
            PortInUseException {
        return parallelPort.getInputStream();
    }

    /**
     * @param mess
     */
    public void write(String mess, String encoding) throws IOException {
        write(mess.getBytes(encoding));
    }

    /**
     * @param mess
     */
    public void write(String mess) throws IOException {
        write(mess.getBytes("UTF-8"));
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
     */
    public void write(char[] chars) throws IOException {
        byte[] bytes = new String(chars).getBytes(StandardCharsets.UTF_8);
        write(bytes);
    }

    /**
     * @param bytes
     */
    public void write(byte[] bytes) throws IOException {
        if (open) {
            OutputStream os = parallelPort.getOutputStream();
            try {
                os.write(bytes);
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
            }
        }
    }

    private void setPort(String port) {
        if (port == null || !pattern.matcher(port).matches())
            throw new IllegalArgumentException("Port is LPT1-99");
        this.port = port;
    }

    private void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    private class ParallelReader implements ParallelPortEventListener {
        private byte[] buffer = new byte[1024];

        @Override
        public void parallelEvent(ParallelPortEvent event) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (event.getEventType()) {
                case ParallelPortEvent.PAR_EV_BUFFER:// 2
                    break;
                case ParallelPortEvent.PAR_EV_ERROR: // 1
                    try {
                        int numBytes = parallelPort.getInputStream().read(buffer);
                        System.arraycopy(buffer, 0, readBuffer, 0, numBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
