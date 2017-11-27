package cc.foxtail.peripheral.miniprinter.thermalprinter;

import cc.foxtail.peripheral.miniprinter.document.Barcode;
import cc.foxtail.peripheral.miniprinter.handle.Handle;

import java.util.Objects;

public class BarcodeHandle extends Handle<Barcode> {
    private Barcode barcode;
    public BarcodeHandle(Barcode barcode){
        this.barcode= Objects.requireNonNull(barcode,"required barcode");
    }
    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
