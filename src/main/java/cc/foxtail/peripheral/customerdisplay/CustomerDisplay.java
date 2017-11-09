/*
 * @(#}CustomerDisplay.java
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
 * @version 0.0.1 20171103
 * @since JDK8.0
 */
public interface CustomerDisplay extends Demo {
    default boolean isLedSupport() {
        return false;
    }

    default boolean isVoiceSupport() {
        return false;
    }

    default boolean isTwoLineCharacterSupport() {
        return false;
    }

    default boolean isFourLineCharacterSupport() {
        return false;
    }

    default void demo() {
        //do nothing
    }

    /**
     * @return true if port is open
     */
    boolean isOpen();

    /**
     * open port get output stream
     */
    void open();

    /**
     * init
     */
    void init();

    /**
     * clear show,set can position on one
     */
    void clear();

    /**
     * open cash box
     */
    void openCashBox();

    /**
     * open voice if support
     */
    default void onVoice() {
        //do nothing
    }

    /**
     * close voice if support
     */
    default void offVoice() {
        //do nothing
    }

    /**
     * @param chang
     */
    default void showChange(double chang) {//do nothing
    }

    /**
     * @param charge
     */
    default void showCharge(double charge) {
        //do nothing
    }

    /**
     * @param total
     */
    default void showTotal(double total) {
        //do nothing
    }

    /**
     * @param price
     */
    default void showUnitPrice(double price) {
        //do nothing
    }

    /**
     * Upper left corner of (0.0), the y-axis position display
     * information specified
     */
    default void show(Set<Y_axis> y_axis, Align align, String info) {
        //do nothing
    }

    /**
     * @param instruction extend
     */
    default void execute(byte[] instruction) {
        //do nothing
    }

    /**
     * @param instruction extend
     */
    default void execute(char[] instruction) {
        //do nothing
    }

    /**
     * close input strean and output stream
     */
    void close();

    /**
     * adjust moniter bright,do nothing if not support level
     *
     * @param level
     */
    void adjustBrightness(Bright level);

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
        ONE, TWO, THRER, FOUR;
    }
}
