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

import cc.foxtail.peripheral.annotations.Source;
import cc.foxtail.peripheral.util.Align;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;


/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171109
 * @since JDK8.0
 */
public class ThermalPrinter implements MiniPrinter, Observer {

    private OutputStream os;

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
            os.write(new byte[]{0x1d, 0x68, 0x5c, 0x1d, 0x48, 0x02, 0x1d,
                    0x6b, 0x49, 0x0a, 0x7b, 0x42});
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void print(String s) {

    }

    @Override
    public void print(String s, Align align) {

    }

    @Override
    public void print(String s, Align align, Set<PrintMode> style) {

    }

    @Override
    public void print(String s, int offset) {

    }

    @Override
    public void print(String s, int offset, Set<PrintMode> style) {

    }

    @Override
    public void printBitmapInFlash(int id) {

    }

    @Override
    public void println() {

    }

    @Override
    public void println(BufferedImage img) {

    }

    @Override
    public void println(String s) {

    }

    @Override
    public void println(String s, Align align, Set<PrintMode> style) {

    }

    @Override
    public void println(String s, int offset, Set<PrintMode> style) {

    }

    @Override
    public void rest() {

    }

    /*
     * (non-Javadoc)
     *
     * @see com.bulepos.peripheral.miniprinter.MiniPrinterAdapter#close()
     */
    @Override
    public void close() {
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param halfCut
     */
    @Override
    public void cutPaper(boolean halfCut) {

    }


    @Override
    public void cutPaper() {
        cutPaper(true);
    }

    @Override
    public void downloadBitmapToFlash(BufferedImage img) {

    }

    @Override
    public boolean isPaperOut() {
        return false;
    }


    @Override
    public boolean isSupportCutPaper() {
        return false;
    }

    @Override
    public Dimension maxFlashBitmapSize() {
        return null;
    }

    @Override
    public String toString() {
        return "通用热敏打印机";
    }

    @Override
    @Source
    public void open() {

    }

    @Override
    public void openCashBox() {

    }

    @Override
    public void print(DividingLine line) {

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