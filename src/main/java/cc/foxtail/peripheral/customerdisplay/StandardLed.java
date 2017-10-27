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

import cc.foxtail.peripheral.communication.Serial;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.Objects;
import java.util.TooManyListenersException;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 2013年11月6日
 * @since JDK7.0
 */
public class StandardLed extends CustomerDisplayAdapter {
    private static final char[] CHANG = {0x1b, 0x73, 0x34};
    private static final char[] INIT = {0x1b, 0x40};
    private static final char[] OPEN_BOX = {0x02, 0x4d};
    private static final char[] PAID = {0x1b, 0x73, 0x33};
    private static final Pattern PATTERN = Pattern
            .compile("(GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
    private static final char[] PRICE = {0x1b, 0x73, 0x31};
    private static final char SHOW_END = 0x0d;
    private static final char[] SHOW_START = {0x1b, 0x51, 0x41};
    private static final char[] TOTAL = {0x1b, 0x73, 0x32};
    private Serial serial;

    public StandardLed(Serial serial, String encoding) {
        if (encoding == null || !PATTERN.matcher(encoding).matches())
            throw new IllegalArgumentException(
                    "Encoding must be (GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
        this.serial = Objects.requireNonNull(serial, "rs232 is required");
        try {
            serial.open();
        } catch (NoSuchPortException e) {
            throw new RuntimeException(
                    "Comm port not init,customer display will not show", e);
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
