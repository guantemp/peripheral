package cc.foxtail.peripheral;

import cc.foxtail.peripheral.communication.Serial;
import cc.foxtail.peripheral.customerdisplay.CustomerDisplay;
import cc.foxtail.peripheral.customerdisplay.LedCustomerDisplay;
import gnu.io.*;

import java.io.*;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
       System.out.println((byte)0x32);

        System.out.println("Execute time:"
                + (System.currentTimeMillis() - start));
    }
}
