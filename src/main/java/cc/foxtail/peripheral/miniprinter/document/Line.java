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
 * @version 0.0.1 20171124
 * @since JDK8.0
 */
public class Line implements Printable {
    public static final Line DASHED=new Line('-');
    public static final Line BLANK=new Line(' ');
    private char symbol;


    /**
     * @param symbol
     */
    public Line(char symbol) {
        this.symbol = Objects.requireNonNull(symbol, "required symbol");
    }

    public char symbol() {
        return symbol;
    }
}