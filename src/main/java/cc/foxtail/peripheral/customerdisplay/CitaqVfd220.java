/*
 * @(#}CitaqVfd220.java
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
 * @version 0.0.2 20171102
 * @since JDK8.0
 */
public class CitaqVfd220 implements CharacterCustomerDispaly {
    private static final Pattern PATTERN = Pattern
            .compile("(GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
    private static final char[] OPEN_BOX = {0x02, 0x4d};
    private static final char[] INIT = {0x02, 0x43, 0x31};
    private static final char[] BRIGHT = {0x1b, 0x2a};
    private static final char[] CLEAR = {0x0c};
    private Serial serial;
    private String encoding;
    private boolean open;

    public CitaqVfd220(Serial serial) {
        this(serial, "UTF-8");
    }

    public CitaqVfd220(Serial serial, String encoding) {
        this.serial = Objects.requireNonNull(serial, "serial is required");
        setEncoding(encoding);
    }

    @Override
    public void init() {
        try {
            if (open) {
                serial.write(INIT);
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void adjustBrightness(Bright level) {
        try {
            if (open) {
                serial.write(BRIGHT);
                serial.write(level.level());
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void clear() {
        try {
            if (open) {
                serial.write(CLEAR);
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void close() {
        if (open) {
            serial.close();
            open = false;
        }
    }

    /**
     *
     */
    @Override
    public void open() {
        if (!open) {
            try {
                serial.open();
                open = true;
            } catch (NoSuchPortException e) {
                e.printStackTrace();
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
    }

    /**
     * open
     */
    @Override
    public void openCashBox() {
        try {
            if (open) {
                serial.write(OPEN_BOX);
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show(Set<Y_axis> y_axis, Align align, String info) {

    }

    /**
     * @param y_axis
     * @param info
     */
    @Override
    public void show(Set<Y_axis> y_axis, String info) {

    }

    private void setEncoding(String encoding) {
        if (encoding == null || !PATTERN.matcher(encoding).matches())
            throw new IllegalArgumentException(
                    "Encoding must be (GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
        this.encoding = encoding;
    }
}
