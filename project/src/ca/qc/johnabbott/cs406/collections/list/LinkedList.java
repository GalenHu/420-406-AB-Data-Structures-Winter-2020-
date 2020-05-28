/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections.list;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * An implementation of the List interface using unidirectional links, forming a "chain".
 *
 * @author Ian Clement
 */
public class LinkedList<T extends Serializable> implements List<T>, Serializable {

    public static final byte SERIAL_ID = 0x27;

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(this.size);
        Link<T> current = this.head;
        while (current != this.last){
            serializer.write(current.element);
            current = current.next;
        }
        serializer.write(this.last.element);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
    this.size = serializer.readInt();
    Link current = this.head = new Link(serializer.readSerializable());
        for (int i = 1; i < this.size ; i++) {
            current.next = new Link(serializer.readSerializable());

            current = current.next;

            if (i == this.size-1)
                this.last = current;
        }
    }

    @Override
    public String toString() {
        return "LinkedList{" +
                "head=" + head +
                ", last=" + last +
                ", size=" + size +
                '}';
    }

    /* private inner class for link "chains" */
    private static class Link<T> {
        T element;
        Link<T> next;
        public Link(T element) {
            this.element = element;
        }
        public Link() {}
    }

    /**
     * Move a reference `i` links along the chain. Returns null if `i` exceeds chain length.
     * @param i number of links to move.
     * @return the reference to the link after `i` moves.
     */
    private Link<T> move(int i) {
        // move traversal forward i times.
        Link<T> current = head;
        for(int j=0; j<i && current != null; j++)
            current = current.next;
        return current;
    }

    /* a list is represented with a head "dummy" node to simplify the
     * add/remove operation implementation. */
    private Link<T> head;

    /* a last reference is used to make list append operations
     *     add(x),
     *     add(size(), x)
     * more efficient */
    private Link<T> last;

    private int size;

    public LinkedList() {
        // create a "dummy" link, representing an empty list
        last = head = null;
        size = 0;
    }

    @Override
    public void add(T element) {
        if(head == null)
            head = last = new Link<>(element);
        else {
            // add a new link at the end of the list, put last accordingly
            last.next = new Link<>(element);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(int position, T element) {
        if(position < 0 || position > size)
            throw new ListBoundsException();
        
        // when "appending" call the add(x) method
        if(position == size) {
            add(element);
            return;
        }

        if(position == 0) {
            Link<T> tmp = head;
            head = new Link<>(element);
            head.next = tmp;
        }
        else {
            // move a link reference to the desired position (point to link "position")
            Link<T> current = move(position - 1);

            // place new link between "position" and "position + 1"
            Link<T> tmp = new Link<T>(element);
            tmp.next = current.next;
            current.next = tmp;
        }
        size++;
    }

    @Override
    public T remove(int position) {
        if(position < 0 || position >= size)
            throw new ListBoundsException();

        T element;

        if(position == 0) {
            element = head.element;
            head = head.next;
            if(head.next == null)
                last = head;
        }
        else {
            // move a link pointer to the desired position (point to link "position")
            Link<T> current = move(position - 1);

            element = current.next.element;
            current.next = current.next.next;

            // reset the last if we're removing the last link
            if (current.next == null)
                last = current;
        }
        size--;
        
        return element;
    }

    @Override
    public void clear() {
        head = last = null; // remove all the links
        size = 0;
    }

    @Override
    public T get(int position){
        if(position < 0 || position >= size)
            throw new ListBoundsException();

        // move a link pointer to the desired position (point to link "position")
        Link<T> link = move(position);
        return link.element;
    }

    @Override
    public T set(int position, T element){
        if(position < 0 || position >= size)
            throw new ListBoundsException();

        // move a link pointer to the desired position (point to link "position")
        Link<T> current = move(position);
        T ret = current.element;
        current.element = element;
        return ret;
    }

    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean contains(T element){
        // simple linear search
        Link<T> current = head;
        while(current != null) {
            if(current.element.equals(element))
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    @Override
    public List<T> subList(int from, int to) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean remove(T element) {
        throw new RuntimeException("not implemented");
    }

    /**
     * An iterator class for the link chain
     */
    private class ListIterator implements Iterator<T> {

        // stores the current link in the traversal.
        private Link<T> traversal;

        public ListIterator() {
            traversal = head;
        }

        @Override
        public boolean hasNext() {
            return traversal != null;
        }

        @Override
        public T next() {
            T tmp = traversal.element;
            traversal = traversal.next;
            return tmp;
        }

        @Override
        public void remove() {
            throw new RuntimeException("Not implemented");
        }
    }

}


     
      
