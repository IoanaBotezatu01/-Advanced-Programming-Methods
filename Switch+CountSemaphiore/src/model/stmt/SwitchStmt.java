package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.type.BoolType;
import model.type.Type;
import model.exp.*;

public class SwitchStmt implements IStmt{
    private Exp exp;
    private Exp exp1;
    private IStmt stmt1;
    private Exp exp2;
    private IStmt stmt2;
    private IStmt stmt3;
    public SwitchStmt(Exp exp,Exp exp1,IStmt stmt1,Exp exp2,IStmt stmt2,IStmt stmt3)
    {
        this.exp=exp;
        this.exp1=exp1;
        this.stmt1=stmt1;
        this.exp2=exp2;
        this.stmt2=stmt2;
        this.stmt3=stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack=state.getExeStack();
        IStmt switchStmt= new IfStmt(new RelationalExpressions("==",exp,exp1),stmt1,
                new IfStmt(new RelationalExpressions("==",exp,exp2),stmt2,stmt3));
        stack.push(switchStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp,exp1,stmt1,exp2,stmt2,stmt3);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp=exp.typecheck(typeEnv);
        Type typeExp1=exp1.typecheck(typeEnv);
        Type typeExp2=exp.typecheck(typeEnv);
        if(typeExp.equals(typeExp1))
            if(!typeExp1.equals(typeExp2))
            throw new MyException("The Expression for Switch do not have the same type!");
        else
            { stmt1.typecheck(typeEnv.deepCopy());
                stmt2.typecheck(typeEnv.deepCopy());
                stmt3.typecheck(typeEnv.deepCopy());
                return typeEnv;
            }
        else throw new MyException("The Expression for Switch do not have the same type!");

    }

    @Override
    public String toString() {
        return "Switch(" + exp +")"+
                "(case " + exp1 +":"+ stmt1 +")"+
                "(case "+exp2+":"+stmt2+")"+
                stmt3;
    }
}
