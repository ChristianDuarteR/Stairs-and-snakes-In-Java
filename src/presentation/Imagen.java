package presentation;

import java.awt.*;
import javax.swing.*;

class Imagen extends JPanel {
	
	String nombreImagen;
	
	/**
	 * Crea un objeto Imagen con el nombre de la imagen especificada.
	 * @param nombreImagen
	 */
	public Imagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	/**
	 * Pinta la imagen en el componente gráfico.
	 * @param g Graphics .
	 */
	public void paint(Graphics g) {
		Image fondo = new ImageIcon(getClass().getResource("Imagenes/"+nombreImagen+".png")).getImage();
		g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}
}