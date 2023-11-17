package presentation;

import java.awt.*;
import javax.swing.*;

public class ImagenesStairsAndSnakes extends JPanel {
	
		String nombreImagen;
		
		/**
		 * Crea una instancia de la clase ImagenesStairsAndSnakes con el nombre de la imagen especificado.
		 * @param nombreImagen
		 */
		public ImagenesStairsAndSnakes(String nombreImagen) {
			this.nombreImagen = nombreImagen;
		}
		
		/**
		 * Pinta el componente gráfico utilizando la imagen de fondo especificada.
		 * @param g el objeto Graphics utilizado para dibujar.
		 */
		@Override
		public void paint(Graphics g) {
			Image fondo = new ImageIcon(getClass().getResource("Imagenes/"+nombreImagen+".png")).getImage();
			g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	

}
