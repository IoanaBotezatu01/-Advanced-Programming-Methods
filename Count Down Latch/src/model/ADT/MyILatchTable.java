package model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface MyILatchTable {
    void put(Integer key,Integer value);
    Integer get(Integer key);
    boolean containsKey(Integer key);
    void setContent(HashMap<Integer, Integer> newLatchTable);

    void setFirstFreeAddress();
    int getFirstFreeAddress();
    void update(Integer key, Integer value);
    HashMap<Integer,Integer> getContent();
    Collection<Integer> getAllValues();
}
