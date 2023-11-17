package domain;
public interface Item {

	/**
	 * Realiza la acción asociada a la ficha cuando cae en una casilla especial.
	 * Este método debe ser implementado por las subclases de Ficha para definir
	 * el comportamiento específico de la ficha en la casilla especial.
	 * @param ficha La ficha que realiza la acción en la casilla especial.
	 * @throws StairsAndSnakesException Si ocurre un error durante la ejecución de la acción.
	 */
	default void DoAction(Ficha ficha) throws StairsAndSnakesException {

	}

	/**
	 * Configura el objeto asociado a la ficha.
	 * Este método debe ser implementado por las subclases de Ficha para establecer el objeto
	 * específico asociado a la ficha.
	 */
	default void SetObject(){

	}

}
