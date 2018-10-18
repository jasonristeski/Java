import java.io.*;

public class FileIO 
{
	private String fFileName;
	private int[] data;
	private int N;
	
	public FileIO(String aFileName)
	{
		fFileName = aFileName;
	}
	
	public int[] getData()
	{
		return this.data;
	}
	
	public void read()
	{
		try
		{
			FileReader lFile = new FileReader(fFileName);
			BufferedReader lReader = new BufferedReader(lFile);
			
			String temp = lReader.readLine();
			N = Integer.parseInt(temp); // N
			if(((N & (N-1)) == 0) && N > 0) // check if pow of 2
			{
				data = new int[N];
				for(int i = 0; i < N;i++)
				{
					data[i] = Integer.parseInt(lReader.readLine().trim());
				}
			}
			else
			{
				System.out.println("Input array size should be ^2 ");
				System.exit(-1);
			}
			lReader.close();
			lFile.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File " + fFileName + " not found.");
			System.exit(1);
		}
		catch(IOException e)
		{
			System.out.println("Error reading from file");
			System.exit(-1);
		}
	}
	
	public void write(int[] out)
	{
		try
		{
			FileWriter lFile = new FileWriter("out.txt");
			BufferedWriter lWriter = new BufferedWriter(lFile);
			for(int i = 0; i < out.length;i++)
			{
				lWriter.write(String.valueOf(out[i]) + " ");
				
				
			}
			lWriter.close();
			lFile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}	
	}
}
