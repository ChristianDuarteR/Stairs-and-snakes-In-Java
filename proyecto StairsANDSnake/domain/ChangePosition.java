package domain;

import java.io.Serializable;

public class ChangePosition extends Modifier implements Serializable{

	/**
	 * Constructor de la clase ChangePosition.
	 * @param dado el valor del dado asociado al cambio de posición.
	 */
	public ChangePosition(Valor dado) {
		super(dado);
	}

	/**
	 * Realiza la acción de cambio de posición de la ficha.
	 * @param ficha la ficha que realiza el cambio de posición.
	 * @throws StairsAndSnakesException si ocurre un error relacionado con las escaleras y serpientes.
	 */
	@Override
	public void DoAction(Ficha ficha) throws StairsAndSnakesException {
		Box MyCasilla = ficha.getBox();
		Box casillaCambio;
		Tablero tablero =MyCasilla.getTablero();
		Ficha ficheOpponent = tablero.searchOpponent(ficha.getColor());
		casillaCambio = ficheOpponent.getBox();
		MyCasilla.deleteToken(ficha);
		casillaCambio.deleteToken(ficheOpponent);
		MyCasilla.addToken(ficheOpponent.getColor(),ficheOpponent);
		casillaCambio.addToken(ficha.getColor(),ficha);
		ficheOpponent.setMaxCas(MyCasilla.getValue());
		ficha.setMaxCas(casillaCambio.getValue());

		if (casillaCambio.hasApower()) {
			casillaCambio.moveTokenWithPower(ficha);

		} else if (casillaCambio.hasAnyTramp()) {
			Item trampa = casillaCambio.getItem();
			trampa.DoAction(ficha);
		}
	}

    /**
     * Devuelve una representación en forma de cadena del objeto.
     * @return Cadena que representa el cambio de posición.
     */
    @Override
    public String toString() {
        return "Cambio de Posicion";
    }

}
