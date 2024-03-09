package model.stmt;

import javafx.util.Pair;
import model.ADT.MyIBarrier;
import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStmt implements IStmt{
    private String var;
    private Exp exp;
    private Lock lock=new ReentrantLock();

    public NewBarrierStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String , Value> symTable=state.getSymTable();
        MyIHeap<Integer,Value> heap=state.getHeap();
        MyIBarrier barrier=state.getBarrier();
        int freePos=barrier.getFirstFreeAddress();
        IntValue nr= (IntValue) exp.eval(symTable,heap);
        int number=nr.getVal();
        barrier.put(freePos, new Pair<>(number,new ArrayList<>()));
        if(symTable.isDefined(var))
        {
            symTable.update(var,new IntValue(freePos));
        }else
            symTable.add(var,new IntValue(freePos));

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var,exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            if (exp.typecheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            }
            else {
                throw new MyException("Expression must be of type int!");
            }
        }
        else {
            throw new MyException("Variable must be of type int!");
        }
    }

    @Override
    public String toString() {
        return "NewBarrierStmt{" + var  +
                ", " + exp +
                ", " + lock +
                '}';
    }
}
