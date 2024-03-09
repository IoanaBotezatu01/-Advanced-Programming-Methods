package Repository;
import Model.Item;

public interface IRepository {
    void add(Item item);
    void remove(Item item);
    Item[] getAll();
    int size();

}
