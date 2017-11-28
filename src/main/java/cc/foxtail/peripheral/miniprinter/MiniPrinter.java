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
package cc.foxtail.peripheral.miniprinter;

import cc.foxtail.peripheral.Demo;
import cc.foxtail.peripheral.communication.Communication;
import cc.foxtail.peripheral.miniprinter.document.Document;
import cc.foxtail.peripheral.miniprinter.document.Printable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.3 20171126
 * @since JDK8.0
 */
public abstract class MiniPrinter implements Demo, Observer {
    private Communication comm;
    protected String encoding = "gbk";
    protected int pixel;

    public void conectionTo(Communication comm) {
        this.comm = Objects.requireNonNull(comm, "required comm");
    }

    public void registerHandle(){

    }

    public boolean isConectioned() {
        return comm == null ? false : true;
    }

    public void print(Document document) {
        if (isConectioned()) {
            try {
                comm.open();
                for (Iterator<Printable> iter = document.iterator(); iter.hasNext(); ) {
                }
                comm.flush();
                comm.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void execute(byte[] instruction) {
        if (isConectioned()) {
            try {
                comm.open();
                comm.write(instruction);
                comm.flush();
                comm.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void execute(char[] instruction) {
        try {
            execute(String.valueOf(instruction).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    public void demo() {
        if (isConectioned()) {
            print(Document.DEMO);
        }
    }
}
