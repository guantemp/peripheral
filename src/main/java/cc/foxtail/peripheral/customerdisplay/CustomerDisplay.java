/*
 * @(#}CustomerDisplay.java
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

import cc.foxtail.peripheral.Demo;
import cc.foxtail.peripheral.util.Align;

import java.util.Set;

/**
 * Connect customer display and displays status and data, for some pole display
 * will be able to display the product name and display advertising language(Supports two rows at most)
 * <p>
 *
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 2013年11月6日
 * @since JDK6.0
 */
public interface CustomerDisplay extends Demo {

    /**
     * @param level do nothing if not support
     */
    void adjustBrightness(Bright level);

    /**
     * clear all show
     */
    void clear();

    /**
     * close customer display
     */
    void close();

    /**
     *open customer display port
     */
    void open();

    /**
     * @return
     */
    boolean isOpen();

    /**
     * initialization
     */
    void init();

    /**
     * Open the cash box
     */
    void openCashBox();

    /**
     * Keep the change
     *
     * @param chang
     */
    void showChange(double chang);

    /**
     * Upper left corner of (0.0), the x-axis y-axis position display
     * information specified
     *
     * @param x
     * @param y_axis
     * @param align
     * @param info
     */
    void showInfo(int x, Set<Y_axis> y_axis, Align align, String info);

    /**
     * @param x
     * @param y_axis
     * @param info
     */
    void showInfo(int x, Set<Y_axis> y_axis, String info);

    /**
     * Actual receipts
     *
     * @param paid
     */
    void showPaid(double paid);

    /**
     * @param quantity
     */
    void showQuantity(double quantity);

    /**
     * Total
     *
     * @param total
     */
    void showTotal(double total);

    /**
     * Unit price
     *
     * @param price
     */
    void showUnitPrice(double price);

    /**
     * @return
     */
    boolean isSupportShowInfo();

    enum Bright {
       ONE((byte) 1), TWO((byte) 2), THRER((byte) 3), FOUR((byte) 4), FIVE((byte) 5);

        private byte level;

        Bright(byte level) {
            this.level = level;
        }

        public byte level() {
            return level;
        }
    }

    /**
     * Now supports up to 4 lines
     */
    enum Y_axis {
        ONE, TWO;
    }
}
