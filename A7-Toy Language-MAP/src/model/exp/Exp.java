package model.exp;
import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.type.Type;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> symTbl, MyIHeap<Integer,Value> heap) throws MyException;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
