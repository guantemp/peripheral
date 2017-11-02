package cc.foxtail.peripheral.customerdisplay;

import cc.foxtail.peripheral.communication.Serial;

import java.io.IOException;
import java.util.Locale;

public class VtopVfd8c extends LedCustomerDisplay implements VoiceCustomerPlay {
    /**
     * @param serial
     * @param encoding be (GB|gb)2312|(BIG|big)5|(UTF|utf)-8|(GBK|gbk)|(UTF|utf)-16
     */
    public VtopVfd8c(Serial serial, String encoding) {
        super(serial, encoding);
    }

    @Override
    public void on() {
        try {
            if (open) {
                serial.write(new char[]{0x1f, 0x23, 0x31, 0x30, 0x31, 0x33, 0x32, 0x33});
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void off() {
        try {
            if (open) {
                serial.write(new char[]{0x1f, 0x23, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30});
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extend() {
        try {
            if (open) {
                serial.write(new char[]{0x1f, 0x5f, 0x31, 0x31});
                Locale locale = Locale.getDefault();
                if (locale == Locale.CHINA || locale == Locale.CHINESE || locale == Locale.PRC) {
                    serial.write(new char[]{0x1f, 0x73, 0x31});
                } else if (locale == Locale.US) {
                    serial.write(new char[]{0x1f, 0x73, 0x32});
                } else if (locale == Locale.FRANCE || locale == Locale.GERMANY) {
                    serial.write(new char[]{0x1f, 0x73, 0x33});
                } else {
                    serial.write(new char[]{0x1f, 0x73, 0x30});
                }
                serial.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
