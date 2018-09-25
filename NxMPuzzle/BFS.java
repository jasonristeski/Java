package NxMPuzzle;
import java.util.*;

/**
 *  BFS - Implements Breadth First Search (BFS) strategy. Frontier is a queue.
 * @author Jason Risteski (7627149)
 *
 */
public class BFS extends SearchMethod {
	
	/**
	 * BFS Constructor
	 */
	public BFS()
	{
		fId = "BFS";
		fName = "Breadth-First Search";
		fFrontier = new LinkedList<Node>();
		fSearched = new LinkedList<Node>();
	}
		
	/**
	 * Adds element to frontier if it has not been explored.
	 * @return Returns true if node has not been explored and has been added to Frontier. 
	 * @param aNode to be added to frontier
	 */
	public boolean add(Node aNode)
	{
		//Previously Explored?
		if(fSearched.contains(aNode) || fFrontier.contains(aNode))
		{
			return false;
		}
		else
		{
			// Add to frontier 
			fFrontier.add(aNode);
			return true;
		}
	}
	
	/**
	 * Logical implementation of BFS strategy.
	 * @param Puzzle problem
	 * @return Array of directions if solution is found, null if no solution was found.
	 */
	public direction[] solve(Puzzle puzzle) {
		add(puzzle.fStart);
		ArrayList<Node> lChildren = new ArrayList<Node>();
		while(fFrontier.size() > 0)
		{
			Node lNode = pop();
			
			// Goal Found?
			if(lNode.equals(puzzle.fGoal))
			{
				return lNode.getPathToState();
			}
			else
			{
				//Expand Children(neighbours)
				lChildren = lNode.explore();
				
				for(int i = 0; i < lChildren.size(); i++)
				{
					// add to frontie.r
					add(lChildren.get(i));
				}
			}
		}
		
		// No solution found, return null. 
		return null;
	}
	


}
