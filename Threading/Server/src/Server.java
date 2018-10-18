
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server 
{
	static int fPort = 9001;
	
	public static void main(String[] args)
	{
		System.out.println("Server Online...");
		try
		{
			ServerSocket lListener = new ServerSocket(fPort);
			ExecutorService lExec = Executors.newFixedThreadPool(4); // Assuming WT = 3, ST =1
			while(true)
			{
				Socket lSocket = lListener.accept();
				lExec.submit(new Task(lSocket));
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
