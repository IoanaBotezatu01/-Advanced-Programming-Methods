package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyILatchTable;
import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.exp.ValueExp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt{
    private String var;
    private static Lock lock=new ReentrantLock();

    public CountDownStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable= state.getSymTable();
        MyILatchTable latchTable= state.getLatchTable();
        MyIStack<IStmt> stack= state.getExeStack();
        int freePos=latchTable.getFirstFreeAddress();
        if(symTable.isDefined(var))
        {
            if(symTable.lookup(var).getType().equals(new IntType()))
            {
                IntValue fi= (IntValue) symTable.lookup(var);
                int foundIndex=fi.getVal();
                if(latchTable.containsKey(foundIndex))
                {
                    if(latchTable.get(foundIndex)>0)
                    {
                        latchTable.update(foundIndex,latchTable.get(foundIndex)-1);
                    }
                    state.getExeStack().push(new PrintStmt(new ValueExp(new IntValue(state.getId()))));
                }else throw new MyException("The index is not in the LatchTable");
            }else throw new MyException("The var is not int");
        }else throw new MyException("The var is not defined!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new MyException("The var is not int");
    }

    @Override
    public String toString() {
        return "CountDown(" +var  +
                ')';
    }
}
