package Prefix;

import java.util.HashMap;
import java.util.Map;

public class PrefixTree<Value> {

    private Node root;

    public PrefixTree() {
        root = new Node();
    }

    public boolean isEmpty(){
        return root.getChildren() == null;
    }

    public void put(Value value) {
        String word = value.toString();
        HashMap<Character, Node> children = root.getChildren();

        for(int i=0; i < word.length(); ++i){
            char c = word.charAt(i);

            Node node;
            if(children.containsKey(c))
                node = children.get(c);
            else{
                node = new Node(c);
                children.put(c, node);
            }

            children = node.getChildren();

            if(i == word.length() - 1)
                node.setLeaf(true);
        }
    }

    public boolean containsKey(String word) {
        Node t = get(word);
        return t != null && t.isLeaf();
    }

    public Node get(String str){
        Map<Character, Node> children = root.getChildren();
        Node t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.getChildren();
            }else{
                return null;
            }
        }

        //return (Value)t;
        return t;
    }

    public void remove(String word){
        HashMap<Character, Node> children = root.getChildren();
        for(int i = word.length(); i >= 0; --i){
            char c = word.charAt(i);
            char cParent = word.charAt(i-1);
            Node node = children.get(c);
            Node parent = children.get(cParent);
            if(node.isLeaf())
                children.remove(c);
            if(parent.getChildren().size() == 0)
                parent.setLeaf(true);
        }
    }
}