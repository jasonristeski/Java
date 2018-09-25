package NxMPuzzle;

public class Puzzle 
{
	public Node fStart;
	public Node fGoal;
	
	/**
	 * Puzzle constructor, parameters are Nodes
	 * @param aStartState Start state
	 * @param aGoalState Goal State
	 */
	public Puzzle(Node aStartState,Node aGoalState)
	{
		fStart = aStartState;
		fGoal = aGoalState;
	}
	
	/**
	 * Puzzle Constructor, parameters are multi-dimensional array
	 * @param aStartState StartState of puzzle
	 * @param aGoalState GoalState of puzzle
	 */
	public Puzzle(int[][] aStartState,int[][] aGoalState)
	{
		fStart = new Node(aStartState);
		fGoal = new Node(aGoalState);
	}
}
