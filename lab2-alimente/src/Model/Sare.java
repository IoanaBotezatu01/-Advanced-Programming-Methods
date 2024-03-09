package Model;

public class Sare implements Item{
    private int price =0;
    public Sare()
    {
        this(0);
    }
    public Sare(int price)
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
        return "Salt with price: "+this.price;
    }

}
