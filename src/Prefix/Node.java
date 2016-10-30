package Prefix;

import java.util.HashMap;

public class Node {

    private boolean isLeaf;
    private HashMap<Character, Node> children = new HashMap<>();

    private char letter;

    public Node(){}

    public Node(char letter) {
        this.letter = letter;
    }

    public HashMap<Character, Node> getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
}
