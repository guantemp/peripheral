/*
 * Bluetooth.java
 *
 * Copyright 2017 www.foxtail.cc rights Reserved.
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


import javax.bluetooth.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/***
 * @author <a href=
 *         "mailto:myis1000@126.com?subject=about%20com.bulepos.peripheral.util.Bluetooth.java">guan
 *         xiangHuan</a>
 * @since JDK8.0
 * @version 0.0.1 builder 20170415
 */
public class Bluetooth {
    public static String[] listBluetooth() throws BluetoothStateException, InterruptedException {
        final Object inquiryCompletedEvent = new Object();
        Map<String, String> devicesDiscovered = new HashMap<String, String>();
        DiscoveryListener listener = new DiscoveryListener() {

            @Override
            public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
                //devicesDiscovered.put(key, value)

            }

            @Override
            public void inquiryCompleted(int arg0) {
                synchronized (inquiryCompletedEvent) {
                    inquiryCompletedEvent.notifyAll();
                }
            }

            @Override
            public void serviceSearchCompleted(int arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
                // TODO Auto-generated method stub

            }

        };
        synchronized (inquiryCompletedEvent) {
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC,
                    listener);
            if (started) {
                inquiryCompletedEvent.wait();
            }
        }
        return null;
    }

    public static final Vector<RemoteDevice> devicesDiscovered = new Vector();

    public static void main(String[] args) throws IOException, InterruptedException {

        final Object inquiryCompletedEvent = new Object();

        devicesDiscovered.clear();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
                devicesDiscovered.addElement(btDevice);
                try {
                    System.out.println("     name " + btDevice.getFriendlyName(false));
                } catch (IOException cantGetDeviceName) {
                }
            }

            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");
                synchronized (inquiryCompletedEvent) {
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized (inquiryCompletedEvent) {
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC,
                    listener);
            if (started) {
                System.out.println("wait for device inquiry to complete...");
                inquiryCompletedEvent.wait();
                System.out.println(devicesDiscovered.size() + " device(s) found");
                for (RemoteDevice btDevice : devicesDiscovered.toArray(new RemoteDevice[0]))
                    System.out.println(btDevice.getFriendlyName(false));
            }
        }
    }
}
