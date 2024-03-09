module GUI {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    opens GUI.ProgramList to javafx.fxml;
    opens GUI.ProgramController to javafx.fxml;
    exports GUI;

}