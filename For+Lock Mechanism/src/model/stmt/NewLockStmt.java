package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyILockTable;
import model.ADT.MyLockTable;
import model.MyException;
import model.PrgState;
import model.exp.ValueExp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt{
    private String var;
    private static Lock lock=new ReentrantLock();
    public  NewLockStmt(String var)
    {
        this.var=var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
    MyIDictionary<String, Value> symTable=state.getSymTable();
    MyILockTable lockTable=state.getLockTable();
    int freeAddress=lockTable.getFirstFreeAddress();
    lockTable.put(freeAddress,-1);
    if(symTable.isDefined(var))
    {
        if(symTable.lookup(var).getType().equals(new IntType()))
        {
           symTable.update(var,new IntValue(freeAddress));
        }else throw new MyException("NewLock Statement:The variable is not of the type int!");

    }else throw new MyException("NewLock Statement:The variable is not defined in the SymbolTable!!");

    lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new MyException("NewLock Statement:The variable is not of the type int!");
    }
    @Override
    public String toString() {
        return "newLock(" + var + ')';
    }
}
