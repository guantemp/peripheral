/*
 * @(#}ImageView.java
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
package cc.foxtail.peripheral.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 2014年10月10日
 * @since JDK6.0
 */
public class ImageView {
    private static final int BYTE_SIZE = Byte.SIZE;

    /**
     * Adjust picture width and height are multiples of 8
     *
     * @param img
     * @return
     */
    public static BufferedImage adjustImageHeightAndWidthOf8Multiples(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        if (w % BYTE_SIZE == 0 && h % BYTE_SIZE == 0)
            return img;
        if (w % BYTE_SIZE != 0)
            w = (BYTE_SIZE - w % BYTE_SIZE) + w;
        if (h % BYTE_SIZE != 0)
            h = (BYTE_SIZE - h % BYTE_SIZE) + h;
        BufferedImage dress = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = dress.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return dress;
    }

    private static byte[] dotToByte(int[] dots) {
        int len = dots.length;
        int shift = BYTE_SIZE - 1;
        int times = (len + shift) / BYTE_SIZE;
        byte[] result = new byte[times];
        for (int i = 0; i < times; i++) {
            int px = 0;
            for (int j = 0; j < 8; j++) {
                px |= dots[i * 8 + j] << (shift - j);
            }
            result[i] = (byte) ((~px | (1 << 31)) & 0xFF);
        }
        return result;
    }

    /**
     * Convenience method that returns a scaled instance of the provided
     * BufferedImage.
     *
     * @param img                 the original image to be scaled
     * @param targetWidth         the desired width of the scaled instance, in pixels
     * @param targetHeight        the desired height of the scaled instance, in pixels
     * @param hint                one of the rendering hints that corresponds to
     *                            RenderingHints.KEY_INTERPOLATION (e.g.
     *                            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,
     *                            RenderingHints.VALUE_INTERPOLATION_BILINEAR,
     *                            RenderingHints.VALUE_INTERPOLATION_BICUBIC)
     * @param progressiveBilinear if true, this method will use a multi-step scaling technique
     *                            that provides higher quality than the usual one-step technique
     *                            (only useful in down-scaling cases, where targetWidth or
     *                            targetHeight is smaller than the original dimensions)
     * @return a scaled version of the original BufferedImage
     */
    public static BufferedImage fasterScaled(BufferedImage img, int targetWidth, int targetHeight, Object hint) {
        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;
        int w, h, prevW, prevH;
        w = prevW = ret.getWidth();
        h = prevH = ret.getHeight();
        boolean isTranslucent = img.getTransparency() != Transparency.OPAQUE;

        // Use multi-step technique: start with original size, then
        // scale down in multiple passes with drawImage()
        // until the target size is reached
        do {
            if (w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }
            if (h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            if (scratchImage == null || isTranslucent) {
                // Use a single scratch buffer for all iterations
                // and then copy to the final, correctly-sized image
                // before returning
                scratchImage = new BufferedImage(w, h, type);
                g2 = scratchImage.createGraphics();
            }
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);
            prevW = w;
            prevH = h;

            ret = scratchImage;
        } while (w != targetWidth || h != targetHeight);

        if (g2 != null) {
            g2.dispose();
        }

        // If we used a scratch buffer that is larger than our target size,
        // create an image of the right size and copy the results into it
        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }

        return ret;
    }

    /**
     * 获取图片的分辨率
     *
     * @param path
     * @return
     */
    public static Dimension fastGetImageDimension(String path) {
        Dimension result = null;
        String suffix = getFileSuffix(path);
        // 解码具有给定后缀的文件
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        System.out.println(ImageIO.getImageReadersBySuffix(suffix));
        if (iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(new File(path));
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                result = new Dimension(width, height);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.dispose();
            }
        }
        return result;
    }

    /**
     * 获得图片的后缀名
     *
     * @param path
     * @return
     */
    private static String getFileSuffix(final String path) {
        String result = null;
        if (path != null) {
            result = "";
            if (path.lastIndexOf('.') != -1) {
                result = path.substring(path.lastIndexOf('.'));
                if (result.startsWith(".")) {
                    result = result.substring(1);
                }
            }
        }
        System.out.println("getFileSuffix:" + result);
        return result;
    }

    /**
     * @param img
     * @return
     */
    public static byte[] mappingImageToMiniPrinter(BufferedImage img) {
        if (img.getColorModel().getPixelSize() > 2)
            throw new RuntimeException("Image must be monochrome");
        int w = img.getWidth();
        int h = img.getHeight();
        if (w % BYTE_SIZE != 0 || h % BYTE_SIZE != 0)
            throw new RuntimeException("Image height and width must be of 8 multiples");
        int bs = h / BYTE_SIZE;
        WritableRaster raster = img.getRaster();
        int[] row = new int[h];
        byte[] result = new byte[w * bs];
        for (int i = raster.getMinX(), j = raster.getMinY(); i < w; i++) {
            Arrays.fill(row, 0xff);
            raster.getPixels(i, j, 1, h, row);
            System.arraycopy(dotToByte(row), 0, result, i * bs, bs);
        }
        return result;
    }

    /**Convert pictures to grayscale
     * @param img
     * @return
     */
    public static BufferedImage toMonochromeImage(BufferedImage img) {
        if (img.getColorModel().getPixelSize() < 2)
            return img;
        int h = img.getHeight();
        int w = img.getWidth();
        WritableRaster raster = img.getRaster();
        byte[] pixels = ((DataBufferByte) raster.getDataBuffer()).getData();
        for (int j = raster.getMinY(); j < h; j++) { // 扫描列 {
            for (int i = raster.getMinX(); i < w; i++) // 扫描行
            { // 由红，绿，蓝值得到灰度值
                int position = w * j + i;
                int gray = (int) (((pixels[position] >> 16) & 0xff) * 0.3);// red
                gray += (int) (((pixels[position] >> 8) & 0xff) * 0.6);// Green
                gray += (int) (((pixels[position]) & 0xff) * 0.1);// blue
                pixels[position] = (byte) ((255 << 24) | (gray << 16) | (gray << 8) | gray);
            }
        }
        BufferedImage monochrome = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = monochrome.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return monochrome;
    }
}
