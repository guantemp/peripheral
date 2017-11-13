/*
 * @(#}ThermalPrinter.java
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
package cc.foxtail.peripheral.miniprinter;


import cc.foxtail.peripheral.util.Align;
import cc.foxtail.peripheral.util.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;


/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171109
 * @since JDK8.0
 */
public class ThermalPrinter implements MiniPrinter, Observer {
    private boolean open;
    private OutputStream os;
    private byte[] status;
    protected int maxCharacter=32;
    protected Dimension maxFlashImageSize=new Dimension(384, 600);

    /**
     * Closes this output stream and releases any system resources associated
     * with this stream
     */
    @Override
    public void close() {
        try {
            if (open) {
                os.close();
                open = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * half cut
     */
    @Override
    public void cutPaper() {
        if (open) {
            try {
                os.write(new byte[]{0x1d, 0x56, 0x49});
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param img
     */
    @Override
    public void downloadBitmapToFlash(BufferedImage img) {
        int maxWidth = (int) maxFlashImageSize.getWidth();
        int maxHeight = (int) maxFlashImageSize.getHeight();
        if (img.getWidth() > maxWidth || img.getHeight() > maxHeight)
            throw new IllegalArgumentException("Max bitmap size is " + maxWidth
                    + " × " + maxHeight);
        img = ImageView.toMonochromeImage(img);
        img = ImageView.adjustImageHeightAndWidthOf8Multiples(img);
        byte[] pixels = ImageView.mappingImageToMiniPrinter(img);
        int w = img.getWidth() / 8;
        int h = img.getHeight() / 8;
        try {
            os.write(new byte[]{0x1c, 0x71, 0x31, (byte) (w % 256),
                    (byte) (w / 256), (byte) (h % 256), (byte) (h / 256)});
            os.write(pixels);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    @Override
    public boolean isPaperOut() {
        return false;
    }

    /**
     * open output stream and add listeren for input
     */
    @Override
    public void open() {
        open = true;
    }

    /**
     * clear buffer data and set default when the power is on
     */
    @Override
    public void init() {
        if (open) {
            try {
                os.write(new byte[]{0x1b, 0x40});
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * open cash box
     */
    @Override
    public void openCashBox() {
        if (open) {
            try {
                os.write(new byte[]{0x1b, 0x70, 0x30, 0x32, 0x32, 0x1b, 0x70, 0x31, 0x32, 0x32});
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param line parting line
     */
    @Override
    public void printPartingLine(PartingLine line) {

    }

    /**
     * @param num
     */
    @Override
    public void printBlankLine(int num) {

    }

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
    @Override
    public void printBarcode(int height, int nHriFontPosition, Barcode value) {

    }

    /**
     * @param id
     */
    @Override
    public void printBitmapInFlash(int id) {
        if (id < 0 || id > 8)
            throw new IllegalArgumentException();
        if (open) {
            try {
                os.write(new byte[]{0x1c, 0x70, (byte) id, 0x48});
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param img
     */
    @Override
    public void printBitmap(BufferedImage img) {

    }

    /**
     * @param mode
     * @param offset percentage of line
     * @param value
     */
    @Override
    public void print(Set<PrintMode> mode, int offset, String value) {

    }

    /**
     * @param mode
     * @param align
     * @param value
     */
    @Override
    public void print(Set<PrintMode> mode, Align align, String value) {

    }

    /**
     * @param value
     */
    @Override
    public void print(String value) {

    }

    /**
     * @param instruction
     */
    @Override
    public void execute(byte[] instruction) {
        if (open) {
            try {
                os.write(instruction);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param instruction
     */
    @Override
    public void execute(char[] instruction) {
        if (open) {
            try {
                os.write(String.valueOf(instruction).getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void demo() {

    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {

    }
}
