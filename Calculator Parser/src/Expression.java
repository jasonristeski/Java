
public abstract class Expression 
{
	public int fBeginLine; 
	public int fBeginColumn;
	public int fEndLine;
	public int fEndColumn; 
	
	public abstract int accept( IVisitor aVisitor );
}
