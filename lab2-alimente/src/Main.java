
import Model.Item;
import Repository.InMemoryRepository;
import Controller.Controller;
import View.UI;

public class Main {
    public static void main(String[] args) {
        UI ui = new UI(new Controller(new InMemoryRepository()));
        ui.run();
    }
}