package domain;

import java.util.Random;

public class Dado{

	private int numero;
	private Random ramdom;

	public Dado() {
		ramdom = new Random();
	}
	
	public void lanzarDado() {
		numero = ramdom.nextInt(6) + 1;
	}
	
	public int getDado() {
		return numero;
	}

	
}