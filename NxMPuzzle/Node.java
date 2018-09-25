package NxMPuzzle;
import java.util.*;

/**
 * Node represents state in puzzle.
 * @author Jason Risteski(7627149)
 *
 */
public class Node 
{
	public static final int STEP_COST = 1;
	
	public int[][] fPuzzle;
	public Node fParent;
	public ArrayList<Node> fChildren;
	public int fCost;
	public int fHValue;
	public int fEValue;
	public direction fPathFromParent;
	
	/**
	 * Node Constructor initializes Node
	 * @param aParent Parent of Node
	 * @param aFromParent Path from parent(direction)
	 * @param aPuzzle Input problem
	 */
	public Node(Node aParent,direction aFromParent,int[][] aPuzzle)
	{
		fParent = aParent;
		fPathFromParent = aFromParent;
		fPuzzle = aPuzzle;
		fCost = fParent.fCost + STEP_COST;
		fEValue = 0;
		fHValue = 0; 
	}
	
	/**
	 * Node Constructor 
	 * @param aPuzzle Puzzle problem
	 */
	public Node(int[][] aPuzzle)
	{
		fParent = null;
		fPathFromParent = null;
		fPuzzle = aPuzzle;
		fCost = 0;
		fEValue = 0;
		fHValue = 0; 
	}
	
	/**
	 * Returns fEvalue
	 * @return Returns EvaluationFunction
	 */
	public int getEvaluationFunction()
	{
		return fEValue;
	}
	
	/**
	 * @return Step cost occurred whilst traversing
	 */
	public int getCost()
	{
		return fCost; 
	}
	
	/**
	 * Set EvaluationFunction
	 * @param aValue EvaluationFunction result
	 */
	public void setEvaluationFunction(int aValue)
	{
		fEValue = aValue;
	}
	
	/**
	 * Locates blankCell and its appropriate valid moves.
	 * @return direction array
	 */
	public direction[] getValidActions()
	{
		direction[] lResult;
		int[] lZeroLocation = {0,0};
		
		try
		{
			lZeroLocation = findBlankCell();
		}
		catch(InvalidPuzzleException e)
		{
			System.out.println(e.toString());
			System.out.println("InvalidPuzzleException.Aborting!");
			System.exit(1);
		}
		
		lResult = new direction[countMovements()];
		int i = 0;
		if(lZeroLocation[0] == 0)
		{
			// Zero is in far left, only right
			lResult[i++] = direction.RIGHT;
		}
		else if(lZeroLocation[0] == (fPuzzle.length -1))
		{
			// Zero is far right, only left
			lResult[i++] = direction.LEFT;
		}
		else 
		{
			lResult[i++] = direction.LEFT;
			lResult[i++] = direction.RIGHT;
		}
		
		if((lZeroLocation[1] == 0))
		{
			// Zero is top row, only down
			lResult[i++] = direction.DOWN;
		}
		else if (lZeroLocation[1] == (fPuzzle[1].length -1))
		{
			
			// Zero is bottom row,only up
			lResult[i++] = direction.UP;
		}
		else 
		{
			lResult[i++] = direction.UP;
			lResult[i++] = direction.DOWN;
		}
		
		return lResult;
				
	}

	// 
	private int countMovements() 
	{
		// At least two possible moves
		int lResult = 2;
		try
		{
			// find cell
			int[] lBlankCell = findBlankCell();
			int lColumn= fPuzzle.length -1;
			int lRow = fPuzzle[1].length -1;
			int[] lDimension = {lColumn,lRow};
			
			
			for(int i = 0; i < 2; i++)
			{
				if(lBlankCell[i] == 0 || lBlankCell[i] == lDimension[i])
				{}
				else
					lResult++;
			}
		}
		catch (InvalidPuzzleException e)
		{
			System.out.println(e.toString());
			System.out.println("InvalidPuzzleException");
			
		}
		return lResult;
	}

	// Find blankcell in Puzzle
	private int[] findBlankCell() throws InvalidPuzzleException
	{
		// Iterate over puzzle
		for(int i = 0; i < fPuzzle.length;i++)
		{
			for(int j = 0; j < fPuzzle[i].length;j++)
			{
				// BlankCell?
				if(fPuzzle[i][j] == 0)
				{
					int[] lResult = {i,j};
					return lResult;
				}
			}
		}
		throw new InvalidPuzzleException(this);
	}
	
	private int[][] cloneArray(int[][] aCloneArray)
	{
		// Clones array
		int[][] lResult = new int[aCloneArray.length][aCloneArray[0].length];
		for(int i = 0; i< aCloneArray.length;i++)
		{
			for(int j = 0; j < aCloneArray[i].length;j++)
			{
				lResult[i][j] = aCloneArray[i][j];
			}
		}
		return lResult;
	}
	
	public Node move(direction aDirection) throws CantMoveThatWayException
	{
		// Copy,return after cells moved.
		Node lResult = new Node(this,aDirection,cloneArray(this.fPuzzle));
		
		int[] lZeroLocation = {0,0};
		
		try
		{
			lZeroLocation = findBlankCell();
		}
		catch(InvalidPuzzleException e)
		{
			System.out.println(e.toString());
			System.out.println("findBlankCell() threw InvalidPuzzleException...Aborting!");
			System.exit(1);
		}
		
		try
		{
			if(aDirection == direction.UP)
			{
				lResult.fPuzzle[lZeroLocation[0]][lZeroLocation[1]] = lResult.fPuzzle[lZeroLocation[0]][lZeroLocation[1] -1];
				lResult.fPuzzle[lZeroLocation[0]][lZeroLocation[1] -1] = 0;
			}
			else if (aDirection == direction.DOWN)
			{
				lResult.fPuzzle[lZeroLocation[0]][lZeroLocation[1]] = lResult.fPuzzle[lZeroLocation[0]][lZeroLocation[1] +1];
				lResult.fPuzzle[lZeroLocation[0]][lZeroLocation[1]+1] = 0;
			}
			else if(aDirection == direction.LEFT)
			{
				lResult.fPuzzle[lZeroLocation[0]][lZeroLocation[1]] = lResult.fPuzzle[lZeroLocation[0] -1][lZeroLocation[1]];
				lResult.fPuzzle[lZeroLocation[0]-1][lZeroLocation[1]] = 0;
			}
			else //direction.RIGHT
			{
				lResult.fPuzzle[lZeroLocation[0]][lZeroLocation[1]] = lResult.fPuzzle[lZeroLocation[0]+1][lZeroLocation[1]] ;
				lResult.fPuzzle[lZeroLocation[0]+1][lZeroLocation[1]] = 0; 
			}
			return lResult;
		}
		catch(IndexOutOfBoundsException ex)
		{
			System.out.println(ex.toString());
			throw new CantMoveThatWayException(this, aDirection);
		}
	}
	
	public boolean equals(Object aObject) throws ClassCastException
	{
		Node ltemp = (Node)aObject; // cast aobject as node
		for(int i = 0;i < fPuzzle.length;i++)
		{
			for(int j = 0; j < fPuzzle[i].length;j++)
			{
				if(this.fPuzzle[i][j] != ltemp.fPuzzle[i][j]) // are puzzle elements at goal, return false if one element out of place.
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Compares EvaluationFunction of two nodes.
	 * @param aNode Node to compare with
	 * @return Difference in evaluationFunction
	 */
	public int compareTo(Node aNode)
	{
		return fEValue - aNode.getEvaluationFunction();
	}
	
	/**
	 * Returns Valid children of parent node.
	 * @return Valid Children of parent node
	 */
	public ArrayList<Node> explore()
	{
		direction[] lPossibleMoves = getValidActions();
		fChildren = new ArrayList<Node>();
		for(int i = 0; i < lPossibleMoves.length;i++)
		{
			try
			{
				fChildren.add(move(lPossibleMoves[i]));
			}
			catch(CantMoveThatWayException e)
			{
				System.out.println(e.toString());
				System.out.println("Aborting");
				System.exit(1);
			}
		}
		return fChildren;
	}
	
	/**
	 * Goes through each parent until all states from Goal to start are explored revelaing path
	 * @return Path to goal state
	 */
	public direction[] getPathToState()
	{
		direction[] lResult;
		if(fParent == null)
		{
			lResult = new direction[0];
			return lResult;
		}
		else
		{
			direction[] lPathToParent = fParent.getPathToState();
			lResult = new direction[lPathToParent.length + 1];
			for(int i = 0; i < lPathToParent.length;i++)
			{
				lResult[i] = lPathToParent[i];
			}
			lResult[lResult.length -1] = this.fPathFromParent;
			return lResult;
		}
	}
}
