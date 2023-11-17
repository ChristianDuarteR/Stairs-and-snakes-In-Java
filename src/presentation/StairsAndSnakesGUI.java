package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class StairsAndSnakesGUI extends JFrame{
JButton nuevo,abrir,salir,instruciones;
	
	/**
	*Constructor de la interfaz gráfica de StairsAndSnakes.
	*/
	private StairsAndSnakesGUI() {
		super("Inicio StairsAndSnakes");
		prepararElementos();
		prepararAcciones();
	}
	
	
	/**
     * Prepara los elementos de la interfaz gráfica de StairsAndSnakes.
     */
    private void prepararElementos() {   
    	setSize(1045, 665);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        ImagenesStairsAndSnakes ImagenesStairsAndSnakes = new ImagenesStairsAndSnakes("pantalla1");
        Font fuente = new Font("oxford", 0, 20);
        LineBorder borde = new LineBorder(Color.black, 2);
        ImagenesStairsAndSnakes.setLayout(null);
        nuevo = new JButton("Nuevo Juego");
        nuevo.setBounds(390, 318, 260, 45);
        nuevo.setBackground(Color.white);
        nuevo.setBorder(borde);
        nuevo.setFont(fuente);
        abrir = new JButton("Abrir Juego");
        abrir.setBounds(390, 372, 260, 45);
        abrir.setBackground(Color.white);
        abrir.setBorder(borde);
        abrir.setFont(fuente);
        salir = new JButton("Salir");
        salir.setBounds(390, 426, 260, 45);
        salir.setBackground(Color.white);
        salir.setBorder(borde);
        salir.setFont(fuente);
        instruciones= new JButton("Instruciones");
        instruciones.setBounds(390, 480, 260, 45);
        instruciones.setBackground(Color.white);
        instruciones.setBorder(borde);
        instruciones.setFont(fuente);
        ImagenesStairsAndSnakes.add(nuevo);
        ImagenesStairsAndSnakes.add(abrir);
        ImagenesStairsAndSnakes.add(salir);
        ImagenesStairsAndSnakes.add(instruciones);
        add(ImagenesStairsAndSnakes);
    } 
    
    /**
     * Prepara las acciones de la interfaz gráfica de StairsAndSnakes.
     */
    private void prepararAcciones(){   
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            
            public void windowClosing(WindowEvent e){
                accionSalir();
            }
        });
        nuevo.addActionListener(e -> accionNuevo());
        salir.addActionListener(e -> accionSalir());
        abrir.addActionListener(e -> accionAbrir());
        instruciones.addActionListener(e -> accionnInstruciones());
        
        
    } 
    /**
     * Realiza la acción de salir del juego.
     */
    private void accionSalir() {   
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir del juego?", "Salir del juego", JOptionPane.OK_CANCEL_OPTION);
        if (respuesta == JOptionPane.OK_OPTION){
            setVisible(false);
            System.exit(0);
        }
    } 
    
    /**
     * Realiza la acción de iniciar un nuevo juego.
     */
    private void accionNuevo() {
        dispose();
        Opciones op = new Opciones();
        op.setVisible(true);
    }
    
    /**
     * Realiza la acción de abrir un juego guardado.
     */
    private void accionAbrir() {   
        JFileChooser chooser = new JFileChooser();
        int open = chooser.showOpenDialog(null);
        if (open == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, file.getName());
        }
    }
    
    private void accionnInstruciones() {
    	dispose();
    	Instruciones ins = new Instruciones();
    	ins.setVisible(true);
    }
    
    /**
     * Punto de entrada principal para el programa StairsAndSnakes.
     * @param args 
     */
	public static void main(String[] args) {
		StairsAndSnakesGUI gui = new StairsAndSnakesGUI();
	    gui.setVisible(true);
	} 
}