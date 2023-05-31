package domain;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.Serializable;

public class StairsAndSnakes implements Serializable {
	private Tablero tablero;

	private int turno = 0;

	protected  boolean win = false;
	
	/**
	 * Constructor de la clase StairsAndSnakes que inicializa el juego con los parámetros especificados.
	 * @param nombres Lista de nombres de los jugadores.
	 * @param colores Lista de colores de los jugadores.
	 * @param rival Tipo de rival ("Player" para jugador humano, otro valor para jugador máquina).
	 * @param tamano Tamaño del tablero.
	 * @param numE Número de escaleras.
	 * @param numS Número de serpientes.
	 * @param transformar Indicador de si las casillas se transforman o no.
	 * @param porC Porcentaje de casillas especiales.
	 * @param porM Porcentaje de modificadores.
	 * @param dificultad Dificultad del jugador máquina.
	 * @throws StairsAndSnakesException Excepción lanzada si se producen errores en los parámetros.
	 */
	public StairsAndSnakes (ArrayList<String> nombres, ArrayList<Color> colores,String rival, int tamano, int numE, int numS, boolean transformar, int porC, int porM,String dificultad) throws StairsAndSnakesException{
		if (nombres.get(0).length() < 1 || nombres.get(1).length() < 1) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_ENOUGHT_PLAYERS);
		if (porM > 6) throw  new StairsAndSnakesException(StairsAndSnakesException.NOT_ALLOW_MODIFIERS);
		if(porC > (10*tamano) /4) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_PORC_ALLOW);
		if((numE + numS)*2 > ((10*tamano)/2) - (1 + porC)) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_ENOUGHT_SPACE);
		if (rival.equals("Player")) tablero = new Tablero(nombres,colores,numS,numE,tamano,porC,porM,transformar);
		else {
			tablero = new Tablero(nombres.get(1),colores,numS,numE,tamano,porC,porM,transformar,dificultad);
		}
	}
	
	/**
	 * Constructor de la clase StairsAndSnakes.
	 * @param nombre Nombre del jugador.
	 * @param color Color del jugador.
	 * @param rival Nombre del rival.
	 * @param tamano Tamaño del tablero.
	 * @param numE Número de escaleras.
	 * @param numS Número de serpientes.
	 * @param transformar Indicador de transformación.
	 * @param porC Porcentaje de cajas especiales.
	 * @param porM Porcentaje de modificadores.
	 * @throws StairsAndSnakesException Si se produce algún error durante la configuración del juego.
	 */
	public StairsAndSnakes(String nombre, Color color,String rival, int tamano, int numE, int numS, boolean transformar, int porC, int porM) throws StairsAndSnakesException{
		if(porC > 50) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_PORC_ALLOW);
		if(numE + numS > 10* tamano) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_ENOUGHT_SPACE);
		if (porM > 6) throw  new StairsAndSnakesException(StairsAndSnakesException.NOT_ALLOW_MODIFIERS);

		ArrayList<String> jugadores = new ArrayList<>();
		ArrayList<Color> colores = new ArrayList<>();
		colores.add(color);
		colores.add(Color.gray);
		jugadores.add(nombre);
		jugadores.add("Maquina");
		tablero = new Tablero(jugadores,colores,numS,numE,tamano,porC,porM,transformar);
	}

	/**
	 * Devuelve el tablero del juego.
	 * @return El objeto Tablero del juego.
	 */
	public Tablero getTablero() {
		return tablero;
	}

	/**
	 * Devuelve el turno actual del juego.
	 * @return El número del turno actual.
	 */
	public int getTurno() {
		return turno;
	}

	/**
	 * Devuelve el estado de victoria del juego.
	 * @return `true` si se ha alcanzado la condición de victoria, `false` de lo contrario.
	 */
	public boolean getWin(){
		return win;
	}

	/**
	 * Cambia los dados y avanza el turno al siguiente jugador.
	 * @param name El nombre del jugador cuyos dados se van a cambiar.
	 */
	public void changeDados(String name){
		Player jugador = tablero.getJugador(name);
		jugador.playAturn();
		turno ++;
	}

	/**
	 * Mueve el jugador y verifica si ha ganado el juego.
	 * @param name El nombre del jugador cuyo token se va a mover.
	 * @throws StairsAndSnakesException Si ocurre un error relacionado con escaleras y serpientes.
	 */
	public void movePlayer(String name) throws StairsAndSnakesException{
		Player jugador = tablero.getJugador(name);
		jugador.moveToken();
		verifyWin(name);
	}

	/**
	 * Verifica si el jugador ha ganado el juego.
	 * @param name El nombre del jugador a verificar.
	 */
	public void verifyWin(String name) {
		Player player = tablero.getJugador(name);
		Ficha ficha = player.getFichas().get(0);
		int posicion = ficha.getBox().getValue();
		if (posicion == tablero.getWidth() * tablero.getWidth()){
			win = true;
			JOptionPane.showMessageDialog(null,"Felicidades el jugador: "+ player + " Ha ganado!!!");
		}
	}
	/**
	 * Guarda el objeto StairsAndSnakes en un archivo.
	 * @param archivo El archivo en el que se va a guardar el objeto.
	 * @throws StairsAndSnakesException Si ocurre algún error durante el proceso de guardado.
	 */
	public void guarde(File archivo) throws StairsAndSnakesException {
		try {
    		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo));
    		out.writeObject(this);
    		out.close();
		} catch (Exception e) {
            e.printStackTrace();
		}	
	}
	
	/**
	 * Abre y carga un objeto StairsAndSnakes desde un archivo.
	 * @param archivo El archivo desde el que se va a cargar el objeto.
	 * @return El objeto StairsAndSnakes cargado desde el archivo.
	 * @throws StairsAndSnakesException Si ocurre algún error durante el proceso de apertura y carga.
	 */
	public static StairsAndSnakes abra(File archivo) throws StairsAndSnakesException {
		StairsAndSnakes juego = null;
    	try {
            if(!archivo.exists()){
            	throw new StairsAndSnakesException(StairsAndSnakesException.ARCHIVO_NO_ENCONTRADO);
            }
    		ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo));
    		juego = (StairsAndSnakes) in.readObject();
    		in.close();
		} catch (Exception e) {
			throw new StairsAndSnakesException(StairsAndSnakesException.ARCHIVO_NO_ENCONTRADO);
		}
    	return juego;
    }

}