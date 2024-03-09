package model;

import javafx.util.Pair;
import model.ADT.*;
import model.stmt.IStmt;
import model.value.Value;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;


public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIFileTable<String, BufferedReader> fileTable;
    MyIHeap<Integer ,Value>  heap;
    IStmt originalProgram;
    MyISemaphoreTable semaphoreTable;
    private int id;
    private static int currentId=0;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot,MyIFileTable<String,BufferedReader> fileTable,MyIHeap<Integer,Value> heap,MyISemaphoreTable semaphoreTable ,IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        originalProgram=prg;
        this.fileTable = fileTable;
        this.heap= heap;
        id=getNewId();
        this.semaphoreTable=semaphoreTable;
        stk.push(prg);
    }

    private int getNewId() {
        currentId++;
        return currentId;
    }

    @Override
    public String toString() {
        return "PrgState{" +
                "id ="+ id+
                "exeStack=" + exeStack +
                ", symTable=" + symTable +
                ", out=" + out +
                ", heap="+ heap+
                '}';
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }
    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }
    public MyIList<Value> getOut() {
        return out;
    }
    public MyISemaphoreTable getSemaphore(){return semaphoreTable;}
    public MyIFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIFileTable<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }
    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }
    public void setOut(MyIList<Value> out) {
        this.out = out;
    }
    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }
    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()) throw new MyException("Program state stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setHeap(MyIHeap<Integer, Value> newHeap) {
        this.heap = newHeap;
    }
    public MyIHeap<Integer,Value> getHeap(){
        return this.heap;
    }

    public int getId() {
        return id;
    }
    public static int getNextID()
    {
        return currentId++; // this will be passed to the id from our class
    }
}
