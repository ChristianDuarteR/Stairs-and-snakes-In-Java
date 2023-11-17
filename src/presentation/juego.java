package presentation;

import domain.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.EOFException;

import javax.swing.filechooser.FileNameExtensionFilter;

import domain.Box;


public class juego extends JFrame {
	private StairsAndSnakes stairsandsnakes;
	private JMenuItem nuevo, abrir, salvar, salir, cambiocolor;
	private JPanel tableroJuego, jugador1, jugador2;
	private JLabel nombre1, color1, numEsc1, numSer1, numCas1, maxCas1, numMod1;
	private JLabel nombre2, color2, numEsc2, numSer2, numCas2, maxCas2, numMod2;
	private JLabel nom1, col1, esca1, serp1, casEsp1, maxcas1, mod1;
	private JLabel nom2, col2, esca2, serp2, casEsp2, maxcas2, mod2;
	private JPanel[][] casillas;
	private JButton lanzar, finalizar, volver;
	private ImageIcon cara1, cara2, cara3, cara4, cara5, cara6;
	private JLabel lcaras;
	private JButton player1, player2;

	/**
	 * Constructs a juego object with the specified parameters.
	 * @param nombre1 the name of player 1
	 * @param nombre2 the name of player 2
	 * @param coloruno the color of player 1
	 * @param colordos the color of player 2
	 * @param tipoRival the type of opponent
	 * @param casilla the number of boxes on the board
	 * @param modificador the number of modifiers in the game
	 * @param tablero the type of board layout
	 * @param escalera the type of ladder element
	 * @param serpiente the type of snake element
	 * @param transformar the flag indicating whether transformations are allowed
	 * @param dificultad the difficulty level of the game
	 * @throws StairsAndSnakesException if there is an error in preparing the Stairs and Snakes game
	 */
	public juego(String nombre1, String nombre2, String coloruno, String colordos, String tipoRival, int casilla, int modificador, String tablero, String escalera, String serpiente, String transformar, String dificultad) throws StairsAndSnakesException {
		nom1 = new JLabel(nombre1);
		nom2 = new JLabel(nombre2);
		col1 = new JLabel(coloruno);
		col2 = new JLabel(colordos);
		prepareStairsAndSnakes(nombre1, nombre2, casilla, modificador, tablero, escalera, serpiente, transformar, coloruno, colordos, tipoRival,dificultad);
		prepareElements();
		prepareActions();
		if (stairsandsnakes.getTablero().getJugador("Machine") != null) movePlayer();
	}

	/**
	 * Prepara el juego de Stairs and Snakes con los nombres de los jugadores, configuraciones de casilla y modificadores y opciones de tablero.
	 * @param nombre1 Nombre del primer jugador.
	 * @param nombre2 Nombre del segundo jugador.
	 * @param casilla Número de casilla inicial.
	 * @param modificador Número de modificadores.
	 * @param tablero Tamaño del tablero.
	 * @param escalera Número de escaleras.
	 * @param serpiente Número de serpientes.
	 * @param transformar Opción para transformar casillas.
	 * @param coloruno Color del primer jugador.
	 * @param colordos Color del segundo jugador.
	 * @param tipoRival Tipo de rival (jugador o máquina).
	 * @param dificultad Nivel de dificultad para la máquina.
	 * @throws StairsAndSnakesException Excepción lanzada si no se proporcionan todas las configuraciones de casilla y modificador.
	 */
	private void prepareStairsAndSnakes(String nombre1, String nombre2, int casilla, int modificador, String tablero, String escalera, String serpiente, String transformar, String coloruno, String colordos, String tipoRival,String dificultad) throws StairsAndSnakesException {
		ArrayList<String> nombres = new ArrayList<>();
		ArrayList<Color> colores = new ArrayList<>();
		boolean trans;
		nombres.add(nombre1);
		nombres.add(nombre2);
		colores.add(verifyColor(coloruno));
		colores.add(verifyColor(colordos));
		trans = transformar.equals("Yes");
		if (tablero.equals("") || escalera.equals("") || serpiente.equals(""))
			throw new StairsAndSnakesException(StairsAndSnakesException.NOT_ALL_SPACES_FULL);

		int tab = Integer.parseInt(tablero);
		int ser = Integer.parseInt(serpiente);
		int esc = Integer.parseInt(escalera);
		stairsandsnakes = new StairsAndSnakes(nombres, colores, tipoRival, tab, ser, esc, trans, casilla, modificador,dificultad);
	}

	/*
	 * Prepara los elementos visuales del juego Stairs and Snakes.
	 */
	private void prepareElements() {
		setLayout(null);
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		setMinimumSize(new Dimension(1045, 665));
		setSize(1045, 665);
		setLocationRelativeTo(null);
		setResizable(false);
		prepareElementsMenu();
		prepareElementstablero();
		prepareElementsjugadores();
		prepareElementsInformacion();
		prepareElementsTokens();
		prepareElementsDade();
		prepareSpecialBoxes();
	}

	/*
	 * Prepara los elementos del menú del juego Stairs and Snakes.
	 */
	private void prepareElementsMenu() {
		JMenuBar herramientas = new JMenuBar();
		JMenu Menu = new JMenu("Menu"), settings = new JMenu("configuracion");
		nuevo = new JMenuItem("New");
		salvar = new JMenuItem("Save");
		abrir = new JMenuItem("Open");
		salir = new JMenuItem("Exit");
		cambiocolor = new JMenuItem("Cambiar color");
		settings.add(cambiocolor);
		herramientas.add(Menu);
		herramientas.add(settings);
		Menu.add(salvar);
		Menu.add(new JSeparator());
		Menu.add(abrir);
		Menu.add(new JSeparator());
		Menu.add(salir);
		setJMenuBar(herramientas);
	}

	/*
	 * Prepara los elementos del tablero de juego.
	 */
	private void prepareElementstablero() {
		Tablero tablero = stairsandsnakes.getTablero();
		tableroJuego = new JPanel();
		int tamano = tablero.getWidth();
		tableroJuego.setLayout(new GridLayout(tamano, tamano));
		casillas = new JPanel[tamano][tamano];
		JPanel casilla;
		for (int i = 0; i < tamano; i++) {
			for (int j = 0; j < tamano; j++) {
				casilla = new JPanel(new BorderLayout());
				casilla.setBorder(new LineBorder(Color.black));
 				casillas[i][j] = casilla;
				tableroJuego.add(casilla);
			}
		}
		prepareNumbers(tamano);
		add(tableroJuego);
		tableroJuego.setBounds(10, 10, (5 * getWidth()) / 7, getHeight() - getHeight() / 8);
	}

	/*
	 * Prepara los números de las casillas del tablero de juego cuando el tamaño es par.
	 * @param tamano Tamaño del tablero de juego.
	 */
	private void prepareNumbers(int tamano) {
		if (tamano % 2 != 0) prepareNumbersOdd(tamano);
		else {
			int numCasilla = 1;
			for (int i = tamano - 1; i >= 0; i--) {
				if (i % 2 != 0) {
					for (int j = 0; j <= tamano - 1; j++) {
						casillas[i][j].add(new JLabel(Integer.toString(numCasilla)), BorderLayout.SOUTH);
						if (numCasilla % 2 != 0) {
							casillas[i][j].setBackground(Color.gray);
						} else {
							casillas[i][j].setBackground(Color.white);
						}
						numCasilla++;
					}
				} else {
					for (int k = tamano - 1; k >= 0; k--) {
						casillas[i][k].add(new JLabel(Integer.toString(numCasilla)), BorderLayout.SOUTH);
						if (numCasilla % 2 != 0) {
							casillas[i][k].setBackground(Color.gray);
						} else {
							casillas[i][k].setBackground(Color.white);
						}
						numCasilla++;
					}
				}
			}
		}
	}

	/*
	 * 
	 */
	private void prepareNumbersOdd(int tamano) {
		int numCasilla = 1;
		for (int i = tamano - 1; i >= 0; i--) {
			if (i % 2 == 0) {
				for (int j = 0; j <= tamano - 1; j++) {
					casillas[i][j].add(new JLabel(Integer.toString(numCasilla)), BorderLayout.SOUTH);
					if (numCasilla % 2 != 0) {
						casillas[i][j].setBackground(Color.gray);
					} else {
						casillas[i][j].setBackground(Color.white);
					}
					numCasilla++;
				}
			} else {
				for (int k = tamano - 1; k >= 0; k--) {
					casillas[i][k].add(new JLabel(Integer.toString(numCasilla)), BorderLayout.SOUTH);
					if (numCasilla % 2 != 0) {
						casillas[i][k].setBackground(Color.gray);
					} else {
						casillas[i][k].setBackground(Color.white);
					}
					numCasilla++;
				}
			}
		}
	}

	/*
	 * Prepara los números de las casillas del tablero de juego cuando el tamaño es impar.
	 * @param tamano Tamaño del tablero de juego.
	 */
	private void prepareSpecialBoxes() {
		Tablero tablero = stairsandsnakes.getTablero();
		for (int i = 0; i < tablero.getWidth(); i++) {
			for (Box b : tablero.getBoxs()[i]) {
				if (b.hasApower()) {
					int v = b.getValue();
					int[] posiciones = tablero.searchRowAndColumn(v);
					changeImagen(posiciones, b);
				}
			}
		}
	}

	/*
	 * Cambia la imagen de una casilla del tablero de juego según el tipo de casilla.
	 * @param posiciones Arreglo que contiene las posiciones de la casilla en el tablero (fila y columna).
	 * @param casilla Objeto de tipo Box que representa la casilla del tablero.
	 */
	private void changeImagen(int[] posiciones, Box casilla) {
		JLabel fondo = null;
		int fila = posiciones[0];
		int columna = posiciones[1];
		JPanel cas = casillas[fila][columna];

		if (casilla instanceof Death) {
			fondo = new JLabel(new ImageIcon(getClass().getResource("Imagenes/" + "muerte" + ".png")));
		} else if (casilla instanceof Jumper) {
			fondo = new JLabel(new ImageIcon(getClass().getResource("Imagenes/" + "saltarina" + ".png")));
		} else if (casilla instanceof JumperInverse) {
			fondo = new JLabel(new ImageIcon(getClass().getResource("Imagenes/" + "saltarinainversa" + ".png")));
		} else if (casilla instanceof Movement) {
			fondo = new JLabel(new ImageIcon(getClass().getResource("Imagenes/" + "movimiento" + ".png")));
		} else if (casilla instanceof Recoil) {
			System.out.println(casilla);
			fondo = new JLabel(new ImageIcon(getClass().getResource("Imagenes/" + "devolver" + ".png")));
		} else if (casilla instanceof Question) {
			fondo = new JLabel(new ImageIcon(getClass().getResource("Imagenes/" + "pregunta" + ".png")));
		}
		assert fondo != null;
		fondo.setPreferredSize(new Dimension(cas.getWidth(), cas.getHeight()));
		cas.add(fondo, BorderLayout.CENTER);
	}

	/*
	 * 
	 */
	private void prepareElementsjugadores(){
		jugador1 = new JPanel();
		jugador1.setBackground(Color.white);
		jugador1.setBorder(BorderFactory.createTitledBorder(new TitledBorder("Jugador 1")));
		jugador1.setLayout(new GridLayout(7,2));
		serp1 = new JLabel("0");
		serp2 = new JLabel("0");
		esca1 = new JLabel("0");
		esca2 = new JLabel("0");
		maxcas1 = new JLabel( "1");
		maxcas2 = new JLabel("1");
		casEsp1 = new JLabel("0");
		casEsp2 = new JLabel("0");
		mod1 = new JLabel("0");
		mod2 = new JLabel("0");
		numMod1 = new JLabel("Modificadores: ");
		nombre1 = new JLabel("Nombre: ");
		color1 = new JLabel("Color: ");
		numEsc1 = new JLabel("Escaleras: ");
		numSer1 = new JLabel("Serpientes: ");
		maxCas1 = new JLabel("Maxima Casilla: ");
		numCas1 = new JLabel("Especiales: ");
		numMod2 = new JLabel("Modificadores: ");
		nombre2 = new JLabel("Nombre: ");
		color2 = new JLabel("Color: ");
		numEsc2 = new JLabel("Escaleras: ");
		numSer2 = new JLabel("Serpientes: ");
		maxCas2 = new JLabel("Maxima Casilla: ");
		numCas2 = new JLabel("Especiales: ");
		jugador1.add(nombre1);
		jugador1.add(nom1);
		jugador1.add(color1);
		jugador1.add(col1);
		jugador1.add(numEsc1);
		jugador1.add(esca1);
		jugador1.add(numSer1);
		jugador1.add(serp1);
		jugador1.add(maxCas1);
		jugador1.add(maxcas1);
		jugador1.add(numMod1);
		jugador1.add(mod1);
		jugador1.add(numCas1);
		jugador1.add(casEsp1);
		add(jugador1);
		jugador1.setBounds(tableroJuego.getWidth()+20,10,getWidth()- tableroJuego.getWidth()-40,getHeight()/2 - getHeight()/4);

		jugador2 = new JPanel();
		jugador2.setBackground(Color.white);
		jugador2.setBorder(BorderFactory.createTitledBorder(new TitledBorder("Jugador 2")));
		jugador2.setLayout(new GridLayout(7,2));
		jugador2.add(nombre2);
		jugador2.add(nom2);
		jugador2.add(color2);
		jugador2.add(col2);
		jugador2.add(numEsc2);
		jugador2.add(esca2);
		jugador2.add(numSer2);
		jugador2.add(serp2);
		jugador2.add(maxCas2);
		jugador2.add(maxcas2);
		jugador2.add(numMod2);
		jugador2.add(mod2);
		jugador2.add(numCas2);
		jugador2.add(casEsp2);
		add(jugador2);
		jugador2.setBounds(tableroJuego.getWidth()+20,jugador1.getHeight() + 20,getWidth()- tableroJuego.getWidth()-40,getHeight()/2 - getHeight()/4);
	}

	/*
	 * Prepara los elementos visuales para mostrar la información de los jugadores.
	 * Crea paneles y etiquetas para mostrar el nombre, color, escaleras, serpientes, máxima casilla, modificadores y casillas especiales de cada jugador.
	 */
	private void prepareElementsInformacion(){
		lanzar = new JButton("Lanzar Dado");
		lanzar.setBounds(tableroJuego.getWidth()+20,tableroJuego.getHeight()-50,jugador2.getWidth()/2 -20,25);
		add(lanzar);
		finalizar = new JButton("Finalizar Juego");
		finalizar.setBounds(lanzar.getX()+lanzar.getWidth()/2,lanzar.getY() + lanzar.getHeight() + 10,lanzar.getWidth()+ 40,25);
		add(finalizar);
		volver = new JButton("Volver");
		volver.setBounds(lanzar.getX() +jugador2.getWidth()-lanzar.getWidth(),lanzar.getY(),lanzar.getWidth(),25);
		add(volver);
	}

	/*
	 * Prepara los elementos visuales relacionados con el dado.
	 * Carga las imágenes de las caras del dado y crea una etiqueta para mostrar la cara actual del dado.
	 */
	private void prepareElementsDade(){
		cara1 = new ImageIcon(getClass().getResource("Imagenes/"+"cara1"+".png"));
		cara2 = new ImageIcon(getClass().getResource("Imagenes/"+"cara2"+".png"));
		cara3 = new ImageIcon(getClass().getResource("Imagenes/"+"cara3"+".png"));
		cara4 = new ImageIcon(getClass().getResource("Imagenes/"+"cara4"+".png"));
		cara5 = new ImageIcon(getClass().getResource("Imagenes/"+"cara5"+".png"));
		cara6 = new ImageIcon(getClass().getResource("Imagenes/"+"cara6"+".png"));

		lcaras = new JLabel(cara2);
		lcaras.setBounds(jugador2.getX() + lanzar.getWidth()/2 + 11,jugador2.getY() + jugador2.getHeight()+20,128,128);
		add(lcaras);
	}

	/*
	 * Prepara los elementos visuales relacionados con los tokens de los jugadores.
	 * Crea botones para representar los tokens de los jugadores en la casilla inicial.
	 * Asigna el color correspondiente a cada jugador.
	 */
	private void prepareElementsTokens(){
		Color mycolor;
		int tamano = stairsandsnakes.getTablero().getWidth();
		JPanel casillaInicial = casillas[tamano-1][0];
		player1 = new JButton();
		mycolor = verifyColor(col1.getText());
		player1.setBackground(mycolor);
		player1.setEnabled(false);

		player2 = new JButton();
		mycolor = verifyColor(col2.getText());
		player2.setBackground(mycolor);
		player2.setEnabled(false);
		casillaInicial.add(player1,BorderLayout.EAST);
		casillaInicial.add(player2,BorderLayout.WEST);
	}

	/*
	 * Verifica y devuelve el objeto Color correspondiente al nombre de color especificado.
	 * @param color El nombre del color a verificar.
	 * @return El objeto Color correspondiente al nombre de color.
	 */
	private Color verifyColor(String color){
		Color myColor = switch (color) {
			case "Azul" -> Color.blue;
			case "Verde" -> Color.GREEN;
			case "Rojo" -> Color.RED;
			case "Amarillo" -> Color.yellow;
			default -> Color.cyan;
		};
		return myColor;
	}

	/*
	 * Prepara y dibuja las líneas de las trampas en el tablero.
	 * @param g El objeto Graphics.
	 */
	private void prepareLines(Graphics g){
		Tablero tablero = stairsandsnakes.getTablero();
		for (int i=0; i<tablero.getWidth();i++){
			for (Box b: tablero.getBoxs()[i]){
				if (b.hasAnyTramp()){
					Tramp trampa = (Tramp) b.getItem();
					Box casIni = trampa.getCasillainicial();
					Box casFin = trampa.getCasillafin();
					paintLine(casIni,casFin,trampa,g);
				}
			}
		}
	}

	/*
	 * Dibuja una línea entre las casillas de inicio y fin de una trampa en el tablero.
	 * @param casIni La casilla de inicio de la trampa.
	 * @param casFin La casilla de fin de la trampa.
	 * @param tramp La trampa que se está dibujando.
	 * @param g El objeto Graphics.
	 */
	private void paintLine(Box casIni, Box CasFin, Tramp tramp, Graphics g){
		int xi, xf;
		int yi, yf;
		Tablero tablero = stairsandsnakes.getTablero();
		int[] posIni = tablero.searchRowAndColumn(casIni.getValue());
		int[] posFin = tablero.searchRowAndColumn(CasFin.getValue());
		JPanel head = casillas[posIni[0]][posIni[1]];
		JPanel tail = casillas[posFin[0]][posFin[1]];

		xi = head.getX() + head.getWidth()/2;
		yi = head.getY() + head.getWidth();
		xf = tail.getX() + tail.getWidth()/2;
		yf = tail.getY() + tail.getWidth();
		if (tramp instanceof Snake){
			g.setColor(Color.green);
		}else if(tramp instanceof Stair){
			g.setColor(Color.orange);
		}
		g.drawLine(xi,yi,xf,yf);
	}

	/*
	 * Prepara las acciones del menú principal.
	 * Configura el comportamiento de los elementos del menú "Abrir", "Guardar" y "Salir".
	 */
	/*private void prepareActionsMenu() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            
            public void windowClosing(WindowEvent e){
                salir();
            }
        });
		abrir.addActionListener(e -> open());
        salvar.addActionListener(e -> save());
        salir.addActionListener(e -> salir());
	}*/

	/**
	 * Prepara las acciones de los elementos interactivos del juego.
	 * Configura los ActionListener correspondientes a los eventos de los botones.
	 */
	private void prepareActions(){
		ActionListener oyenteDeColor = e -> changeColor();
		cambiocolor.addActionListener(oyenteDeColor);
		ActionListener oyenteDeVolver = e -> createOption();
		volver.addActionListener(oyenteDeVolver);
		ActionListener oyenteDeFinalizar = e -> exit();
		finalizar.addActionListener(oyenteDeFinalizar);

		if (stairsandsnakes.getTablero().getJugador("Machine") == null) {
			ActionListener oyenteDeMover = e -> movePlayer();
			lanzar.addActionListener(oyenteDeMover);
		} else {
			ActionListener oyenteDeMover = e -> movePlayers();
			lanzar.addActionListener(oyenteDeMover);
		}
	}
	
	/*
	 * Cambia el color de los elementos interactivos del juego.
	 * Muestra un cuadro de diálogo para que el jugador elija un color.
	 * Actualiza los colores según la selección del jugador.
	 */
	private void changeColor(){
		String[] opciones = {"Rojo","Verde","Gris"};
		int option = JOptionPane.showOptionDialog(this,"Que color desea? ", "Cambio Color",
				JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,opciones,opciones[0]);

		if (option == 0){
			cambiarColor(Color.RED,Color.pink);
		}else if (option == 1){
			cambiarColor(Color.getHSBColor(0,200,55),Color.GREEN);
		}else {
			cambiarColor(Color.gray,Color.white);
		}
	}

	/*
	 * Cambia el color de las casillas del tablero según el esquema de colores seleccionado.
	 * @param oscuro Color oscuro a aplicar a las casillas impares.
	 * @param claro Color claro a aplicar a las casillas pares.
	 */
	private void cambiarColor(Color oscuro,Color claro) {
		Tablero tablero = stairsandsnakes.getTablero();
		int tamano = tablero.getWidth();
		int value = 1;
		for (int i = 0; i < tamano; i++) {
			if(i%2!=0){
				for(int j=0;j<=tamano-1;j++){
					if(value%2!=0){
						casillas[i][j].setBackground(oscuro);
					}else {
						casillas[i][j].setBackground(claro);
					}
					value++;
				}
			}else {
				for(int k=tamano-1;k>=0;k--){
					if(value%2!=0){
						casillas[i][k].setBackground(oscuro);
					}else {
						casillas[i][k].setBackground(claro);
					}
					value++;
				}
			}
		}
	}

	/*
	 * Crea una ventana de confirmación para preguntar al jugador si desea volver a empezar la partida.
	 * Si el jugador confirma, se crea una nueva instancia de la clase Opciones y se muestra la ventana.
	 * Si el jugador cancela, no se realiza ninguna acción.
	 */
	private void createOption(){
		if(JOptionPane.showConfirmDialog(this,"Desea volver a empezar la partida? ")==0) {
			Opciones op = new Opciones();
			op.setVisible(true);
			dispose();
		}
	}
	
	/*private void save() {
	    if (stairsandsnakes == null) {
	        JOptionPane.showMessageDialog(this, "No se puede guardar hasta hacer una acción", "Info", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        File archivo = null;
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo DAT (*.dat)", "dat");
	        if (selectArchivo == null) {
	            selectArchivo = new JFileChooser();
	        }
	        selectArchivo.setFileFilter(filter);
	        selectArchivo.setVisible(true);
	        int confirmation = selectArchivo.showSaveDialog(salvar);
	        if (confirmation == JFileChooser.APPROVE_OPTION) {
	            archivo = new File(selectArchivo.getSelectedFile() + ".dat");
	        }
	        selectArchivo.setVisible(false);
	        if (archivo == null) {
	            JOptionPane.showMessageDialog(this, "Se canceló la operación de guardar.", "Información", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            try {
	                stairsandsnakes.guarde(archivo);
	            } catch (StairsAndSnakesException pe) {
	                System.out.println(pe.getMessage());
	            }
	        }
	    }
	}*/
	
	/**
     * Accion de guardar un archivo.
     */
	/*private void save() {
	    if (stairsandsnakes == null) {
	        JOptionPane.showMessageDialog(this, "No se puede guardar hasta hacer una acción", "Info", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        File archivo = null;
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo DAT (*.dat)", "dat");
	        if (selectArchivo == null) {
	            selectArchivo = new JFileChooser();
	        }
	        selectArchivo.setFileFilter(filter);
	        selectArchivo.setVisible(true);
	        int confirmation = selectArchivo.showSaveDialog(this);
	        if (confirmation == JFileChooser.APPROVE_OPTION) {
	            archivo = selectArchivo.getSelectedFile();
	            if (!archivo.getName().endsWith(".dat")) {
	                archivo = new File(archivo.getAbsolutePath() + ".dat");
	            }
	        }
	        selectArchivo.setVisible(false);
	        if (archivo == null) {
	            JOptionPane.showMessageDialog(this, "Se canceló la operación de guardar.", "Información", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            try {
	                FileOutputStream fileOutputStream = new FileOutputStream(archivo);
	                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
	                objectOutputStream.writeObject(stairsandsnakes);
	                objectOutputStream.close();
	                fileOutputStream.close();
	                JOptionPane.showMessageDialog(this, "El archivo se ha guardado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }
	}*/


	
	/*private void open() {
		File archivo = null;
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo DAT (*.dat)", "dat");
    	selectArchivo.setFileFilter(filter);
    	selectArchivo.setVisible(true);
    	int confirmation = selectArchivo.showOpenDialog(abrir);
    	if (confirmation == selectArchivo.APPROVE_OPTION) {
    		archivo = selectArchivo.getSelectedFile();
    	}
    }*/
	
	/*private void open() {
	    File archivo = null;
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo DAT (*.dat)", "dat");
	    JFileChooser selectArchivo = new JFileChooser();
	    selectArchivo.setFileFilter(filter);
	    selectArchivo.setVisible(true);
	    int confirmation = selectArchivo.showOpenDialog(abrir);
	    if (confirmation == JFileChooser.APPROVE_OPTION) {
	        archivo = selectArchivo.getSelectedFile();
	    }
	    selectArchivo.setVisible(false);
	    if (archivo == null) {
	        JOptionPane.showMessageDialog(this, "Se canceló la operación de abrir.", "Información", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        try {
	            FileInputStream fileIn = new FileInputStream(archivo);
	            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	            stairsandsnakes = (StairsAndSnakes) objectIn.readObject();
	            objectIn.close();
	            fileIn.close();
	            
	            // Realiza las acciones necesarias con el objeto stairsandsnakes abierto
	            // ...

	            JOptionPane.showMessageDialog(this, "Archivo abierto correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
	        } catch (IOException | ClassNotFoundException e) {
	            JOptionPane.showMessageDialog(this, "Error al abrir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}*/
	
	/**
     * Accion de abrir un arhivo.
     */
	/*private void open() {
	    JFileChooser selectArchivo = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo DAT (*.dat)", "dat");
	    selectArchivo.setFileFilter(filter);
	    selectArchivo.setVisible(true);
	    int confirmation = selectArchivo.showOpenDialog(this);
	    if (confirmation == JFileChooser.APPROVE_OPTION) {
	        File archivo = selectArchivo.getSelectedFile();
	        selectArchivo.setVisible(false);
	        try {
	        	
	            FileInputStream fileInputStream = new FileInputStream(archivo);
	            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	            stairsandsnakes = (StairsAndSnakes) objectInputStream.readObject();
	            objectInputStream.close();
	            fileInputStream.close();
	            JOptionPane.showMessageDialog(this, "El archivo se ha abierto correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        } catch (ClassNotFoundException e) {
	            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        selectArchivo.setVisible(false);
	        JOptionPane.showMessageDialog(this, "Se canceló la operación de abrir.", "Información", JOptionPane.INFORMATION_MESSAGE);
	    }
	}*/


	
	/**
     * Accion de cerrar la ventana.
     */
	private void salir() {   
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir del juego?", "Salir del juego", JOptionPane.OK_CANCEL_OPTION);
        if (respuesta == JOptionPane.OK_OPTION){
            setVisible(false);
            System.exit(0);
        }
    } 
	
	/**
     * Accion de cerrar la ventana.
     */
	private void exit(){
		if(JOptionPane.showConfirmDialog(this,"Desea acabar el juego? ")==0){
			System.exit(0);
		}
	}
	
	/**
	 * Mueve a los jugadores en el juego y actualiza la interfaz gráfica.
	 */
	private void movePlayers(){
		String name;
		for (JLabel jLabel : Arrays.asList(nom2, nom1)) {
			name = jLabel.getText();
			stairsandsnakes.changeDados(name);
			if (name.equals(nom2.getText())){
				paintDados();
			}
			try {
				stairsandsnakes.movePlayer(name);
				if(stairsandsnakes.getWin()) System.exit(0);
			} catch (StairsAndSnakesException e){
				JOptionPane.showMessageDialog(this,e.getMessage());
			}
		}
		moveTokens();
		repaint();
		updateJLabels();
	}
	
	/**
     * Accion para mover un player.
     */
	private void movePlayer() {
		String name;
		int turno = stairsandsnakes.getTurno();

		if (turno %2 == 0){
			name = nom1.getText();
			stairsandsnakes.changeDados(name);
			paintDados();
			try {
				stairsandsnakes.movePlayer(name);
			} catch (StairsAndSnakesException e) {
				JOptionPane.showMessageDialog(this,e.getMessage());
			}
		}else {
			name = nom2.getText();
			stairsandsnakes.changeDados(name);
			paintDados();
			try {
				stairsandsnakes.movePlayer(name);
			} catch (StairsAndSnakesException e) {
				JOptionPane.showMessageDialog(this,e.getMessage());
			}

		}
		moveTokens();
		repaint();
		updateJLabels();
		if(stairsandsnakes.getWin()) System.exit(0);

	}
	
	/**
	*Actualiza las etiquetas (JLabels) del tablero.
	*/
	private void updateJLabels(){
		Tablero tablero = stairsandsnakes.getTablero();
		Ficha ficha1, ficha2;
		ficha1 = tablero.getJugador(nom1.getText()).getFichas().get(0);
		ficha2 = tablero.getJugador(nom2.getText()).getFichas().get(0);

		updateStairs(ficha1.getStairs(),ficha2.getStairs());
		updateSnakes(ficha1.getSnakes(),ficha2.getSnakes());
		updateMaxBox(ficha1.getMaxCas(),ficha2.getMaxCas());
		updateEspecialBox(ficha1.getNumSpecialBox(),ficha2.getNumSpecialBox());
		updateMods(ficha1.getNumMod(),ficha2.getNumMod());
	}

	/*
	 * Actualiza el número de escaleras para los jugadores en la interfaz.
	 * @param stairs1 Número de escaleras para el jugador 1.
	 * @param stairs2 Número de escaleras para el jugador 2.
	 * @throws IllegalArgumentException
	 */
	private void updateStairs(int stairs1, int stairs2){
		esca1.setText(Integer.toString(stairs1));
		esca2.setText(Integer.toString(stairs2));
	}

	/*
	 * Actualiza el número de serpientes para los jugadores en la interfaz.
	 * @param snakes1 Número de serpientes para el jugador 1.
	 * @param snakes2 Número de serpientes para el jugador 2.
	 * @throws IllegalArgumentException si los valores de serpientes son negativos.
	 */
	private void updateSnakes(int snakes1, int snakes2){
		serp1.setText(Integer.toString(snakes1));
		serp2.setText(Integer.toString(snakes2));
	}

	/*
	 * Actualiza el número de la casilla máxima alcanzada por los jugadores en la interfaz.
	 * @param maxCas1 Número de la casilla máxima alcanzada por el jugador 1.
	 * @param maxCas2 Número de la casilla máxima alcanzada por el jugador 2.
	 */
	private void updateMaxBox(int maxCas1, int maxCas2){
		maxcas1.setText(Integer.toString(maxCas1));
		maxcas2.setText(Integer.toString(maxCas2));
	}

	/*
	 * Actualiza el número de casillas especiales alcanzadas por los jugadores en la interfaz.
	 * @param specialCas1 Número de casillas especiales alcanzadas por el jugador 1.
	 * @param specialCas2 Número de casillas especiales alcanzadas por el jugador 2.
	 */
	private void updateEspecialBox(int specialCas1, int specialCas2){
		casEsp1.setText(Integer.toString(specialCas1));
		casEsp2.setText(Integer.toString(specialCas2));
	}
	
	/**
	 * Actualiza los valores de los modificadores en la interfaz gráfica.
	 * @param modi1 Valor del modificador del jugador 1.
	 * @param modi2 Valor del modificador del jugador 2.
	 */
	private void updateMods(int modi1, int modi2){
		mod1.setText(Integer.toString(modi1));
		mod2.setText(Integer.toString(modi2));
	}

	/*
	 * Muestra en la interfaz el resultado del lanzamiento de los dados.
	 * Se actualiza la imagen del dado correspondiente al valor obtenido.
	 */
	private void paintDados(){
		Tablero tablero = stairsandsnakes.getTablero();
		Dado dado = tablero.getDados().get(0);
		int valor = dado.getDado().getNumero();
		switch (valor) {
			case 1 -> lcaras.setIcon(cara1);
			case 2 -> lcaras.setIcon(cara2);
			case 3 -> lcaras.setIcon(cara3);
			case 4 -> lcaras.setIcon(cara4);
			case 5 -> lcaras.setIcon(cara5);
			case 6 -> lcaras.setIcon(cara6);
		}
	}

	/*
	 * Mueve las fichas de los jugadores en la interfaz gráfica.
	 * Elimina las fichas de su ubicación anterior y las vuelve a dibujar en su nueva ubicación.
	 */
	private void moveTokens(){
		Tablero tablero = stairsandsnakes.getTablero();

		for(int i=0;i<tablero.getWidth();i++){
			for(JPanel c: casillas[i]){
				if (c.contains(player1.getLocation())){
					c.remove(player1);
				}
				if (c.contains(player2.getLocation()) && stairsandsnakes.getTurno() > 1){
					c.remove(player2);
				}
			}
		}
		paintTokens();
	}

	/*
	 * Actualiza la posición de las fichas de los jugadores en la interfaz gráfica.
	 * Recorre todas las casillas del tablero y verifica si hay fichas en ellas.
	 * Si encuentra una ficha del jugador 1, la coloca en la casilla correspondiente en la interfaz.
	 * Si encuentra una ficha del jugador 2, la coloca en la casilla correspondiente en la interfaz.
	 */
	private void paintTokens(){
		HashMap<Color,Ficha> tokens;
		Tablero tablero = stairsandsnakes.getTablero();
		Ficha ficha;
		JPanel casNueva;

		for (int i=0; i<tablero.getWidth();i++){
			for (Box b: tablero.getBoxs()[i]){
				tokens = b.getTokens();

				ficha = tokens.get(verifyColor(col1.getText()));
				if (ficha !=null) {
					int value = ficha.getBox().getValue();
					int[] rowAndCol = tablero.searchRowAndColumn(value);
					casNueva = casillas[rowAndCol[0]][rowAndCol[1]];
					casNueva.add(player1,BorderLayout.WEST);
				}

				ficha = tokens.get(verifyColor(col2.getText()));
				if(ficha != null) {
					int value = ficha.getBox().getValue();
					int[] rowAndCol = tablero.searchRowAndColumn(value);
					casNueva = casillas[rowAndCol[0]][rowAndCol[1]];
					casNueva.add(player2,BorderLayout.EAST);
				}
			}
		}
	}

	/*
	 * Pinta la representación gráfica del tablero y las líneas de las casillas.
	 * @param g Objeto Graphics utilizado para dibujar en el lienzo.
	 */
	@Override
	public void paint(Graphics g){
		super.paint(g);
		prepareLines(g);
	}
}