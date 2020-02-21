package ca.qc.johnabbott.cs406.collections;

import java.util.Arrays;

/**
 * TODO
 */
public class SortedSet<T extends Comparable<T>> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 100;

    private T[] elements;
    private int size;
    private int front;

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.size = 0;
        this.front = 0;
        this.elements = (T[]) new Comparable[capacity];

    }

    @Override
    public boolean contains(T elem) {
        // elements is sorted, so we can binary search for the element.
        return Arrays.binarySearch(elements, 0, size, elem) >= 0;
    }

    @Override
    public boolean containsAll(Set<T> rhs) {
        boolean[] myContains = new boolean[elements.length];
        int index = 0;
        int count = 0;
        int trueCount = 0;

        for (T item:elements) {
            count++;
            if(rhs.contains(item)){
                myContains[index++] = true;
            }
            else
                myContains[index++] = false;
        }
        for (int i = 0; i < myContains.length ; i++) {
            if (myContains[i] == true){
                trueCount++;
            }
        }
        if(count == trueCount)
            return true;
        else
            return false;
    }

    @Override
    public boolean add(T elem) {
        T tmp;
        if(Arrays.binarySearch(elements,elem) >=0)
            return false;
        if (!isFull()){
            elements[front++] = elem;
            size++;
            return true;
        }
        else
            return false;
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
        throw new RuntimeException("Not implemented.");
    }

    /**
     * TODO
     * @return
     */
    public T max() {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * TODO
     * @param first
     * @param last
     * @return
     */
    public SortedSet<T> subset(T first, T last) {
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
