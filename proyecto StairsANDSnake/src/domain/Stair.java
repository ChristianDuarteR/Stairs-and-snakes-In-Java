package domain;

import java.awt.Color;

public class Stair extends Tramp {

	public Stair(Box finalBox, Box startBox) {
		super(startBox, finalBox);
		SetBox();
	}

	public void getpower(String name) {
		Player player= casillainicial.getPlayer(name);
		player.mustMove(casillafin);
		
	}
	
	public void SetBox() {
		casillainicial.setItem(this);
		casillafin.setItem(this);
	}
	
}
