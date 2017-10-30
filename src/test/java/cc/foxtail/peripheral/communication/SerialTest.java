package cc.foxtail.peripheral.communication;

import org.junit.Test;

public class SerialTest {
    @Test
    public void listAllSerialPort() throws Exception {
        String[] com = Serial.listAllSerialPort();
        for (String s : com)
            System.out.println(s);
    }

    @Test
    public void listAvailableSerialPort() throws Exception {
        String[] com = Serial.listAvailableSerialPort();
        for (String s : com)
            System.out.println(s);
    }

    @Test
    public void isOpen() throws Exception {
    }

    @Test
    public void close() throws Exception {
    }

    @Test
    public void open() throws Exception {
    }

    @Test
    public void write() throws Exception {
    }

    @Test
    public void write1() throws Exception {
    }

    @Test
    public void write2() throws Exception {
    }

}