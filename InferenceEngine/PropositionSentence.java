package InferenceEngine;

import java.util.ArrayList;

public abstract class PropositionSentence 
{
	protected int fCount = 0;
	public abstract ArrayList<PropositionID> getPropositions();
	public abstract int getCount();
	
	
}
