package NxMPuzzle;

import java.util.*;

/**
 * A* - Implements A* Search strategy. Frontier is a SortedQueue based on f(n) = g(n) + h(n)
 * @author Jason Risteski(7627149)
 *
 */
public class AS extends SearchMethod
{
	private Puzzle fPuzzle;
	
	/**
	 * A* Constructor - initializes A* method. 
	 */
	public AS()
	{
		fId = "AS";
		fName = "A* Search";
		fFrontier = new LinkedList<Node>();
		fSearched = new LinkedList<Node>();
	}
	
	/**
	 * Calculates and sets EvaluationFunction f(n) = g(n) + h(n)
	 * @param aNode - Specified node to calculate EvaluationFunction
	 */
	private void calcEval(Node aNode)
	{
		// f(n) = g(n) + h(n)
		//Hamming distance --> Number of misplated tiles, not including blank. 
		int lResult = 0; 
		for(int i = 0; i < aNode.fPuzzle.length;i++)
		{
			for(int j = 0; j < aNode.fPuzzle[1].length;j++)
			{
				if(aNode.fPuzzle[i][j] == 0) // skip blank --> admissable 
					continue; 
				if(aNode.fPuzzle[i][j] != fPuzzle.fGoal.fPuzzle[i][j])
					lResult++;
			}
		}
		lResult += aNode.getCost();
		aNode.setEvaluationFunction(lResult);
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
			
			fFrontier.add(aNode);
			// Sort based on f(n), Frontier is a priority q.
			Collections.sort(fFrontier,new NodeComparator());
			return true;
		}
	}
	
	@Override
	public direction[] solve(Puzzle aPuzzle) 
	{
		fPuzzle = aPuzzle;
		add(fPuzzle.fStart);
		ArrayList<Node> lChildren = new ArrayList<Node>();
		while(fFrontier.size() > 0)
		{
			Node thisState = pop();
			if(thisState.equals(fPuzzle.fGoal))
			{
				return thisState.getPathToState();
			}
			lChildren = thisState.explore();
			for(int i = 0; i < lChildren.size();i++)
			{
				// Child better path?
				calcEval(lChildren.get(i));
				if(lChildren.get(i).compareTo(thisState) >= 0)
					add(lChildren.get(i));
			}	
		}
		return null; // No solution found, search exhausted.
	}


}
