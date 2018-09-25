package InferenceEngine;

import java.util.ArrayList;

/**
 * Data structure for Model
 * @author Jason Risteski (7627149)
 *
 */
public class Pair
{
	private ArrayList<PropositionID> fKey;
	private boolean fValue;

	/**
	 * Constructor creates a new pair
	 * @param p Proposition
	 * @param b Boolean vale
	 */
	public Pair(ArrayList<PropositionID> p, boolean b) 
	{
		
		fKey = new ArrayList<PropositionID>(p);
		fValue = b;
	}
	
	/**
	 * Returns Propositions in Pair
	 * @return ArrayList<PropositionID>
	 */
	public ArrayList<PropositionID> getKey()
	{
		return fKey;
	}
	
	/**
	 * Returns boolean value of pair
	 * @return Boolean value of pair
	 */
	public boolean getValue()
	{
		return fValue;
	}
	
	public void setValue(boolean aValue)
	{
		fValue = aValue;
	}
	
}