package Model;

public class Zahar implements Item{
    private int price =0;
    public Zahar()
    {
        this(0);
    }
    public Zahar(int price)
    {
        this.price =price;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String toString()
    {
        return "Sugar with price:"+this.price;
    }
}
