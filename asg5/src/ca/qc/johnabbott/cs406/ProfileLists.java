package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.collections.list.ArrayList;
import ca.qc.johnabbott.cs406.collections.list.LinkedList;
import ca.qc.johnabbott.cs406.collections.list.List;
import ca.qc.johnabbott.cs406.generator.Generator;
import ca.qc.johnabbott.cs406.generator.SentenceGenerator;
import ca.qc.johnabbott.cs406.generator.WordGenerator;
import ca.qc.johnabbott.cs406.profiler.Profiler;

import java.util.Random;

public class ProfileLists {

    public static final Generator<String> STRING_GENERATOR;
    public static final Random RANDOM;
    public static final int SAMPLE_SIZE = 10000;

    static {
        RANDOM = new Random();
        STRING_GENERATOR = new SentenceGenerator(new WordGenerator("foo bar baz qux quux quuz corge grault garply waldo fred plugh xyzzy thud".split(" ")), 10);
    }

    public static void main(String[] args) throws InterruptedException {

        Profiler.getInstance().startSection("LinkedList -  Initialize with add(x)");
        listInitializeWithAppend(new LinkedList<>());
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("LinkedList -  Initialize with add(0,x)");
        listInitializeAddAt0(new LinkedList<>());
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("LinkedList -  Initialize with add(middle,x)");
        listInitializeAddAtMiddle(new LinkedList<>());
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("LinkedList -  Initialize with add(size-2,x)");
        listInitializeAddAtSizeMinus2(new LinkedList<>());
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("ArrayList -  Initialize with add(x)");
        listInitializeWithAppend(new ArrayList<>());
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("ArrayList -  Initialize with add(0,x)");
        listInitializeAddAt0(new ArrayList<>());
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("ArrayList -  Initialize with add(middle,x)");
        listInitializeAddAtMiddle(new ArrayList<>());
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("ArrayList -  Initialize with add(size-2,x)");
        listInitializeAddAtSizeMinus2(new ArrayList<>());
        Profiler.getInstance().endSection();

        List<String> llist = new LinkedList<>();
        for(int i=0; i<SAMPLE_SIZE; i++)
            llist.add(STRING_GENERATOR.generate(RANDOM));

        Profiler.getInstance().startSection("LinkedList - List Reverse Front To Back");
        listReverseFrontToBack(llist);
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("LinkedList - List Reverse Back To Front");
        listReverseBackToFront(llist);
        Profiler.getInstance().endSection();

        List<String> alist = new ArrayList<>();
        for(int i=0; i<SAMPLE_SIZE; i++)
            alist.add(STRING_GENERATOR.generate(RANDOM));

        Profiler.getInstance().startSection("ArrayList - List Reverse Front To Back");
        listReverseFrontToBack(alist);
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("ArrayList - List Reverse Back To Front");
        listReverseBackToFront(alist);
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("LinkedList - Max using for loop");
        listMaxUsingForLoop(llist);
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("LinkedList - Max using traversal");
        listMaxUsingTraversal(llist);
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("ArrayList - Max using for loop");
        listMaxUsingForLoop(alist);
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("ArrayList - Max using traversal");
        listMaxUsingTraversal(alist);
        Profiler.getInstance().endSection();
    }

    private static void listInitializeWithAppend(List<String> list) {
        for(int i = 0; i< SAMPLE_SIZE; i++)
            list.add(STRING_GENERATOR.generate(RANDOM));
    }

    private static void listInitializeAddAt0(List<String> list) {
        for(int i=0; i<SAMPLE_SIZE; i++)
            list.add(0, STRING_GENERATOR.generate(RANDOM));
    }

    private static void listInitializeAddAtMiddle(List<String> list) {
        for(int i=0; i<SAMPLE_SIZE; i++) {
            int pos = list.size() / 2;
            list.add(pos, STRING_GENERATOR.generate(RANDOM));
        }
    }

    private static void listInitializeAddAtSizeMinus2(List<String> list) {
        list.add("a");
        list.add("b");
        for(int i=2; i<SAMPLE_SIZE; i++) {
            int pos = list.size()-2;
            list.add(pos, STRING_GENERATOR.generate(RANDOM));
        }
    }

    private static void listReverseFrontToBack(List<String> list) {
        for(int i=0; i<SAMPLE_SIZE; i++)
            list.add(list.remove(0));
    }

    private static void listReverseBackToFront(List<String> list) {
        for(int i=0; i<SAMPLE_SIZE; i++)
            list.add(0, list.remove(list.size()-1));
    }

    private static String listMaxUsingForLoop(List<String> list) {
        if(list.size() == 0)
            throw new RuntimeException("List can't be empty");
        String max = list.get(0);
        for(int i=1; i<list.size(); i++) {
            String current = list.get(i);
            if(current.compareTo(max) > 0)
                max = current;
        }
        return max;
    }

    private static String listMaxUsingTraversal(List<String> list) {
        if(list.size() == 0)
            throw new RuntimeException("List can't be empty");
        list.reset();
        String max = list.next();
        while(list.hasNext()) {
            String current = list.next();
            if(current.compareTo(max) > 0)
                max = current;
        }
        return max;
    }





}
