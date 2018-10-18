import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedIntStack extends Thread
{

	private int MAXSIZE = 10;
	private ArrayList<Integer> list = new ArrayList();
	private Lock l = new ReentrantLock();
	private Condition full = l.newCondition();
	private Condition empty = l.newCondition();
	
	
	public int pop() 
	{
		l.lock();
		int result;
		try
		{
		
			if (list.size() == 0)
				try {
					empty.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			full.signal();
			result = list.remove(0);
			
		}
		finally
		{
			l.unlock();
		}
		return result;
	}

	public boolean push(int number) 
	{
		
		l.lock();
		
		try
		{
			System.out.print("PUSH");
			if(list.size() == MAXSIZE)
				empty.await();
			list.add(0,number);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			l.unlock();
		}
		return true;
		
	}

	public int getSize()
	{
		return list.size();
	}


	
	public static void main(String[] args)
	{
		BoundedIntStack stack = new BoundedIntStack();
		
		worker w1 = new worker(stack);
		worker w2 = new worker(stack);
		worker w3 = new worker(stack);
		
		w1.start();
		w2.start();
		w3.start();
	}
}
