package Prefix;

import java.util.ArrayList;
import java.util.List;

public class PrefixTree<Value> {

    private List<Node> tree;

    public PrefixTree(){
        tree = new ArrayList<>();
    }

    public boolean isEmpty(){
        return tree.isEmpty();
    }

    public boolean containsKey(String word){
        return false;
    }

    public Value get(String word){
        return null;
    }

    public void put(String key, Value value){

    }

    public void remove(String word){

    }

    public PrefixTree<Value> get(Character c){
        return null;
    }
}