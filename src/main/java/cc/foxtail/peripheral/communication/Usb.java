/*
 * Usb.java
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

import org.usb4java.*;

/***
 * @author <a href=
 *         "mailto:myis1000@126.com?subject=about%20cc.foxtail.peripheral.communication.Usb.java">guan
 *         xiangHuan</a>
 * @since JDK8.0
 * @version 0.0.1 builder 20170514
 */

public class Usb {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // Create the libusb context
        Context context = new Context();

        // Initialize the libusb context
        int result = LibUsb.init(context);
        if (result < 0) {
            throw new LibUsbException("Unable to initialize libusb", result);
        }

        // Read the USB device list
        DeviceList list = new DeviceList();
        result = LibUsb.getDeviceList(context, list);
        if (result < 0) {
            throw new LibUsbException("Unable to get device list", result);
        }

        try {
            // Iterate over all devices and list them
            for (Device device : list) {

                int address = LibUsb.getDeviceAddress(device);
                int busNumber = LibUsb.getBusNumber(device);
                DeviceDescriptor descriptor = new DeviceDescriptor();
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                if (result < 0) {
                    throw new LibUsbException("Unable to read device descriptor", result);
                }
                System.out.format("Bus %03d, Device %03d: Vendor %04x, Product %04x%n", busNumber, address,
                        descriptor.idVendor(), descriptor.idProduct());
                System.out.println(descriptor.toString());
            }
        } finally {
            // Ensure the allocated device list is freed
            LibUsb.freeDeviceList(list, true);
        }

        // Deinitialize the libusb context
        LibUsb.exit(context);
        System.out.println("time：" + (System.currentTimeMillis() - start));
    }
}
