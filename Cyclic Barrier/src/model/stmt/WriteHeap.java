package model.stmt;

import model.ADT.MyDictionary;
import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class WriteHeap implements IStmt{
    private String var_name;
    private Exp expression;
    public  WriteHeap(String var_name,Exp expression)
    {
        this.var_name=var_name;
        this.expression=expression;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        MyIHeap<Integer,Value> heap= state.getHeap();
        if(!(symTbl.isDefined(var_name)))
            throw new MyException("The variable name is not defined in the SymTable!");

        Value getVarSymTable=symTbl.lookup(var_name);
        if(!((getVarSymTable.getType()) instanceof RefType))
            throw new MyException("The type of the variable is not RefType!");

        RefValue refValSymTable=(RefValue) getVarSymTable;
        if(!(heap.containsKey(refValSymTable.getAddr())))
            throw new MyException("The address is not a key in the heap!");

        Value evalExp=expression.eval(symTbl,heap);
        if(!(evalExp.getType().equals(((RefType) getVarSymTable.getType()).getInner())))
            throw new MyException("The result type and the locationType are not the same!");

        heap.update(refValSymTable.getAddr(),evalExp);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeap(var_name,expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp =expression.typecheck(typeEnv);
        Type typevar=typeEnv.lookup(var_name);
        if(!(typevar.equals(new RefType(typexp))))
            throw  new MyException("Write heap statement:right side and the left side are different types!");
        return typeEnv;
    }
}
