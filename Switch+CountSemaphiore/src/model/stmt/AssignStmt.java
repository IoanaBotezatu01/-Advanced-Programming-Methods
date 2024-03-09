package model.stmt;

import model.ADT.*;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.Type;
import model.value.Value;
public class AssignStmt implements IStmt{
    private Exp expression;
    private String id;
    public AssignStmt(String id, Exp exp){
        this.expression = exp;
        this.id = id;
    }
    public Exp getExpression() {
        return expression;
    }
    public String getId() {
        return id;
    }
    public void setExpression(Exp expression) {
        this.expression = expression;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String toString(){
        return id + "=" + expression.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIHeap<Integer,Value> heap= state.getHeap();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(id)){
            Value val = expression.eval(symTable,heap);
            Type typId = (symTable.lookup(id)).getType();
            if(val.getType().equals(typId))
                symTable.update(id, val);
            else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
        }
        else
            throw new MyException("the used variable" + id + " was not declared before");
        //return state;
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id,expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

}
