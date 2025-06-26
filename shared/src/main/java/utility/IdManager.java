package utility;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class IdManager implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    static int nextProductId = 1;
    private static int nextOrgId = 1;

    private static final Set<Integer> usedProducts = new HashSet<>();
    private static final Set<Integer> usedOrgs = new HashSet<>();

    public static void registerProductId(int id){
        usedProducts.add(id);
        if (id >= nextProductId){
            nextProductId = id + 1;
        }
    }

    public static void registerOrgId(int id){
        usedOrgs.add(id);
        if(id >= nextOrgId){
            nextOrgId = id + 1;
        }
    }

    public static int getNextProductId(){
        while(usedProducts.contains(nextProductId)) {
            System.out.println("ID " + nextProductId + " уже занят, пробуем следующий...");
            nextProductId++;
        }
        usedProducts.add(nextProductId);
        System.out.println("Сгенерирован новый ID: " + nextProductId);
        return nextProductId++;
    }

    public static int getNextOrgId(){
        while (usedOrgs.contains(nextOrgId)) nextOrgId++;
        usedOrgs.add(nextOrgId);
        return nextOrgId++;
    }

    public static void clear(){
        usedOrgs.clear();
        usedProducts.clear();
        nextOrgId = 1;
        nextProductId = 1;
    }

}
