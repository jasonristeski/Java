
public class ExpressionVisitor implements IVisitor
{
	private boolean fShowTrace;

	public ExpressionVisitor()
	{
		this( false );
	}

	public ExpressionVisitor( boolean aShowTrace )
	{
		fShowTrace = aShowTrace;
	}
	
	private void trace( String aMessage )
	{
		if ( fShowTrace )
		{
			System.out.println( aMessage );
		}
	}
	
	
    public int visit( PlusExpression e ) 
    { 
    	trace( "Visiting PlusExpression..." );
    	return e.fLeft.accept( this ) + e.fRight.accept( this ); 
    }
    
    public int visit( MinusExpression e ) 
    { 
    	trace( "Visiting MinusExpression..." );
    	return e.fLeft.accept( this ) - e.fRight.accept( this );
    }
    
    public int visit( TimesExpression e )
    { 
    	trace( "Visiting TimesExpression..." );
    	return e.fLeft.accept( this ) * e.fRight.accept( this );
    }
    
    public int visit( DivideExpression e )
    {
    	trace( "Visiting DivideExpression..." );
    	int lLeft = e.fLeft.accept( this );
    	int lRight = e.fRight.accept( this );

    	if ( lRight == 0 )
	    { 
    		System.out.println( "DivisionByZero, expression starting in line " + e.fRight.fBeginLine +
                                " column " + e.fRight.fBeginColumn + " evaluates to zero." );
            System.exit( 1 );
        }
    	
    	return lLeft / lRight;
    }
    
    public int visit( UnaryMinusExpression e )
    { 
    	trace( "Visiting UnaryMinusExpression..." );
    	return - e.fExp.accept( this ); 
    }
    
    public int visit( IntegerLiteral e )
    { 
    	trace( "Visiting IntegerLiteral..." );
    	return e.getValue();
    }
}
