package domain;

import java.io.Serializable;
public class Snake extends Tramp implements Serializable {
	
	/**
	 * Crea una serpiente con la casilla de inicio y la casilla final especificadas.
	 * @param startBox la casilla de inicio de la serpiente.
	 * @param finalBox la casilla final de la serpiente.
	 * @param transformar indica si la serpiente realiza una transformación en el juego.
	 */
	public Snake (Box startBox, Box finalBox, boolean transformar) {
		super(startBox, finalBox,transformar);
	}

	/**
	 * Realiza la acción asociada a la ficha en la casilla de la serpiente.
	 * @param ficha la ficha que realiza la acción.
	 */
	@Override
	public void DoAction(Ficha ficha){
		super.DoAction(ficha);
		ficha.setSnakes();
		ficha.setMaxCas(casillainicial.getValue());
	}

}
