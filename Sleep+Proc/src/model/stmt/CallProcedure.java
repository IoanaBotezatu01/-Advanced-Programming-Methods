package model.stmt;

import model.ADT.*;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.Type;
import model.value.Value;

import java.util.List;

public class CallProcedure implements IStmt{
    private String procName;
    private List<Exp> exps;
    public CallProcedure(String  procName,List<Exp> exps)
    {
        this.procName=procName;
        this.exps=exps;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getTopSymTable();
        MyIHeap<Integer,Value> heap = state.getHeap();
        MyIProcTable procTable = state.getProcTable();
        if(procTable.containsKey(procName))
        {
            List<String> variables = procTable.get(procName).getKey();
            IStmt statement=procTable.get(procName).getValue();
            MyIDictionary<String,Value> newSymbolTable= new MyDictionary<>();
            for(String v:variables)
            {
                int i=variables.indexOf(v);
                newSymbolTable.add(v,exps.get(i).eval(symTable,heap));
            }
            state.getSymTable().push(newSymbolTable);
            state.getExeStack().push(new ReturnStmt());
            state.getExeStack().push(statement);
        }else throw new MyException("The Procedure does not exist in the ProcedureTable!");


        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CallProcedure(procName,exps);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Call " + procName  +
                "(" + exps.toString() +
                ')';
    }
}
