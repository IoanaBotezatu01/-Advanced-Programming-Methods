package model.stmt;

import javafx.util.Pair;
import model.ADT.MyIDictionary;
import model.ADT.MyISemaphoreTable;
import model.MyException;
import model.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements IStmt{
    private String var;
    private static Lock lock=new ReentrantLock();
    public ReleaseStmt(String var)
    {
        this.var=var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable=state.getSymTable();
        MyISemaphoreTable semaphoreTable=state.getSemaphore();
        if(symTable.isDefined(var))
        {
            if(symTable.lookup(var).getType().equals(new IntType()))
            {
                IntValue found= (IntValue) symTable.lookup(var);
                int foundIndex=found.getVal();
                if(semaphoreTable.getSemaphoreTable().containsKey(foundIndex))
                {
                    Pair<Integer, List<Integer>> foundSemaphore=semaphoreTable.get(foundIndex);
                    if (foundSemaphore.getValue().contains(state.getId()))
                        foundSemaphore.getValue().remove((Integer) state.getId());
                    semaphoreTable.update(foundIndex, new Pair<>(foundSemaphore.getKey(), foundSemaphore.getValue()));
                }else throw new MyException("ReleaseStmt:The variable is not in the SemaphoreTable!");
            }else throw new MyException("ReleaseStmt:The variable type should be int");

        }else throw new MyException("ReleaseStmt:The variable is not defined in the symTable!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
       if(typeEnv.lookup(var).equals(new IntType()))
           return typeEnv;
       else throw new MyException("The variable is not of the type int!");
    }
}
