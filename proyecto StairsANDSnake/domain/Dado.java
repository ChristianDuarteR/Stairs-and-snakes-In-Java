package domain;

import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

public class Dado implements Serializable{

	private final ArrayList<Valor> values;
	private final int probabilidad;
	private int state;

	/**
	 * Crea un objeto Dado con la probabilidad de movimiento.
	 * @param porM la probabilidad de movimiento del dado
	 */
	public Dado(int porM) {
		this.state = -1;
		this.probabilidad = porM;
		values = new ArrayList<>();
		for (int i=0;i<6;i++) {
			values.add(new Valor(i+1));
		}
	}
	
	/**
	 * Simula el lanzamiento del dado y actualiza el estado del dado y los modificadores de las caras según la probabilidad.
	 */
	public void lanzarDado() {
		Random random = new Random();
		int cara;
		int index =  random.nextInt(0,6);
		state = values.get(index).getNumero();

		for(Valor v: values) {
			v.setModifier(null);
		}

		int contador = 0;
		while (contador < probabilidad){

			cara = random.nextInt(0,6);
			Valor valor = values.get(cara);
			if(valor.getModifier() == null) {
				setRandomModifierToSomeFace(valor, random);
				contador++;
			}
		}
	}

	/**
	 * Asigna un modificador aleatorio a una cara del dado.
	 * @param cara La cara del dado a la que se asignará el modificador.
	 * @param random Objeto Random utilizado para generar números aleatorios.
	 */
	private void setRandomModifierToSomeFace(Valor cara,Random random) {
		int mdf = random.nextInt(1,4);

		switch (mdf) {
			case 1 -> new MovementNerf(cara);
			case 2 -> new MovementBuff(cara);
			case 3 -> new ChangePosition(cara);
		}
	}
	
	/**
	 * Retorna el objeto Valor correspondiente al estado actual del dado.
	 * @return El objeto Valor correspondiente al estado actual del dado.
	 */
	public Valor getDado() {
		if(state == -1) {
			return new Valor(0);
		}else {
			return values.get(state -1);
		}
	}

	/**
	 * Retorna la lista de objetos Valor del dado.
	 * @return La lista de objetos Valor del dado.
	 */
	public ArrayList<Valor> getValues() {
		return  values;
	}
}