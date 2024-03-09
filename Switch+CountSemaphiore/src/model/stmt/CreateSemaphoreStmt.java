package model.stmt;

import javafx.util.Pair;
import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.ADT.MyISemaphoreTable;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStmt implements IStmt{
    private String var;
    private Exp exp1;
    private static Lock lock =new ReentrantLock();
    public CreateSemaphoreStmt(String var,Exp exp1)
    {
        this.exp1=exp1;
        this.var=var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIHeap<Integer, Value> heap=state.getHeap();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyISemaphoreTable semaphoreTable=state.getSemaphore();
        IntValue nr = (IntValue) exp1.eval(symTable, heap);
        int number=nr.getVal();
        int freeAddress=state.getSemaphore().getFreeAddress();
        semaphoreTable.put(freeAddress,new Pair<>(number,new ArrayList<>()));
        if(symTable.isDefined(var)&&symTable.lookup(var).getType().equals(new IntType()))
            symTable.update(var,new IntValue(freeAddress));
        else
            throw new MyException("The variable is not defined!!");
        lock.unlock();
        return null;


    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(var,exp1);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            if (exp1.typecheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            } else throw new MyException("Expression must be of type int!");

        } else throw new MyException("Variable must be of type int!");

    }

    @Override
    public String toString() {
        return "CreateSemaphore(" + var +
                ", " + exp1 +
                ')';
    }
}
