package domain;

import java.io.Serializable;
import javax.swing.*;
import java.awt.Color;
import java.util.HashMap;

public class Box implements Serializable{
	protected final Tablero tablero;
	private int value ;
	protected Item item;
	private final HashMap<Color,Ficha> tokens;
	
	/**
	 * Constructor de la clase Box.
	 * @param tbl El tablero al que pertenece la casilla.
	 */
	public  Box(Tablero tbl) {
		tokens = new HashMap<>();
		tablero = tbl;
	}

	/**
	 * Establece el valor de la casilla.
	 * @param value El nuevo valor de la casilla.
	 */
	public void setValue(int value){
		this.value = value;
	}

	/**
	 * Obtiene el valor de la casilla.
	 * @return El valor de la casilla.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Obtiene el tablero al que pertenece la casilla.
	 * @return El tablero al que pertenece la casilla.
	 */
	public Tablero getTablero(){
		return tablero;
	}

	/**
	 * Obtiene el ítem asociado a la casilla.
	 * @return El ítem asociado a la casilla.
	 */
	public Item getItem(){
		return item;
	}

	/**
	 * Obtiene los tokens (fichas) presentes en la casilla.
	 * @return Un mapa que contiene los tokens (fichas) presentes en la casilla, donde la clave es el color de la ficha y el valor es la ficha correspondiente.
	 */
	public HashMap<Color,Ficha>getTokens(){
		return tokens;
	}
	
	/**
	 * Elimina un token (ficha) de la casilla.
	 * @param ficha La ficha que se desea eliminar de la casilla.
	 */
	public void deleteToken(Ficha ficha) {
		Color color = ficha.getColor();
		tokens.remove(color);
		ficha.setBox(null);
	}
	
	/**
	 * Agrega un token (ficha) a la casilla.
	 * @param color El color del token a agregar.
	 * @param ficha La ficha que se desea agregar a la casilla.
	 */
	public void addToken(Color color, Ficha ficha) {
		tokens.put(color, ficha);
		ficha.setBox(this);
	}
	
	/**
	 * Verifica si la casilla tiene alguna trampa.
	 * @return true si la casilla tiene una trampa, false de lo contrario.
	 */
	public final boolean hasAnyTramp() {
		return item != null;
	}

	/**
	 * Verifica si la casilla tiene algún poder.
	 * @return true si la casilla tiene un poder, false de lo contrario.
	 */
	public boolean hasApower(){
		return false;
	}
	
	/**
	 * Establece el ítem de la casilla.
	 * @param item El ítem a establecer en la casilla.
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * Mueve el token de la ficha a una nueva posición en el tablero.
	 * @param ficha La ficha a mover.
	 * @param cantidad La cantidad de casillas a mover.
	 * @throws StairsAndSnakesException Si el movimiento no está permitido.
	 */
	public void moveToken(Ficha ficha, int cantidad) throws StairsAndSnakesException {
		int res;
		Dado dado = tablero.getDados().get(0);
		Valor cara = dado.getDado();

		if (cantidad <= tablero.getWidth()*tablero.getWidth()) {
			ficha.setMaxCas(cantidad);
			deleteToken(ficha);
			Box reNew = tablero.searchBox(cantidad);
			reNew.addToken(ficha.getColor(), ficha);
			if (cara.getModifier() != null) {
				if (ficha.getOwner() instanceof  Machine) {
					res = ((Machine)ficha.getOwner()).getDecision();
				}else {
					res = JOptionPane.showConfirmDialog(null, "Ha atrapado un modificador de " +
							cara.getModifier().toString() + " Desea usarlo? ");
				}
				if (JOptionPane.OK_OPTION == res) {
					moveTokenWithModifer(ficha, cara);
				} else {
					if (reNew.hasApower()) {
						reNew.moveTokenWithPower(ficha);

					} else if (reNew.hasAnyTramp()) {
						Item trampa = reNew.getItem();
						trampa.DoAction(ficha);
					}
				}

			}else if (cara.getModifier() != null && ficha.getOwner() instanceof Machine){
				moveTokenWithModifer(ficha,cara);
			} else if (cara.getModifier() == null) {
				if (reNew.hasApower()) {
					reNew.moveTokenWithPower(ficha);

				} else if (reNew.hasAnyTramp()) {
					Item trampa = reNew.getItem();
					trampa.DoAction(ficha);
				}
			}
		} else {
			throw new StairsAndSnakesException(StairsAndSnakesException.NOT_ALLOW_MOVEMENT);
		}
	}

	/**
	 * Mueve la ficha con un modificador específico.
	 * @param ficha La ficha a mover.
	 * @param movimiento El valor del dado con el modificador.
	 * @throws StairsAndSnakesException Si ocurre un error relacionado con las escaleras y serpientes.
	 */
	private void moveTokenWithModifer(Ficha ficha,Valor movimiento)throws StairsAndSnakesException {
		Modifier modifier = movimiento.getModifier();
		modifier.DoAction(ficha);
	}

	/**
	 * Mueve la ficha con el poder especial.
	 * @param ficha La ficha a mover.
	 * @throws StairsAndSnakesException Si ocurre un error relacionado con las escaleras y serpientes.
	 */
	public void moveTokenWithPower(Ficha ficha) throws StairsAndSnakesException {
		ficha.setNumSpecialBox();
	}

}