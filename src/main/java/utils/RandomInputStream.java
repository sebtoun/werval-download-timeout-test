package utils;

/*
 * Copyright WizTools.org
 * Licensed under the Apache License, Version 2.0:
 * http://www.apache.org/licenses/LICENSE-2.0
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Originally written by Elliotte Rusty Harold for the book Java I/O 2nd edition.
 * @author subwiz
 * modified by sebastien andary
 */
public class RandomInputStream extends InputStream {

    private Random generator = new Random();
    private boolean closed = false;
    private int amountRead = 0;
    private int totalSize = Integer.MAX_VALUE;

    public RandomInputStream() {
        this( Integer.MAX_VALUE );
    }

    public RandomInputStream( int size ) {
        this.totalSize = size;
    }

    @Override
    public int read() throws IOException {
        checkOpen();
        if ( amountRead >= totalSize ) {
            return -1;
        }
        int result = generator.nextInt() % 256;
        if (result < 0) {
            result = -result;
        }
        amountRead += 1;
        return result;
    }

    @Override
    public int read(byte[] data, int offset, int length) throws IOException {
        checkOpen();
        if ( amountRead >= totalSize ) {
            return -1;
        }
        length = Math.min( length, totalSize - amountRead );
        byte[] temp = new byte[length];
        generator.nextBytes(temp);
        System.arraycopy(temp, 0, data, offset, length);
        amountRead += length;
        return length;
    }

    @Override
    public int read(byte[] data) throws IOException {
        return this.read(data, 0, data.length);
    }

    @Override
    public long skip(long bytesToSkip) throws IOException {
        checkOpen();

        bytesToSkip = Math.min( bytesToSkip, totalSize - amountRead );
        amountRead += bytesToSkip;

        // It's all random so skipping has no effect.
        return bytesToSkip;
    }

    @Override
    public void close() {
        this.closed = true;
    }

    private void checkOpen() throws IOException {
        if (closed) {
            throw new IOException("Input stream closed");
        }
    }

    @Override
    public int available() {
        return totalSize - amountRead;
    }
}


