
public class Factory extends Thread 
{
	
	private Lucy lucy;
	private Ethel ethel;
	
	private Hopper filling;
	private Hopper flavour;
	private Hopper topping;
	
	
	public Factory(Lucy pieEmployee, Ethel stationEmployee)
	{
		filling = new Hopper("filling");
		flavour = new Hopper("flavour");
		topping = new Hopper("topping");
		
		lucy = pieEmployee;
		ethel = stationEmployee;
		lucy.assign(filling, flavour, topping);
		ethel.assign(filling, flavour, topping);
	}
	
	
	public void run()
	{
		try
		{
			// Start Factory
			Hopper.turnON(); // Start Hoppers	
			filling.start();
			flavour.start();
			topping.start();
			
			// Start workers 
			Thread pieWorker = new Thread(lucy);
			Thread stationWorker = new Thread(ethel);
			pieWorker.start();
			stationWorker.start();
			
			int pieCount = 0;
			int dayCount = 1;
			int factoryCount = 0;
			long endTime = 0;
			long startTime = System.currentTimeMillis();
			while(Hopper.isOnline())
			{
				lucy.accept(new Pie());
				if(pieCount > 8)
				{
					endTime = System.currentTimeMillis();
					System.out.println("Day: " + dayCount + " - production rate: " + (endTime - startTime)/1000.00);
					dayCount++;
					pieCount = 0;
					startTime = System.currentTimeMillis();
				}
				else
					pieCount++;
				factoryCount++;
				Thread.sleep(50);
			}
			System.out.println("Lucy and Ethel fired after " + dayCount + " days working. Producing " + factoryCount + " good pies");
		
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args)
	{
		Factory pieFactory = new Factory(new Lucy(),new Ethel());
		pieFactory.start();
	}
}
