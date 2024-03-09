package model.exp;
import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp{
    private Exp e1;
    private Exp e2;
    public static final int ADD = 1;
    public static final int SUBTRACT = 2;
    public static final int MULTIPLY = 3;
    public static final int DIVIDE = 4;
    private int op; // 1-plus, 2-minus, 3-star, 4-divide
    public ArithExp(String stringOp, Exp e1, Exp e2){
        this.e1 = e1;
        this.e2 = e2;
        switch(stringOp){
            case "+" -> this.op = ADD;
            case "-" -> this.op = SUBTRACT;
            case "*" -> this.op = MULTIPLY;
            case "/" -> this.op = DIVIDE;
        }
    }
    public Exp getE1(){
        return this.e1;
    }
    public Exp getE2(){
        return this.e2;
    }
    public int getOp(){
        return this.op;
    }
    public void setE1(Exp e1){
        this.e1 = e1;
    }
    public void setE2(Exp e2){
        this.e2 = e2;
    }
    public void setOp(int op){
        this.op = op;
    }
    @Override
    public String toString()
    {
        return "ArithExp{"+
                "e1="+e1+
                ", e2="+e2+
                ", op="+op+
                '}';
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap<Integer,Value> heap) throws MyException {
        Value leftValue, rightValue;
        leftValue = e1.eval(symbolTable,heap);

        if (leftValue.getType().equals(new IntType())) {
            rightValue = e2.eval(symbolTable,heap);

            if (rightValue.getType().equals(new IntType())) {
                IntValue intValue1 = (IntValue) leftValue;
                IntValue intValue2 = (IntValue) rightValue;
                int leftIntValue, rightIntValue;
                leftIntValue = intValue1.getVal();
                rightIntValue = intValue2.getVal();

                if (op == ArithExp.ADD) {
                    return new IntValue(leftIntValue + rightIntValue);
                }
                if (op == ArithExp.SUBTRACT) {
                    return new IntValue(leftIntValue - rightIntValue);
                }
                if (op == ArithExp.MULTIPLY) {
                    return new IntValue(leftIntValue * rightIntValue);
                }
                if (op == ArithExp.DIVIDE) {
                    if (rightIntValue == 0) {
                        throw new MyException("division by zero");
                    } else {
                        return new IntValue(leftIntValue / rightIntValue);
                    }
                }
            } else {
                throw new MyException("second operand is not an integer");
            }
        } else {
            throw new MyException("first operand is not an integer");
        }

        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if( typ1.equals(new IntType()) ){
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");

    }


}

