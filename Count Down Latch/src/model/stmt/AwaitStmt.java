package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIFileTable;
import model.ADT.MyILatchTable;
import model.ADT.MyIList;
import model.MyException;
import model.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

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
        MyILatchTable latchTable= state.getLatchTable();
        int freePos=latchTable.getFirstFreeAddress();
        if(symTable.isDefined(var))
        {
            if(symTable.lookup(var).getType().equals(new IntType()))
            {
                IntValue fi= (IntValue) symTable.lookup(var);
                int foundIndex=fi.getVal();
                if(latchTable.containsKey(foundIndex))
                {
                    if(latchTable.get(foundIndex)!=0)
                        state.getExeStack().push(this);
                }else throw new MyException("The LatchTable does not contain the index");
            }else throw new MyException("The var is not int");
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
        else throw new MyException("The var is not int!");
    }

    @Override
    public String toString() {
        return "Await(" + var + ')';
    }
}
