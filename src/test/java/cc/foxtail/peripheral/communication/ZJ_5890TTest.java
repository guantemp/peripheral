/*
 * @(#}ZJ_5890TTest.java
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
package cc.foxtail.peripheral.communication;

import cc.foxtail.peripheral.miniprinter.ZJ_5890T;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TooManyListenersException;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 2014年10月14日
 * @since JDK6.0
 */
public class ZJ_5890TTest {

    @Test
    public void testDownloadBitmapToFlash() throws IOException, NoSuchPortException, PortInUseException, TooManyListenersException {
        Parallel parallel = new Parallel("LPT2", 500);
        ZJ_5890T zj = null;
        try {
            zj = new ZJ_5890T(parallel, "gbk");
            BufferedImage image = ImageIO.read(new File("d:/mvn/look.bmp"));
            zj.downloadBitmapToFlash(image);
        } finally {
            if (zj != null)
                zj.close();
        }
    }

    @Test
    public void testPrintBitmapInFlash() throws NoSuchPortException, PortInUseException, TooManyListenersException {
        Parallel parallel = new Parallel("LPT2", 500);
        ZJ_5890T zj = new ZJ_5890T(parallel, "gbk");
        zj.printBitmapInFlash(1);
        zj.close();
    }

    @Test
    public void testStringPrint() throws IOException, NoSuchPortException, PortInUseException, TooManyListenersException {
        Parallel parallel = new Parallel("LPT2", 500);
        ZJ_5890T zj = new ZJ_5890T(parallel, "GB2312");
        //zj.textOut("字体不对吗", Align.CENTER, EnumSet.of(FontStyle.QUADRUPLE))
        //.textOut("正常字体测试没有看见你")
        //.textOut("我在偏移", 236, EnumSet.noneOf(FontStyle.class)).wrap(2);
        //zj.print();
        zj.close();
    }
}
