package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Objects;

/**
 * DESCRIPTION HERE
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 * @since 2018-04-29
 */
public class Grade implements Serializable{

    public static final byte SERIAL_ID = 0x24;
    private java.lang.String name;
    private int result;
    private Date date;

    public Grade(java.lang.String name, int result, Date date) {
        this.name = name;
        this.result = result;
        this.date = date;
    }

    public Grade() {
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public java.lang.String toString() {
        return "Grade{" +
                "name='" + name + '\'' +
                ", result=" + result +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return result == grade.result &&
                Objects.equals(name, grade.name) &&
                Objects.equals(date, grade.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, result, date);
    }


    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        // to serialize the name
        serializer.write(name.length());
        for (char c : name.toCharArray())
            serializer.write((byte)c);

        //to serialize the result
        serializer.write(result);

        //to serialize the date
        ca.qc.johnabbott.cs406.serialization.util.Date tmpDate = new ca.qc.johnabbott.cs406.serialization.util.Date(date);
        serializer.write(tmpDate);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {

        // to get the name
        long length = serializer.readInt();
        char[] tmp = new char[(int) length];
        for (int i = 0; i < length ; i++)
            tmp[i] = (char) serializer.read();
        name = new String(tmp);

        //to get the result
        result = serializer.readInt();

        //to get the date
        long time = serializer.readLong();
        date = new java.util.Date(time);
    }
}
