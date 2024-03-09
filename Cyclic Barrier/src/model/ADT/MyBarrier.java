package model.ADT;

import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MyBarrier implements MyIBarrier {
    private HashMap<Integer, Pair<Integer, List<Integer>>> barrier;
    int freeAddress = 0;

    public MyBarrier() {
        this.barrier = new HashMap<>();
    }

    @Override
    public void put(Integer key, Pair<Integer, List<Integer>> value) {
        barrier.put(key, value);
    }

    @Override
    public Pair<Integer, List<Integer>> get(Integer key) {
        return barrier.get(key);
    }

    @Override
    public boolean containsKey(Integer key) {
        return barrier.containsKey(key);
    }

    @Override
    public void setContent(HashMap<Integer, Pair<Integer, List<Integer>>> newBarrier) {
        this.barrier = newBarrier;
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
    public void update(Integer key, Pair<Integer, List<Integer>> value) {
        this.barrier.put(key, value);
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getContent() {
        return this.barrier;
    }

    @Override
    public Collection<Pair<Integer, List<Integer>>> getAllValues() {
        return this.barrier.values();
    }
}
