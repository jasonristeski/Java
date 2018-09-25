package NxMPuzzle;
import java.util.*;

public class DFS extends SearchMethod
{
	/**
	 * DFS constructor
	 */
	public DFS()
	{
		fId = "DFS"; 
		fName = "Depth-First Search";
		fFrontier = new LinkedList<Node>();
		fSearched = new LinkedList<Node>();
	}
	
	@Override
	public direction[] solve(Puzzle aPuzzle)
	{
		
		add(aPuzzle.fStart);
		ArrayList<Node> lChildren = new ArrayList<Node>();
		
		while(fFrontier.size() > 0)
		{
			Node lNode = pop();
			
			if(lNode.equals(aPuzzle.fGoal))
			{
				return lNode.getPathToState();
			}
			else
			{
				lChildren = lNode.explore();
				
				for(int i = 0; i < lChildren.size();i++)
				{
					add(lChildren.get(i));
				}
			}
		}
		return null; // no solution found.
	}

	@Override
	public boolean add(Node aNode) 
	{
		if(fSearched.contains(aNode) || fFrontier.contains(aNode))
		{
			return false;
		}
		else
		{
			// Frontier is a stack.
			fFrontier.push(aNode); 
			return true;
		}
		
	}
}
