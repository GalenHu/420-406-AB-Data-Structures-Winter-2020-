package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File file1 = new File("../test/data/test5/in1.txt");
        File file2 = new File("../test/data/test5/in2.txt");
        String output = "out.txt";
        Merge(file1, file2, output);
    }

    public static void Merge(File in1, File in2, String out) throws IOException{

        // Variable for the merge
        Scanner scanner1 = new Scanner(in1);    //scanner1 for in1
        Scanner scanner2 = new Scanner(in2);    //scanner2 for in2
        Log log1 = null;                        //log associate with scanner1
        Log log2 = null;                        //log associate with scanner1
        boolean firstTime = true;               //bool deciding if its the first time passing through the loop
        Log tmp = null;                         //tmp will represent the last printed line
        int count1 = 1;                         //line counter for in1
        int count2 = 1;                         //line counter for in2


        PrintWriter printWriter = new PrintWriter(out);

        //While both file have a next line
        while (scanner1.hasNextLine() && scanner2.hasNextLine()) {
            // if its the first time passing this loop, log1 and log2 have a value.
            if (firstTime) {
                try {
                    log1 = new Log(scanner1.nextLine());
                }
                catch (Exception e){
                    System.err.println(e.toString() + "on line: " + count1 + " file: " + in1.toString());
                }
                try {
                    log2 = new Log(scanner2.nextLine());
                }
                catch (Exception e){
                    System.err.println(e.toString() + "on line: " + count2 + " file: " + in2.toString());
                }
                count1++;
                count2++;

                firstTime = false;
            } else      //second time or more time passing though
                {
                if (log1.compareTo(log2) == -1 && scanner1.hasNextLine()) { //not first time passing, smallest and still have a line
                    try {
                        log1 = new Log(scanner1.nextLine());
                        count1++;
                    }
                    catch (Exception e){
                        System.err.println(e.toString() + "on line: " + count1 + " file: " + in1.toString());
                    }
                } else if (log2.compareTo(log1) == -1 && scanner2.hasNextLine()) {
                    try {
                        log2 = new Log(scanner2.nextLine());
                        count2++;
                    }
                    catch (Exception e){
                        System.err.println(e + "on line: " + count2 + " file: " + in2.toString());
                    }
                }
            }
            // if log1 is smaller, he get printed
            if (log1.compareTo(log2) == -1) {
                printWriter.println(log1.toString());
                tmp = log1;
                //if log 2 is smaller, he gets printed
            } else if (log2.compareTo(log1) == -1) {
                printWriter.println(log2.toString());
                tmp = log2;
            }
        }
        //It will go out when one of the file dont have any line left to output

        //while the first file still have line to scan
        while (scanner1.hasNextLine()) {
            try {
                log1 = new Log(scanner1.nextLine());
                count1++;
            }
            catch (Exception e){
                System.err.println(e.toString() + "on line: " + count1 + " file: " + in1.toString());
            }
            printWriter.println(log1.toString());
            tmp = log1;
        }

        //while the second file still have line to scan
        while (scanner2.hasNextLine()) {
            try {
                log2 = new Log(scanner2.nextLine());
                count2++;
            }
            catch (Exception e){
                System.err.println(e.toString() + "on line: " + count2 + " file: " + in2.toString());
            }
            printWriter.println(log2.toString());
            tmp = log2;
        }

        //because of .hasnextline, the last one will never get outputed
        //This will output the last one
        if (log1 == tmp && log2 != null)
            printWriter.println(log2.toString());
        else if (log2 == tmp && log1 != null)
            printWriter.println(log1.toString());

        //closing everything
        printWriter.close();
        scanner1.close();
        scanner2.close();
    }


}
