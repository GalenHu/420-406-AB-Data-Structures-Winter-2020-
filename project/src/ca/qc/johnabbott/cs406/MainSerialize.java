package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.collections.Either;
import ca.qc.johnabbott.cs406.collections.list.LinkedList;
import ca.qc.johnabbott.cs406.collections.list.List;
import ca.qc.johnabbott.cs406.collections.map.HashMap;
import ca.qc.johnabbott.cs406.collections.set.TreeSet;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs406.serialization.util.Date;
import ca.qc.johnabbott.cs406.serialization.util.Integer;
import ca.qc.johnabbott.cs406.serialization.util.String;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Serialization example.
 */
public class MainSerialize {


    public static void main(java.lang.String arg[]) throws IOException, SerializationException {

        BufferedChannel channel = new BufferedChannel(new RandomAccessFile("foo.bin", "rw").getChannel(), BufferedChannel.Mode.WRITE);

        Serializer serializer = new Serializer(null, channel);

        //Product
//        Product product = new Product(123L, "foo", 3.99);
//        serializer.write(product);

        //Tuple
//        Tuple<Integer, String> tuple = new Tuple<>(new Integer(123), new String("abc"));
//        serializer.write(tuple);

        //Starting code

        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(String.SERIAL_ID, String::new);
        serializer.register(Box.SERIAL_ID, Box::new);

        Integer i = new Integer(1234);
        String s = new String("hi,");
        Box<String> bs = new Box<>(new String("there."));

        serializer.write(i);
        serializer.write(s);
        serializer.write(bs);

        //IPAdress

        IPAddress ip = new IPAddress("192.168.2.1");
        serializer.write(ip);

        //Date

        Date date = new Date(new java.util.Date());
        serializer.write(date);


        //Grade

        serializer.register(Grade.SERIAL_ID, Grade::new);
        Grade grade = new Grade("John", 90, new java.util.Date());
        serializer.write(grade);
//

        //Either

        serializer.register(Either.LeftEither.SERIAL_ID, Either.LeftEither::new);
        serializer.register(Either.RightEither.SERIAL_ID, Either.LeftEither::new);
        Either.LeftEither leftEither = new Either.LeftEither<>(new Integer(1));
        Either.RightEither rightEither = new Either.RightEither<>(new Integer(2));
        serializer.write(leftEither);
        serializer.write(rightEither);

        //LinkedList
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Integer(5));
        linkedList.add(new Integer(14));
        serializer.write(linkedList);

        //Hashmap and TreeSet not working (not done)
        //HashMap
//        HashMap hashMap = new HashMap();

        //TreeSet
//        TreeSet treeSet = new TreeSet();
        serializer.close();

        //channel.close();
    }
}
