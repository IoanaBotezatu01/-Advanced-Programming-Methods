package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.exp.RelationalExpressions;
import model.exp.VarExp;
import model.type.IntType;
import model.type.Type;

public class ForStmt implements IStmt{
    private String v;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt stmt;
    public ForStmt(String v,Exp exp1,Exp exp2,Exp exp3,IStmt stmt){
        this.v=v;
        this.exp1=exp1;
        this.exp2=exp2;
        this.exp3=exp3;
        this.stmt=stmt;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIStack<IStmt> stack=state.getExeStack();
        IStmt forStmt=new CompStmt(new AssignStmt(v,exp1)
                ,new WhileStmt(new RelationalExpressions("<",new VarExp(v),exp2)
                ,new CompStmt(stmt,new AssignStmt(v,exp3))));
        stack.push(forStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(v,exp1,exp2,exp3,stmt);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        Type type3 = exp3.typecheck(typeEnv);
        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType())) {
            return typeEnv;
        }
        else {
            throw new MyException("For statement has wrong types! All must be int!");
        }
    }
    @Override
    public String toString() {
        return "for(" + v+ "=" + exp1 + "; " + v + "<" + exp2 + "; " + v+ "=" +
                exp3 + ") { " + stmt+ "}";
    }
}
