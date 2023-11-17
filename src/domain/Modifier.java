package domain;

public abstract class Modifier implements Item {

	private final Valor cara;
	
	/**
	 * Crea un objeto Modifier con la cara de valor especificada.
	 * @param cara La cara de valor para el modificador.
	 */
	public Modifier(Valor cara) {
		this.cara = cara;
		SetObject();
	}
	
	/**
	 * Realiza la acción asociada al modificador en la ficha especificada.
	 * Este método debe ser implementado en las clases concretas que heredan de Modifier.
	 * @param ficha La ficha en la que se aplicará la acción del modificador.
	 * @throws StairsAndSnakesException Si ocurre un error durante la ejecución de la acción del modificador.
	 */
	@Override
	public abstract void DoAction(Ficha ficha) throws StairsAndSnakesException;

	/**
	 * Devuelve una representación en forma de cadena de caracteres del modificador.
	 * Este método debe ser implementado en las clases concretas que heredan de Modifier.
	 * @return Representación en forma de cadena de caracteres del modificador.
	 */
	@Override
	public abstract String toString();

	/**
	 * Establece el modificador en el objeto Valor especificado.
	 * El modificador se asocia al objeto Valor para su posterior uso.
	 * Este método se utiliza internamente dentro del constructor de la clase Modifier.
	 */
	@Override
	public void SetObject() {
		cara.setModifier(this);
	}
}
