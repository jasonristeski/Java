package InferenceEngine;

import java.util.ArrayList;

/**
 * Implication object (=>)for HNF. 
 * @author Jason Risteski (7627149)
 *
 */
public class Implication extends PropositionSentence
{
	
	private ArrayList<PropositionID> fClause = new ArrayList<PropositionID>();
	

	/**
	 *  Basic implication constructor (LeftSide => conclusion)
	 * @param aLeft Left side of implcation
	 * @param aConclusion Conclusion of implicaiton
	 */
	public Implication(PropositionID aLeft,PropositionID aConclusion)
	{
		fClause.add(aLeft);
		fClause.add(aConclusion);
		fCount = 2;
	}
	
	/**
	 * Complex implication constructor (Conjunction => conclusion)
	 * @param aProp Left side of implication 
	 * @param aConclusion Conclusion of implication
	 */
	public Implication(PropositionSentence aProp,PropositionID aConclusion)
	{
		fClause = aProp.getPropositions();
		fClause.add(aConclusion);
		fCount = aProp.getCount() +1;
	}

	/**
	 * Gets and returns all propositions in implication (including parent Propositions)
	 * @return ArrayList of propositions in implication
	 */
	public ArrayList<PropositionID> getPropositions() 
	{
		return fClause;
		
	}
	
	/**
	 * Gets and returns size of implication.
	 * @return int : Implication size
	 */
	public int getCount()
	{
		return fCount;
	}


}
