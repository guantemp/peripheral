package cc.foxtail.peripheral.communication;

import java.io.IOException;
import java.util.Observer;

public interface Communication extends Observer {
    void open() throws IOException;

    void write(byte[] bytes) throws IOException;

    void write(byte[] bytes, int start, int length) throws IOException;

    void flush() throws IOException;

    int read(byte[] bytes) throws IOException;

    void close() throws IOException;
}
