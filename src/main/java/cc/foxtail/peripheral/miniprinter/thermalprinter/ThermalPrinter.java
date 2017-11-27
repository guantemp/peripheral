package cc.foxtail.peripheral.miniprinter.thermalprinter;

import cc.foxtail.peripheral.miniprinter.MiniPrinter;
import cc.foxtail.peripheral.miniprinter.document.Document;

import java.util.Observable;

public class ThermalPrinter implements MiniPrinter {
    private String encoding;

    @Override
    public void print(Document document) {

    }

    @Override
    public void execute(byte[] instruction) {

    }

    @Override
    public void execute(char[] instruction) {

    }

    @Override
    public void demo() {

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
}
