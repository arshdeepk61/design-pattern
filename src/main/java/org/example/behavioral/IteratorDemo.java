package org.example.behavioral;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ITERATOR (Behavioral)
 * ---------------------
 * Intent: Provide a way to access the elements of an aggregate object sequentially without
 *         exposing its underlying representation.
 *
 * Use when:
 *   - You want a uniform way to traverse a collection regardless of its internal structure.
 *   - You want multiple, independent traversals over the same collection.
 *
 * Interview points:
 *   - Java's java.util.Iterator + Iterable IS this pattern; implementing Iterable enables
 *     the enhanced for-each loop.
 */
public class IteratorDemo {

    /** A custom collection backed by a fixed array, exposing iteration via Iterable. */
    static class RingBuffer<T> implements Iterable<T> {
        private final Object[] data;
        private int size;

        RingBuffer(int capacity) { data = new Object[capacity]; }

        void add(T item) {
            if (size == data.length) throw new IllegalStateException("full");
            data[size++] = item;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {
                private int cursor = 0;

                public boolean hasNext() { return cursor < size; }

                @SuppressWarnings("unchecked")
                public T next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    return (T) data[cursor++];
                }
            };
        }
    }

    public static void main(String[] args) {
        RingBuffer<String> buffer = new RingBuffer<>(5);
        buffer.add("a");
        buffer.add("b");
        buffer.add("c");

        // Enhanced for-each works because RingBuffer is Iterable.
        for (String s : buffer) {
            System.out.println("Got: " + s);
        }
    }
}
