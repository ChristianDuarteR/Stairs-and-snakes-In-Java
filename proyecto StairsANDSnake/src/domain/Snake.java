package domain;

import java.awt.Color;

public class Snake extends Tramp {
	
	public Snake (Box finalBox, Box startBox) {
		super(startBox, finalBox);
		SetBox();
	}
	
	public void getpower(String name) {
		Player player= casillafin.getPlayer(name);
		player.mustMove(casillainicial);
	}
	
	public void SetBox() {
		casillainicial.setItem(this);
		casillafin.setItem(this);
	}

}
