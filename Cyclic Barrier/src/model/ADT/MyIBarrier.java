package model.ADT;

import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MyIBarrier {
    void put(Integer key, Pair<Integer, List<Integer>> value);
    Pair<Integer,List<Integer>>get(Integer key);
    boolean containsKey(Integer key);
    void setContent(HashMap<Integer,Pair<Integer,List<Integer>>> newBarrier);

    void setFirstFreeAddress();
    int getFirstFreeAddress();
    void update(Integer key, Pair<Integer,List<Integer>> value);
    HashMap<Integer,Pair<Integer,List<Integer>>> getContent();
    Collection<Pair<Integer,List<Integer>>> getAllValues();

}
