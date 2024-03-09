package model.exp;

import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class ReadHeap implements Exp{
    private Exp expression;
    public ReadHeap(Exp expression){
        this.expression=expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value evalExp=expression.eval(symTbl,heap);
        if(!(evalExp.getType()instanceof RefType))
            throw new MyException("The Expression is not RefValue!");

       int address=((RefValue)evalExp).getAddr();
       if(!(heap.containsKey(address)))
           throw new MyException("The address does not exist in the Heap Table!");



        return heap.get(address);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "ReadHeap("+expression.toString()+")";
    }
}
