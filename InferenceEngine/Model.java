package InferenceEngine;

import java.util.ArrayList;

/**
 * Particular instance of knowledgebase with different truths applied to propositions
 * @author Jason Risteski (7627149)
 *
 */
public class Model
{
	// hashmap implmentation of ArrayList<PropositionID,bool>
	private ArrayList<Pair> fMap;
	
	/**
	 * Model constructor.
	 */
	public Model()
	{
		fMap = new ArrayList<Pair>();
	}
	
	/**
	 * Copy Constructor for model object
	 * @param aMap Map to be copied. 
	 */
	public Model(ArrayList<Pair> aMap)
	{
		this.fMap = aMap;
	}
	
	/**
	 * Gets and returns mapping of KB to model
	 * @return returns ArrayList<Pair> Map
	 */
	public ArrayList<Pair> getMap()
	{
		return fMap;
	}
	
	/**
	 * PL-True for KB.
	 * @param aList Sentence of KB in model to be found
	 * @return true if sentence holds
	 */
	public boolean isTrue(ArrayList<PropositionID> aList)
	{
		// keep track of which propositions return what
		int flag = 0;
		// literal
		if(aList.size() == 1)
		{
			return isTrue(aList.get(0));
		}
		else
		{
			// for each proposition in sentence
			// required to keep track of conjunctive propositions
			for(int i = 0; i < aList.size();i++)
			{	
				// Conjunction/implication proposition == conclusion??
				flag += (isTrue(aList.get(i)) == (isTrue(aList.get(aList.size()-1)))  ? 1 : 0);
						
			}		
		}
		return flag == aList.size();
	}
		
		

	/**
	 * PL-True for Proposition sentence
	 * @param aQuery aPropostion in world
	 * @return boolean mapping from proposition to world.
	 */
	public boolean isTrue(PropositionID aQuery) 
	{
		boolean lResult = false;
		for(int i = 0; i < fMap.size();i++)
		{
			if(fMap.get(i).getKey().contains((aQuery)))
			{
			lResult = fMap.get(i).getValue();
				//Ripple effect value of propostion changes subsequent propositions.
				for(int j = i; j < fMap.size();j++)
				{
					if(fMap.get(j).getKey().get(fMap.get(j).getKey().size() -1).equals(aQuery))
					{
						if(fMap.get(j).getValue() != lResult)
							fMap.get(j).setValue(lResult);
					}
				}
				break;
				
			}
		}
		return lResult;
	}
	
	/**
	 * Add Propositions to model. Returns new model with appropriate mapping made. 
	 * @param aProp Proposition to be added
	 * @param aValue Value of sentence
	 * @return new model with appropriate addition.
	 */
	public Model add(ArrayList<PropositionID> aProp, boolean aValue) 
	{
		
		Model lResult = new Model(fMap);
		// remove old mapping (required??)
		for(int i = 0; i < lResult.getMap().size();i++)
		{
			if(lResult.getMap().get(i).getKey().equals(aProp))
				lResult.getMap().remove(i);
		}
		
		lResult.getMap().add(0,new Pair(aProp,aValue));
		return lResult;
	}
}