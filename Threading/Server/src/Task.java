import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class Task extends Thread
{

	private Socket fSocket;
	private int fCount;
	
	public Task(Socket aSocket)
	{
		this.fCount = 0;
		this.fSocket = aSocket;
		
	}
	
	
	public void run()
	{
		try
		{
			boolean read = false;
			String lID = "";
			BufferedReader lReader = new BufferedReader(new InputStreamReader(fSocket.getInputStream()));
			PrintWriter lWriter = new PrintWriter(fSocket.getOutputStream(),true);
			lWriter.println("connection acknowledged");
			while(!fSocket.isClosed())
			{
				String lData = lReader.readLine();
				
				if(!read)// get ID of client 
				{
					lID = lData.split(" ")[1];
					read = true;
				}
				
				// All requests done, client sent goodbye 
				if(lData.equals("Bye Bye!"))
				{
					// Show Result 
					System.out.println(lID +" : "+ fCount);
					
					// Close, no longer required 
					lWriter.close();
					lReader.close();
					fSocket.close();
					
				}
				else
				{
					fCount++;
					lWriter.println("request acknowledged"); // Request processed, waiting for new...
				}
				Thread.sleep(1000);
			}
				
		}
		catch(IOException e )
		{
			e.printStackTrace();
		}
		catch(InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}
}
