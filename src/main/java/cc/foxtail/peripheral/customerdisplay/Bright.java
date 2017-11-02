package cc.foxtail.peripheral.customerdisplay;

public enum Bright {
    ONE((byte) 1), TWO((byte) 2), THRER((byte) 3), FOUR((byte) 4), FIVE((byte) 5);

    private byte level;

    Bright(byte level) {
        this.level = level;
    }

    public byte level() {
        return level;
    }
}
