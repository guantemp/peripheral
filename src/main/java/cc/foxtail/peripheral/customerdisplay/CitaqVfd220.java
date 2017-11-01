/*
 * @(#}CitaqVfd810CustomerDisplay.java
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

import cc.foxtail.peripheral.communication.Serial;
import cc.foxtail.peripheral.util.Align;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.TooManyListenersException;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 2013年11月15日
 * @since JDK6.0
 */
public class CitaqVfd220 extends StandardLed {
    private static final char[] CHANG = {0x1b, 0x73, 0x34};
    private static final char[] INIT = {0x1b, 0x40};
    private static final char[] OPEN_BOX = {0x02, 0x4d};
    private static final char[] PAID = {0x1b, 0x73, 0x33};
    private static final Pattern PATTERN = Pattern
            .compile("(GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
    private static final char[] PRICE = {0x1b, 0x73, 0x31};
    private static final char[] SHOW_ALIGN_LEFT = {0x1b, 0x4c, 0x41};
    private static final char[] SHOW_ALIGN_RIGHT = {0x1b, 0x52, 0x41};
    private static final char[] SHOW_ALIGN_RIGHT_TO_LEFT = {0x1b, 0x4f, 0x41};
    private static final char SHOW_END = 0x0d;
    private static final char[] SHOW_START = {0x1b, 0x51, 0x41};
    private static final char[] TOTAL = {0x1b, 0x73, 0x32};
    private Serial serial;

    /**
     * @param serial
     */
    public CitaqVfd220(Serial serial) {
        super(serial);
    }

    @Override
    public void close() {
        serial.close();
    }

    @Override
    public void init() {
        try {
            serial.write(INIT);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void openCashBox() {
        try {
            serial.write(OPEN_BOX);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void showChange(double chang) {
        try {
            serial.write(CHANG);
            serial.write(SHOW_START);
            serial.write(String.valueOf(chang));
            serial.write(SHOW_END);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void showInfo(int x, Set<Y_axis> y, Align align, String info) {
        for (Y_axis axis : y) {
            System.out.println(axis);
        }
        try {
            switch (align) {
                case LEFT:
                    serial.write(SHOW_ALIGN_LEFT);
                    break;
                case RIGHT:
                    serial.write(SHOW_ALIGN_RIGHT);
                    break;
                case RIGHT_TO_LEFT:
                    serial.write(SHOW_ALIGN_RIGHT_TO_LEFT);
                    break;
                case LEFT_TO_RIGHT:
                case CENTER:
                case NONE:
                    break;
                case BOTTOM:
                    break;
                case BOTTOM_TO_TOP:
                    break;
                case SCROLL:
                    break;
                case TOP:
                    break;
                case TOP_TO_BOTTOM:
                    break;
                default:
                    break;
            }
            serial.write(info);
            serial.write(SHOW_END);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void showPaid(double paid) {
        try {
            serial.write(PAID);
            serial.write(SHOW_START);
            serial.write(String.valueOf(paid));
            serial.write(SHOW_END);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void showTotal(double total) {
        try {
            serial.write(TOTAL);
            serial.write(SHOW_START);
            serial.write(String.valueOf(total));
            serial.write(SHOW_END);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void showUnitPrice(double price) {
        try {
            serial.write(PRICE);
            serial.write(SHOW_START);
            serial.write(String.valueOf(price));
            serial.write(SHOW_END);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
