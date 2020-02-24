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
        this.elements = (T[]) new Comparable[capacity];
        this.front = 0;
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

        if (Arrays.binarySearch(elements,0,size,elem) >= 0) //if element is inside, return false
            return false;

        if(isEmpty()){
            elements[0] = elem;
            size++;
            return true;
        }

        int tmp = 0;

        for (int i = 0; i < size; i++) {
            if(elem.compareTo(elements[i]) > 0){
                tmp = i;
            }
        }
        size++;
        for (int i = size; i > tmp ; i--) {
            elements[i] = elements[i-1];
        }

        elements[tmp] = elem;
        return true;
    }

    @Override
    public boolean remove(T elem) {
        int tmp = Arrays.binarySearch(elements,elem);
        if(tmp >= 0) {
            for (int i = tmp; i < size; i++) {
                    elements[i] = elements[i+1];
            }
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
        return size == DEFAULT_CAPACITY;
    }

    @Override
    public String toString(){
        String set = "";
        for (T element: elements) {
            set.concat(element + ", ");
        }
        return set;
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
    return elements[size];
    }

    /**
     * TODO
     * @param low
     * @param high
     * @return
     */

    //I assume low and high are integer, otherwise there is a infinity number of element between low and high
    public SortedSet<T> subset(T low, T high) {
        if (low.compareTo(high) == 1)
            throw new IndexOutOfBoundsException();
        SortedSet<T> sortedSet = new SortedSet<T>();
        // Find the index of Low
        int left = 0;
        int right = size;
        int mid = right/2;
        int lowIndex = 0;
        int highIndex = 100;

        for (int i = 0; i < size ; i++) {
            if (elements[i].compareTo(low) >= 0){
                lowIndex = i;
                break;
            }
            else {
                lowIndex = size+1;     //the lowest value in low is higher than everything else
                return sortedSet;
            }
        }
        //lowest index is found

        for (int i = lowIndex; i < size ; i++) {
            if (elements[i].compareTo(high) > 0){
                highIndex = i;
                break;
            }
        }

        for (int i = lowIndex; i < highIndex ; i++) {
            sortedSet.add(elements[i]);
        }
        return sortedSet;
    }

    @Override
    public void reset() {
        front = 0;
    }

    @Override
    public T next() {
        if (!this.hasNext())
            throw new RuntimeException();
        front++;
        return elements[front];
    }

    @Override
    public boolean hasNext() {
        return front != size;
    }
}
