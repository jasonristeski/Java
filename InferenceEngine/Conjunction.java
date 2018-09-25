package InferenceEngine;

import java.util.ArrayList;

/**
 * Conjunction (&)  object for HNF
 * @author Jason Risteski(7627149)
 *
 */
public class Conjunction extends PropositionSentence
{
	
	private PropositionID fLeft;
	private PropositionID fRight;
	
	/**
	 * Conjunction Constructor (LeftSide & RightSide)
	 * @param aLeft Left side of conjunction
	 * @param aRight Right side of conjunction
	 */
	public Conjunction(PropositionID aLeft,PropositionID aRight)
	{
		fLeft = aLeft;
		fRight = aRight;
		fCount = 2;
	}

	/**
	 * Returns both propositions in conjunction in arrayList
	 * @return ArrayList<PropositionID> of propostions
	 */
	public ArrayList<PropositionID> getPropositions() 
	{
		ArrayList<PropositionID> lResult = new ArrayList<PropositionID>();
		lResult.add(fLeft);
		lResult.add(fRight);
		return lResult;
	}
	
	/**
	 * Returns size of conjunction
	 * @return int : size of conjunction (2)
	 */
	public int getCount()
	{
		return fCount;
	}
	
	
	
}
