package Model;

public class Faina implements Item{
    private int price =0;
    public Faina()
    {
        this(0);
    }
    public Faina(int price)
    {
        this.price =price;
    }
    @Override
    public int getPrice()
    {
        return price;
    }
    @Override
    public String toString()
    {
        return "Flour with price: "+ this.price;
    }
}
