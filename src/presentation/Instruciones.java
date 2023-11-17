package presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;
import domain.StairsAndSnakesException;

public class Instruciones extends JDialog {
	private Font fuente;
	private Border borde;
	private Imagen imagen;
	private JButton Nuevo;

	public Instruciones() {
		prepareElementos();
		prepararAcciones();
		
	}
	
	public void prepareElementos() {
		setSize(1045,665);
		setTitle("Opciones Stairs and Snakes");
		setLocationRelativeTo(null);
		setLayout(new GridLayout(1,1));
		setResizable(false);
		imagen = new Imagen("pantalla2");
		borde = new LineBorder(Color.black, 2);
		imagen.setLayout(null);
		TitledBorder titulo = new TitledBorder(borde, "Opciones Stairs and Snakes");
		fuente = new Font("oxford", Font.BOLD, 30);
		titulo.setTitleFont(fuente);
		imagen.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), titulo));
		fuente = new Font("oxford", Font.BOLD, 20);
		prepararElementosModificadores();
		prepararElemtosCasillas();
		Nuevo = new JButton("Nuevo");
		Nuevo.setBounds(447, 550, 150, 50);
		Nuevo.setBackground(Color.white);
		Nuevo.setBorder(borde);
		Nuevo.setFont(fuente);
		imagen.add(Nuevo);
		add(imagen);
	}
	
	private void prepararElemtosCasillas() {
		JLabel instruciones = new JLabel();
    	TitledBorder titulo = new TitledBorder(borde,"Casillas");
    	titulo.setTitleFont(fuente);
    	instruciones.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5),titulo));
    	instruciones.setLayout(null);
    	instruciones.setOpaque(false);
    	instruciones.setBounds(40, 40, 950, 370);
    	JLabel saltarina = new JLabel("Saltarina n:"), saltarinain = new JLabel("Saltarina inversa n:"), mortal = new JLabel("Mortal:"), avance = new JLabel("Avance:"), retroceso = new JLabel("retroseso:"), preguntona = new JLabel("Preguntona:");
    	saltarina.setBounds(20, 30, 400, 30);
    	saltarina.setFont(fuente);
    	
    	saltarinain.setBounds(300, 30, 400, 30);
    	saltarinain.setFont(fuente);
    	
    	mortal.setBounds(600, 30, 400, 30);
    	mortal.setFont(fuente);
    	
    	avance.setBounds(20, 200, 400, 30);
    	avance.setFont(fuente);
    	
    	retroceso.setBounds(300, 200, 400, 30);
    	retroceso.setFont(fuente);
    	
    	preguntona.setBounds(600, 200, 400, 30);
    	preguntona.setFont(fuente);
    	
    	JLabel accionsaltarina = new JLabel("El jugador avanza"), accionsaltarinain = new JLabel("El jugador retrocede"), accionmortal = new JLabel("El jugador se devuelve"), accionavance = new JLabel("El jugador avanza hasta"), accionretroseso = new JLabel("El jugador retrocede"), accionpreguntona = new JLabel("El jugador debe responder");
    	JLabel accionsaltarina2 = new JLabel("automáticamente n"), accionsaltarinain2 = new JLabel("automáticamente n"), accionmortal2 = new JLabel("al inicio del juego"), accionavance2 = new JLabel("la siguiente escalera"), accionretroseso2 = new JLabel("hasta la serpiente más cercan"), accionpreguntona2 = new JLabel("una pregunta de conocimiento ");
    	JLabel accionsaltarina3 = new JLabel("posiciones hacia adelante"), accionsaltarinain3 = new JLabel("posiciones hacia atrá"), accionmortal3 = new JLabel(""), accionavance3 = new JLabel("escalera"), accionretroseso3 = new JLabel(""), accionpreguntona3 = new JLabel("general para poder avanzar");
    	JLabel accionsaltarina4 = new JLabel();

    	accionsaltarina.setBounds(20, 60, 400, 30);
    	accionsaltarina.setFont(fuente);
    	accionsaltarina2.setBounds(20, 80, 400, 30);
    	accionsaltarina2.setFont(fuente);
    	accionsaltarina3.setBounds(20, 100, 400, 30);
    	accionsaltarina3.setFont(fuente);
    	
    	accionsaltarina4.setBounds(20, 120, 50, 50);

    	
    	
    	accionsaltarinain.setBounds(300, 60, 400, 30);
    	accionsaltarinain.setFont(fuente);
    	accionsaltarinain2.setBounds(300, 80, 400, 30);
    	accionsaltarinain2.setFont(fuente);
    	accionsaltarinain3.setBounds(300, 100, 400, 30);
    	accionsaltarinain3.setFont(fuente);
    	
    	accionmortal.setBounds(600, 60, 400, 30);
    	accionmortal.setFont(fuente);
    	accionmortal2.setBounds(600, 80, 400, 30);
    	accionmortal2.setFont(fuente);
    	accionmortal3.setBounds(600, 100, 400, 30);
    	accionmortal3.setFont(fuente);
    	
    	accionavance.setBounds(20, 220, 400, 30);
    	accionavance.setFont(fuente);
    	accionavance2.setBounds(20, 240, 400, 30);
    	accionavance2.setFont(fuente);
    	
    	accionretroseso.setBounds(300, 220, 400, 30);
    	accionretroseso.setFont(fuente);
    	accionretroseso2.setBounds(300, 240, 400, 30);
    	accionretroseso2.setFont(fuente);
    	
    	accionpreguntona.setBounds(600, 220, 400, 30);
    	accionpreguntona.setFont(fuente);
    	accionpreguntona2.setBounds(600, 240, 400, 30);
    	accionpreguntona2.setFont(fuente);
    	accionpreguntona3.setBounds(600, 260, 400, 30);
    	accionpreguntona3.setFont(fuente);
    	
    	
    	instruciones.add(saltarina);
    	instruciones.add(accionsaltarina);
    	instruciones.add(accionsaltarina2);
    	instruciones.add(accionsaltarina3);
    	instruciones.add(accionsaltarina4);

    	instruciones.add(saltarinain);
    	instruciones.add(accionsaltarinain);
    	instruciones.add(accionsaltarinain2);
    	instruciones.add(accionsaltarinain3);


    	instruciones.add(mortal);
    	instruciones.add(accionmortal);
    	instruciones.add(accionmortal2);
    	instruciones.add(accionmortal3);

    	
    	instruciones.add(avance);
    	instruciones.add(accionavance);
    	instruciones.add(accionavance2);
    	
    	instruciones.add(retroceso);
    	instruciones.add(accionretroseso);
    	instruciones.add(accionretroseso2);
    	
    	instruciones.add(preguntona);
    	instruciones.add(accionpreguntona);
    	instruciones.add(accionpreguntona2);


    	imagen.add(instruciones);
	}
	
	private void prepararElementosModificadores() {
		JLabel instruciones = new JLabel();
    	TitledBorder titulo = new TitledBorder(borde,"Modificadores");
    	titulo.setTitleFont(fuente);
    	instruciones.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5),titulo));
    	instruciones.setLayout(null);
    	instruciones.setOpaque(false);
    	instruciones.setBounds(40, 400, 950, 150);
    	JLabel cambio = new JLabel("Cambio posición: "), bonificacion = new JLabel("Bonificación: "), penalización = new JLabel("Penalización: ");
    	cambio.setBounds(20, 35, 400, 30);
    	cambio.setFont(fuente);
    	bonificacion.setBounds(300, 35, 400, 30);
    	bonificacion.setFont(fuente);
    	penalización.setBounds(600, 35, 400, 30);
    	penalización.setFont(fuente);
    	JLabel accioncambio = new JLabel("El jugador cambia de"), accionbonificacion = new JLabel("El jugador puede avanzar una"), accionpenalización = new JLabel("El jugador puede retroceder");
    	JLabel accioncambio2 = new JLabel("posición con su oponente"), accionbonificacion2 = new JLabel("casilla adicional, si lo desea"), accionpenalización2 = new JLabel("una casilla, si lo desea");
    	JLabel accioncambio3 = new JLabel("antes de avanzar");

    	accioncambio.setBounds(20, 65, 400, 30);
    	accioncambio.setFont(fuente);
    	accioncambio2.setBounds(20, 85, 400, 30);
    	accioncambio2.setFont(fuente);
    	accioncambio3.setBounds(20, 105, 400, 30);
    	accioncambio3.setFont(fuente);
    	
    	accionbonificacion.setBounds(300, 65, 400, 30);
    	accionbonificacion.setFont(fuente);
    	accionbonificacion2.setBounds(300, 85, 400, 30);
    	accionbonificacion2.setFont(fuente);
    	
    	accionpenalización.setBounds(600, 65, 400, 30);
    	accionpenalización.setFont(fuente);
    	accionpenalización2.setBounds(600, 85, 400, 30);
    	accionpenalización2.setFont(fuente);
    	
    	
    	instruciones.add(cambio);
    	instruciones.add(accioncambio);
    	instruciones.add(accioncambio2);
    	instruciones.add(accioncambio3);


    	instruciones.add(bonificacion);
    	instruciones.add(accionbonificacion);
    	instruciones.add(accionbonificacion2);


    	instruciones.add(penalización);
    	instruciones.add(accionpenalización);
    	instruciones.add(accionpenalización2);


    	imagen.add(instruciones);
    	
    	
	}

	/**
     * Prepara las acciones de la interfaz gráfica de StairsAndSnakes.
     */
    private void prepararAcciones(){   
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            
            public void windowClosing(WindowEvent e){
            }
        });
        Nuevo.addActionListener(e -> accionNuevo());
        
        
    } 
    
    /**
     * Realiza la acción de iniciar un nuevo juego.
     */
    private void accionNuevo() {
        dispose();
        Opciones op = new Opciones();
        op.setVisible(true);
    }

}
