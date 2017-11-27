/*
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
package cc.foxtail.peripheral.miniprinter.document;

import java.util.Objects;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171125
 * @since JDK8.0
 */
public class Barcode implements Printable {
    private int height;
    private String barcode;
    private boolean hri;
    private int margin;
    private BarcodeType type;

    /**
     * @param type
     * @param barcode
     * @param height
     * @param margin
     * @param hri
     */
    public Barcode(BarcodeType type, String barcode, int height, int margin, boolean hri) {
        this.type = Objects.requireNonNull(type, "required type");
        this.barcode = Objects.requireNonNull(barcode, "required barcode");
        setHeight(height);
        setMargin(margin);
        this.hri = hri;
    }

    /**
     * @param type
     * @param barcode
     */
    public Barcode(BarcodeType type, String barcode) {
        this(type, barcode, 60, 6, true);
    }

    /**
     * @param type
     * @param barcode
     * @param hri
     */
    public Barcode(BarcodeType type, String barcode, boolean hri) {
        this(type, barcode, 60, 6, hri);
    }

    public int height() {
        return height;
    }

    /**
     * @param height
     */
    private void setHeight(int height) {
        if (height < 0 || height > 255)
            throw new IllegalArgumentException("height rang is (1-255)");
        this.height = height;
    }

    /**
     * @param margin
     */
    private void setMargin(int margin) {
        this.margin = margin;
    }

    public String barcode() {
        return barcode;
    }

    public boolean isHri() {
        return hri;
    }

    public int margin() {
        return margin;
    }

    public BarcodeType getType() {
        return type;
    }

    public enum BarcodeType {
        EAN13, EAN8, UPCA, UPCE, ITF, CODE39, CODE128
    }
}
