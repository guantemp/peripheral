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

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.ParallelPort;
import gnu.io.ParallelPortEvent;
import gnu.io.ParallelPortEventListener;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.TooManyListenersException;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @since JDK7.0
 * @version 0.0.1 2014年10月6日
 */
public class Parallel {
	private class SerialReader implements ParallelPortEventListener {
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

	private static final Pattern pattern = Pattern.compile("LPT(\\d?\\d)");

	/**
	 * @return The machine all parallel name
	 */
	public static String[] listAllParallel() {
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		Set<String> parallel = new HashSet<String>();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			if (portIdentifier.getPortType() == CommPortIdentifier.PORT_PARALLEL)
				parallel.add(portIdentifier.getName());
		}
		return parallel.toArray(new String[0]);
	}

	private ParallelPort parallelPort;
	private String port;
	private byte[] readBuffer;
	private int timeout;

	public Parallel(String port, int timeout) throws NoSuchPortException,
			PortInUseException, TooManyListenersException {
		setPort(port);
		setTimeout(timeout);
		init();
	}

	public void close() {
		if (parallelPort != null)
			parallelPort.close();
	}

	private void init() throws NoSuchPortException, PortInUseException,
			TooManyListenersException {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(port);
		if (portIdentifier.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
			parallelPort = (ParallelPort) portIdentifier.open("parallel",
					timeout);
			parallelPort.addEventListener(new SerialReader());
			parallelPort.notifyOnError(true);
		}
	}

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
	 * @return
	 * @throws NoSuchPortException
	 * @throws PortInUseException
	 * @throws IOException
	 */
	public OutputStream openOutputStream() throws NoSuchPortException,
			PortInUseException, IOException {
		return parallelPort.getOutputStream();
	}

	private void setPort(String port) {
		if (port == null || !pattern.matcher(port).matches())
			throw new IllegalArgumentException("Port is LPT1-99");
		this.port = port;
	}

	private void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
