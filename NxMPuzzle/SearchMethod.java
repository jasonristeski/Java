package NxMPuzzle;

import java.util.*;

public abstract class SearchMethod 
{
	public String fId; 
	public String fName; 
	
	public LinkedList<Node> fFrontier; //Frontier data structure
	public LinkedList<Node> fSearched; // Searched nodes
	
	
	/**
	 * Logical Implementation of Search Strategy.
	 * @param aPuzzle Puzzle input
	 * @return Path to goal if solution is found, or null.
	 */
	public abstract direction[] solve(Puzzle aPuzzle); 
	
	/**
	 * Adds element to appropriate position in Frontier (depending on Frontier/Method implementation) 
	 * if element has not been previously evaluated.
	 * @return Returns true if node has not been explored and has been added to Frontier. 
	 * @param aNode - Node to be added in Frontier
	 */
	public abstract boolean add(Node aNode);
	
	/**
	 * Removes first element in Frontier, adds it to searched list
	 * @return Node - Returns first element of frontier;
	 */
	protected Node pop()
	{
		Node lResult = fFrontier.pop();
		fSearched.add(lResult);
		return lResult;
	}
	
	/**
	 * Clears frontier and search storage. 
	 */
	public void reset()
	{
		fFrontier.clear();
		fSearched.clear();
	}

}
