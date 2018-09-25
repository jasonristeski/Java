package InferenceEngine;

import java.util.ArrayList;

/**
 * KnowledgeBase object for propositional logic.
 * @author Jason Risteski (7627149)
 *
 */
public class KnowledgeBase 
{
	private ArrayList<PropositionSentence> fClause;
	
	/**
	 * KnowledgeBase constructor 
	 */
	public KnowledgeBase()
	{
		fClause = new ArrayList<PropositionSentence>();
	}
	
	
	/**
	 * Adds propositions to knowledgebase
	 * @param aOperation Proposition sentence to add to knowledgebase
	 */
	public void tell(PropositionSentence aOperation)
	{
		fClause.add(aOperation);
	}
	
	/**
	 * Returns the knowledgebase 
	 * @return ArrayList<PropositionSentence> KnowledgeBase
	 */
	public ArrayList<PropositionSentence> getWorld()
	{
		return fClause;
	}
	
}
