package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyILockTable;
import model.MyException;
import model.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStmt implements IStmt{
    private String var;
    private static Lock lock=new ReentrantLock();
    public LockStmt(String var)
    {
        this.var=var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable=state.getSymTable();
        MyILockTable lockTable=state.getLockTable();
        int freePos=lockTable.getFirstFreeAddress();
        if(symTable.isDefined(var))
        {
        if(symTable.lookup(var).getType().equals(new IntType()))
        {
            IntValue foundIndex= (IntValue) symTable.lookup(var);
            int fi=foundIndex.getVal();
            if(lockTable.containsKey(fi))
            {
                if(lockTable.get(fi)==-1)
                {
                    lockTable.update(fi,state.getId());
                }
                else state.getExeStack().push(this);
            }else throw new MyException("Lock Statement:The index is not in the LockTable!");
        }else throw new MyException("Lock Statement:The variable is not of the type int!");
        }else throw new MyException("Lock Statement:The variable is not defined in the SymbolTable!");


        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new MyException("Lock Statement:The variable is not of the type int!!");
    }
    @Override
    public String toString() {
        return "lock(" + var + ")";
    }
}
