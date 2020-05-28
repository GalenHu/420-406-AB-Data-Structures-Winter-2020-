/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.serialization.util;

import ca.qc.johnabbott.cs406.serialization.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * A serializable wrapper class for longs.
 */
public class Long implements Serializable, Comparable<Long> {

    public static final byte SERIAL_ID = 0x02;

    // optimization, use a static ByteBuffer to avoid extra allocations on each (de)serialize operation.
    private static final ByteBuffer buffer;

    // initialize a static field in a static initialization block.
    static {
        byte[] bytes = new byte[java.lang.Long.BYTES];
        buffer = ByteBuffer.wrap(bytes);
    }

    /**
     * Write a long primitive to the serializer.
     * @param value The long primitive to write.
     * @param serializer The serializer to write to.
     * @throws IOException
     */
    public static void write(long value, Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // place the long
        buffer.putLong(value);
        serializer.write(buffer.array());
    }

    /**
     * Read a long primitive from the serializer.
     * @param serializer The serializer to read from.
     * @return The long read from the serializer.
     * @throws IOException
     */
    public static long read(Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // extract the backing byte[] and read into it.
        byte[] bytes = buffer.array();
        serializer.read(bytes);

        // read the long value from the ByteBuffer,
        // since the backing byte[] now has the data.
        return buffer.getLong();
    }
    
    // store the wrapped long value.
    private long value;

    /**
     * Serializable 0 value.
     */
    public Long() {
        value = 0L;
    }

    /**
     * Custom serializable value.
     * @param value
     */
    public Long(long value) {
        this.value = value;
    }

    /**
     * Get the value of the serializable long.
     * @return
     */
    public long get() {
        return value;
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // place the long
        buffer.putLong(value);
        serializer.write(buffer.array());
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException {

        // clear the buffer in case of previous use.
        buffer.clear();

        // extract the backing byte[] and read into it.
        byte[] bytes = buffer.array();
        serializer.read(bytes);

        // read the long value from the ByteBuffer,
        // since the backing byte[] now has the data.
        value = buffer.getLong();

    }

    @Override
    public java.lang.String toString() {
        return java.lang.String.valueOf(value);
    }

    @Override
    public int compareTo(Long rhs) {
        long tmp = this.value - rhs.value;
        if(tmp < 0)
            return -1;
        else if(tmp > 0)
            return 1;
        else
            return 0;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Long rhs = (Long) o;
        return value == rhs.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


}
