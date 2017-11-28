package cc.foxtail.peripheral.miniprinter.thermalprinter;

import cc.foxtail.peripheral.miniprinter.document.Barcode;
import cc.foxtail.peripheral.miniprinter.Handle;


public class BarcodeHandle extends Handle<Barcode> {

    @Override
    public byte[] toBytes() {
        switch(get().type()){
            case EAN13:
                System.out.println("ena13");
                break;
            default:
        }
        return new byte[0];
    }
}
