package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.type.Type;

public class SleepStmt implements IStmt{
    private int number;
    public SleepStmt(int nr){
        this.number=nr;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack=state.getExeStack();
        if(number!=0)
        {
            IStmt sleepStmt=new SleepStmt(number-1);
            stack.push(sleepStmt);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Sleep(" +
                 number +
                ')';
    }
}
