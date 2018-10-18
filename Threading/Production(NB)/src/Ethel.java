import java.util.Random;

public class Ethel extends Employee implements Runnable
{
	private Random rand = new Random();
	
	
	public Ethel()
	{
		
	}
	
	public void run()
	{
		while(Hopper.isOnline())
		{
			if(filling.isCritical(200))
				operate(filling);
			if(flavour.isCritical(10))
				operate(flavour);
			if(topping.isCritical(100))
				operate(topping);
		}

	}
	
	public synchronized void operate(Hopper station)
	{
		try
		{
			int delay = rand.nextInt((40 - 10) + 1) + 10;
			Thread.sleep(delay);
	 		station.operate(); // open valve
	 		// Station fills itself when valve is open
	 		delay = rand.nextInt((40 - 10) + 1) + 10;
	 		Thread.sleep(delay);
	 		station.operate(); // close valve;
		
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
}
