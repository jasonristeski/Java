package NxMPuzzle;

public class InvalidPuzzleException extends Exception 
{
	private static final long serialVersionUID = 1L;
	public Node fExceptionNode;
	
	/**
	 * Invalid Puzzle Exception.
	 * @param aExceptionNode Specified node
	 */
	public InvalidPuzzleException(Node aExceptionNode)
	{
		fExceptionNode = aExceptionNode;
	}
}
