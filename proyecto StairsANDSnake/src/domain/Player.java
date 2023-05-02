package domain;

import java.awt.Color;

public class Player {

	private Tablero tablero;
	
	private Box position;

	private Color color;

	private String name;

	private Dado  dado ;

	public Player(Tablero tbl, String name) {
		
		this.name = name;
		tablero = tbl;
		position = tbl.getBox(0,0);
	}
	
	public void setPositionBox(Box position) {
		this.position = position;
	}
	
	public Box getPosition() {
		return position;
	}

	public void mustMove(Box position) {
		tablero.changePlayerToSpecial(name, position);
	}
	
	public void playAturn() {
		int steps,jugada;
		dado.lanzarDado();
		jugada = dado.getDado() + position.getValue();
		tablero.realizarJugada(name,jugada);
	}
		
		
}
