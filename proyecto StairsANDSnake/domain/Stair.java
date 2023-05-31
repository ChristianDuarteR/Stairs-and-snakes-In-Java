package domain;

import java.io.Serializable;

public class Stair extends Tramp implements Serializable {

	/**
	 * Constructor de la clase Stair.
	 * @param startBox la caja de inicio de la escalera.
	 * @param finalBox la caja final de la escalera.
	 * @param transformar indica si la escalera permite transformación de fichas.
	 */
	public Stair(Box startBox, Box finalBox, boolean transformar) {
		super(startBox, finalBox, transformar);
	}

	/**
	 * Realiza la acción asociada a la ficha en el casillero actual.
	 * @param ficha la ficha a la que se le realizará la acción.
	 */
	@Override
	public void DoAction(Ficha ficha){
		super.DoAction(ficha);
		ficha.setStairs();
		ficha.setMaxCas(casillafin.getValue());
	}

}
