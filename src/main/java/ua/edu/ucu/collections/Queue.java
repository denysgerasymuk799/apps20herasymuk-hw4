package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList queue;

    public Queue() {
        queue = new ImmutableLinkedList();
    }

    public void enqueue(Object e) {
        queue = queue.addLast(e);
    }

    public Object peek() {
        return queue.getFirst();
    }

    public Object dequeue() {
        Object item = queue.getFirst();
        queue = queue.removeFirst();
        return item;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}
