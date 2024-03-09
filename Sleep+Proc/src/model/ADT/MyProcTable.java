package model.ADT;

import javafx.util.Pair;
import model.stmt.IStmt;

import java.util.*;


public class MyProcTable implements MyIProcTable{
    private HashMap<String,  Pair<List<String>, IStmt>> procTable;
    public MyProcTable(){
        this.procTable=new HashMap<>();
    }
    @Override
    public void put(String key, Pair<List<String>, IStmt> value) {
        procTable.put(key,value);

    }

    @Override
    public Pair<List<String>, IStmt> get(String key) {
        return procTable.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return procTable.containsKey(key);
    }

    @Override
    public void setContent(HashMap<String, Pair<List<String>, IStmt>> newProcTable) {
        this.procTable=newProcTable;

    }


    @Override
    public void update(String key, Pair<List<String>, IStmt> value) {
        procTable.put(key,value);

    }

    @Override
    public Map<String, Pair<List<String>, IStmt>> getContent() {
        return procTable;
    }

    @Override
    public Collection<Pair<List<String>, IStmt>> getAllValues() {
        return this.procTable.values();
    }
    @Override
    public Set<String> keySet() {

            return procTable.keySet();

    }

    @Override
    public MyIDictionary<String, Pair<List<String>, IStmt>> deepCopy() {
        MyIDictionary<String, Pair<List<String>, IStmt>> res = new MyDictionary<>();
        for (String key: keySet())
            res.add(key, get(key));
        return res;
    }
}
