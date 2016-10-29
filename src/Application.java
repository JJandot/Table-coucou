import Cuckoo.CuckooTable;
import Cuckoo.HashableString;

public class Application {

    private Application(){
        CuckooTable<HashableString, String> cuckooTable = new CuckooTable<>(3);
        System.out.println(cuckooTable.isEmpty());
        cuckooTable.put(new HashableString("test"), "test");
        System.out.println(cuckooTable.toString());
        cuckooTable.put(new HashableString("kek"), "kek");
        System.out.println(cuckooTable.toString());
        cuckooTable.put(new HashableString("lel"), "lel");
        System.out.println(cuckooTable.toString());
        cuckooTable.remove(new HashableString("test"));
        cuckooTable.remove(new HashableString("mdr"));
        System.out.println(cuckooTable.toString());
        cuckooTable.put(new HashableString("test"), "test");
        System.out.println(cuckooTable.toString());
        System.out.println(cuckooTable.containsKey(new HashableString("lel")));
        System.out.println(cuckooTable.containsKey(new HashableString("lol")));
        System.out.println(cuckooTable.isEmpty());
        System.out.println(cuckooTable.get(new HashableString("lel")));
    }

    public static void main(String[] args) {
        new Application();
    }

}
