package cc.foxtail.peripheral.communication;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;
import java.util.TooManyListenersException;

public class SerialReader implements Observer {
    public static void main(String[] args) throws PortInUseException, IOException, NoSuchPortException, TooManyListenersException, UnsupportedCommOperationException, InterruptedException {
        Serial serial = new Serial("COM1", 9600, 8, 1, 0, 500, 120);
        SerialReader r1 = new SerialReader();
        serial.addObserver(r1);
        serial.open();
        System.out.println("begin");
        Thread.sleep(30000);
        serial.close();
        System.out.println("close");
        Thread.sleep(10000);
        System.out.println("end");
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
        try {
            System.out.println("I get " + new String((byte[]) arg, "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
