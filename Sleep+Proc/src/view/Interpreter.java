//package view;

import model.*;
import model.PrgState;
import model.MyException;
import model.ADT.MyIStack;
import model.ADT.MyStack;
import model.exp.*;
import model.stmt.AssignStmt;
import model.stmt.CompStmt;
import model.stmt.IStmt;
import model.stmt.VarDeclStmt;
import model.type.StringType;
import model.value.StringValue;
import model.ADT.MyIStack;
import model.ADT.MyStack;
import model.exp.ValueExp;
import model.ADT.MyIFileTable;
import model.ADT.MyFileTable;
import model.exp.VarExp;
import model.ADT.*;
import model.stmt.*;
import model.type.*;
import model.value.*;
import repository.*;
import controller.*;
import view.*;
import java.util.List;

import java.io.BufferedReader;
/*
public class Interpreter {
    public static void main(String[] args){
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        //int v; v=2;Print(v)
        IStmt ex4= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        try {
            ex4.typecheck(new MyDictionary<String, Type>());
           MyIStack<IStmt> stk4 = new MyStack<IStmt>();
           MyIDictionary<String, Value> symtbl4 = new MyDictionary<String, Value>();
           MyIList<Value> out4 = new MyList<Value>();
           MyIFileTable<String, BufferedReader> fileTable4 = new MyFileTable<String, BufferedReader>();
           MyIHeap<Integer, Value> heap4 = new MyHeap<Integer, Value>();

           PrgState prg4 = new PrgState(stk4, symtbl4, out4, fileTable4, heap4, ex4);
           List<PrgState> l4 = List.of(prg4);
           IRepository repo4 = new Repository(l4, "log1.txt");
           Controller ctr4 = new Controller(repo4);
           menu.addCommand(new RunExample("1", ex4.toString(), ctr4));
       }
        catch (MyException e){System.out.println("Ex1 TypeCheck Error:"+e.getMessage());}

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+",new ValueExp(new IntValue(2)),new
                                ArithExp("*",new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp("+",new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        try {
            ex2.typecheck(new MyDictionary<String, Type>());
            MyIStack<IStmt> stk2 = new MyStack<>();
            MyIDictionary<String, Value> symtbl2 = new MyDictionary<>();
            MyIList<Value> out2 = new MyList<>();
            MyIFileTable<String, BufferedReader> fileTable2 = new MyFileTable<>();
            MyIHeap<Integer, Value> heap2 = new MyHeap<>();

            PrgState prg2 = new PrgState(stk2, symtbl2, out2, fileTable2, heap2, ex2);
            List<PrgState> myPrgList2 = List.of(prg2);
            IRepository repo2 = new Repository(myPrgList2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        }catch (MyException e){System.out.println("Ex2 TypeCheck Error:"+e.getMessage());}

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        try {
            ex3.typecheck(new MyDictionary<String, Type>());
        MyIStack<IStmt> stk3 = new MyStack<>();
        MyIDictionary<String, Value> symtbl3 = new MyDictionary<>();
        MyIList<Value> out3 = new MyList<>();
        MyIFileTable<String, BufferedReader> fileTable3 = new MyFileTable<>();
        MyIHeap<Integer,Value> heap3=new MyHeap<>();

        PrgState prgState3 = new PrgState(stk3, symtbl3, out3,fileTable3,heap3, ex3);
        List<PrgState> myPrgList3 = List.of(prgState3);
        IRepository repo3 = new Repository(myPrgList3, "log3.txt");
        Controller ctr3 = new Controller(repo3);
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        }catch (MyException e){System.out.println("Ex3 TypeCheck Error:"+e.getMessage());}


        //THE READ FROM FILE EXAMPLE:
        IStmt example1 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenReadFileStatement(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseReadFileStatement(new VarExp("varf"))))))))));
        try {
            example1.typecheck(new MyDictionary<String, Type>());
        MyIStack<IStmt> stk1 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl1 = new MyDictionary<String,Value>();
        MyIList<Value> out1 = new MyList<Value>();
        MyIFileTable<String, BufferedReader> fileTable1 = new MyFileTable<String, BufferedReader>();
        MyIHeap<Integer,Value> heap1=new MyHeap<>();

        PrgState prg1 = new PrgState(stk1,symtbl1,out1,fileTable1,heap1,example1);
        List<PrgState> l1 = List.of(prg1);
        IRepository repo1 = new Repository(l1,"testOpenReadClose.txt");
        Controller ctr1 = new Controller(repo1);
        menu.addCommand(new RunExample("4", example1.toString(), ctr1));
    }catch (MyException e){System.out.println("Ex4 TypeCheck Error:"+e.getMessage());}

        // Example 5
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        try {
            ex5.typecheck(new MyDictionary<String, Type>());
        MyIStack<IStmt> stk5 = new MyStack<>();
        MyIDictionary<String, Value> symtbl5 = new MyDictionary<>();
        MyIList<Value> out5 = new MyList<>();
        MyIFileTable<String, BufferedReader> fileTable5 = new MyFileTable<>();
        MyIHeap<Integer, Value> heap5 = new MyHeap<Integer, Value>();

        PrgState prgState5 = new PrgState(stk5, symtbl5, out5, fileTable5,  heap5, ex5);
        List<PrgState> myPrgList5 = List.of(prgState5);
        IRepository repo5 = new Repository(myPrgList5, "logAlloc.txt");
        Controller controller5 = new Controller(repo5);
        menu.addCommand(new RunExample("5", ex5.toString(), controller5));
        }catch (MyException e){System.out.println("Ex5 TypeCheck Error:"+e.getMessage());}

        // Example 6
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))),
                                                new PrintStmt(new ArithExp("+", new ReadHeap(new ReadHeap(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        try {
            ex6.typecheck(new MyDictionary<String, Type>());
        MyIStack<IStmt> stk6 = new MyStack<>();
        MyIDictionary<String, Value> symtbl6 = new MyDictionary<>();
        MyIList<Value> out6 = new MyList<>();
        MyIFileTable<String, BufferedReader> fileTable6 = new MyFileTable<>();
        MyIHeap<Integer, Value> heap6 = new MyHeap<>();

        PrgState prgState6 = new PrgState(stk6, symtbl6, out6, fileTable6, heap6, ex6);
        List<PrgState> myPrgList6 = List.of(prgState6);
        IRepository repo6 = new Repository(myPrgList6, "logHeapRead.txt");
        Controller controller6 = new Controller(repo6);
        menu.addCommand(new RunExample("6", ex6.toString(), controller6));
    }catch (MyException e){System.out.println("Ex6 TypeCheck Error:"+e.getMessage());}
        // Example 7
        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))),
                                new CompStmt(new WriteHeap("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp("+", new ReadHeap(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        try {
            ex7.typecheck(new MyDictionary<String, Type>());
        MyIStack<IStmt> stk7 = new MyStack<>();
        MyIDictionary<String, Value> symtbl7 = new MyDictionary<>();
        MyIList<Value> out7 = new MyList<>();
        MyIFileTable<String, BufferedReader> fileTable7 = new MyFileTable<>();
        MyIHeap<Integer,Value> heap7 = new MyHeap<>();

        PrgState prgState7 = new PrgState(stk7, symtbl7, out7, fileTable7, heap7,ex7);
        List<PrgState> myPrgList7 = List.of(prgState7);
        IRepository repo7 = new Repository(myPrgList7, "logHeapWrite.txt");
        Controller controller7 = new Controller(repo7);
        menu.addCommand(new RunExample("7", ex7.toString(), controller7));
        }catch (MyException e){System.out.println("Ex7 TypeCheck Error:"+e.getMessage());}

        //Example 8
        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex8 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExpressions(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1))))
                                        )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        try {
            ex8.typecheck(new MyDictionary<String, Type>());
        MyIStack<IStmt> stk8 = new MyStack<>();
        MyIDictionary<String, Value> symtbl8 = new MyDictionary<>();
        MyIList<Value> out8 = new MyList<>();
        MyIFileTable<String, BufferedReader> fileTable8= new MyFileTable<>();
        MyIHeap<Integer,Value> heap8=new MyHeap<>();

        PrgState prgState8 = new PrgState(stk8, symtbl8, out8, fileTable8, heap8,ex8);
        List<PrgState> myPrgList8 = List.of(prgState8);
        IRepository repo8 = new Repository(myPrgList8, "logExample8.txt");
        Controller controller8 = new Controller(repo8);
        menu.addCommand(new RunExample("8", ex8.toString(), controller8));
    }catch (MyException e){System.out.println("Ex8 TypeCheck Error:"+e.getMessage());}


        //Example 9 - garbage collector

        //int v;v=20;v=30;Ref int a; a=v;v=40;print(rH(rH(a)))
        IStmt ex9 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(30))),
                                new CompStmt(
                                        // Using VarDeclStmt for reference variable
                                        new VarDeclStmt("a",new IntType()),
                                        new CompStmt(
                                                new AssignStmt("a", new VarExp("v")),
                                                new CompStmt(
                                                        new AssignStmt("v", new ValueExp(new IntValue(40))),
                                                        new PrintStmt(new VarExp("a")))
                                                )
                                        )
                                )
                        )

        );
        try {
            ex9.typecheck(new MyDictionary<String, Type>());
        MyIStack<IStmt> stk9 = new MyStack<>();
        MyIDictionary<String, Value> symtbl9 = new MyDictionary<>();
        MyIList<Value> out9 = new MyList<>();
        MyIFileTable<String, BufferedReader> fileTable9= new MyFileTable<>();
        MyIHeap<Integer,Value> heap9=new MyHeap<>();

        PrgState prgState9 = new PrgState(stk9, symtbl9, out9, fileTable9, heap9,ex9);
        List<PrgState> myPrgList9 = List.of(prgState9);
        IRepository repo9 = new Repository(myPrgList9, "logExample9.txt");
        Controller controller9 = new Controller(repo9);
        menu.addCommand(new RunExample("9", ex9.toString(), controller9));
        }catch (MyException e){System.out.println("Ex9 TypeCheck Error:"+e.getMessage());}

        // int v; Ref int a; v=10;new(a,22);
        IStmt ex10 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                // fork(wH(a,30);v=32;print(v);print(rH(a)))
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteHeap("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new ReadHeap(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                // print(v);print(rH(a))
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeap(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            ex10.typecheck(new MyDictionary<String, Type>());
        MyIStack<IStmt> stk10 = new MyStack<>();
        MyIDictionary<String, Value> symtbl10 = new MyDictionary<>();
        MyIList<Value> out10 = new MyList<>();
        MyIFileTable<String, BufferedReader> fileTable10= new MyFileTable<>();
        MyIHeap<Integer,Value> heap10=new MyHeap<>();

        PrgState prgState10 = new PrgState(stk10, symtbl10, out10, fileTable10, heap10,ex10);
        List<PrgState> myPrgList10 = List.of(prgState10);
        IRepository repo10 = new Repository(myPrgList10, "logExampleFork.txt");
        Controller controller10 = new Controller(repo10);
        menu.addCommand(new RunExample("10", ex10.toString(), controller10));
    }catch (MyException e){System.out.println("Ex10 TypeCheck Error:"+e.getMessage());}

        menu.show();

    }
}
*/