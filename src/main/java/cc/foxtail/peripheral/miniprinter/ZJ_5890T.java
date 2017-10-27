/*
 * @(#}ZJ_5890T.java
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
package cc.foxtail.peripheral.miniprinter;

import cc.foxtail.peripheral.communication.Parallel;
import cc.foxtail.peripheral.util.Align;
import cc.foxtail.peripheral.util.ImageView;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 2014年10月9日
 * @since JDK6.0
 */
public class ZJ_5890T extends MiniPrinterAdapter {
    private static final Pattern PATTERN = Pattern
            .compile("(GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16");
    private final String encoding;
    private BufferedOutputStream bos;
    private Parallel parallel;

    public ZJ_5890T(Parallel parallel) {
        this(parallel, "GB2312");
    }

    public ZJ_5890T(Parallel parallel, String encoding) {
        if (parallel == null)
            throw new IllegalArgumentException("Parallel port can not be NULL");
        this.parallel = parallel;
        if (encoding == null || !PATTERN.matcher(encoding).matches())
            throw new IllegalArgumentException(
                    "Encoding is not GB2312|BIG5|UTF-8|GBK|UTF-16");
        this.encoding = encoding;
        try {
            bos = new BufferedOutputStream(parallel.openOutputStream());
        } catch (NoSuchPortException | PortInUseException | IOException e) {
            close();
            e.printStackTrace();
        }
    }

    @Override
    public void print(String s) {
        try {
            bos.write(s.getBytes(encoding));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void compositionLine(String s) {
        try {
            bos.write(s.getBytes(encoding));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void compositionLine(String s, int offset, Set<PrintMode> style) {
        try {
            if (style.contains(PrintMode.QUADRUPLE))
                bos.write(new byte[]{0x1b, 0x21, 0x30});
            bos.write(new byte[]{0x1b, 0x24, (byte) (offset % 256),
                    (byte) (offset / 256)});
            bos.write(s.getBytes(encoding));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void compositionLine(String s, Align align, Set<PrintMode> style) {

    }

    @Override
    public void print(String s, int offset, Set<PrintMode> style) {
        try {
            if (style.contains(PrintMode.QUADRUPLE))
                bos.write(new byte[]{0x1b, 0x21, 0x30});
            bos.write(new byte[]{0x1b, 0x24, (byte) (offset % 256),
                    (byte) (offset / 256)});
            bos.write(s.getBytes(encoding));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (bos != null)
            try {
                bos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        parallel.close();
    }

    @Override
    public void print(DividingLine line) {
        try {
            bos.write(new byte[]{0x1b, 0x2a, 0x00, (byte) 0x80, 0x01});
            byte[] ds = new byte[384];
            Arrays.fill(ds, (byte) 0x18);
            bos.write(ds);
            bos.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void downloadBitmapToFlash(BufferedImage img) {
        int maxWidth = (int) maxFlashBitmapSize().getWidth();
        int maxHeight = (int) maxFlashBitmapSize().getHeight();
        if (img.getWidth() > maxWidth || img.getHeight() > maxHeight)
            throw new IllegalArgumentException("Max bitmap size is " + maxWidth
                    + " × " + maxHeight);
        img = ImageView.toMonochromeImage(img);
        img = ImageView.adjustImageHeightAndWidthOf8Multiples(img);
        byte[] pixels = ImageView.mappingImageToMiniPrinter(img);
        int w = img.getWidth() / 8;
        int h = img.getHeight() / 8;
        try {
            bos.write(new byte[]{0x1c, 0x71, 0x01, (byte) (w % 256),
                    (byte) (w / 256), (byte) (h % 256), (byte) (h / 256)});
            bos.write(pixels);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isSupportFlashBitmap() {
        return true;
    }

    @Override
    public Dimension maxFlashBitmapSize() {
        return new Dimension(384, 600);
    }

    @Override
    public String name() {
        return "ZJ_5890T";
    }

    @Override
    public void openCashBox() {
        try {
            bos.write(new byte[]{0x1b, 0x70, 0x00, 0x05, 0x05, 0x1b, 0x70,
                    0x01, 0x05, 0x05});
            bos.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void printBitmapInFlash(int id) {
        try {
            bos.write(new byte[]{0x1b, 0x61, 0x01, 0x1c, 0x70, 0x00, 0x00,
                    0x1b, 0x61, 0x00});
            bos.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void rest() {
        try {
            bos.write(new byte[]{0x1b, 0x40});
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
