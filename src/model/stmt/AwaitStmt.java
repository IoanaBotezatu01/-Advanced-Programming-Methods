package model.stmt;

import javafx.util.Pair;
import model.ADT.MyIBarrier;
import model.ADT.MyIDictionary;
import model.MyException;
import model.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt{
    private String var;
    private static Lock lock=new ReentrantLock();

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable= state.getSymTable();
        MyIBarrier barrier= state.getBarrier();
        int freePos=barrier.getFirstFreeAddress();
        if(symTable.isDefined(var))
        {
            IntValue fi= (IntValue) symTable.lookup(var);
            int foundIndex=fi.getVal();
            if(barrier.containsKey(foundIndex))
            {
                Pair<Integer, List<Integer>> foundBarrier = barrier.get(foundIndex);
                int NL = foundBarrier.getValue().size();
                int N1 = foundBarrier.getKey();
                if(N1>NL)
                {
                    if(foundBarrier.getValue().contains(state.getId()))
                    {
                        state.getExeStack().push(this);
                    }else {
                        foundBarrier.getValue().add(state.getId());
                        barrier.update(foundIndex,new Pair<>(N1,foundBarrier.getValue()));
                    }
                }
            }else throw new MyException("The index is not in the Barrier!");
        }else throw new MyException("The var is not defined");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
      if(typeEnv.lookup(var).equals(new IntType()))
          return typeEnv;
      else throw new MyException("The var is not int");
    }

    @Override
    public String toString() {
        return "AwaitStmt{"  + var +
                '}';
    }
}
