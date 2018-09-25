package InferenceEngine;


import java.util.ArrayList;

/**
 *  Backward chain class. 
 * @author Jason Risteski (7627149)
 *
 */
public class BackwardChain extends InferenceMethod
{	
	
	/***
	 * Backward chain constructor 
	 * @param aKB KnowledgeBase
	 * @param aQuery Query
	 */
	public BackwardChain(KnowledgeBase aKB,PropositionID aQuery)
	{
		super(aKB,aQuery);
		initSearch();
	}
	

	/**
	 * Starts at goal(Query) and searches Propositions to find truth. 
	 * @return True if goal is found to be true.
	 */
	public boolean entails()
	{
		for(int i = 0; i < fKB.getWorld().size();i++)
		{
			// has it been explored?
			if(goalConclusion(fKB.getWorld().get(i).getPropositions()))
			{	
				if(!fSearch.contains(fKB.getWorld().get(i).getPropositions()))
				{
					for(PropositionID p : fKB.getWorld().get(i).getPropositions())
					{
						if(!fSearch.contains(p))
							fSearch.add(0,p);
					}
					
					// is new subgoal a fact?
					if(isTrue(fKB.getWorld().get(i).getCount()-1))					
						return true;
					else
						return entails(); 
					// recursively iterate from start until goal is true or search exhausted.
				}
			}
		}
		
		return false;
	}
	
	/**
	 * returns result of search
	 * @return String result.
	 */
	public String getResult()
	{
		
		return "YES: " +  fSearch.toString().replace("[","").replace("]", "");
	}
	
	// Initialise chain search. 
	private void initSearch()
	{
		// Start from goal
		fSearch.add(fQuery);
		for(int i = 0; i < fKB.getWorld().size();i++)
		{
			// Add known propositions to fsearched
			if(fKB.getWorld().get(i) instanceof Literal)
			{	
				fSearched.add(0,fKB.getWorld().get(i).getPropositions().get(0));
			}
		}
	}
	
	// is a subgoal a conclusion to a proposition?
	private boolean goalConclusion(ArrayList<PropositionID> aPropList)
	{
		// x => goal
		for(PropositionID i : fSearch)
		{
			if(aPropList.get(aPropList.size() -1 ).equals(i))
			{
				return true;
			}
		}
		return false;
	}
	
	// Is subgoal known?
	private boolean isTrue(int aSize)
	{		
		int flag = 0;
		for(int i = 0; i < fSearched.size();i++)
		{
			for(int j = 0; j < aSize;j++)
			{
				if(fSearch.get(j).equals(fSearched.get(i)))
					flag++;
			}
		}
		return flag == aSize;
	}
}


