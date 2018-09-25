package InferenceEngine;

/**
 * PropoistionID object used in Propositions 
 * @author Ajvar
 *
 */
public class PropositionID  
{
	private String fID;
	
	/**
	 * Constructor of Proposition
	 * @param aID String Identifier
	 */
	public PropositionID(String aID)
	{
		fID = aID.toLowerCase();
	}
	
	/**
	 * toString returns Identifier
	 * @return String Identifier
	 */
	public String toString()
	{
		return fID;
	}
	
	/**
	 * hashCode function used to determine equality.
	 * Code was Generated by eclipse. 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fID == null) ? 0 : fID.hashCode());
		return result;
	}

	/**
	 * Equals function used to determine equality. 
	 * Code was generated by eclipse;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropositionID other = (PropositionID) obj;
		if (fID == null) {
			if (other.fID != null)
				return false;
		} else if (!fID.equals(other.fID))
			return false;
		return true;
	}
	
}
