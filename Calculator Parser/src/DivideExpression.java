
public class DivideExpression extends Expression 
{
	public Expression fLeft;
    public Expression fRight;

    public DivideExpression( Expression aLeft, Expression aRight )
    {
	  fBeginLine = aLeft.fBeginLine; 
	  fBeginColumn = aLeft.fBeginColumn;
	  fEndLine = aRight.fEndLine;
	  fEndColumn = aRight.fEndColumn; 

	  fLeft = aLeft;
	  fRight = aRight;
    }

    public int accept( IVisitor aVisitor ) 
    { 
    	return aVisitor.visit( this ); 
    }
}