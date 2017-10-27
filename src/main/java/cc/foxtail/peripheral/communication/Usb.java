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

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/***
 * @author <a href=
 *         "mailto:myis1000@126.com?subject=about%20cc.foxtail.peripheral.communication.Usb.java">guan
 *         xiangHuan</a>
 * @since JDK8.0
 * @version 0.0.1 builder 20170514
 */

public class Usb {
    private static long TIMEOUT=1000;

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


        //begian
        Device device = findDevice((short) 0x0416, (short) 0x5011);
        DeviceHandle handle = LibUsb.openDeviceWithVidPid(context, (short) 0x0416, (short) 0x5011);
        result = LibUsb.open(device, handle);
        if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to open USB device", result);
        try {
            // write(handle,"正常字体测试没有看见你".getBytes("GB2312"));
            // } catch (UnsupportedEncodingException e) {
            //     e.printStackTrace();
        } finally {
            LibUsb.close(handle);
        }
        System.out.println("device:" + device);
        // Deinitialize the libusb context
        LibUsb.exit(context);
        System.out.println("time：" + (System.currentTimeMillis() - start));
    }


    public static void write(DeviceHandle handle, byte[] data) {
        ByteBuffer buffer = BufferUtils.allocateByteBuffer(data.length);
        buffer.put(data);
        IntBuffer transferred = BufferUtils.allocateIntBuffer();
        int result = LibUsb.bulkTransfer(handle, (byte) 0x03, buffer,
                transferred, TIMEOUT);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to send data", result);
        }
        System.out.println(transferred.get() + " bytes sent to device");
    }

    public static Device findDevice(short vendorId, short productId) {
        // Create the libusb context
        Context context = new Context();

        // Initialize the libusb context
        LibUsb.init(context);
        // Read the USB device list
        DeviceList list = new DeviceList();
        int result = LibUsb.getDeviceList(context, list);
        if (result < 0) throw new LibUsbException("Unable to get device list", result);

        try {
            // Iterate over all devices and scan for the right one
            for (Device device : list) {
                DeviceDescriptor descriptor = new DeviceDescriptor();
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to read device descriptor", result);
                if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
            }
        } finally {
            // Ensure the allocated device list is freed
            LibUsb.freeDeviceList(list, true);
        }

        // Device not found
        return null;
    }
}
