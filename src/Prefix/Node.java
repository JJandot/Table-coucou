package Prefix;

import Cuckoo.CuckooTable;
import Cuckoo.HashableString;

public class Node {

    private boolean isLeaf;
    private CuckooTable<HashableString, Node> children = new CuckooTable<>(1);
    private char letter;

    /**
     * Constructor.
     * Used for creating the first Node
     */
    public Node(){}


    /**
     * Constructor.
     * Used to add new Nodes to the tree
     * @param letter
     */
    public Node(char letter) {
        this.letter = letter;
    }


    /**
     * Getter.
     * @return
     */
    public CuckooTable<HashableString, Node> getChildren() {
        return children;
    }

    /**
     * Setter
     * @return true if the current node is a leaf
     */
    public boolean isLeaf() {
        return isLeaf;
    }


    /**
     * Setter.
     * @param leaf true or false
     */
    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    /**
     * Getter.
     * @return the value of the node
     */
    public char getLetter(){
        return letter;
    }
}
