package ca.qc.johnabbott.cs406.collections;

import java.util.Arrays;

/**
 * TODO
 */
public class SortedSet<T extends Comparable<T>> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 100;

    private T[] elements;
    private int size;

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.size = 0;
        this.elements = (T[]) new Comparable[capacity];

    }

    @Override
    public boolean contains(T elem) {
        // elements is sorted, so we can binary search for the element.
        return Arrays.binarySearch(elements, 0, size, elem) >= 0;
    }

    @Override
    public boolean containsAll(Set<T> rhs) {
        while(rhs.hasNext()){
            T tmp = rhs.next();
            if(Arrays.binarySearch(elements,tmp) < 0){  //return false the moment tmp is not found in the elements
                return false;
            }
        }
        return true;    //since it didnt go to false, it will return true once outside of the loop
    }

    @Override
    public boolean add(T elem) { //do binary sort and put it were its suppose to do
        if(isFull())
            throw new FullSetException();
        if (isEmpty()){
            elements
        }
        size++;
        for (int i = 0; i < size ; i++) {
            if (elem.compareTo(elements[i]) == -1){
                int high = i;
                int low = i-1;
                break;
            }
        }


    }

    @Override
    public boolean remove(T elem) {
        if(Arrays.binarySearch(elements,elem) >=0) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] == elem) {
                    elements[i] = null;
                    size--;
                    return true;
                }
            }
            return false;
        }
        else return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * TODO
     * @return
     */
    public T min() {
        if(!isEmpty()){
            return elements[0];
        }
        else
            return null;
    }

    /**
     * TODO
     * @return
     */
    public T max() {
        if(!isEmpty()){
            return elements[front];
        }
        else
            return null;
    }

    /**
     * TODO
     * @param low
     * @param high
     * @return
     */
    public SortedSet<T> subset(T low, T high) {
        throw new RuntimeException("Not implemented.");
    }


    @Override
    public boolean isFull() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public String toString() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public void reset() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public T next() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean hasNext() {
        throw new RuntimeException("Not implemented.");
    }
}
