package domain;

public abstract class Modifier implements Item {

	private Box casilla;
	
	public Modifier(Box casilla) {
		this.casilla = casilla;
	}

	@Override
	public abstract void getpower(String name);

	@Override
	public void SetBox() {
		casilla.setItem(this);
	}


}
