package NxMPuzzle;
import java.util.*;

public class GBFS extends SearchMethod
{
	private Puzzle fPuzzle;
	
	public GBFS()
	{
		fId = "GBFS";
		fName = "Greedy Best-First Search";
		fFrontier = new LinkedList<Node>();
		fSearched = new LinkedList<Node>();
	}
	
	/**
	 * Calculates and sets EvaluationFunction f(n) = h(n)
	 * @param aNode - Specified node to calculate EvaluationFunction
	 */
	private void calcEval(Node aNode)
	{
		//Hamming distance --> Number of misplated tiles, not including blank. 
		// f(n) = h(n)
		int lResult = 0; 
		for(int i = 0; i < aNode.fPuzzle.length;i++)
		{
			for(int j = 0; j < aNode.fPuzzle[1].length;j++)
			{
				if(aNode.fPuzzle[i][j] == 0) // skip
					continue;
				if(aNode.fPuzzle[i][j] != fPuzzle.fGoal.fPuzzle[i][j])
					lResult++;
			}
		}
		
		aNode.setEvaluationFunction(lResult);
	}
	
	@Override
	public direction[] solve(Puzzle aPuzzle) 
	{
		fPuzzle = aPuzzle;
		add(fPuzzle.fStart);
		ArrayList<Node> newStates = new ArrayList<Node>();
		while(fFrontier.size() > 0)
		{
			Node thisState = pop(); 
			if(thisState.equals(aPuzzle.fGoal))
			{
				return thisState.getPathToState();
			}
			else
			{
				newStates = thisState.explore();
				for(int i = 0; i < newStates.size();i++)
				{
					add(newStates.get(i));
				}
			}
		}
		return null;
	}

	@Override
	public boolean add(Node aNode) 
	{
		if(fSearched.contains(aNode) || fFrontier.contains(aNode))
		{
			//discard it
			return false;
		}
		else
		{
			calcEval(aNode);
			fFrontier.add(aNode);
			Collections.sort(fFrontier,new NodeComparator());
			return true;
		}
		
	}

}
