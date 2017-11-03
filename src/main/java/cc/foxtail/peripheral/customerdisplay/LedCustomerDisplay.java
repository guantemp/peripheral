/*
 * @(#}LedCustomerDisplay.java
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
 * @version 0.0.3 20171102
 * @since JDK8.0
 */
public class LedCustomerDisplay implements CustomerDisplay {
    private static final char[] CHANG = {0x1b, 0x73, 0x34};
    private static final char[] INIT = {0x1b, 0x40};
    private static final char[] OPEN_BOX = {0x02, 0x4d};
    private static final char[] CHARGE = {0x1b, 0x73, 0x33};
    private static final Pattern PATTERN = Pattern
            .compile("(GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
    private static final char[] PRICE = {0x1b, 0x73, 0x31};
    private static final char SHOW_END = 0x0d;
    private static final char[] SHOW_START = {0x1b, 0x51, 0x41};
    private static final char[] TOTAL = {0x1b, 0x73, 0x32};
    private static final char[] BRIGHT = {0x1F, 0x58};
    private static final char CLEAR = 0x0c;
    protected boolean open;
    protected Serial serial;
    protected String encoding;

    /**
     * @param serial
     * @param encoding be (GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16
     */
    public LedCustomerDisplay(Serial serial, String encoding) {
        this.serial = Objects.requireNonNull(serial, "serial is required");
        setEncoding(encoding);
    }

    /**
     * @param serial
     */
    public LedCustomerDisplay(Serial serial) {
        this(serial, "UTF-8");
    }

    @Override
    public boolean isLedSupport() {
        return true;
    }

    /**
     * @param encoding
     */
    private void setEncoding(String encoding) {
        if (encoding == null || !PATTERN.matcher(encoding).matches())
            throw new IllegalArgumentException(
                    "Encoding must be (GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
        this.encoding = encoding;
    }

    public void open() {
        try {
            if (!open) {
                serial.open();
                open = true;
            }
        } catch (NoSuchPortException e) {
            throw new RuntimeException(
                    "Comm port not init,customer display will not show", e);
        } catch (PortInUseException e) {
            throw new RuntimeException(
                    "Comm port has used,please restat", e);
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public boolean isOpen() {
        return false;
    }

    public void adjustBrightness(Bright level) {
        try {
            if (open) {
                serial.write(BRIGHT);
                serial.write(level.level());
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * clear all show
     */
    public void clear() {
        try {
            if (open) {
                serial.write(CLEAR);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close() {
        if (open) {
            serial.close();
            open = false;
        }
    }

    public void init() {
        try {
            if (open) {
                serial.write(INIT);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void openCashBox() {
        try {
            if (open) {
                serial.write(OPEN_BOX);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showChange(double chang) {
        try {
            if (open) {
                serial.write(CHANG);
                serial.write(SHOW_START);
                serial.write(String.valueOf(chang), encoding);
                serial.write(SHOW_END);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showCharge(double charge) {
        try {
            if (open) {
                serial.write(CHARGE);
                serial.write(SHOW_START);
                serial.write(String.valueOf(charge), encoding);
                serial.write(SHOW_END);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showTotal(double total) {
        try {
            if (open) {
                serial.write(TOTAL);
                serial.write(SHOW_START);
                serial.write(String.valueOf(total), encoding);
                serial.write(SHOW_END);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showUnitPrice(double price) {
        try {
            if (open) {
                serial.write(PRICE);
                serial.write(SHOW_START);
                serial.write(String.valueOf(price), encoding);
                serial.write(SHOW_END);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void execute(byte[] instruction) {
        try {
            if (open) {
                serial.write(instruction);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void execute(char[] instruction) {
        try {
            if (open) {
                serial.write(instruction);
                serial.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void demo() {
        try {
            showUnitPrice(7.45);
            Thread.sleep(1500);
            showTotal(51.57);
            Thread.sleep(1500);
            showCharge(60.00);
            Thread.sleep(1500);
            showChange(8.43);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
