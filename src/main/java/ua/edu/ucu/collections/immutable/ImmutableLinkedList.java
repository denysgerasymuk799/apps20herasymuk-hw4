package ua.edu.ucu.collections.immutable;

import java.util.Arrays;

public class ImmutableLinkedList implements ImmutableList {
    private int listSize = 0;
    private Node head;
    private Node tail;

    public ImmutableLinkedList() {
        head = null;
        tail = null;
    }

    public ImmutableLinkedList(Object val) {
        head = new Node(val);
        tail = null;
    }

    // helper functions
    private static class Node {
        private Object value;
        private Node next;
        private Node prev;

        public Node(Node node) {
            value = node.value;
            next = node.next;
            prev = node.prev;
        }

        public Node(Object value) {
            this.value = value;
        }
    }

    private ImmutableLinkedList getCopy() {
        ImmutableLinkedList linkedList = new ImmutableLinkedList();
        linkedList.listSize = listSize;

        if (size() == 1) {
            linkedList.head = new Node(head);
        }
        if (size() > 1) {
            linkedList.head = new Node(head);
            Node curNode = head.next;
            Node prevNode = linkedList.head;
            Node newNode;
            while (curNode.next != null) {
                newNode = new Node(curNode);

                newNode.prev = prevNode;
                prevNode.next = newNode;
                curNode = curNode.next;

                prevNode = newNode;
            }
            linkedList.tail = new Node(curNode);
            linkedList.tail.prev = prevNode;
        }

        return linkedList;
    }


    private ImmutableLinkedList addGeneral(int index, Object e) {
        if (e == null) {
            throw new NullPointerException("Can not add null to ImmutableList");
        }

        ImmutableLinkedList newLinkedList = getCopy();
        if (index == 0) {
            if (newLinkedList.head == null) {
                newLinkedList.head = new Node(e);
            } else {
                // in case if newLinkedList has elements after head
                Node newNode = new Node(e);
                newNode.next = newLinkedList.head;
                newLinkedList.head = newNode;
            }
        }
        else {
            Node newNode = new Node(e);
            insertIndexNode(index, newNode, newLinkedList);
        }
        newLinkedList.listSize += 1;

        return newLinkedList;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return addGeneral(listSize, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        if (index < 0 || index >= listSize) {
            throw new IndexOutOfBoundsException();
        }

        return addGeneral(index, e);
    }

    private Node getBeforeIndexNode(int index,
                                    ImmutableLinkedList newLinkedList) {
        Node curNode = newLinkedList.head;
        int curIndex = index;
        curIndex -= 1;
        while (curIndex > 0) {
            curNode = curNode.next;
            curIndex -= 1;
        }

        return curNode;
    }

    private void insertIndexNode(int index, Node newNode,
                                 ImmutableLinkedList newLinkedList) {
        Node curNode = getBeforeIndexNode(index, newLinkedList);
        if (curNode.next != null) {
            // if input Node has a chain of nodes
            Node tailInNewNode = newNode;
            while (tailInNewNode.next != null) {
                tailInNewNode = tailInNewNode.next;
            }

            curNode.next.prev = tailInNewNode;
            tailInNewNode.next = curNode.next;
        }
        curNode.next = newNode;
        newNode.prev = curNode;
    }

    private ImmutableLinkedList addAllGeneral(int index, Object[] objs) {
        if (objs == null) {
            throw new NullPointerException("Can not add null to ImmutableList");
        }

        if (objs.length == 0) {
            throw new IllegalArgumentException("input array is empty");
        }

        // create linked list of input Object[] to pass
        // this chain in result linkedList
        ImmutableLinkedList littleLinkedList = new ImmutableLinkedList(objs[0]);

        Node newNode;
        Node curLittleNode = littleLinkedList.head;
        for (int i = 1; i < objs.length; i++) {
            newNode = new Node(objs[i]);
            curLittleNode.next = newNode;
            newNode.prev = curLittleNode;
            curLittleNode = curLittleNode.next;
        }
        littleLinkedList.tail = curLittleNode;

        // create newLinkedList to inout our littleLinkedList in it
        ImmutableLinkedList newLinkedList = getCopy();
        if (index == 0) {
            // go to the tail in our littleLinkedList
            Node tailInNewNode = littleLinkedList.head;
            while (tailInNewNode.next != null) {
                tailInNewNode = tailInNewNode.next;
            }

            Node swap = newLinkedList.head;
            newLinkedList.head = littleLinkedList.head;
            tailInNewNode.next = swap;
        }
        else {
            newNode = littleLinkedList.head;
            insertIndexNode(index, newNode, newLinkedList);
        }

        newLinkedList.listSize += objs.length;
        return newLinkedList;
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAllGeneral(size(), c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index < 0 || index >= listSize) {
            throw new IndexOutOfBoundsException();
        }

        return addAllGeneral(index, c);
    }

    public Object get(int index) {
        if (index < 0 || index >= listSize) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return this.head.value;
        }

        return getBeforeIndexNode(index, this).next.value;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (index < 0 || index >= listSize) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList newLinkedList = getCopy();
        Node curNode = newLinkedList.head;
        int curIndex = index;

        if (curIndex == 0) {
            newLinkedList.head = newLinkedList.head.next;
        }
        else {
            curIndex -= 1;
            while (curIndex > 0) {
                curNode = curNode.next;
                curIndex -= 1;
            }

            // remove curNode.next - node on index
            if (curNode.next.next != null) {
                curNode.next.next.prev = curNode;
                curNode.next = curNode.next.next;
            }
            else {
                curNode.next = null;
            }
        }

        newLinkedList.listSize -= 1;
        return newLinkedList;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (index < 0 || index >= listSize) {
            throw new IndexOutOfBoundsException();
        }

        if (e == null) {
            throw new NullPointerException("Can not add null to ImmutableList");
        }

        ImmutableLinkedList newLinkedList = getCopy();
        if (index == 0) {
            if (newLinkedList.head == null) {
                newLinkedList.head = new Node(e);
            } else {
                Node newNode = new Node(e);
                newNode.next = newLinkedList.head.next;
                newLinkedList.head = newNode;
            }
        }
        else {
            Node newNode = new Node(e);
            Node curNode = getBeforeIndexNode(index, newLinkedList);
            newNode.prev = curNode;

            if (curNode.next.next != null) {
                newNode.next = curNode.next.next;
                curNode.next.next.prev = newNode;
            }

            curNode.next = newNode;
        }
        return newLinkedList;
    }

    public int indexOf(Object e) {
        if (e == null) {
            throw new NullPointerException("Can not add null to ImmutableList");
        }

        Node curNode = head;
        int index = 0;
        while (curNode != null) {
            if (curNode.value.equals(e)) {
                return index;
            }
            curNode = curNode.next;
            index += 1;
        }
        return -1;
    }

    public int size() {
        return listSize;
    }


    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }


    public boolean isEmpty() {
        return listSize == 0;
    }


    public Object[] toArray() {
        Object[] arr = new Object[size()];
        Node curNode = head;
        int i = 0;
        while (curNode != null) {
            arr[i] = curNode.value;
            curNode = curNode.next;
            i++;
        }
        return arr;
    }

    public String toString() {
        return Arrays.toString(this.toArray());
    }

    public ImmutableLinkedList addFirst(Object e) {
        if (listSize < 0) {
            throw new IndexOutOfBoundsException();
        }

        return addGeneral(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(listSize - 1);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(listSize - 1);
    }
}
