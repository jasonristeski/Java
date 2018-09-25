package InferenceEngine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Literal object (Proposition) for HNF
 * @author Jason Risteski (7627149)
 *
 */
public class Literal extends PropositionSentence
{
	private PropositionID fProp;
	
	/**
	 * Constructor for literal objects
	 * @param aLiteral Proposition
	 */
	public Literal(PropositionID aLiteral)
	{
		fProp = aLiteral;
		fCount= 1;
	}
	
	/**
	 * Gets ands returns Proposition in ArrayList
	 * @return returns Literal Proposition
	 */
	public ArrayList<PropositionID> getPropositions()
	{
		return new ArrayList<PropositionID>(Arrays.asList(fProp));
	}
	
	/**
	 * Gets and returns size of literal(1)
	 * @return int size of literal
	 */
	public int getCount()
	{
		return fCount;
	}
	
}
