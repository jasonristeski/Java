package NxMPuzzle;
import java.io.*;


/**
 * NxMSolver --- Main Class.
 * @author Jason Risteski(7627149)
 */
class NxMSolver
{
	
	public static final int fMETHOD_COUNT = 4;
	public static Puzzle fPuzzle;
	public static SearchMethod[] fMethods;
	
	public static void main(String[] args)
	{
		initMethods();
		
		//args contains:
		// [0] - File/Puzzle
		// [1] - SearchMethod
		
		fPuzzle = readFile(args[0]);
		
		String lInput = args[1].toUpperCase();
		SearchMethod lMethod = null;
		
		for(int i = 0; i < fMETHOD_COUNT; i++)
		{
			if(fMethods[i].fId.compareTo(lInput) == 0)
			{
				lMethod = fMethods[i];
			}
		}
		
		// Method not found.
		if(lMethod == null)
		{
			System.out.println("Search method " + lInput + " not implemented");
			System.exit(1);
		}
		
		// Solve Problem
		direction[] thisSolution = lMethod.solve(fPuzzle);
		
		System.out.println(args[0] + "   " + lInput + "   " + lMethod.fSearched.size());
		
		if(thisSolution == null)
		{
			// no sol.
			System.out.println("No solution found.");
		}
		else
		{
			// Iterate and print solution.
			for(int j = 0; j < thisSolution.length; j++)
			{
				System.out.print(thisSolution[j].toString() + ";");
			}
			System.out.println();
		}
		// clear frontier for next use. 
		lMethod.reset();
		System.exit(0);
	}
	
	private static void initMethods()
	{
		fMethods = new SearchMethod[fMETHOD_COUNT];
		fMethods[0] = new GBFS();
		fMethods[1] = new BFS();
		fMethods[2] = new DFS();
		fMethods[3] = new AS();
	}
	
	private static Puzzle readFile(String aFileName) // this allow only one puzzle to be specified in a problem file 
	{
		
		try
		{
			//create file reading objects
			FileReader lReader = new FileReader(aFileName);
			BufferedReader lPuzzle = new BufferedReader(lReader);
			Puzzle lResult;
			
			String lSize = lPuzzle.readLine();
			
			String[] bothDimensions = lSize.split("x");
		
			// Assume NxM > 0?
			int lPuzzleColumns = Integer.parseInt(bothDimensions[0]);
			int lPuzzleRows = Integer.parseInt(bothDimensions[1]);
			
			int[][] lStartState = new int[lPuzzleColumns][lPuzzleRows];
			int[][] lGoalState = new int[lPuzzleColumns][lPuzzleRows];
			
			// Read read file states
			String lStartString = lPuzzle.readLine();
			lStartState = getState(lStartString, lStartState, lPuzzleColumns);
			String lGoalString = lPuzzle.readLine();
			lGoalState = getState(lGoalString, lGoalState, lPuzzleColumns);
			
			// Create puzzle object
			lResult = new Puzzle(lStartState, lGoalState);
						
			lPuzzle.close();
			return lResult;
		}
		catch(FileNotFoundException ex)
		{
			// Cant find file
			System.out.println("File " + aFileName + " not found.");
			System.exit(1);
		}
		catch(IOException ex)
		{
			System.out.println("Error in reading" + aFileName);
			System.exit(1);
		}
		return null;
	}
	
	private static int[][] getState(String aState, int[][] aGrid, int aWidth)
	{
		//Parse puzzle data to mutli arrays
		
		String[] lLocation = aState.split(" ");
		// 0,0 top left
		// N,M bottom right
		int x = 0;	
		int y = 0;
		
		for(int i = 0; i < lLocation.length; i++)
		{
			//tileLocations[i] is i+1 tile.
			int lTemp = Integer.parseInt(lLocation[i]);
			
			//within bounds?
			if (x >= aWidth) 
			{
				// Typewriter return. 
				x = 0;
				y++;
			}
			aGrid[x][y] = lTemp;
			x++;
		}
		return aGrid;
	}
}