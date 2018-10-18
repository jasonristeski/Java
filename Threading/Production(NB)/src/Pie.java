
public class Pie 
{
	private int filling = 0;
	private int flavour = 0;
	private int topping = 0;

	public Pie()
	{}
	
	public void add(String ingredient,int amount)
	{
		switch(ingredient)
		{
		case "filling":
			filling += amount;
			break;
			
		case "flavour":
			flavour += amount;
			break;
			
		case "topping":
			topping += amount;
			break;
		}
	}
	
	public void good()
	{
		if(filling == 200 && topping == 100 && flavour == 10 && Hopper.isOnline())
			System.out.println("GOOD");
		else
		{
			Hopper.turnOFF();
			System.out.println("BAD");
		}
	}
}
