import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Hopper extends Thread 
{
	private static boolean status = false;
	
	private boolean valve;
	private int quantity;
	private String ingredient;
		
	private ReentrantLock lock;
	private Condition isEmpty;
	private Condition isFull;
	
	public Hopper(String stationIngredient, ReentrantLock permissionLock)
	{
		ingredient = stationIngredient;
		valve = false;
		quantity = 1000;
		lock = permissionLock;
		isEmpty = lock.newCondition();
		isFull = lock.newCondition();
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
	
	public void operate()
	{
		lock.lock();
		try
		{
			valve = !valve;
		}
		finally
		{
			lock.unlock();
		}
	}

		
	public synchronized int dispense(int requiredAmount)
	{
		int amount = 0;
		lock.lock();
		try
		{	
			Thread.sleep(10);
			if(quantity - requiredAmount < -1)
			{
				isEmpty.await();
			}
			else
			{
				quantity -= requiredAmount;
				amount = requiredAmount;
				isFull.signal();
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
		return amount;
	}
	
	private void fill()
	{
		lock.lock();
		try
		{
			if(valve)
			{
				Thread.sleep(10);
				if(quantity + 100 > 1001)
				{
					quantity = 1000;
					isFull.await();
				}
				else
				{
					quantity += 100;
					isEmpty.signal();
				}
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
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
