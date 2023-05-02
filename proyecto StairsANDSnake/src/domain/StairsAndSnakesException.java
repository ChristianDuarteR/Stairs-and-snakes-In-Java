package domain;

public class StairsAndSnakesException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final static String CASILLA_OCUPADA = "La casilla a cambiar ya esta ocupada";
	
	public StairsAndSnakesException(String mensaje) {
		super(mensaje);
	}
}
