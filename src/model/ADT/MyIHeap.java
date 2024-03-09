package model.ADT;

import java.util.Collection;
import java.util.Map;

public interface MyIHeap<K,V> {
    void put(K key,V value);
    V get(K key);
    boolean containsKey(K key);
     void setContent(Map<K,V> newHeap);

     void setFirstFreeAddress();
     int getFirstFreeAddress();
     void update(K key, V value);
     Map<K,V> getContent();
     Collection<V> getAllValues();

}
