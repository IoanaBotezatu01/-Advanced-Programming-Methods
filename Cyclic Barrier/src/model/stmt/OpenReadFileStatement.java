package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIFileTable;
import model.ADT.MyIHeap;
import model.ADT.MyIStack;

import model.PrgState;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;
import model.MyException;
import model.exp.Exp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class OpenReadFileStatement implements IStmt{
    private Exp expression;
    public OpenReadFileStatement(Exp givenExppression){
        this.expression = givenExppression;
    }
    public String toString(){
        return "openRFile(" +
                "exp=" + expression.toString() +
                ")";
    }
    public void setExpression(Exp givenExppression){
        this.expression = givenExppression;
    }
    public Exp getExpression(){
        return this.expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIFileTable<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer,Value> heap= state.getHeap();
        Value value = this.expression.eval(symbolTable,heap);
        if(value.getType().equals(new StringType())){
            StringValue stringValue = (StringValue)value;
            String file = stringValue.getValue();
            if(fileTable.isDefined(file)){
                throw new MyException("File already opened!");
            }
            else{
                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    fileTable.add(file, bufferedReader);
                }
                catch(IOException e){
                    throw new MyException("File not found!");
                }
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenReadFileStatement(expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=expression.typecheck(typeEnv);
        if(typexp.equals(new StringType()))
            return typeEnv;
        else
            throw  new MyException("Open read file:expression type must be a string!");
    }
}
