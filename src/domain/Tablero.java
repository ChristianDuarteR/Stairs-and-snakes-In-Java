package domain;

import java.util.*;
import java.awt.Color;
import java.io.Serializable;

public class Tablero implements Serializable {

	private final int width;

	private final HashMap<String,Player> players;

	private final ArrayList<Snake> snakes;

	private final ArrayList<Stair> stairs;
	
	private Box[][] boxs;

	private ArrayList<Dado> dados ;
	
	/**
	 * Crea un nuevo objeto Tablero con los nombres de los jugadores, los colores de las fichas,
	 * El número de serpientes, el número de escaleras, el tamaño del tablero, el porcentaje de casillas especiales,
	 * El porcentaje de modificadores, y una bandera para indicar si se deben transformar las serpientes y escaleras.
	 * @param nombres Los nombres de los jugadores.
	 * @param colores Los colores de las fichas de los jugadores.
	 * @param numSnakes El número de serpientes en el tablero.
	 * @param numStairs El número de escaleras en el tablero.
	 * @param tamano El tamaño del tablero.
	 * @param porC El porcentaje de casillas especiales en el tablero.
	 * @param porM El porcentaje de modificadores en el tablero.
	 * @param transformar Indica si se deben transformar las serpientes y escaleras.
	 */
	public Tablero(ArrayList<String> nombres, ArrayList<Color> colores,int numSnakes,int numStairs, int tamano, int porC,int porM,boolean transformar) {
		players = new HashMap<>();
		snakes = new ArrayList<>();
		stairs = new ArrayList<>();
		width = tamano;
		makeBoxes(porC,numStairs,numSnakes);
		makeSnakes(numSnakes,transformar);
		makeStairs(numStairs,transformar);
		makeDados(porM);
		makePlayers(nombres,colores);	
	}

	/**
	 * Constructor de la clase Tablero que crea el tablero de juego con los parámetros especificados.
	 * @param nombre Nombre del jugador máquina.
	 * @param colores Lista de colores de los jugadores.
	 * @param numSnakes Número de serpientes.
	 * @param numStairs Número de escaleras.
	 * @param tamano Tamaño del tablero.
	 * @param porC Porcentaje de casillas especiales.
	 * @param porM Porcentaje de modificadores.
	 * @param transformar Indicador de si las casillas se transforman o no.
	 * @param dificultad Dificultad del jugador máquina.
	 */
	public Tablero(String nombre, ArrayList<Color> colores, int numSnakes, int numStairs, int tamano, int porC, int porM, boolean transformar, String dificultad){
		players = new HashMap<>();
		snakes = new ArrayList<>();
		stairs = new ArrayList<>();
		width = tamano;
		makeBoxes(porC,numStairs,numSnakes);
		makeStairs(numStairs,transformar);
		makeSnakes(numSnakes,transformar);
		makeDados(porM);
		makePlayer(nombre,colores,dificultad);
	}
	
	/**
	 * Devuelve el ancho del tablero.
	 * @return El ancho del tablero.
	 */
	public int getWidth(){
		return width;
	}

	/**
	 * Obtiene el jugador correspondiente al nombre dado.
	 * @param name nombre del jugador.
	 * @return el objeto player correspondiente al nombre 
	 */
	public Player getJugador(String name) {
		return players.get(name);
	}

	/**
	 * Obtine la matriz del tablero 
	 * @return la casilla
	 */
	public Box[][] getBoxs(){
		return boxs;
	}
	
	/**
	 * Obtiene todas las casillas especiales que se encuentran en el camino del movimiento de una ficha.
	 * @param ficha Ficha cuyo movimiento se está evaluando.
	 * @return Lista de casillas especiales en el camino del movimiento.
	 */
	public ArrayList<Box> getSpecials(Ficha ficha){
		ArrayList<Box> specials = new ArrayList<>();
		Box casillaInicial = ficha.getBox(),casillaPower;
		int position = casillaInicial.getValue();
		Dado dado = getDados().get(0);

		for (int i=0; i<dado.getDado().getNumero() - 1; i++){
			casillaPower = searchBox(position +i+1);
			if (searchBox(casillaPower.getValue()).hasApower()){
				specials.add(casillaPower);
			}
		}
		return specials;
	}

	/**
	 * Crea los dados del juego con el porcentaje de modificación especificado. 
	 * @param porM El porcentaje de modificación de los dados.
	 */
	private void makeDados(int porM){
		dados = new ArrayList<>();
		dados.add(new Dado(porM));
	}
	
	/**
	 * Crea los jugadores del juego con los nombres y colores especificados
	 * @param nombres Los nombres de los jugadores.
	 * @param colores Los colores de los jugadores.
	 */
	private void makePlayers(ArrayList<String> nombres,ArrayList<Color> colores) {
		for(int i=0; i<2; i++) {
			Player jugador = new Player(nombres.get(i), colores.get(i),dados);
			players.put(nombres.get(i), jugador);
			makeToken(jugador);
		}
	}
	
	/**
	 * Crea los jugadores y sus respectivas fichas.
	 * @param nombre Nombre del jugador humano.
	 * @param colores Lista de colores de los jugadores.
	 * @param dificultad Dificultad de la máquina.
	 */
	private void makePlayer(String nombre, ArrayList<Color> colores,String dificultad) {
		Player maquina = new Machine("Machine",colores.get(0),dificultad,dados);
		players.put("Machine",maquina);
		makeToken(maquina);
		Player persona = new Player(nombre,colores.get(1),dados);
		players.put(nombre,persona);
		makeToken(persona);
	}

	/**
	 * Crea una ficha para el jugador especificado y la coloca en la casilla inicial.
	 * @param jugador El jugador para el cual se creará la ficha.
	 */
	private void makeToken(Player jugador) {
		Ficha ficha = new Ficha(jugador);
		ficha.setBox(boxs[width-1][0]);
	}
	
	/**
	 * Crea serpientes en el tablero con el número especificado y la opción de transformar.
	 * @param numSnakes El número de serpientes que se crearán.
	 * @param transformar Indica si las serpientes pueden transformarse.
	 */
	private void makeSnakes(int numSnakes, boolean transformar) {
		int contador = 0;
		while (contador < numSnakes){
			int[] ramBoxF = getRamdoms();
			int[] ramBoxI = getRamdoms();
			Box ibox = boxs[ramBoxF[0]][ramBoxF[1]];
			Box fbox = boxs[ramBoxI[0]][ramBoxI[1]];

			if(ibox.getValue() > fbox.getValue() && fbox.getValue() > 1 && ibox.getValue() <width*width & fbox.getValue() <width*width) {
				if(ibox.getValue() - fbox.getValue() >= width) {
					if (!ibox.hasApower() && !fbox.hasApower()) {
						if (!ibox.hasAnyTramp() && !fbox.hasAnyTramp()) {
							Snake snake = new Snake(ibox, fbox, transformar);
							snakes.add(snake);
							contador++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Crea escaleras en el tablero con el número especificado y la opción de transformar.
	 * @param numStairs El número de escaleras que se crearán.
	 * @param transformar Indica si las escaleras pueden transformarse.
	 */
	private void makeStairs(int numStairs, boolean transformar)  {
		int contador = 0;
		while (contador < numStairs){
			int[] ramBoxF = getRamdoms();
			int[] ramBoxI = getRamdoms();
			Box ibox = boxs[ramBoxF[0]][ramBoxF[1]];
			Box fbox = boxs[ramBoxI[0]][ramBoxI[1]];

			if(ibox.getValue() < fbox.getValue() && ibox.getValue() > 1 && ibox.getValue() <width*width & fbox.getValue() <width*width) {
				if(fbox.getValue() - ibox.getValue() >= width) {
					if (!ibox.hasApower() && !fbox.hasApower()) {
						if (!ibox.hasAnyTramp() && !fbox.hasAnyTramp()) {
							Stair stair = new Stair(ibox, fbox, transformar);
							stairs.add(stair);
							contador++;
						}
					}
				}
			}
		}
	}

	/**
	 * Genera dos números aleatorios dentro del rango de ancho del tablero.
	 * @return Un array de enteros que contiene los dos números generados aleatoriamente.
	 */
	private int[] getRamdoms() {
		
		Random ramdom1 = new Random();
		int[] numeros = new int[2];
		
		int miNumero1 = ramdom1.nextInt(width);
		int miNumero2 = ramdom1.nextInt(width);
		numeros[0] = miNumero1;
		numeros[1] = miNumero2;
		
		return numeros;
	}

	/**
	 * Genera una caja aleatoria con poderes especiales.
	 * @return Una instancia de la caja con poderes generada aleatoriamente.
	 */
	private Box getRamdomBox() {
		Random random = new Random();
		int numero = random.nextInt(6)+1 ;

		return switch (numero) {
			case 1 -> new Jumper(this);
			case 2 -> new JumperInverse(this);
			case 3 -> new Death(this);
			case 4 -> new Movement(this);
			case 5 -> new Recoil(this);
			case 6 -> new Question(this);
			default -> null;
		};
	}

	/**
	 * Genera una caja aleatoria con poderes especiales, excluyendo las escaleras.
	 * @return Una instancia de la caja con poderes generada aleatoriamente (excluyendo las escaleras).
	 */
	private Box getRamdomBoxNoStairs() {
		Random random = new Random();
		int numero = random.nextInt(5)+1 ;

		return switch (numero) {
			case 1 -> new Jumper(this);
			case 2 -> new JumperInverse(this);
			case 3 -> new Death(this);
			case 4 -> new Recoil(this);
			case 5 -> new Question(this);
			default -> null;
		};
	}

	/**
	 * Genera una caja aleatoria con poderes especiales, excluyendo las serpientes.
	 * @return Una instancia de la caja con poderes generada aleatoriamente (excluyendo las serpientes).
	 */
	private Box getRamdomBoxNoSnakes() {
		Random random = new Random();
		int numero = random.nextInt(5)+1 ;

		return switch (numero) {
			case 1 -> new Jumper(this);
			case 2 -> new JumperInverse(this);
			case 3 -> new Death(this);
			case 4 -> new Movement(this);
			case 5 -> new Question(this);
			default -> null;
		};
	}

	/**
	 * Genera una caja aleatoria sin ningún ítem especial.
	 * @return Una instancia de la caja generada aleatoriamente sin ningún ítem especial.
	 */
	private Box getRamdomBoxNoItems() {
		Random random = new Random();
		int numero = random.nextInt(4)+1 ;

		return switch (numero) {
			case 1 -> new Jumper(this);
			case 2 -> new JumperInverse(this);
			case 3 -> new Death(this);
			case 4 -> new Question(this);
			default -> null;
		};
	}
	
	/**
	 * Crea las casillas del tablero.
	 * @param porC Porcentaje de casillas especiales.
	 * @param numStairs Número de escaleras.
	 * @param numSnakes Número de serpientes.
	 */
	private void makeBoxes(int porC,int numStairs,int numSnakes) {
		boxs = new Box[width][width];
		for(int i = 0; i< width; i++) {
			for(int j = 0; j< width; j++) {
				boxs[i][j] = new Box(this);
			}
		}
		if(width % 2 !=0) makeBoxesOdd(porC,numStairs,numSnakes);
		else {
			int numCasilla = 1;
			for (int i = width - 1; i >= 0; i--) {
				if (i % 2 != 0) {
					for (int j = 0; j <= width - 1; j++) {
						boxs[i][j].setValue(numCasilla);
						numCasilla++;
					}
				} else {
					for (int k = width - 1; k >= 0; k--) {
						boxs[i][k].setValue(numCasilla);
						numCasilla++;
					}
				}
			}
			castSpecials(porC, numStairs, numSnakes);
		}
	}

	/**
	 * Crea las casillas del tablero para un tamaño impar.
	 * @param porC Porcentaje de casillas especiales.
	 * @param numStairs Número de escaleras.
	 * @param numSnakes Número de serpientes.
	 */
	private void makeBoxesOdd(int porC,int numStairs,int numSnakes) {
		int numCasilla = 1;
		for (int i = width - 1; i >= 0; i--) {
			if (i % 2 == 0) {
				for (int j = 0; j <= width - 1; j++) {
					boxs[i][j].setValue(numCasilla);
					numCasilla++;
				}
			} else {
				for (int k = width - 1; k >= 0; k--) {
					boxs[i][k].setValue(numCasilla);
					numCasilla++;
				}
			}
		}
		castSpecials(porC, numStairs, numSnakes);
	}

	/**
	 * Asigna casillas especiales al azar en el tablero.
	 * @param porC Porcentaje de casillas especiales.
	 * @param numStairs Número de escaleras.
	 * @param numSnakes Número de serpientes.
	 */
	private void castSpecials(int porC,int numStairs,int numSnakes){
		int contador = 0;
		int fila,columna;

		if(numSnakes>0 && numStairs>0) {
			while (contador < porC) {
				int[] filaCol = getRamdoms();
				fila = filaCol[0];
				columna = filaCol[1];

				Box boxWithOutPower = boxs[fila][columna];
				int value = boxWithOutPower.getValue();
				Box BoxWithPower = getRamdomBox();

				if (!boxWithOutPower.hasApower() && value > 1 && value <width*width) {
					setBox(fila, columna, BoxWithPower, value);
					contador++;
				}
			}
		}
		else if (numSnakes >0){
			while (contador < porC) {
				int[] filaCol = getRamdoms();
				fila = filaCol[0];
				columna = filaCol[1];

				Box boxWithOutPower = boxs[fila][columna];
				int value = boxWithOutPower.getValue();
				Box BoxWithPower = getRamdomBoxNoStairs();

				if (!boxWithOutPower.hasApower() && value > 1 & value < width*10) {
					setBox(fila, columna, BoxWithPower, value);
					contador++;
				}
			}
		}else if(numStairs >0 ){
			while (contador < porC) {
				int[] filaCol = getRamdoms();
				fila = filaCol[0];
				columna = filaCol[1];

				Box boxWithOutPower = boxs[fila][columna];
				int value = boxWithOutPower.getValue();
				Box BoxWithPower = getRamdomBoxNoSnakes();

				if (!boxWithOutPower.hasApower() && value > 1 && value < width*10) {
					setBox(fila, columna, BoxWithPower, value);
					contador++;
				}
			}
		}else {
			while (contador < porC) {
				int[] filaCol = getRamdoms();
				fila = filaCol[0];
				columna = filaCol[1];

				Box boxWithOutPower = boxs[fila][columna];
				int value = boxWithOutPower.getValue();
				Box BoxWithPower = getRamdomBoxNoItems();

				if (!boxWithOutPower.hasApower() && value > 1 && value<width*10) {
					setBox(fila, columna, BoxWithPower, value);
					contador++;
				}
			}
		}
	}

	/**
	 * Establece una casilla en una posición específica del tablero.
	 * @param fila  Fila de la casilla.
	 * @param columna Columna de la casilla.
	 * @param box Casilla a establecer.
	 * @param value Valor de la casilla.
	 */
	private void setBox(int fila,int columna, Box box,int value) {
		boxs[fila][columna] = box;
		boxs[fila][columna].setValue(value);
	}

	/**
	 * Devuelve la lista de dados del juego.
	 * @return Lista de dados.
	 */
	public ArrayList<Dado> getDados(){
		return dados;
	}

	/**
	 * Busca la fila y columna de una casilla con un valor específico.
	 * @param cantidadcantidad Valor de la casilla a buscar.
	 * @return Arreglo de enteros con la fila y columna de la casilla encontrada.
	 */
	public int[] searchRowAndColumn(int cantidad) {
		int[] posiciones = new int[2];
		if(cantidad <= 1) {
			posiciones[0] = 0;
			posiciones[1] = 1;
		}
		for (int i=0;i< width;i++){
			for (int j=0;j<width;j++){
				if (boxs[i][j].getValue() == cantidad){
					posiciones[0] = i;
					posiciones[1] = j;
				}
			}
		}
		return posiciones;
	}
	
	/**
	 * Busca una casilla con un valor específico.
	 * @param cantidad Valor de la casilla a buscar.
	 * @return La casilla encontrada, o null si no se encontró ninguna.
	 */
	public Box searchBox(int cantidad){
		if(cantidad <= 1) {
			return boxs[width-1][0];
		}
		for (int i = 0; i < width; i++) {
			for (Box b : boxs[i]) {
				if (b.getValue() == cantidad) {
					return b;
				}
			}
		}
		return null;
	}

	/**
	 * Busca una ficha del oponente por su color.
	 * @param color Color de la ficha del oponente a buscar.
	 * @return La ficha del oponente encontrada, o null si no se encontró ninguna.
	 */
	public Ficha searchOpponent(Color color) {
		Set<Map.Entry<String, Player>> entradas = players.entrySet();

		for (Map.Entry<String,Player> entrada: entradas) {
			String nombre = entrada.getKey();
			Player player = players.get(nombre);
			if (!player.getColor().equals(color)){
				return player.getFichas().get(0);
			}
		}
		return null;
	}

	/**
	 * Busca una serpiente a partir de una ficha.
	 * @param ficha Ficha desde la cual se realizará la búsqueda de la serpiente.
	 * @return La serpiente encontrada.
	 * @throws StairsAndSnakesException Si no se encuentra ninguna serpiente.
	 */
	public Snake searchSnake(Ficha ficha) throws StairsAndSnakesException{
		int value = ficha.getBox().getValue();
		int diferencia, menorSerpiente = width*width;
		int index = width;
		for (int i=0; i<width;i++){
			for(Box b: boxs[i]){
				if(b.hasAnyTramp() && b.getItem() instanceof Snake){
					Tramp tramp = (Snake) b.getItem();
					if (tramp.getCasillaInicial().getValue() < value) {
						Box inicio = tramp.getCasillaInicial();
						diferencia = value - inicio.getValue();
						if (diferencia < menorSerpiente){
							menorSerpiente = diferencia;
							index = inicio.getValue();
						}
					}
				}
			}
		}
		if (menorSerpiente == width*width) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_FOUND_SNAKE);
		Item snake = searchBox(index).getItem();
		return (Snake) snake;
	}

	/**
	 * Busca una escalera a partir de una ficha.
	 * @param ficha Ficha desde la cual se realizará la búsqueda de la escalera.
	 * @return La escalera encontrada.
	 * @throws StairsAndSnakesException Si no se encuentra ninguna escalera.
	 */
	public Stair searchStair(Ficha ficha) throws StairsAndSnakesException{
		int value = ficha.getBox().getValue();
		int diferencia, menorEscalera = width*width;
		int index = width;
		for (int i=0; i<width;i++){
			for(Box b: boxs[i]){
				if(b.hasAnyTramp() && b.getItem() instanceof Stair){
					Tramp tramp = (Stair) b.getItem();
					if (tramp.getCasillaInicial().getValue() > value) {
						Box inicio = tramp.getCasillaInicial();
						diferencia = inicio.getValue() - value;
						if (diferencia < menorEscalera){
							menorEscalera = diferencia;
							index = inicio.getValue();
						}
					}
				}
			}
		}
		if (menorEscalera == width*width) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_FOUND_STAIR);
		Item stair = searchBox(index).getItem();
		return (Stair) stair;
	}

}

