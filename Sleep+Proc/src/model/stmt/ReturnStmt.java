package model.stmt;

import model.ADT.MyIDictionary;
import model.MyException;
import model.PrgState;
import model.type.Type;

public class ReturnStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getSymTable().pop();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReturnStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Return";
    }
}
