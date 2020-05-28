package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;

public class Product implements Serializable{
    public static final byte SERIAL_ID = 0x20;
    private long productId;
    private String description;
    private double pricePerUnit;

    public Product(){

    }

    public Product(long productId, String description, double pricePerUnit){
        this.productId = productId;
        this.description = description;
        this.pricePerUnit = pricePerUnit;
    }

    public long getProductId() {return productId;}

    public void setProductId(long productId) { this.productId = productId ;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.pricePerUnit = pricePerUnit;}

    @Override
    public java.lang.String toString(){
        return "Product{" +
                "productId=" + productId +
                ", description' " + description + '\''  +
                ", pricePerUnit=" + pricePerUnit +
                '}';
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(productId);
        serializer.write(pricePerUnit);
        // before
        serializer.write(description.length());
        for (char c : description.toCharArray())
            serializer.write((byte)c);
        // after
//        ca.qc.johnabbott.cs406.serialization.util.String tmp = new ca.qc.johnabbott.cs406.serialization.util.String(description);
//        serializer.write(tmp);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        productId = serializer.readLong();
        pricePerUnit = serializer.readDouble();
        // before
        int length = serializer.readInt();
        char[] tmp = new char[length];
        for (int i = 0; i < length ; i++)
            tmp[i] = (char) serializer.read();
        description = new String(tmp);
        //after
//        ca.qc.johnabbott.cs406.serialization.util.String tmp = (ca.qc.johnabbott.cs406.serialization.util.String) serializer.readSerializable();
//        description = tmp.get();
    }
}
