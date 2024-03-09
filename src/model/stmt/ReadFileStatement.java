package model.stmt;
import model.ADT.*;
import model.MyException;
import model.PrgState;
import model.type.*;
import model.value.*;
import model.exp.*;
import java.io.BufferedReader;
import java.io.IOException;


public class ReadFileStatement implements IStmt{
    private Exp expression;
    private String variable_name;
    public ReadFileStatement(Exp exppression, String variable_name){
        this.expression = exppression;
        this.variable_name = variable_name;
    }
    public Exp getExpression(){
        return this.expression;
    }
    public void setVariableName(String variable_name){
        this.variable_name = variable_name;
    }
    public void setExpression(Exp expression){
        this.expression = expression;
    }
    public String getVariableName(){
        return this.variable_name;
    }
    @Override
    public String toString(){
        return "ReadFileStatement(" +
                "expression=" + this.expression.toString() +
                ", variable_name=" + this.variable_name +'\'' +
                ')';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
       MyIDictionary<String, Value> symTable = state.getSymTable();
       MyIFileTable<String, BufferedReader> fileTable = state.getFileTable();
       MyIStack<IStmt> stack = state.getExeStack();
       MyIHeap<Integer,Value>heap=state.getHeap();

       if(symTable.isDefined(variable_name))
       {
           Value value = symTable.lookup(variable_name);
           Type type = value.getType();
           if(type.equals(new IntType())){
               StringValue stringValue = (StringValue) expression.eval(symTable,heap);
                String filename = stringValue.getValue();
                BufferedReader bufferedReader = fileTable.lookup(filename);
                try{
                    String line = bufferedReader.readLine();
                    IntValue intValue;
                    if(line == null){
                        intValue = new IntValue(0);
                    }
                    else{
                        intValue = new IntValue(Integer.parseInt(line));
                    }
                    symTable.update(variable_name, intValue);
                }
                catch(IOException e){
                    throw new MyException(e.getMessage());
                }

           }
           else{
               throw new MyException("Variable name is not of type int");
           }
       }
       return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStatement(expression,variable_name);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck(typeEnv);
        Type typevar=typeEnv.lookup(variable_name);
        if(!(typexp.equals(new StringType())))
            throw new MyException("Read file statement:The expression type must be a string!");
        if(!(typevar.equals(new IntType())))
            throw  new MyException("Read file statement:The variable type must be int!");
        return typeEnv;
    }

}
