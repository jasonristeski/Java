package NxMPuzzle;

import java.util.Comparator;

/**
 * NodeComparator --- implementing the comparator between two states based only on the Evaluation Function.
 * @author    COS30019
 */
public class NodeComparator implements Comparator<Node>
{
	
	@Override
	public int compare(Node state1, Node state2) 
	{
		return state1.getEvaluationFunction() - state2.getEvaluationFunction();
	}

}
