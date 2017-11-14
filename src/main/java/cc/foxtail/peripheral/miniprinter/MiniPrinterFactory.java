/*
 * @(#}MiniPrinterFactory.java
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
package cc.foxtail.peripheral.miniprinter;

import cc.foxtail.assistant.util.UrlHelper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171114
 */
public class MiniPrinterFactory {
    private static final Map<String, MiniPrinter> cache = new WeakHashMap();
    private static final String content = UrlHelper.getAbsolutePath(MiniPrinterFactory.class.getResource("/").toExternalForm().replace('/', File.separatorChar), ".." + File.separatorChar + ".." + File.separatorChar + "conf" + File.separatorChar + "context.json");
    private static URL url;

    static {
        try {
            url = new URL(content);
        } catch (MalformedURLException e) {
            throw new RuntimeException("not find file context.json");
        }
    }

    public static MiniPrinter[] getInstance() {
        System.out.println(content);
        return new MiniPrinter[0];
    }

    public static void main(String[] args) throws MalformedURLException {
        System.out.println(content);
        System.out.println(MiniPrinterFactory.class.getResource("/").toExternalForm().replace('/', File.separatorChar));
        String url = UrlHelper.getAbsolutePath(MiniPrinterFactory.class.getResource("/").toExternalForm().replace('/', File.separatorChar), "..\\..\\conf\\context.json");
        System.out.println(new URL(url));

    }
}
