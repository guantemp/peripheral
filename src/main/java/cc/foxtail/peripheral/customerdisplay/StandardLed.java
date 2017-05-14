/*
 * @(#}LedCustomerDisplay.java
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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.regex.Pattern;

import cc.foxtail.peripheral.communication.RS232;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @since JDK7.0
 * @version 0.0.1 2013年11月6日
 */
public class StandardLed extends CustomerDisplayAdapter {
	private static final char[] CHANG = { 0x1b, 0x73, 0x34 };
	private static final char[] INIT = { 0x1b, 0x40 };
	private static final char[] OPEN_BOX = { 0x02, 0x4d };
	private static final char[] PAID = { 0x1b, 0x73, 0x33 };
	private static final Pattern PATTERN = Pattern
			.compile("(GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
	private static final char[] PRICE = { 0x1b, 0x73, 0x31 };
	private static final char SHOW_END = 0x0d;
	private static final char[] SHOW_START = { 0x1b, 0x51, 0x41 };
	private static final char[] TOTAL = { 0x1b, 0x73, 0x32 };
	private RS232 rs232;
	private Writer writer;

	public StandardLed(RS232 rs232, String encoding) {
		if (rs232 == null)
			throw new IllegalArgumentException("Rs232 must not be NULL");
		if (encoding == null || !PATTERN.matcher(encoding).matches())
			throw new IllegalArgumentException(
					"Encoding must be (GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
		this.rs232 = rs232;
		try {
			writer = new PrintWriter(new OutputStreamWriter(
					rs232.openOutputStream(), encoding));
		} catch (IOException e) {
			throw new RuntimeException(
					"Comm port not init,customer display will not show", e);
		}
	}

	@Override
	public void close() {
		rs232.close();
	}

	@Override
	public void init() {
		try {
			writer.write(INIT);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void openCashBox() {
		try {
			writer.write(OPEN_BOX);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void showChange(double chang) {
		try {
			writer.write(CHANG);
			writer.write(SHOW_START);
			writer.write(String.valueOf(chang));
			writer.write(SHOW_END);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void showPaid(double paid) {
		try {
			writer.write(PAID);
			writer.write(SHOW_START);
			writer.write(String.valueOf(paid));
			writer.write(SHOW_END);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void showTotal(double total) {
		try {
			writer.write(TOTAL);
			writer.write(SHOW_START);
			writer.write(String.valueOf(total));
			writer.write(SHOW_END);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void showUnitPrice(double price) {
		try {
			writer.write(PRICE);
			writer.write(SHOW_START);
			writer.write(String.valueOf(price));
			writer.write(SHOW_END);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
