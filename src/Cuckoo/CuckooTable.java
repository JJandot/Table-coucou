package Cuckoo;

import java.util.Vector;

public class CuckooTable<Key extends FamilyHashable,Value> {

    private Vector<Key>     keys;
    private Vector<Key>     keys1;
    private Vector<Value>   values;
    private Vector<Value>   values1;
    private int             size;

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

    public boolean isEmpty(){
        for(Key key: keys)
            if(key != null) return false;
        return true;
    }

    public boolean containsKey(Key key){
        int index = keys.indexOf(key);
        int index1 = keys1.indexOf(key);
        return index != -1 || index1 != -1;
    }

    public Value get(Key key){
        int index = keys.indexOf(key);
        if(index != -1)
            return values.get(index);
        index = keys1.indexOf(key);
        if (index != -1)
            return values1.get(index);
        return null;
    }

    private Key getKey(Vector<Key> keys, Key key, int seed){
        return keys.get(Math.abs((int) key.hashCode(seed)) % size);
    }

    private Value getValue(Vector<Value> values, Key key, int seed){
        return values.get(Math.abs((int) key.hashCode(seed)) % size);
    }

    private void inserer1(Key key, Vector<Key> keys1, Vector<Key> keys2, Value value, Vector<Value> values1, Vector<Value> values2, int compteur){
        if(compteur > 5) return;
        Key cleChasse = getKey(keys1, key, 5);
        Value valueChasse = getValue(values1, key, 5);
        keys1.set(Math.abs((int)key.hashCode(5) % size), key);
        values1.set(Math.abs((int)key.hashCode(5) % size), value);
        if(cleChasse != null)
            inserer2(cleChasse, keys1, keys2, valueChasse, values1, values2, compteur);
    }

    private void inserer2(Key key, Vector<Key> keys1, Vector<Key> keys2, Value value, Vector<Value> values1, Vector<Value> values2, int compteur){
        Key cleChasse = getKey(keys2, key, 10);
        Value valueChasse = getValue(values2, key, 10);
        keys2.set(Math.abs((int)key.hashCode(10) % size), key);
        values2.set(Math.abs((int)key.hashCode(10) % size), value);
        if(cleChasse != null)
            inserer1(cleChasse, keys1, keys2, valueChasse, values1, values2, ++compteur);
    }



    public void put(Key key, Value value){            //comparer cle
        inserer1(key, keys, keys1, value, values,values1, 0);
    }

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

    public String toString(){
        return keys.toString() + values.toString() + keys1.toString() + values1.toString();
    }
}