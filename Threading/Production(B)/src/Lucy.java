
public class Lucy extends Employee implements Runnable 
{

	private Pie pie;
	private boolean alert = false;

	private Object signal;
	
	public void assignSignal(Object buzzer)
	{
		signal = buzzer;
	}
	
	public void accept(Pie crust)
	{
		synchronized(signal)
		{
			pie = crust;
			alert = true;
			try
			{
				signal.wait();
			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	private void work()
	{
		synchronized(signal)
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
					pie.good();
					signal.notify();
				}
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
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
