package com.company;

import com.sun.net.httpserver.Filter;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        //TODO: Remove comment bellow to run the real program
        /*
        int n =Requirement("How many element in the chain");
        int m = Requirement("Number of element to count clockwise?");
        int o = Requirement("Number of element to count anti-clockwise?");
         */
        DonutLink(5,0,4);


    }

    private static class DoubleLink<T> {
        public T element;
        public DoubleLink<T> next;
        public DoubleLink<T> prev;
        public DoubleLink<T> head;

        public Stack<DoubleLink<T>> linkStack = new Stack();

        public DoubleLink() {
        }

        public DoubleLink(T element) {
            this.element = element;
        }

        public DoubleLink<T> build(int n) {
            if (n <= 0) throw new EmptyStackException();
            //DoubleLink<T> head;

            //build the first element
            head = new DoubleLink(1);
            linkStack.push(head);

            DoubleLink<T> current = head;
            for (int i = 2; i <= n; i++) {
                DoubleLink<T> tmp = current;
                current.next = new DoubleLink(i);
                current = current.next;
                current.prev = tmp;
                linkStack.push(current);
            }
            current.next = head;
            head.prev = current;

            return head;
        }

    }

    //Todo: Parse input... only accept positive integer
    static int Requirement(String s){
        Scanner userInput = new Scanner(System.in);
        System.out.println(s);
        int input = userInput.nextInt();
        return input;
    }

    static void DonutLink (int size, int clockwise, int antiClockwise){
        DoubleLink doubleLink = new DoubleLink();
        doubleLink.build(size);
        //DoubleLink head = doubleLink.build(size);
        DoubleLink tmp = doubleLink.head;
        DoubleLink tmpNext = new DoubleLink();
        DoubleLink tmpPrev = new DoubleLink();

        int sizeCounter = size;
        while(sizeCounter>0) {

            if(clockwise>0){
                for (int m = 0; m < clockwise-1; m++){
                    tmp=tmp.next;
                }
                tmpNext = tmp.next;
                tmpPrev = tmp.prev;
                //tmp dissapear completely
                tmpNext.prev = tmp.prev;
                tmpPrev.next = tmp.next;
                System.out.println(tmp.element.toString());
                sizeCounter--;
                if (sizeCounter<=0)
                    break;
                tmp = tmp.next;
            }
            if(antiClockwise>0) {
                for (int m = 0; m < antiClockwise - 1; m++) {
                    tmp = tmp.prev;
                }
                tmpNext = tmp.next;
                tmpPrev = tmp.prev;
                //tmp dissapear completely
                tmpNext.prev = tmp.prev;
                tmpPrev.next = tmp.next;
                System.out.println(tmp.element.toString());
                sizeCounter--;
                tmp = tmp.next;
            }
        }
    }
}