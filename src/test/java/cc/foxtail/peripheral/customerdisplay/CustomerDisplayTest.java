/*
 * @(#}CustomerDisplayTest.java
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
package cc.foxtail.peripheral.customerdisplay;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.TooManyListenersException;

import junit.framework.TestCase;

import org.junit.Test;

import cc.foxtail.peripheral.communication.RS232;
import cc.foxtail.peripheral.customerdisplay.CustomerDisplay;
import cc.foxtail.peripheral.customerdisplay.StandardLed;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @since JDK6.0
 * @version 0.0.1 2014年5月20日
 */
public class CustomerDisplayTest extends TestCase {
	@Test
	public void testDemo() throws InterruptedException, NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, TooManyListenersException, IOException {
		for (String s : RS232.listAllSerialPort())
			System.out.print(s + " ");
		System.out.println();
		for (String s : RS232.listAvailableSerialPort())
			System.out.print(s + " ");
		RS232 serial = new RS232("COM6", 2400, 8, 1, 0);
		CustomerDisplay customerDisplay = new StandardLed(serial, "gb2312");
		customerDisplay.demo();
	}
}
