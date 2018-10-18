
public class Lucy extends Employee implements Runnable 
{

	private Pie pie;
	private boolean alert = false;
	
	public synchronized void accept(Pie crust)
	{
		pie = crust;
		alert = true;
		
	}
		
	private synchronized void work()
	{
		try
		{
			if(alert)
			{
				Thread.sleep(20);
				alert = false;
				pie.add("filling",filling.dispense(200));
				pie.add("flavour", flavour.dispense(10));
				pie.add("topping",topping.dispense(100));
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
			work();
		}
	}
	
	
	

}
