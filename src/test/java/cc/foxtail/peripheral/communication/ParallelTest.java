/*
 * @(#}ParallelTest.java
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

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.TooManyListenersException;

import org.junit.Test;

import cc.foxtail.peripheral.communication.Parallel;


/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @since JDK6.0
 * @version 0.0.1 2014年10月7日
 */
public class ParallelTest {
	@Test
	public void testClose() throws NoSuchPortException, PortInUseException,
			IOException, TooManyListenersException {
		Parallel parallel = new Parallel("LPT1", 500);
		parallel.openOutputStream();
		parallel.close();
	}

	@Test
	public void testIsPaperOut() throws NoSuchPortException,
			PortInUseException, IOException, UnsupportedCommOperationException, TooManyListenersException {
		Parallel parallel = new Parallel("LPT2", 500);
		try {
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(
					parallel.openOutputStream(), "gb2312"));
			writer.write(new char[] { 0x1d, 0x72, 0x01 });
			parallel.close();
			parallel = new Parallel("LPT2", 500);
			InputStream is = parallel.openInputStream();
			// int temp = 0;
			// while ((temp = is.read()) != -1)
			System.out.println(is.read(new byte[1]));
		} finally {
			parallel.close();
		}
	}

	@Test
	public void testIsPrinterBusy() {

	}

	@Test
	public void testIsPrinterError() {

	}

	@Test
	public void testIsPrinterTimedOut() {

	}

	@Test
	public void testListAllParallel() throws NoSuchPortException, PortInUseException {
		for (String s : Parallel.listAllParallel())
			System.out.println(s);
	}

	@Test
	public void testOpenOutputStream() throws UnsupportedEncodingException,
			NoSuchPortException, PortInUseException, IOException, TooManyListenersException {
		Parallel parallel = new Parallel("LPT2", 500);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					parallel.openOutputStream());
			bos.write(new byte[] { 0x1d, 0x68, 0x5c, 0x1d, 0x48, 0x02, 0x1d,
					0x6b, 0x49, 0x0a, 0x7b, 0x42 });
			bos.write("No.123456".getBytes("gb2312"));
			// writer.write(new char[] { 0x1b, 0x24, 0x98, 0x01 });
			// writer.write("巍峨");
			/*
			 * writer.write(new char[] { 0x1c, 0x71, 0x01, 0x04, 0x00, 0x03,
			 * 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0xFE, 0x00,
			 * 0x3F, 0xE0, 0x03, 0xE0, 0x30, 0x0E, 0x00, 0x18, 0x11, 0x00, 0x08,
			 * 0x20, 0xC0, 0x0C, 0x40, 0xC0, 0x0C, 0x80, 0xC0, 0x0C, 0x80, 0x40,
			 * 0x1C, 0x80, 0x60, 0x1C, 0x80, 0xFF, 0xF8, 0x43, 0x9F, 0xF0, 0x7F,
			 * 0x07, 0xC0, 0x3E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03,
			 * 0x00, 0x00, 0xFE, 0x00, 0x3F, 0xE0, 0x03, 0xE0, 0x30, 0x0E, 0x00,
			 * 0x18, 0x11, 0x00, 0x08, 0x20, 0xC0, 0x0C, 0x40, 0xC0, 0x0C, 0x80,
			 * 0xC0, 0x0C, 0x80, 0x40, 0x1C, 0x80, 0x60, 0x1C, 0x80, 0xFF, 0xF8,
			 * 0x43, 0x9F, 0xF0, 0x7F, 0x07, 0xC0, 0x3E, 0x00, 0x00 });
			 * 
			 * writer.write(new char[] { 0x0a }); writer.close();
			 */
			bos.close();
		} finally {
			// assertTrue(parallel.isPaperOut());
			parallel.close();
		}
	}
}
