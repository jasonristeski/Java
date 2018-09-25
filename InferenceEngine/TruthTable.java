package InferenceEngine;

import java.util.ArrayList;

/**
 * TruthTable by entailment.
 * @author Jason Risteski (7627149)
 *
 */
public class TruthTable extends InferenceMethod
{

	private int fCount;

	/**
	 * Constructor creates truthTable object
	 * @param aKB KnowledgeBase
	 * @param aQuery Query
	 */
	public TruthTable(KnowledgeBase aKB, PropositionID aQuery) 
	{
		super(aKB, aQuery);
		fCount = 0;
	}

	/**
	 * Gets result of entailment - number of Models where KB and Query are true
	 * @return String Integer count of models.
	 */
	public String getResult()
	{
		return "YES: " +  Integer.toString(fCount);
	}
	
	/**
	 * Entailment method calls ttCheck, which recursively asserts propositions to models. 
	 * @return Result of entailment
	 */
	public boolean entails() 
	{
		ArrayList<PropositionSentence> lSymbol = new ArrayList<PropositionSentence>(fKB.getWorld());
		//lSymbol.add(new Literal(fQuery));
		return ttCheck(lSymbol,new Model());
	}
	

	// recursive implementation of entailment
	private boolean ttCheck(ArrayList<PropositionSentence> aSymbol,Model aModel) 
	{
		// all possible models are created
		if(aSymbol.isEmpty())
		{
			// For every proposition sentence in kb
			for(int i = 0; i < fKB.getWorld().size();i++)
			{
				// if a sentence fails in kb, kb is false.
				if(!aModel.isTrue(fKB.getWorld().get(i).getPropositions()))
					return true; // model fails always return true;
			}
			
			
			if(aModel.isTrue(fQuery))
			{
				fCount++;
				//Model is true
				return true;
			}
			else
				return false;
			
		}
		else
		{
			// Populate models.
			ArrayList<PropositionID> lp = aSymbol.remove(0).getPropositions();
			ArrayList<PropositionSentence> lps =  new ArrayList<PropositionSentence>(aSymbol);
			return ttCheck(lps,aModel.add(lp, true)) && (ttCheck(lps,aModel.add(lp,false)));
		}
	}



	

}
