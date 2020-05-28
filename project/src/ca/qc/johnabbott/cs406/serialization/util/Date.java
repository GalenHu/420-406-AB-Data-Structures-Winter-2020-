package ca.qc.johnabbott.cs406.serialization.util;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;
import java.text.DateFormat;

public class Date implements Serializable {

    public static final byte SERIAL_ID = 0x23;
    private java.util.Date value;
    private long fastTime;

    public Date(java.util.Date value) {
        this.value = value;
        fastTime = this.value.getTime();
    }

    public Date() {
    }

    public java.util.Date get() {
        return value;
    }

    public void set(java.util.Date value) {
        this.value = value;
    }

    @Override
    public java.lang.String toString() {
        return "Date{" +
                "value=" + value +
                '}';
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(fastTime);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        fastTime = serializer.readLong();
        value = new java.util.Date(fastTime);
    }
}
