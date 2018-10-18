import java.util.Random;

public class worker extends Thread
{
	BoundedIntStack stack;
	
	public worker(BoundedIntStack aStack)
	{
		stack = aStack;
	}
	
	public void run()
	{
		while(true)
		{
			Random r = new Random();
		
			stack.push(r.nextInt(200));
			try {
			
				Thread.sleep(2000);
		
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			
		}
	}
}
