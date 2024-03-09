package Repository;
import Model.Item;
public class InMemoryRepository implements IRepository{
    private Item[] alimente;
    private int size;
    public InMemoryRepository()
    {
        alimente=new Item[100];
        size=0;
    }

    @Override
    public void add(Item item)
    {
        alimente[size++]=item;
    }

    @Override
    public void remove(Item item)
    {int index = -1;
        for (int i = 0; i < size; i++) {
            if (alimente[i] != null && alimente[i].toString().equals(item.toString())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            try{
            throw new IllegalArgumentException("Product not found.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        if (index != -1) {
            for (int i = index; i < size - 1; i++) {
                alimente[i] = alimente[i + 1];
            }
            alimente[size - 1] = null;
            size--;
        }

    }

    @Override
    public Item[] getAll() {
        return this.alimente;
    }

    public int size()
    {
        return size;
    }
}
