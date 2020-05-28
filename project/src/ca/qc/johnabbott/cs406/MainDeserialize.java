package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.collections.Either;
import ca.qc.johnabbott.cs406.collections.list.LinkedList;
import ca.qc.johnabbott.cs406.collections.map.HashMap;
import ca.qc.johnabbott.cs406.collections.set.TreeSet;
import ca.qc.johnabbott.cs406.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.util.Date;
import ca.qc.johnabbott.cs406.serialization.util.Integer;
import ca.qc.johnabbott.cs406.serialization.util.String;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Deserialize example.
 */
public class MainDeserialize {
    public static void main(java.lang.String arg[]) throws IOException, SerializationException {


        Serializer serializer = new Serializer(
                new BufferedChannel(
                        new RandomAccessFile("foo.bin", "rw").getChannel()
                        , BufferedChannel.Mode.READ
                ), null);

        //Product
//        serializer.register(Product.SERIAL_ID, Product::new);
//        serializer.register(String.SERIAL_ID, String::new);
//
//        Product p = (Product) serializer.readSerializable();
//        System.out.println(p);

        //Tuple
//        serializer.register(Integer.SERIAL_ID, Integer::new);
//        serializer.register(String.SERIAL_ID, String::new);
//        serializer.register(Tuple.SERIAL_ID, Tuple::new);
//
//        Tuple<Integer, String> tuple = (Tuple<Integer, String>) serializer.readSerializable();
//        System.out.println(tuple);

        //Starting code
//        serializer.register(Integer.SERIAL_ID, Integer::new);
//        serializer.register(String.SERIAL_ID, String::new);
//        serializer.register(Box.SERIAL_ID, Box::new);
//
//        Integer i = (Integer) serializer.readSerializable();
//        String s = (String) serializer.readSerializable();
//        Box<String> bs = (Box<String>) serializer.readSerializable();
//
//        System.out.println(i);
//        System.out.println(s);
//        System.out.println(bs);

        //IP address
//        serializer.register(IPAddress.SERIAL_ID, IPAddress::new);
//        IPAddress ip = (IPAddress) serializer.readSerializable();
//        System.out.println(ip);
//
        //Date
//        serializer.register(Date.SERIAL_ID, Date::new);
//        Date date = (Date) serializer.readSerializable();
//        System.out.println(date);


        //Grade
//        serializer.register(Grade.SERIAL_ID, Grade::new);
//        Grade grade = (Grade) serializer.readSerializable();
//        System.out.println(grade);

        //Either
//        serializer.register(Either.LeftEither.SERIAL_ID, Either.LeftEither::new);
//        serializer.register(Either.LeftEither.SERIAL_ID, Either.RightEither::new);
//        serializer.register(Either.LeftEither.SERIAL_ID, Either.LeftEither::new);
//        serializer.register(Either.RightEither.SERIAL_ID, Either.RightEither::new);

        //Linked List
        serializer.register(LinkedList.SERIAL_ID, LinkedList::new);
        LinkedList linkedList = (LinkedList) serializer.readSerializable();
        serializer.write(linkedList);

        //HashMap
//        serializer.register(HashMap.SERIAL_ID, HashMap::new);
//        HashMap hashMap = (HashMap) serializer.readSerializable();
//
//        //TreeSet
//        serializer.register(TreeSet.SERIAL_ID, TreeSet::new);
//        TreeSet treeSet = (TreeSet) serializer.readSerializable();

        serializer.close();
    }
}
