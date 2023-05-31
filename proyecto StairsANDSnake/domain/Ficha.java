package domain;

import java.awt.*;
import java.io.Serializable;

public class Ficha implements Serializable{
	private Player owner;
	private final Color color;
	private Box casilla;
	private int stairs = 0;

	private int snakes = 0;

	private int maxCas = 1;

	private int numSpecialBox = 0;
	
	private int numMod = 0;

	public boolean canMove = true;

	/**
	 * Crea una nueva ficha asociada a un jugador.
	 * @param player El jugador al que pertenece la ficha.
	 */
	public Ficha(Player player) {
		owner = player;
		color = player.getColor();
		player.setToken(this);
	}

	/**
	 * Obtiene la casilla actual en la que se encuentra la ficha.
	 * @return La casilla actual de la ficha.
	 */
	public Box getBox() {
		return casilla;
	}
	
	/**
	 * Obtiene el jugador propietario de la ficha.
	 * @return El jugador propietario de la ficha.
	 */
	public Player getOwner(){
		return owner;
	}

	/**
	 * Obtiene el número de escaleras en las que ha caído la ficha.
	 * @return El número de escaleras en las que ha caído la ficha.
	 */
	public int getStairs(){
		return stairs;
	}

	/**
	 * Incrementa el número de escaleras en las que ha caído la ficha.
	 */
	public void setStairs(){
		stairs++;
	}

	/**
	 * Devuelve el número de serpientes en las que ha caído la ficha.
	 * @return El número de serpientes en las que ha caído la ficha.
	 */
	public int getSnakes(){
		return snakes;
	}

	/**
	 * Incrementa el número de serpientes en las que ha caído la ficha.
	 */
	public void setSnakes(){
		snakes++;
	}

	/**
	 * Obtiene el valor máximo de casilla alcanzado por la ficha.
	 * @return El valor máximo de casilla alcanzado por la ficha.
	 */
	public int getMaxCas(){
		return maxCas;
	}

	/**
	 * Establece el valor máximo de casilla alcanzado por la ficha.
	 * @param value El valor máximo de casilla a establecer.
	 */
	public void setMaxCas(int value){
		if (maxCas < value) maxCas = value;
	}
	
	/**
	 * Obtiene el número de modificadores de la ficha.
	 * @return El número de modificadores de la ficha.
	 */
	public int getNumMod(){
		return numMod;
	}

	/**
	 * Incrementa el número de modificadores de la ficha en 1.
	 */
	public void setNumMod(){
		numMod++;
	}

	/**
	 * Devuelve el número de casillas especiales (escaleras o serpientes) en las que ha caído la ficha.
	 * @return El número de casillas especiales alcanzadas por la ficha.
	 */
	public int getNumSpecialBox(){
		return numSpecialBox;
	}

	/**
	 * Incrementa el número de casillas especiales (escaleras o serpientes) alcanzadas por la ficha.
	 */
	public void setNumSpecialBox(){
		numSpecialBox++;
	}

	/**
	 * Establece la casilla actual de la ficha.
	 * @param casilla La casilla en la que se encuentra la ficha.
	 */
	public void setBox(Box casilla) {
		this.casilla = casilla;
	}
	
	/**
	 * Devuelve el color de la ficha.
	 * @return El color de la ficha.
	 */
	public Color getColor() {
		return color;
	}

}
