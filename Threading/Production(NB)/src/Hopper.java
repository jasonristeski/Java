
public class Hopper extends Thread 
{
	private static boolean status = false;
	
	private boolean valve;
	private int quantity;
	private String ingredient;
	public Hopper(String stationIngredient)
	{
		ingredient = stationIngredient;
		valve = false;
		quantity = 1000;
	}
	
	public static void turnON()
	{
		Hopper.status = true;
	}
	
	public static void turnOFF()
	{
		Hopper.status = false;
	}
	
	public static boolean isOnline() 
	{
		return Hopper.status;
	}
	
	public synchronized void operate()
	{
		valve = !valve;
	}

	public synchronized boolean isCritical(int threshold)
	{
		return quantity <= threshold;
	}
	
	public synchronized int dispense(int requiredAmount)
	{
		int amount = 0;
		try
		{	
			Thread.sleep(10);
			if(quantity - requiredAmount < -1)
			{
				System.out.println("Station: " + ingredient + " - underflow");
				Hopper.turnOFF();
				amount = quantity;
				quantity = 0;
			}
			else
			{
				quantity -= requiredAmount;
				amount = requiredAmount;
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		return amount;
	}
	
	private synchronized void fill()
	{
		try
		{
			if(valve)
			{
				Thread.sleep(10);
				if(quantity + 100 > 1001)
				{
					System.out.println("Station: " + ingredient + " - overflow");
					Hopper.turnOFF();
					quantity = 1000;
				}
				else
					quantity += 100;
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(Hopper.isOnline())
		{
			fill();
		}
	}

	
	

	
	
}
