/*
 * @(#}CustomerDisplayFactory.java
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

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171102
 * @since JDK8.0
 */
public class CustomerDisplayFactory {
    private static CustomerDisplay customerDisplay;
    private static Serial serial;

    public static synchronized CustomerDisplay createCustomerDisplay(Model model, Serial serial, String encoding) {
        switch (model) {
            case VTOP_VFD8C:
                return new VtopVfd8c(serial, encoding);
            default:
                return new LedCustomerDisplay(serial, encoding);
        }
    }

    public enum Model {
        LED8, VTOP_VFD8C, CITAQ_VFD220
    }
}
