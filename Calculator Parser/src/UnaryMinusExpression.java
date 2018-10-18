
public class UnaryMinusExpression extends Expression
{
    public Expression fExp;

    public UnaryMinusExpression( Token aMinus, Expression aExp )
    {
	  fBeginLine = aMinus.beginLine; 
  	  fBeginColumn = aMinus.beginColumn;
	  fEndLine = aExp.fEndLine;
	  fEndColumn = aExp.fEndColumn; 

	  fExp = aExp;
    }

    public int accept( IVisitor aVisitor ) 
    { 
    	return aVisitor.visit( this ); 
    }
}
