package model;

import model.ADT.*;
import model.stmt.IStmt;
import model.value.Value;
import java.io.BufferedReader;
import java.io.PrintWriter;


public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIStack<MyIDictionary<String, Value>> symTable;
    MyIList<Value> out;
    MyIFileTable<String, BufferedReader> fileTable;
    MyIHeap<Integer ,Value>  heap;
    MyIProcTable procTable;
    IStmt originalProgram;
    private int id;
    private static int currentId=0;

    public PrgState(MyIStack<IStmt> stk, MyIStack<MyIDictionary<String, Value>> symtbl, MyIList<Value> ot,MyIFileTable<String,BufferedReader> fileTable,MyIHeap<Integer,Value> heap,MyIProcTable procTable ,IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        originalProgram=prg;
        this.fileTable = fileTable;
        this.heap= heap;
        this.procTable=procTable;
        id=getNewId();
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
                ", procTable"+ procTable+
                '}';
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }
    public MyIStack<MyIDictionary<String, Value>> getSymTable() {
        return symTable;
    }
    public MyIList<Value> getOut() {
        return out;
    }
    public MyIFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }
    public MyIProcTable getProcTable() {return procTable;}
    public void setFileTable(MyIFileTable<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }
    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIStack<MyIDictionary<String, Value>> symTable) {
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
    public MyIDictionary<String, Value> getTopSymTable() {
        return symTable.peek();
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

}
