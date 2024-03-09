package model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface MyILockTable {
    void put(Integer key,Integer value);
    Integer get(Integer key);
    boolean containsKey(Integer key);
    void setContent(HashMap<Integer,Integer> newLockTable);

    void setFirstFreeAddress();
    int getFirstFreeAddress();
    void update(Integer key, Integer value);
    HashMap<Integer,Integer> getContent();
    Collection<Integer> getAllValues();

}
