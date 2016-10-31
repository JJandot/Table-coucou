package Cuckoo;

import java.util.Vector;

public class CuckooTable<Key extends FamilyHashable,Value> {

    /**
     * Two vectors used for containing the keys, two others for the values, and the initial size of the vectors
     */
    private Vector<Key>     keys;
    private Vector<Key>     keys1;
    private Vector<Value>   values;
    private Vector<Value>   values1;
    private int             size;


    public int getSize() {
        return size;
    }

    /**
     * Constructor.
     * Instantiate our vectors with a given number of elements and set them to the given size
     * @param expectedKeys the number of elements and the size
     */
    public CuckooTable(int expectedKeys){
        this.keys           = new Vector<>(expectedKeys);
        this.keys1          = new Vector<>(expectedKeys);
        this.values         = new Vector<>(expectedKeys);
        this.values1        = new Vector<>(expectedKeys);
        this.size           = expectedKeys;
        this.keys           .setSize(size);
        this.keys1          .setSize(size);
        this.values         .setSize(size);
        this.values1        .setSize(size);
    }


    /**
     * For each key, check if it's null or not
     * @return false if a key isn't null
     */
    public boolean isEmpty(){
        for(Key key: keys)
            if(key != null) return false;
        return true;
    }

    /**
     * Check if a specific key is inside the current CuckooTable, by using indexOf(key), which returns -1 if key isn't
     * in the vector
     * @param key the searched key
     * @return true if one index ins't equals to -1
     */
    public boolean containsKey(Key key){
        int index = keys.indexOf(key);
        int index1 = keys1.indexOf(key);
        return index != -1 || index1 != -1;
    }

    /**
     * Gets the value specified by the given key
     * @param key the key we want to check the value
     * @return null if the key is not in the CuckooTable, the value associated with the key instead
     */
    public Value get(Key key){
        int index = keys.indexOf(key);
        if(index != -1)
            return values.get(index);
        index = keys1.indexOf(key);
        if (index != -1)
            return values1.get(index);
        return null;
    }

    /**
     * Format the key and returns its index in the given vector
     * @param keys the vector used for the research
     * @param key the searched key
     * @param seed used in the hashCode function
     * @return
     */
    private Key getKey(Vector<Key> keys, Key key, int seed){
        return keys.get(Math.abs((int) key.hashCode(seed)) % size);
    }

    /**
     * Format the key and returns the value present in its index in the given vector
     * @param values the vector used for the research
     * @param key the searched value
     * @param seed used in the hashCode function
     * @return
     */
    private Value getValue(Vector<Value> values, Key key, int seed){
        return values.get(Math.abs((int) key.hashCode(seed)) % size);
    }


    /**
     * Try to nsert the key and the value in the first vector. If a key and a value are already present, move them into the other vectors.
     * Recursive <code>compteur</code> times
     * @param key the inserted key
     * @param keys1 the first key vector
     * @param keys2 the second key vector
     * @param value the inserted value
     * @param values1 the first value vector
     * @param values2 the second value vector
     * @param compteur the number of recursion the function will be able to do
     */
    private void inserer1(Key key, Vector<Key> keys1, Vector<Key> keys2, Value value, Vector<Value> values1, Vector<Value> values2, int compteur){
        if(compteur > 5) return;
        Key cleChasse = getKey(keys1, key, 5);
        Value valueChasse = getValue(values1, key, 5);
        keys1.set(Math.abs((int)key.hashCode(5) % size), key);
        values1.set(Math.abs((int)key.hashCode(5) % size), value);
        if(cleChasse != null)
            inserer2(cleChasse, keys1, keys2, valueChasse, values1, values2, compteur);
    }

    /**
     * The recursive call of <code>inserrer1</code>. Try to insert the key and the value in the second vector.
     * Increments <code>compteur</code> if there already are a key and a value here, then calls back <code>inserer1</code>
     * @param key the inserted key
     * @param keys1 the first key vector
     * @param keys2 the second key vector
     * @param value the inserted value
     * @param values1 the first value vector
     * @param values2 the second value vector
     * @param compteur the number of recursion the function will be able to do
     */
    private void inserer2(Key key, Vector<Key> keys1, Vector<Key> keys2, Value value, Vector<Value> values1, Vector<Value> values2, int compteur){
        Key cleChasse = getKey(keys2, key, 10);
        Value valueChasse = getValue(values2, key, 10);
        keys2.set(Math.abs((int)key.hashCode(10) % size), key);
        values2.set(Math.abs((int)key.hashCode(10) % size), value);
        if(cleChasse != null)
            inserer1(cleChasse, keys1, keys2, valueChasse, values1, values2, ++compteur);
    }

    /**
     * If the key is not in the CuckooTable, calls the recursive function <code>inserer1</code>
     * @param key the key we want to insert
     * @param value the value we want to insert
     */
    public void put(Key key, Value value){
        if(!containsKey(key))
            inserer1(key, keys, keys1, value, values,values1, 0);
    }

    /**
     * Remove the given key from the CuckooTable, by checking indexes
     * @param key the key we want to remove
     */
    public void remove(Key key){
        int index = keys.indexOf(key);
        if(index != -1) {
            keys.set(index, null);
            values.set(index, null);
        }
        index = keys1.indexOf(key);
        if (index != -1) {
            keys1.set(index,null);
            values1.set(index, null);
        }
    }

    @Override
    public String toString(){
        return keys.toString() + values.toString() + keys1.toString() + values1.toString();
    }
}