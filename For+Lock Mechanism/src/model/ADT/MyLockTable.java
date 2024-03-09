package model.ADT;

import java.util.Collection;
import java.util.HashMap;

public class MyLockTable implements MyILockTable{
    private HashMap<Integer,Integer> lockTable;
    private int freeAddress=0;
    public MyLockTable()
    {
        this.lockTable=new HashMap<>();
    }
    @Override
    public void put(Integer key, Integer value) {
        lockTable.put(key,value);

    }

    @Override
    public Integer get(Integer key) {
        return lockTable.get(key);
    }

    @Override
    public boolean containsKey(Integer key) {
        return lockTable.containsKey(key);
    }

    @Override
    public void setContent(HashMap<Integer, Integer> newLockTable) {
    this.lockTable=newLockTable;
    }

    @Override
    public void setFirstFreeAddress() {
        this.freeAddress=freeAddress+1;
    }

    @Override
    public int getFirstFreeAddress() {
        setFirstFreeAddress();
        return this.freeAddress;
    }

    @Override
    public void update(Integer key, Integer value) {
        lockTable.put(key,value);

    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        synchronized (this) {
            return lockTable;
        }
    }

    @Override
    public Collection<Integer> getAllValues() {
        return this.lockTable.values();
    }
}
