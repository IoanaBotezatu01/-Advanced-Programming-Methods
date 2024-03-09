package model.ADT;

import java.util.Collection;
import java.util.HashMap;

public class MyLatchTable implements MyILatchTable{
    private HashMap<Integer,Integer> latchTable;
    private int freeAddress=0;
    public MyLatchTable()
    {
        this.latchTable=new HashMap<>();
    }
    @Override
    public void put(Integer key, Integer value) {
        latchTable.put(key,value);
    }

    @Override
    public Integer get(Integer key) {
        return latchTable.get(key);
    }

    @Override
    public boolean containsKey(Integer key) {
        return latchTable.containsKey(key);
    }

    @Override
    public void setContent(HashMap<Integer, Integer> newLatchTable) {
    this.latchTable=newLatchTable;
    }

    @Override
    public void setFirstFreeAddress() {
        this.freeAddress++;
    }

    @Override
    public int getFirstFreeAddress() {
        setFirstFreeAddress();
        return this.freeAddress;
    }

    @Override
    public void update(Integer key, Integer value) {
    latchTable.put(key, value);
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        return this.latchTable;
    }

    @Override
    public Collection<Integer> getAllValues() {
        return this.latchTable.values();
    }
}
