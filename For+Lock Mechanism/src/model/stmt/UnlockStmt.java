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

public class UnlockStmt implements IStmt{
    private String var;
    private static Lock lock =new ReentrantLock();
    public UnlockStmt(String var)
    {
        this.var=var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable= state.getSymTable();
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
                    if(lockTable.get(fi)==state.getId())
                        lockTable.put(fi,-1);
                }else throw new MyException("UnlockStatement:The index is nor in the LockTable!");
            }else throw new MyException("Unlock Statement:The variable is not int Type!");
        }else throw new MyException("UnlockStatement:The variable is not defined in the SymTable!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new MyException("UnlockStatement:The variable is not int type!");
    }

    @Override
    public String toString() {
        return "Unlock(" + var +')';
    }
}
