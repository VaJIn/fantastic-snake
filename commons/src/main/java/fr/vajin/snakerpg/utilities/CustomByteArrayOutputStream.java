package fr.vajin.snakerpg.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CustomByteArrayOutputStream extends ByteArrayOutputStream {

    public CustomByteArrayOutputStream() {
        super();
    }

    public CustomByteArrayOutputStream(int i) {
        super(i);
    }

    public void writeInt(int value) throws IOException {

        write(new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value}
        );
    }
}
