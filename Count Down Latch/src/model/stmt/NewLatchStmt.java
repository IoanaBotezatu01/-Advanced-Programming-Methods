package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.ADT.MyILatchTable;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt{
    private String var;
    private Exp exp;
    private static Lock lock=new ReentrantLock();

    public NewLatchStmt(String var,Exp exp) {
        this.var = var;
        this.exp=exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable=state.getSymTable();
        MyIHeap<Integer,Value> heap=state.getHeap();
        MyILatchTable latchTable= state.getLatchTable();
        int freePos=latchTable.getFirstFreeAddress();

        IntValue n1= (IntValue) exp.eval(symTable,heap);
        int num1=n1.getVal();
        latchTable.update(freePos,num1);
        if(symTable.isDefined(var))
        {
            if(symTable.lookup(var).getType().equals(new IntType()))
            {
                symTable.update(var,new IntValue(freePos));
            }else throw new MyException("The variable is not int type!");
        }else throw new MyException("The variable is not defined!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(var,exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            if(exp.typecheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("The expression is not int");
        else
            throw new MyException("The variable is not int!");
    }

    @Override
    public String toString() {
        return "NewLatch(" +var  +
                ", " + exp +
                ')';
    }
}
