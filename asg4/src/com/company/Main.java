package com.company;

import com.sun.net.httpserver.Filter;

import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //get user input
        try{
            System.out.println("How many element in the chain");
            int n = scanner.nextInt();
            System.out.println("Number of element to count clockwise?");
            int m= scanner.nextInt();
            System.out.println("Number of element to count anti-clockwise?");
            int o = scanner.nextInt();

            System.out.println(donutLink(n,m,o));
        }
        catch (InputMismatchException exception){
            System.err.println("Error: Please enter a valid number (integer)");;
        }
    }

    //DoubleLink class
    private static class DoubleLink<T> {
        //field
        public T element;
        public DoubleLink<T> next;
        public DoubleLink<T> prev;
        public DoubleLink<T> head;


        //constructor
        public DoubleLink() {
        }

        public DoubleLink(T element) {
            this.element = element;
        }

        //build the Link method
        public DoubleLink<T> build(int n) {
            if (n <= 0) throw new InputMismatchException();
            //build the first element
            head = new DoubleLink(1);

            //build rest of doubleLink
            DoubleLink<T> current = head;
            for (int i = 2; i <= n; i++) {
                DoubleLink<T> tmp = current;
                current.next = new DoubleLink(i);
                current = current.next;
                current.prev = tmp;
            }

            //set .next and .prev for the first and last element
            current.next = head;
            head.prev = current;
            return head;
        }
    }

    //Donut Link/chain function
    static String donutLink (int size, int clockwise, int antiClockwise){
        if(clockwise+antiClockwise <= 0) throw new InputMismatchException(); //check if it will remove from at least one side

        String output = "";
        DoubleLink doubleLink = new DoubleLink();
        doubleLink.build(size);

        DoubleLink tmp = doubleLink.head;
        DoubleLink tmpNext;
        DoubleLink tmpPrev;

        int sizeCounter = size;

        System.out.println("n>"+size+"\nm>"+clockwise+"\no>"+antiClockwise); //output the user input

        //Main removing loop
        while(sizeCounter>0) {

            //Check clockwise
            if(clockwise>0){
                for (int m = 0; m < clockwise-1; m++){
                    tmp=tmp.next;
                }
                tmpNext = tmp.next;
                tmpPrev = tmp.prev;
                //tmp disappear completely
                tmpNext.prev = tmp.prev;
                tmpPrev.next = tmp.next;
                output = output + tmp.element.toString() + " ";
                sizeCounter--;
                if (sizeCounter<=0)
                    break;
                tmp = tmp.next;
            }
            //Check anticlockwise
            if(antiClockwise>0) {
                for (int m = 0; m < antiClockwise - 1; m++) {
                    tmp = tmp.prev;
                }
                tmpNext = tmp.next;
                tmpPrev = tmp.prev;
                //tmp disappear completely
                tmpNext.prev = tmp.prev;
                tmpPrev.next = tmp.next;
                output = output + tmp.element.toString() + " ";
                sizeCounter--;
                tmp = tmp.next;
            }
        }
        return output;
    }
}