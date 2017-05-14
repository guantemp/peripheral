/*
 * @(#}MiniPrinterAdapter.java
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

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Set;

import cc.foxtail.peripheral.util.Align;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @since JDK6.0
 * @version 0.0.1 2014年10月9日
 */
public class MiniPrinterAdapter implements MiniPrinter {

	@Override
	public void demo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cutPaper() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadBitmapToFlash(BufferedImage img) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPaperOut() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrinterBusy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrinterError() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrinterTimedOut() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportBarcode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportCutPaper() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportFlashBitmap() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Dimension maxFlashBitmapSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openCashBox() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(String s, Align align) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(String s, Align align, Set<PrintMode> style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(String s, int offset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(String s, int offset, Set<PrintMode> style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printBitmapInFlash(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(DividingLine line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void println() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void println(BufferedImage img) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void println(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void println(String s, Align align, Set<PrintMode> style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void println(String s, int offset, Set<PrintMode> style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(int barcodeTypes, int height, int nHriFontPosition,
			String value) {
		// TODO Auto-generated method stub
		
	}
}
