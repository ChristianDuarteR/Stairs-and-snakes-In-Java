package domain;

import java.io.Serializable;

public class MovementBuff extends Modifier implements Serializable{

	/**
	 * Crea una nueva instancia de la clase MovementBuff con el dado especificado.
	 * @param dado el valor del dado para el movimiento.
	 */
	public MovementBuff(Valor dado) {
		super(dado);
	}

	/**
	 * Realiza la acción de movimiento para la ficha especificada.
	 * @param ficha la ficha a la que se le aplicará la acción de movimiento.
	 * @throws StairsAndSnakesException si ocurre un error durante el movimiento.
	 */
	@Override
	public void DoAction(Ficha ficha) throws StairsAndSnakesException{
		Box casilla = ficha.getBox();
		casilla.deleteToken(ficha);
		int newPosition = casilla.getValue() + 1;
		changePositions(ficha, casilla, newPosition);
	}

	/**
	 * Realiza el cambio de posiciones de la ficha a una nueva casilla en el tablero.
	 * @param ficha la ficha a mover.
	 * @param casilla la casilla actual de la ficha.
	 * @param newPosition la nueva posición de la ficha.
	 * @throws StairsAndSnakesException si ocurre un error durante el cambio de posiciones.
	 */
	public static void changePositions(Ficha ficha, Box casilla, int newPosition) throws StairsAndSnakesException {
		Tablero tablero = casilla.getTablero();
		Box renew = tablero.searchBox(newPosition);
		renew.addToken(ficha.getColor(),ficha);
		ficha.setMaxCas(renew.getValue());

		if (renew.hasApower()) {
			renew.moveTokenWithPower(ficha);

		} else if (renew.hasAnyTramp()) {
			Item trampa = renew.getItem();
			trampa.DoAction(ficha);
		}
	}

	/**
	 * Devuelve una representación en forma de cadena de caracteres de la bonificación.
	 * @return la representación en forma de cadena de caracteres de la bonificación.
	 */
	@Override
	public String toString() {
		return "Bonificacion";
	}
}
