package domain;
import java.io.Serializable;

/**
* Excepciones presentadas en el juego.
*/
public class StairsAndSnakesException extends Exception implements Serializable{

	public static final String NOT_ENOUGHT_SPACE = "No hay suficientes casillas libres para el numero indicado de" + " serpientes y escaleras";

	public static final String NOT_FOUND_SNAKE = "No existe una serpiente menor a la posicion de la ficha";

	public static final String NOT_FOUND_STAIR = "No existe una escalera mayor a la posicion de la ficha";

	public static final String NOT_PORC_ALLOW = "Demasiadas casillas especiales";

    public static final String NOT_ALLOW_MOVEMENT = "No puede realizar ese movimiento supera al tablero";
	public static final String NOT_ENOUGHT_PLAYERS = "Debe rellenar el nombre de los jugadores";
    public static final String NOT_ALLOW_MODIFIERS = "Demadiados modificadores";
    public static final String NOT_ALL_SPACES_FULL = "Debe rellenar todos los espacios";
    public static final String ARCHIVO_NO_ENCONTRADO = "Archivo no encontrado.";

	public static final String NOT_ALLOW_NUMBER = "Debe escojer uno de los numeros";

    public StairsAndSnakesException(String mensaje) {
		super(mensaje);
	}
}
