package model.ADT;

import javafx.util.Pair;
import model.stmt.IStmt;

import java.util.*;

public interface MyIProcTable {
    void put(String key, Pair<List<String>, IStmt> value);
    Pair<List<String>, IStmt> get(String key);
    boolean containsKey(String key);

    void setContent(HashMap<String,Pair<List<String>, IStmt>> newProcTable);

    void update(String key, Pair<List<String>, IStmt> value);
    Set<String> keySet();
    Map<String,Pair<List<String>, IStmt>> getContent();
    Collection<Pair<List<String>, IStmt>> getAllValues();
    MyIDictionary<String, Pair<List<String>, IStmt>> deepCopy();

}
