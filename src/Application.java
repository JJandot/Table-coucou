
import Prefix.PrefixTree;

public class Application {

    private Application(){
        PrefixTree prefixTree = new PrefixTree();
        System.out.println(prefixTree.isEmpty());
    }

    public static void main(String[] args) {
        new Application();
    }

}
