
public abstract class Employee 
{
	protected Hopper filling;
	protected Hopper flavour;
	protected Hopper topping;
	
	protected void assign(Hopper filling,Hopper flavour,Hopper topping)
	{
		this.filling = filling;
		this.flavour = flavour;
		this.topping = topping;
	}
}
