package Prefix;

import Cuckoo.CuckooTable;
import Cuckoo.HashableString;

public class PrefixTree<Value> {

    /**
     * The base of the tree
     */
    private Node root;

    /**
     * Constructor.
     * Creates a new PrefixTree with a unique node
     */
    public PrefixTree() {
        root = new Node();
    }

    /**
     * Check if the PrefixTree is empty, by checking if its root has children
     * @return true if it didn't
     */
    public boolean isEmpty(){
        return root.getChildren() == null;
    }

    /**
     * Insert a value in the PrefixTree. Check if the chars are already in the tree before inserting them.
     * Sets the last node as a leaf
     * @param value the added value
     */
    public void put(Value value) {
        String word = value.toString();
        CuckooTable<HashableString, Node> children = root.getChildren();

        for(int i=0; i < word.length(); ++i){
            char c = word.charAt(i);

            Node node;
            if(children.containsKey(new HashableString(String.valueOf(c))))
                node = children.get(new HashableString(String.valueOf(c)));
            else{
                node = new Node(c);
                children.put(new HashableString(String.valueOf(c)), node);
            }

            children = node.getChildren();

            if(i == word.length() - 1)
                node.setLeaf(true);
        }
    }

    /**
     * Checks if the PrefixTree contains the given word
     * @param word the searched word
     * @return true if it does
     */
    public boolean containsKey(String word) {
        Node t = (Node) get(word);
        return t != null && t.isLeaf();
    }

    /**
     * Gets the value of the node which will 'contains' the given string
     * @param str the searched string
     * @return the node's value
     */
    public Value get(String str){
        CuckooTable<HashableString, Node> children = root.getChildren();
        Node t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(new HashableString(String.valueOf(c)))){
                t = children.get(new HashableString(String.valueOf(c)));
                children = t.getChildren();
            }else{
                return null;
            }
        }
        return (Value) t;
    }

    /**
     * Browse in reverse the PrefixTree and remove the given word if the current node is a leaf
     * @param word
     */
    public void remove(String word){
        CuckooTable<HashableString, Node> children = root.getChildren();
        for(int i = word.length(); i >= 0; --i){
            char c = word.charAt(i);
            char cParent = word.charAt(i-1);
            Node node = children.get(new HashableString(String.valueOf(c)));
            Node parent = children.get(new HashableString(String.valueOf(cParent)));
            if(node.isLeaf())
                children.remove(new HashableString(String.valueOf(c)));
            if(parent.getChildren().getSize() == 0)
                parent.setLeaf(true);
        }
    }
}