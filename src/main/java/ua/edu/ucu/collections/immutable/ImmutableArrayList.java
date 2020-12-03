package ua.edu.ucu.collections.immutable;


import java.util.Arrays;


public class ImmutableArrayList implements ImmutableList {
    private Object[] immutableArray;
    private int arraySize = 0;

    public ImmutableArrayList() {
        immutableArray = new Object[arraySize];
    }

    public ImmutableArrayList(int size) {
        immutableArray = new Object[size];
        arraySize = size;
    }

    // helper functions
    private ImmutableArrayList getModifiedCopy(int buffer) {
        ImmutableArrayList copyArrayList =
                new ImmutableArrayList(arraySize + buffer);

        System.arraycopy(immutableArray, 0,
                copyArrayList.toArray(), 0, arraySize);
        return copyArrayList;
    }

    private ImmutableArrayList addGeneral(int index, Object e) {
        if (e == null) {
            throw new NullPointerException("Can not add null to ImmutableList");
        }

        ImmutableArrayList newImmutableArray =
                new ImmutableArrayList(size() + 1);

        System.arraycopy(immutableArray, 0, newImmutableArray.toArray(),
                0, index);
        newImmutableArray.toArray()[index] = e;

        if (index != size()) {
            System.arraycopy(immutableArray, index, newImmutableArray.toArray(),
                    index + 1, size() - (index + 1));
        }

        return newImmutableArray;
    }

    @Override
    public ImmutableArrayList add(Object e) {
        return addGeneral(size(), e);
    }

    /** This function works like extend function in python, so
     * it put on special position value, and values after this position
     * replace on one element next*/
    @Override
    public ImmutableArrayList add(int index, Object e) {
        if (index < 0 || index >= arraySize) {
            throw new IndexOutOfBoundsException();
        }

        return addGeneral(index, e);
    }

    private ImmutableArrayList addAllGeneral(int index, Object[] c) {
        if (c == null) {
            throw new NullPointerException("Can not add null to ImmutableList");
        }

        if (c.length == 0) {
            throw new IllegalArgumentException("input array is empty");
        }

        ImmutableArrayList newImmutableArray =
                new ImmutableArrayList(size() + c.length);

        System.arraycopy(immutableArray, 0, newImmutableArray.toArray(),
                0, index);

        System.arraycopy(c, 0, newImmutableArray.toArray(),
                index, c.length);

        if (index != size()) {
            System.arraycopy(immutableArray, index, newImmutableArray.toArray(),
                    index + c.length, size() - index);
        }
        return newImmutableArray;
    }

    @Override
    public ImmutableArrayList addAll(Object[] c) {
        return addAllGeneral(size(), c);
    }

    /** This function works like extend function in python, so
     * it put on special position values from input array,
     * and values after this position
     * replace on a length of input array next*/
    @Override
    public ImmutableArrayList addAll(int index, Object[] c) {
        if (index < 0 || index >= arraySize) {
            throw new IndexOutOfBoundsException();
        }

        return addAllGeneral(index, c);
    }

    public Object get(int index) {
        if (index < 0 || index >= arraySize) {
            throw new IndexOutOfBoundsException();
        }
        return immutableArray[index];
    }

    @Override
    public ImmutableList remove(int index) {
        if (index < 0 || index >= arraySize) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableList newImmutableArray = new ImmutableArrayList(size() - 1);
        System.arraycopy(immutableArray, 0,
                newImmutableArray.toArray(), 0, index);
        System.arraycopy(immutableArray, index + 1,
                newImmutableArray.toArray(), index,
                size() - (index + 1));
        return newImmutableArray;
    }

    @Override
    public ImmutableList set(int index, Object e) {
        if (index < 0 || index >= arraySize) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableList newImmutableArray = getModifiedCopy(0);
        newImmutableArray.toArray()[index] = e;
        return newImmutableArray;
    }

    public int indexOf(Object e) {
        int itemPosition = -1;
        for (int i = 0; i < size(); i++) {
            if (this.toArray()[i].equals(e)) {
                itemPosition = i;
                break;
            }
        }
        return itemPosition;
    }


    public int size() {
        return arraySize;
    }


    @Override
    public ImmutableArrayList clear() {
        return new ImmutableArrayList();
    }


    public boolean isEmpty() {
        return arraySize == 0;
    }


    public Object[] toArray() {
        return immutableArray;
    }


    public String toString() {
        return Arrays.toString(this.toArray());
    }
}
