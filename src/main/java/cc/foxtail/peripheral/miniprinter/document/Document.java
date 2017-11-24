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

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171124
 * @since JDK8.0
 */
public class Document {
    private List<Printable> printables = new ArrayList<>();

    /**
     * @return
     */
    public Iterator<Printable> printables() {
        return printables.iterator();
    }

    /**
     * @param os
     * @throws IOException
     */
    public void saveJson(OutputStream os) throws IOException {
        os = Objects.requireNonNull(os, "required os");
        JsonFactory factory = new JsonFactory();
        JsonGenerator generator = factory.createGenerator(os, JsonEncoding.UTF8);
        generator.writeStartObject();
        generator.writeEndObject();
        generator.close();
        os.close();
    }

    /**
     * @param is
     * @throws IOException
     */
    public void loadJson(InputStream is) throws IOException {
        is = Objects.requireNonNull(is, "required is");
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(is);
    }

    /**
     * @param printable
     * @return
     */
    public boolean add(Printable printable) {
        return printables.add(printable);
    }

    /**
     * @param printables
     * @return
     */
    public boolean addAll(Collection<Printable> printables) {
        return this.printables.addAll(printables);
    }
}
