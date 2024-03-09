package GUI;

import GUI.ProgramController.ProgramController;
import GUI.ProgramList.ProgramList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainGUI extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader programListLoader = new FXMLLoader();
        programListLoader.setLocation(MainGUI.class.getResource("ProgramList/ProgramList.fxml"));
        Parent programListRoot = programListLoader.load();
        Scene programListScene = new Scene(programListRoot, 500, 550);
        ProgramList programChooserController = programListLoader.getController();
        primaryStage.setTitle("Select a program");
        primaryStage.setScene(programListScene);
        primaryStage.show();

        FXMLLoader programExecutorLoader = new FXMLLoader();
        programExecutorLoader.setLocation(MainGUI.class.getResource("ProgramController/ProgramController.fxml"));
        Parent programExecutorRoot = programExecutorLoader.load();
        Scene programExecutorScene = new Scene(programExecutorRoot, 700, 700);
        ProgramController programExecutorController = programExecutorLoader.getController();
        programChooserController.setProgramExecutorController(programExecutorController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(programExecutorScene);
        secondaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}