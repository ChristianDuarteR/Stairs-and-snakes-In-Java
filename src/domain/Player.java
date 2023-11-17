package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;

public class Player implements Serializable {
	private final Color color;

	private final String name;

	protected final ArrayList<Dado> dados ;

	private final ArrayList<Ficha> fichas;

	/**
	 * Crea un objeto de jugador con el nombre, color y dados especificados.
	 * @param name el nombre del jugador.
	 * @param color el color del jugador.
	 * @param dados la lista de dados del jugador.
	 */
	public Player(String name, Color color, ArrayList<Dado> dados) {
		fichas = new ArrayList<>();
		this.name = name;
		this.color = color;
		this.dados = dados;
	}

	/**
	 * Realiza el turno de juego del jugador lanzando todos los dados.
	 */
	public void playAturn() {
		for(Dado d: dados) {
			d.lanzarDado();
		}
	}

	/**
	 * Mueve el token de la ficha de acuerdo a la decisión tomada por la máquina.
	 * @throws StairsAndSnakesException si ocurre un error durante el movimiento del token.
	 */
	public void moveToken() throws StairsAndSnakesException{
		Ficha ficha = fichas.get(0);
		if (ficha.canMove) {
			int cantidad = decideBox(ficha);
			Box casilla = ficha.getBox();
			casilla.moveToken(ficha,cantidad);
		} else {
			boolean nowCan;
			Box casilla = ficha.getBox();
			if (casilla instanceof Question){
				nowCan = ((Question) casilla).doQuestion(ficha);
				if(nowCan) { 
					ficha.canMove = true;
					moveToken();
				}
			}
		}
	}
	
	/**
	 * Decide la casilla a la que se moverá la ficha.
	 * @param ficha La ficha que se va a mover.
	 * @return El valor de la casilla a la que se moverá la ficha.
	 */
	public int decideBox(Ficha ficha) {
		int cantidad;
		Box casilla = ficha.getBox();
		Tablero tablero = casilla.getTablero();
		Dado dado = tablero.getDados().get(0);
		int valueElecion;
		cantidad = casilla.getValue() + dado.getDado().getNumero();

		if (searchSpecialBoxs(ficha) > 0){
			ArrayList<Box> specials = tablero.getSpecials(ficha);
			ArrayList<Integer> opciones = new ArrayList<>();
			int cont = 0;
			while (cont < specials.size()) {
				opciones.add(specials.get(cont).getValue());
				cont++;
			}
			opciones.add(cantidad);
			if (ficha.getOwner() instanceof  Machine){
				valueElecion = ((Machine)ficha.getOwner()).DecideBox(opciones);
			}else {
				valueElecion = (int) JOptionPane.showInputDialog(null, "Que casilla desea? ", "Elegir",
						JOptionPane.QUESTION_MESSAGE, null, opciones.toArray(), opciones.get(0));
			}
			cantidad = valueElecion;
		}
		return cantidad;

	}
	
	/**
	 * Busca las casillas especiales en un rango determinado desde la posición actual de la ficha.
	 * @param ficha La ficha para la cual se busca las casillas especiales.
	 * @return El número de casillas especiales encontradas dentro del rango.
	 */
	private int searchSpecialBoxs(Ficha ficha){
		int range = dados.get(0).getDado().getNumero(),longitud;
		int especials = 0;
		Tablero tablero = ficha.getBox().getTablero();
		int position = ficha.getBox().getValue();
		if (position + 6 >= tablero.getWidth()*tablero.getWidth() - 6) {
			longitud = tablero.getWidth() - position;
			while (range >= longitud) {
				range--;
			}
		}
		for (int i=1;i <range; i++){
			if (tablero.searchBox(position+i).hasApower()){
				especials++;
			}
		}
		return especials;
	}
	
	/**
	 * Obtiene el color del jugador.
	 * @return el color del jugador.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Obtiene el nombre del jugador.
	 * @return el nombre del jugador.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Obtiene las fichas del jugador.
	 * @return una lista de las fichas del jugador.
	 */
	public ArrayList<Ficha> getFichas(){
		return fichas;
	}

	/**
	 * Establece una ficha para el jugador.
	 * @param ficha la ficha a establecer.
	 */
	public void setToken(Ficha ficha) {
		fichas.add(ficha);
	}

	/**
	 * Devuelve una representación en forma de cadena de texto del jugador.
	 * @return el nombre del jugador.
	 */
	@Override
	public String toString() {
		return name;
	}

}
