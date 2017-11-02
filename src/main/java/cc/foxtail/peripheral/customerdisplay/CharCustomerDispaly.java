/*
 * @(#}CharCustomerDisplay.java
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

import cc.foxtail.peripheral.Demo;
import cc.foxtail.peripheral.util.Align;

import java.util.Set;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171102
 * @since JDK8.0
 */
public interface CharCustomerDispaly extends Demo {
    @Override
    default void demo() {
        try {
            Thread.sleep(1500);

            // showInfo(0, EnumSet.of(Y_axis.ONE, Y_axis.TWO), Align.CENTER,
            //        "德芙黑浓巧克力");
            // showInfo(0, EnumSet.of(Y_axis.ONE), "数量：1.52");
            // showInfo(125, EnumSet.of(Y_axis.ONE),
            //        "单价：115.73");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * set customer diaplay init(such as.brighted,cover,dispoad can,clear)
     */
    void init();

    /**
     * @param level do nothing if not support
     */
    void adjustBrightness(Bright level);

    /**
     *
     */
    void clear();

    /**
     *
     */
    void close();

    /**
     *
     */
    void open();

    /**
     * open
     */
    void openCashBox();

    /**
     * Upper left corner of (0.0), the y-axis position display
     * information specified
     */
    void show(Set<Y_axis> y_axis, Align align, String info);

    /**
     * @param y_axis
     * @param info
     */
    void show(Set<Y_axis> y_axis, String info);

    /**
     * Now supports up to 4 lines
     */
    enum Y_axis {
        ONE, TWO, THRER, FOUR;
    }


    /**
     *
     */
    enum ShowModel {
        COVER, VERTICALSCROLL, HORIZONTALSCROLL
    }
}
