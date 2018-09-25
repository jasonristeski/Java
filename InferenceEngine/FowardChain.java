package InferenceEngine;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implements FowardChain algorithm. Starts from known propositions and searches to find goal(Query)
 * @author Jason Risteski(7627149)
 *
 */
public class FowardChain extends InferenceMethod
{		
	// Represents table mapping in algorithm, used to keep track of Proppstion sizes as they are explored.
	private LinkedHashMap<PropositionSentence,Integer> fMap;
	
	/**
	 * FowardChain constructor 
	 * @param aKB KnowledgeBase
	 * @param aQuery Query
	 */
	public FowardChain(KnowledgeBase aKB,PropositionID aQuery)
	{
		super(aKB,aQuery);
		fMap = new LinkedHashMap<PropositionSentence,Integer>();
		initSearch();
	}
	
	/**
	 * Searches through KB from known propositions to goal.
	 * @return True if goal is found.
	 */
	public boolean entails()
	{
		while(!fSearch.isEmpty())
		{
			PropositionID lp = fSearch.remove(0);
			
			// is explored, now known. 
			if(!fSearched.contains(lp))
				fSearched.add(lp);
			
			if(lp.equals(fQuery))
				return true;
			
			for(Map.Entry<PropositionSentence,Integer> ps : fMap.entrySet())
			{
				if(ps.getKey().getPropositions().contains(lp))
				{
					int j = ps.getValue();
					fMap.replace(ps.getKey(),j, j-1);
					 if(ps.getValue() == 0)
					 {
						 // Conclusion is known, add to agenda, add to known 
						fSearch.add(ps.getKey().getPropositions().get(ps.getKey().getCount() -1));
						// remove mapping its no longer required. 
						fMap.remove(ps);
					 }
				}
			}
		}
		return false;
	}
	/**
	 * Returns result of searched propositions.
	 * @return String result
	 */
	public String getResult()
	{
		return "YES: " + fSearched.toString().replace("[","").replace("]", "");
	}
	
	// Initialise search 
	private void initSearch()
	{
		for(int i = 0; i < fKB.getWorld().size();i++)
		{
			// isKnown, therefore add to searched.
			if(fKB.getWorld().get(i) instanceof Literal)
			{

				fSearch.add(fKB.getWorld().get(i).getPropositions().get(0));	
			}
			else
			{
				// else create size,proposition mapping. 
				fMap.put(fKB.getWorld().get(i),fKB.getWorld().get(i).getCount()-1);
			}
		}
	}

}
	


