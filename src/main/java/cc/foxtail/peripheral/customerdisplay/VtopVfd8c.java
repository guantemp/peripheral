/*
 * @(#}VtopVfd8c.java
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

import java.io.IOException;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171103
 * @since JDK8.0
 */
public class VtopVfd8c extends LedCustomerDisplay {
    /**
     * @param serial
     * @param encoding be (GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16
     */
    public VtopVfd8c(Serial serial, String encoding) {
        super(serial, encoding);
    }

    @Override
    public boolean isVoiceSupport() {
        return true;
    }

    @Override
    public void onVoice() {
        try {
            if (open && isVoiceSupport()) {
                serial.write(new char[]{0x1f, 0x23, 0x31, 0x30, 0x31, 0x33, 0x32, 0x33});
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void offVoice() {
        try {
            if (open && isVoiceSupport()) {
                serial.write(new char[]{0x1f, 0x23, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30});
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
