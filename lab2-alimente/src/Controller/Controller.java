package Controller;

import Model.Item;
import Repository.IRepository;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    IRepository repo;
    public Controller(IRepository repo)
    {
        this.repo=repo;
    }
    public void addToRepo(Item item)
    {
        repo.add(item);
    }
    public void removeFromRepo(Item item){repo.remove(item);}
    public Item[] getAll()
    {

        return repo.getAll();
    }
    public List<Item> filterrepo(int price)
    {
        List<Item> result = new ArrayList<>();
        Item[] all = repo.getAll();
        for(int i = 0; i < repo.size(); i++)
        {
            Item item = all[i];
            if(item.getPrice() > price)
                result.add(item);
        }
        return result;
    }

}
