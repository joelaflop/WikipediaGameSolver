import edu.upenn.cis121.project.index.RecordInvertedIndex;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An {@code RecordInvertedIndex} stores a mapping from keys to document
 * descriptors. This is an inverted index at the record level. A key is mapped
 * to references to documents in which that key appears. Each key should only be
 * mapped to a document descriptor once, so you should not allow for duplicate
 * values associated with a key.
 *
 * Implementation of a {@link RecordInvertedIndex}. For testing purposes, this
 * class must provide a public default no-argument constructor. This means that
 * you do not need to write any constructor at all; if you do write other
 * constructors, be sure you have one that is public and takes no arguments.
 *
 * @param <T>
 *            the type of the values in the inverted index
 *
 * @author davix
 */
public class RecordInvertedIndexImpl<T> implements RecordInvertedIndex<String, T> {

    /**
     * Adds a record at the corresponding key.
     *
     * @param key
     *            the key
     * @param t
     *            the value
     * @throws IllegalArgumentException
     *             if the specified key is null
     */
    Map<String, Set<T>> table;

    public RecordInvertedIndexImpl() {
        table = new HashMap<String, Set<T>>();
    }

    @Override
    public void addRecord(String key, T t) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (table.containsKey(key)) {
            table.get(key).add(t);
        } else {
            Set<T> newSet = new HashSet<T>();
            newSet.add(t);
            table.put(key, newSet);
        }
    }

    /**
     * Returns a listing of record values associated with a key. Returns null in the
     * case key is null
     *
     * @param key
     *            the key
     * @return the record values mapped to the specified key, or {@code null} if the
     *         key did not exist
     */
    @Override
    public Collection<T> getRecords(String key) {
        return table.get(key);
    }

    /**
     * Deletes the records for the specified key. In the case of a null key, return
     * null
     *
     * @param key
     *            the key
     * @return the former records for the specified key, or {@code null} if the key
     *         did not exist
     */
    @Override
    public Collection<T> deleteRecords(String key) {
        if (table.containsKey(key)) {
            Collection<T> temp = table.get(key);
            table.remove(key);
            return temp;
        }
        return null;

    }

    /**
     * Returns {@code true} if this map contains one or more records for the
     * specified key. In the case key is null, it will return false.
     *
     * @return {@code true} if this map contains one or more records for the
     *         specified key
     */
    @Override
    public boolean containsKey(String key) {
        if (key == null) {
            return false;
        } else {
            return table.containsKey(key);
        }
    }

    /**
     * Returns a set view of the keys in the inverted index. The set is backed by
     * the map, so changes to the map are reflected in the set, and vice-versa. If
     * the map is modified while an iteration over the set is in progress (except
     * through the iterator's own remove operation), the results of the iteration
     * are undefined. The set may or may not support supports element removal or
     * addition, depending on the actual type of the inverted index.
     *
     * @return a set view of the keys in the inverted index
     */
    @Override
    public Set<String> keySet() {
        return table.keySet();
    }

    /**
     * Returns the number of keys in the inverted index.
     *
     * @return the number of keys in the invertex index
     */
    @Override
    public int size() {
        // TODO: implement
        return table.size();
    }

    /**
     * Returns the number of record values associated with a key. If the key does
     * not exist, then returns 0.
     *
     * @param key
     *            the key
     * @return the number of record values associated with the specified key, or 0
     *         if the key does not exist or the key is null
     */
    @Override
    public int count(String key) {
        if (key == null || !table.containsKey(key)) {
            return 0;
        } else {
            return table.get(key).size();
        }
    }
}
