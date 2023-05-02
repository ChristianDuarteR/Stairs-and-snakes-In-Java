package domain;



public abstract class Tramp implements Item {
	
	protected Box casillainicial;
	protected Box casillafin;
	
	public Tramp (Box casillainicial, Box casillafin) {
		
		this.casillainicial = casillainicial;
		this.casillafin = casillafin;
		
	}
	
	public abstract void getpower(String name);
	
	public abstract void SetBox();

}
