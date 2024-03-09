package model.exp;

import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class NotExp implements Exp{
    private Exp exp;
    public NotExp(Exp expression){this.exp=expression;}
    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws MyException {
        BoolValue val= (BoolValue) exp.eval(symTbl,heap);
        if(!val.getVal())
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return exp.typecheck(typeEnv);
    }
}
