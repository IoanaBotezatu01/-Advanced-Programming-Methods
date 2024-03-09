package model.stmt;

import model.ADT.MyDictionary;
import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class NewStmt implements IStmt{
    String var_name;
    Exp expression;
    public NewStmt(String var_name,Exp expression){
        this.expression=expression;
        this.var_name=var_name;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symbolTable=state.getSymTable().peek();
        MyIHeap<Integer,Value> heap=state.getHeap();

        if(!symbolTable.isDefined(var_name))
            throw new MyException("Variable is not declared in the SymTable!");

        Value getVarSymTable=symbolTable.lookup(var_name);
        if(!((getVarSymTable.getType()) instanceof RefType))
            throw new MyException("The type of the variable is not RefType!");

        Value evalExp=expression.eval(symbolTable,heap);

        if(!(evalExp.getType().equals(((RefType) getVarSymTable.getType()).getInner())))
            throw new MyException("The type of the expression should be the same as the one from the inner value!");

        int getFreePos= heap.getFirstFreeAddress();
        heap.put(getFreePos,evalExp);

        symbolTable.update(var_name,new RefValue(getFreePos,((RefType) getVarSymTable.getType()).getInner()));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(var_name,expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "new("+this.var_name.toString()+", "+this.expression.toString()+")";
    }

}
