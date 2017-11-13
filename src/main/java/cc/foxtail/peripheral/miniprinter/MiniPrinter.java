/*
 * @(#}MiniPrinter.java
 *
 * Copyright 2013 www.foxtail.cc All rights Reserved.
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

import cc.foxtail.peripheral.Demo;
import cc.foxtail.peripheral.util.Align;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171107
 * @since JDK7.0
 */
public interface MiniPrinter extends Demo {
    /**
     * Closes this output stream and releases any system resources associated
     * with this stream
     */
    void close();

    /**
     * half cut
     */
    void cutPaper();

    /**
     * @param img
     */
    void downloadBitmapToFlash(BufferedImage img);

    /**
     * @return
     */
    boolean isPaperOut();

    /**
     * open output stream and add listeren for input
     */
    void open();

    /**
     * clear buffer data and set default when the power is on
     */
    void init();

    /**
     * open cash box
     */
    void openCashBox();

    /**
     * @param line parting line
     */
    void printPartingLine(PartingLine line);

    /**
     * @param num
     */
    void printBlankLine(int num);

    /**
     * Center print  UPC-A, UPC-E, EAN13, EAN8, CODE39, CODE93, CODE128, ITF
     * QR
     *
     * @param height           barcode height default is 162, range is 1-255
     * @param nHriFontPosition HRI character position ,0 does not print, 1 printed above the
     *                         bar code, the bar code printed below 2, 3 are printed on the
     *                         bottom, the default is 3
     * @param value            print barcode value
     */
    void printBarcode(int height, int nHriFontPosition, Barcode value);

    /**
     * @param id
     */
    void printBitmapInFlash(int id);

    /**
     * @param img
     */
    void printBitmap(BufferedImage img);

    /**
     * @param mode
     * @param offset percentage of line
     * @param value
     */
    void print(Set<PrintMode> mode, int offset, String value);

    /**
     * @param mode
     * @param align
     * @param value
     */
    void print(Set<PrintMode> mode, Align align, String value);

    /**
     * @param value
     */
    void print(String value);

    /**
     * @param instruction
     */
    void execute(byte[] instruction);

    /**
     * @param instruction
     */
    void execute(char[] instruction);

    /**
     *
     */
    enum PartingLine {
        ASTERISK('*'), HORIZONTAL_LINE('-');
        private char delimiter;

        PartingLine(char delimiter) {
            this.delimiter = delimiter;
        }

        public char delimiter() {
            return delimiter;
        }
    }
    /**
     *
     */
    enum PrintMode {
        DOUBLE_HEIGHT, DOUBLE_WIDTH, QUADRUPLE, REVERSE, THICK_UNDERLINE, THIN_UNDERLINE, UPSIDEDOWN, WHIRL
    }
}
