package view;

import controller.Controller;
import model.ADT.*;
import model.MyException;
import model.PrgState;
import model.exp.ValueExp;
import model.exp.VarExp;
import model.stmt.*;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.util.List;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }
    public Controller getController() {
        return controller;
    }
    public void run() throws  InterruptedException {
        controller.allStep();

    }

}
