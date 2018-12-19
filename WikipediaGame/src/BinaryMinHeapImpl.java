
//package edu.upenn.cis121.project.impl;
import edu.upenn.cis121.project.data.BinaryMinHeap;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @param <V>
 *            {@inheritDoc}
 * @param <Key>
 *            {@inheritDoc}
 *
 * @author joncho, 16sp
 */
public class BinaryMinHeapImpl<V, Key extends Comparable<Key>> implements BinaryMinHeap<V, Key> {

    List<Entry<V, Key>> heap;
    int size;

    public BinaryMinHeapImpl() {
        heap = new ArrayList<Entry<V, Key>>();
        heap.add(null);
        size = 0;
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
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < size; i++) {
            if (heap.get(i + 1).getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(V value, Key key) {
        if (key == null || containsValue(value)) {
            throw new IllegalArgumentException();
        }
        size++;
        int pos = size;
        heap.add(new Entry<V, Key>(value, key));
        while (pos > 1 && key.compareTo(heap.get(pos / 2).getKey()) < 0) {
            Entry<V, Key> temp = heap.get(pos);
            heap.set(pos, heap.get(pos / 2));
            heap.set(pos / 2, temp);
            pos = pos / 2;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!containsValue(value)) {
            throw new NoSuchElementException();
        }
        Key k = heap.get(1).getKey();
        int pos = 0;
        for (int i = 0; i < size(); i++) {
            if (heap.get(i + 1).getValue().equals(value)) {
                pos = i + 1;
                //heap.set(i + 1, new Entry<V, Key>(value, newKey));
                heap.get(i+1).setKey(newKey);
            }
        }
        if (newKey == null || newKey.compareTo(k) > 0) {
            throw new IllegalArgumentException();
        }
        while (pos > 1 && newKey.compareTo(heap.get(pos / 2).getKey()) < 0) {
            Entry<V, Key> temp = heap.get(pos);
            heap.set(pos, heap.get(pos / 2));
            heap.set(pos / 2, temp);
            pos = pos / 2;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V peek() {
        return heap.get(1).getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        V min = heap.get(1).getValue();
        heap.set(1, heap.get(size));
        heap.remove(size);
        size--;
        if (!isEmpty()) {
            minHeapify(1);
        }
        return min;
    }

    void minHeapify(int i) {
        int l = 2 * i;
        int r = l + 1;
        int smallest;
        Key min = heap.get(i).getKey();
        if (l <= size && heap.get(l).getKey().compareTo(min) < 0) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r <= size && heap.get(r).getKey().compareTo(heap.get(smallest).getKey()) < 0) {
            smallest = r;
        }
        if (smallest != i) {
            Entry<V, Key> temp = heap.get(smallest);
            heap.set(smallest, heap.get(i));
            heap.set(i, temp);
            minHeapify(smallest);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        Set<V> vals = new HashSet<V>();
        for (int i = 1; i < heap.size(); i++) {
            vals.add(heap.get(i).getValue());
        }
        return vals;
    }

    /**
     * Helper entry class for maintaining value-key pairs. The underlying indexed
     * list for your heap will contain these entries.
     *
     * You are not required to use this, but we recommend it.
     */
    class Entry<A, B> {

        private A value;
        private B key;

        public Entry(A value, B key) {
            this.value = value;
            this.key = key;
        }

        /**
         * @return the value stored in the entry
         */
        public A getValue() {
            return this.value;
        }

        /**
         * @return the key stored in the entry
         */
        public B getKey() {
            return this.key;
        }

        /**
         * Changes the key of the entry.
         *
         * @param key
         *            the new key
         * @return the old key
         */
        public B setKey(B key) {
            B oldKey = this.key;
            this.key = key;
            return oldKey;
        }

    }

}
