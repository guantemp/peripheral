/*
 * @(#}ImageViewTest.java
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

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 2014年10月13日
 * @since JDK6.0
 */
public class ImageViewTest {

    @Test
    public void testFasterScaled() {

    }

    @Test
    public void testGetImageDimension() {

    }

    @Test
    public void testToMonochromeImage() throws IOException {
        /*
        BufferedImage img = ImageIO.read(new File("d:/mvn/hp-logo.jpg"));
        ImageIO.write(ImageView.toMonochromeImage(img), "bmp", new File(
                "d:/mvn/hp-logo.bmp"));
                */
    }

    @Test
    public void testMappingImageToMiniPrinter() throws Exception {
        /*
        BufferedImage img = ImageIO.read(new File("d:/mvn/look.bmp"));
        img = ImageView.adjustImageHeightAndWidthOf8Multiples(img);
        ImageView.mappingImageToMiniPrinter(img);
        */
    }
}
