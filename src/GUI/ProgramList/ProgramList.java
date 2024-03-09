package GUI.ProgramList;

import GUI.ProgramController.ProgramController;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import javafx.scene.input.MouseEvent;

import model.MyException;
import model.PrgState;
import model.exp.*;
import model.ADT.*;
import model.stmt.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;
import view.RunExample;

import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class ProgramList {
    private ProgramController programExecutorController;
    private  int nrOfPrograms;

    public void setProgramExecutorController(ProgramController programExecutorController) {
        this.programExecutorController = programExecutorController;
    }
    @FXML
    private ListView<IStmt> programsListView;



    @FXML
    public void initialize() throws Exception
    {
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(MouseEvent actionEvent) {
        IStmt selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typecheck(new MyDictionary<String, Type>());
                PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                        new MyFileTable<String, BufferedReader>(), new MyHeap(),new MyBarrier(), selectedStatement);
                ArrayList<PrgState> l1 = new ArrayList<PrgState>();
                l1.add(prg1);
                IRepository repo1 = new Repository(l1, "log" + (id + 1) + ".txt");
                Controller controller = new Controller(repo1);
                programExecutorController.nrOfPrograms=this.nrOfPrograms;
                programExecutorController.setController(controller);

            } catch (MyException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }


    @FXML
    private ObservableList<IStmt> getAllStatements() throws Exception
    {
        List<IStmt> allStatements = new ArrayList<>();

        //int v; v=2;Print(v)
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        allStatements.add(ex1);

//int a;int b; a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+",new ValueExp(new IntValue(2)),new
                                ArithExp("*",new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp("+",new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        allStatements.add(ex2);


        //ex3 : bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        allStatements.add(ex3);

        //THE READ FROM FILE EXAMPLE:
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenReadFileStatement(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseReadFileStatement(new VarExp("varf"))))))))));

        allStatements.add(ex4);

        // Example 5
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        allStatements.add(ex5);

        // Example 6
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))),
                                                new PrintStmt(new ArithExp("+", new ReadHeap(new ReadHeap(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        allStatements.add(ex6);


        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))),
                                new CompStmt(new WriteHeap("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp("+", new ReadHeap(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        allStatements.add(ex7);


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
                                        )), new PrintStmt(new VarExp("v")))));

       allStatements.add(ex8);


        //int v;v=20;v=true;Ref int a; a=v;v=40;print(rH(rH(a)))
        IStmt ex9 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        // Using VarDeclStmt for reference variable
                                        new VarDeclStmt("a",new IntType()),
                                        new CompStmt(
                                                new AssignStmt("a", new VarExp("v")),
                                                new CompStmt(
                                                        new AssignStmt("v", new ValueExp(new IntValue(40))),
                                                        new PrintStmt(new VarExp("a"))))))));
        allStatements.add(ex9);


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
                                                                                new PrintStmt(new ReadHeap(new VarExp("a"))))))),
                                                // print(v);print(rH(a))
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeap(new VarExp("a")))))))));
        allStatements.add(ex10);


        IStmt ex11 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(
                                        new VarDeclStmt("cnt", new IntType()),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                        new CompStmt(
                                                                new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                                new CompStmt(
                                                                        new NewBarrierStmt("cnt", new ReadHeap(new VarExp("v2"))),
                                                                        new CompStmt(
                                                                                new ForkStmt(
                                                                                        new CompStmt(
                                                                                                new AwaitStmt("cnt"),
                                                                                                new CompStmt(
                                                                                                        new WriteHeap("v1", new ArithExp("*", new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                                        new PrintStmt(new ReadHeap(new VarExp("v1")))
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new AwaitStmt("cnt"),
                                                                                                        new CompStmt(
                                                                                                                new WriteHeap("v2", new ArithExp("*", new ReadHeap(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                new CompStmt(
                                                                                                                        new WriteHeap("v2", new ArithExp("*", new ReadHeap(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                        new PrintStmt(new ReadHeap(new VarExp("v2")))
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new AwaitStmt("cnt"),
                                                                                                new PrintStmt(new ReadHeap(new VarExp("v3")))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        allStatements.add(ex11);
    this.nrOfPrograms=allStatements.size();

        return FXCollections.observableArrayList(allStatements);

    }
}
