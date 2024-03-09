package model.stmt;

import javafx.util.Pair;
import model.ADT.MyIDictionary;
import model.ADT.MyISemaphoreTable;
import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStmt implements IStmt{
    private String var;
    private static Lock lock=new ReentrantLock();
    public AcquireStmt(String var)
    {
        this.var=var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        //MyIStack<IStmt> stack=state.getExeStack();
        MyIDictionary<String, Value> symTable=state.getSymTable();
        MyISemaphoreTable semaphoreTable=state.getSemaphore();
        if(symTable.isDefined(var))
        {
            if(symTable.lookup(var).getType().equals(new IntType())) {
                IntValue found = (IntValue) symTable.lookup(var);
                int foundIndex=found.getVal();
                if(semaphoreTable.getSemaphoreTable().containsKey(foundIndex))
                {
                    Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(foundIndex);
                    int NL = foundSemaphore.getValue().size();
                    int N1 = foundSemaphore.getKey();
                    if (N1 > NL) {
                        if (!foundSemaphore.getValue().contains(state.getId())) {
                            foundSemaphore.getValue().add(state.getId());
                            semaphoreTable.update(foundIndex, new Pair<>(N1, foundSemaphore.getValue()));
                        }
                    } else state.getExeStack().push(this);
                }else throw new MyException("Acquire:The index is not in the Semaphore Table!");
            }else throw new MyException("The variable type should be INT for Acquire!");

        }else throw new MyException("The variable is not defined in the SymTable for Acquire!");


        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new MyException("Variable must be of type int!");


    }
    @Override
    public String toString() {
        return "Acquire("+ var +
                ")";
    }
}
