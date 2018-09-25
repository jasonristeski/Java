package NxMPuzzle;

/**
 * Is thrown when node is moved to an invalid location
 * @author Jason Risteski(7627149)
 *
 */
public class CantMoveThatWayException extends Exception {

	private static final long serialVersionUID = 1L;
	public Node fNode;
	public direction fDirection;
	
	/**
	 * Invalid Movement at specified direction 
	 * @param aNode
	 * @param aDirect
	 */
	public CantMoveThatWayException(Node aNode,direction aDirect)
	{
		fNode = aNode;
		fDirection = aDirect;
	}

}
