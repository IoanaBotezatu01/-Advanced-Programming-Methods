package view;
import controller.Controller;
import model.MyException;

public class RunExample extends Command {
    private Controller controller;
    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }
    @Override
    public void execute() {
        try{
            controller.allStep();
        }catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
    public Controller getController(){
        return this.controller;
    }
}
