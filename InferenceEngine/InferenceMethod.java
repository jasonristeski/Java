package InferenceEngine;

import java.util.ArrayList;

public abstract class InferenceMethod 
{
	protected KnowledgeBase fKB;
	protected PropositionID fQuery;
	protected ArrayList<PropositionID> fSearch;
	protected ArrayList<PropositionID> fSearched;
	
	
	public InferenceMethod(KnowledgeBase aKB,PropositionID aQuery)
	{
		fKB = aKB;
		fQuery = aQuery;
		fSearched = new ArrayList<PropositionID>();
		fSearch = new ArrayList<PropositionID>();
	}
	
	public abstract boolean entails();
	
	public abstract String getResult();
	
	
			
}
