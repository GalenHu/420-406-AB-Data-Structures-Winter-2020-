package ca.qc.johnabbott.cs406.collections;

import java.util.Arrays;

/**
 * TODO
 */
public class SortedSet<T extends Comparable<T>> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 100;

    private T[] elements;
    private int size;
    private int front;          //use for traversal
    private boolean modified;   //identify if array has been modified in traversal

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.size = 0;
        this.elements = (T[]) new Comparable[capacity];
        this.front = 0;
        this.modified = false;
    }

    //Look if the element is contain in the set
    @Override
    public boolean contains(T elem) {
        // elements is sorted, so we can binary search for the element.
        return Arrays.binarySearch(elements, 0, size, elem) >= 0;
    }

    // all the element on the right side is in the set
    @Override
    public boolean containsAll(Set<T> rhs) {
        if (rhs.isEmpty() && !this.isEmpty())   //rhs is empty
            return true;
        if(rhs.isEmpty() && this.isEmpty())     //both sets are empty
            return true;
        if(this.isEmpty() && !rhs.isEmpty())    //current set is empty, but rhs set has element
            return false;

        rhs.reset();                            //reset traversal pointer
        for (int i = 0; i < rhs.size(); i++){
            if(rhs.hasNext()){
                T tmp = rhs.next();
                if(!this.contains(tmp))
                    return false;
            }
        }
        return true;    //since it didnt go to false, it will return true once outside of the loop
    }

    //Add a new element to the set
    @Override
    public boolean add(T elem) {
        if(isFull() && Arrays.binarySearch(elements, 0, size, elem) >= 0)   //element found, return false to avoid duplicate
            return false;
        if(isFull())                //set is full, cannot add new element
            throw new FullSetException();

        if(isEmpty()){              //if its empty, add the element and return
            elements[0] = elem;
            size++;
            modified = true;
            return true;
        }


        //Main loop

        int tmp = 0;
        // loop to get the index where element is suppose to be place
        for (int i = 0; i < size; i++) {
            if (elem.compareTo(elements[i]) == 0)
                return false;
            if(elem.compareTo(elements[i]) == 1){
                tmp = i+1;
            }
        }

        //move every element by one to the right
        size++;
        for (int i = size-1; i > tmp ; i--) {
            elements[i] = elements[i-1];
        }
        elements[tmp] = elem;   //element is place properly
        modified = true;
        return true;
    }

    @Override
    public boolean remove(T elem) {
        int tmp = Arrays.binarySearch(elements, 0, size, elem);
        if(tmp >= 0) {              //if tmp is smaller than 0, this mean the removed element doesnt exist in the set
            for (int i = tmp; i < size; i++) {
                    elements[i] = elements[i+1];    //move elements to the left
            }
            size--;
            //if(modified == false) throw new TraversalException();
            modified = true;
            return true;
        }
        return false;
    }

    //return the size of the set
    @Override
    public int size() {
        return size;
    }

    //return true if the set is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    //return true if the set if full
    @Override
    public boolean isFull() {
        return size == elements.length;
    }

    //return the set of element as string
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        boolean first = true;
        for (int i = 0; i < size; i++) {
            if (first)
                first = false;
            else
                stringBuilder.append(", ");
            stringBuilder.append(elements[i]);
        }
        stringBuilder.append('}');

        return stringBuilder.toString();
    }


    //return the smallest element of the set
    //since the set is sorted, we can assume the first element is the smallest
    public T min() {
    if (isEmpty())
        throw new EmptySetException();
    return elements[0];
    }

    //return the largest element of the set
    //since the set is sorted, we can assume the last element is the largest
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

    //get the element between low and high
    public SortedSet<T> subset(T low, T high) {
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

        //attach the element inside of the limit in the new sorted set
        for (int i = lowIndex; i < highIndex ; i++) {
            sortedSet.add(elements[i]);
        }
        return sortedSet;
    }

    //reset the pointer to zero
    //element inside is untouch
    //modify become false, since its reseted.
    @Override
    public void reset() {
        modified = false;
        front = 0;
    }

    //get the next element of the set according to the pointer of the traversal
    @Override
    public T next() {
        if (!hasNext() || modified)
            throw new TraversalException();
        return elements[front++];

    }

    //check if there is an element in front of the pointer of the traversal
    @Override
    public boolean hasNext() {
        return front < size;
    }
}
