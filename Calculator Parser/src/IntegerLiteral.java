
public class IntegerLiteral extends Expression
{
    public Token fNumber;

    public IntegerLiteral( Token aNumber )
    {
	  fBeginLine = aNumber.beginLine; 
  	  fBeginColumn = aNumber.beginColumn;
	  fEndLine = aNumber.endLine;
	  fEndColumn = aNumber.endColumn; 

	  fNumber = aNumber;
    }

    public int getValue()
    {
    	return (new Integer( fNumber.image )).intValue();
    }
    
    public int accept( IVisitor aVisitor ) 
    { 
    	return aVisitor.visit( this ); 
    }
}
