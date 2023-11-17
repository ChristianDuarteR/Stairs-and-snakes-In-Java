package domain;

import java.io.Serializable;

public class MovementNerf extends Modifier implements Serializable{

	/**
	 * Crea una instancia de la clase MovementNerf con el valor del dado especificado.
	 * @param dado el valor del dado para el movimiento nerf.
	 */
	public MovementNerf(Valor dado) {
		super(dado);
	}
	
	/**
	 * Realiza la acción del movimiento nerf en la ficha especificada.
	 * Elimina la ficha de la casilla actual y la mueve a una casilla anterior.
	 * @param ficha la ficha en la que se realiza la acción del movimiento nerf.
	 * @throws StairsAndSnakesException si ocurre un error durante la ejecución del movimiento nerf.
	 */
	@Override
	public void DoAction(Ficha ficha) throws StairsAndSnakesException{
		Box casilla = ficha.getBox();
		casilla.deleteToken(ficha);
		int newPosition = casilla.getValue() - 1;
		MovementBuff.changePositions(ficha, casilla, newPosition);
	}

	/**
	 * Devuelve una representación en forma de cadena del objeto Penalizacion.
	 * @return una cadena que representa la penalización.
	 */
	@Override
	public String toString() {
		return "Penalizacion";
	}

}
