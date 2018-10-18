import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FileIO 
{
	private String fFileName;
	private int[][] fMatrixA;
	private int[][] fMatrixB;
	private int N;
	private int P;
	private int S;
	
	public FileIO(String aFileName)
	{
		P = 0;
		N = 0;
		S = 0;
		fFileName = aFileName;
	}
	
	public int[][] getMatrixA()
	{
		return this.fMatrixA;
	}
	
	public int[][] getMatrixB()
	{
		return this.fMatrixB;
	}
	
	public int getN()
	{
		return this.N;
	}
	
	public int getP()
	{
		return this.P;
	}
	
	public int getS()
	{
		return this.S;
	}
	
	public void read()
	{
		try
		{
			FileReader lFile = new FileReader(fFileName);
			BufferedReader lReader = new BufferedReader(lFile);
			
			String temp = lReader.readLine();
			N = Integer.parseInt(temp.substring(2)); // N
			fMatrixA = new int[N][N];
			fMatrixB = new int[N][N];
			
			temp = lReader.readLine();
			P = Integer.parseInt(temp.substring(2)); // P
			
			temp = lReader.readLine();
			S = Integer.parseInt(temp.substring(2)); // S
			
			lReader.readLine(); // Matrix  Label
			populateArray(lReader,fMatrixA);// Pass read for array to be populated. 
			
			lReader.readLine();// Matrix Label
			populateArray(lReader,fMatrixB);
			
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
	
	public void write(int[][] aMatrix)
	{
		try
		{
			FileWriter lFile = new FileWriter("output.txt");
			BufferedWriter lWriter = new BufferedWriter(lFile);
			for(int i = 0; i < aMatrix.length;i++)
			{
				for(int j = 0; j < aMatrix.length;j++)
				{
					lWriter.write(String.valueOf(aMatrix[i][j]) + " ");
				}
				lWriter.newLine();
			}
			//System.out.println(Arrays.deepToString(aMatrix));
			lWriter.close();
			lFile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private int[][] populateArray(BufferedReader aReader,int[][] aMatrix)
	{
		for(int i = 0; i < aMatrix.length;i++)
		{
			try
			{
				String[] lData = aReader.readLine().split(" ");
				for(int j = 0; j < lData.length;j++)
					aMatrix[i][j] = Integer.parseInt(lData[j]);
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return aMatrix;
	}
}
