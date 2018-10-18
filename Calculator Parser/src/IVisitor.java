
public interface IVisitor 
{
	public int visit( PlusExpression e );
    public int visit( MinusExpression e );
    public int visit( TimesExpression e );
    public int visit( DivideExpression e );
    public int visit( UnaryMinusExpression e );
    public int visit( IntegerLiteral e );
}
