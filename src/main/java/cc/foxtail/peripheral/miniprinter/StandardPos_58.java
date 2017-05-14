/*
 * @(#}StandardPrinter_58.java
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
package cc.foxtail.peripheral.miniprinter;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import cc.foxtail.peripheral.communication.Parallel;


/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @since JDK6.0
 * @version 0.0.1 2015年6月11日
 */
public class StandardPos_58 extends MiniPrinterAdapter {

	private static final Pattern PATTERN = Pattern
			.compile("(GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
	private BufferedOutputStream bos;
	private final String encoding;
	private Parallel parallel;

	public StandardPos_58(Parallel parallel) {
		this(parallel, "GB2312");
	}

	public StandardPos_58(Parallel parallel, String encoding) {
		if (parallel == null)
			throw new IllegalArgumentException("Parallel port can not be NULL");
		this.parallel = parallel;
		if (encoding == null || !PATTERN.matcher(encoding).matches())
			throw new IllegalArgumentException(
					"Encoding is not GB2312|BIG5|UTF-8|GBK|UTF-16");
		this.encoding = encoding;
		try {
			bos = new BufferedOutputStream(parallel.openOutputStream());
		} catch (NoSuchPortException | PortInUseException | IOException e) {
			close();
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bulepos.peripheral.miniprinter.MiniPrinterAdapter#print(int,
	 * int, int, java.lang.String)
	 */
	@Override
	public void print(int barcodeTypes, int height, int nHriFontPosition,
			String value) {
		try {
			bos.write(new byte[] { 0x1d, 0x68, 0x5c, 0x1d, 0x48, 0x02, 0x1d,
					0x6b, 0x49, 0x0a, 0x7b, 0x42 });
			bos.write(value.getBytes(encoding));
			bos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bulepos.peripheral.miniprinter.MiniPrinterAdapter#close()
	 */
	@Override
	public void close() {
		if (bos != null)
			try {
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		parallel.close();
	}

}
