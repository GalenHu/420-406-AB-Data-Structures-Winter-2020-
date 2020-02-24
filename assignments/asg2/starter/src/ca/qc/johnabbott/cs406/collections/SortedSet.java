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
    private boolean modified;

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.size = 0;
        this.elements = (T[]) new Comparable[capacity];
        this.front = 0;
        this.modified = false;
    }

    @Override
    public boolean contains(T elem) {
        // elements is sorted, so we can binary search for the element.
        return Arrays.binarySearch(elements, 0, size, elem) >= 0;
    }

    @Override
    public boolean containsAll(Set<T> rhs) {
        System.out.println(rhs.toString());
        System.out.println(this.toString());
        if (rhs.isEmpty() && !this.isEmpty())
            return true;
        if(rhs.isEmpty() && this.isEmpty())
            return true;
        if(this.isEmpty() && !rhs.isEmpty())
            return false;

        rhs.reset();
        for (int i = 0; i < rhs.size(); i++){
            if(rhs.hasNext()){
                T tmp = rhs.next();
                if(!this.contains(tmp))
                    return false;
            }
        }
        return true;    //since it didnt go to false, it will return true once outside of the loop
    }

    @Override
    public boolean add(T elem) {
        if(isFull() && Arrays.binarySearch(elements, 0, size, elem) >= 0)
            return false;
        if(isFull())
            throw new FullSetException();

        if(isEmpty()){
            elements[0] = elem;
            size++;
            modified = true;
            return true;
        }


        //Main loop

        int tmp = 0;

        for (int i = 0; i < size; i++) {
            if (elem.compareTo(elements[i]) == 0)
                return false;
            if(elem.compareTo(elements[i]) == 1){
                tmp = i+1;
            }
        }
        size++;
        System.out.println(size);
        for (int i = size-1; i > tmp ; i--) {
            elements[i] = elements[i-1];
        }
        //if(modified == false) throw new TraversalException();
        elements[tmp] = elem;
        modified = true;
        return true;
    }

    @Override
    public boolean remove(T elem) {
        int tmp = Arrays.binarySearch(elements, 0, size, elem);
        if(tmp >= 0) {
            for (int i = tmp; i < size; i++) {
                    elements[i] = elements[i+1];
            }
            size--;
            //if(modified == false) throw new TraversalException();
            modified = true;
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == elements.length;
    }

    @Override
    public String toString(){
        String set = "";
        for (int i = 0; i < size; i++) {
            if (i < size-1)
                set += elements[i] + ", ";
            else
                set += elements[i];
        }
        return "{"+set+"}";
    }

    //

    public T min() {
    if (isEmpty())
        throw new EmptySetException();
    return elements[0];
    }

    public T max() {
    if (isEmpty())
        throw new EmptySetException();
        System.out.println(size);
    return elements[size-1];
    }

    /**
     * TODO
     * @param low
     * @param high
     * @return
     */

    //I assume low and high are integer, otherwise there is a infinity number of element between low and high
    public SortedSet<T> subset(T low, T high) {
        System.out.println("low: " + low);
        System.out.println("high: " + high);

        System.out.println(this.toString());

        if (low.compareTo(high) == 1)
            throw new IllegalArgumentException();
        SortedSet<T> sortedSet = new SortedSet<T>();
        // Find the index of Low
        int lowIndex = 0;
        int highIndex = size;

        //search for lowIndex
        for (int i = 0; i < size ; i++) {
            if (elements[i].compareTo(low) >= 0){
                lowIndex = i;
                break;
            }
            else {
                lowIndex = size+1;     //the lowest value in low is higher than everything else
            }
        }

        //search for highIndex
        for (int i = lowIndex; i < size ; i++) {
            if (elements[i].compareTo(high) >= 0){
                highIndex = i;
                break;
            }
        }

        for (int i = lowIndex; i < highIndex ; i++) {
            sortedSet.add(elements[i]);
        }

        System.out.println(highIndex);
        System.out.println(lowIndex);
        return sortedSet;
    }

    @Override
    public void reset() {
        modified = false;
        front = 0;
    }

    @Override
    public T next() {
        if (!hasNext() || modified)
            throw new TraversalException();
        return elements[front++];

    }

    @Override
    public boolean hasNext() {
        return front < size;
    }
}
