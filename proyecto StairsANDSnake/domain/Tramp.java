package domain;
public abstract class Tramp implements Behavior {
	
	protected Box casillainicial;
	protected Box casillafin;

	private String comportamiento;

	/**
	 * Obtiene la casilla inicial asociada a esta trampa.
	 * @return La casilla inicial.
	 */
	public Box getCasillaInicial(){
		return casillainicial;
	}

	/**
	 * Obtiene la casilla final asociada a esta trampa.
	 * @return La casilla final.
	 */
	public Box getCasillafin(){
		return casillafin;
	}
	
	/**
	 * Crea una nueva trampa con las casillas inicial y final especificadas.
	 * @param casillainicial
	 * @param casillafin
	 * @param canChange
	 */
	public Tramp (Box casillainicial, Box casillafin, boolean canChange) {
		this.casillainicial = casillainicial;
		this.casillafin = casillafin;
		SetObject();

		if (canChange){
			setBehavior(Behavior.normal);
		}else {
			setBehavior(null);
		}
	}

	/**
	 * Establece el comportamiento de la trampa.
	 * @param comportamiento El comportamiento de la trampa.
	 */
	@Override
	public void setBehavior(String comportamiento) {
		this.comportamiento = comportamiento;
	}

	/**
	 * Obtiene la casilla inicial de la trampa.
	 * @return La casilla inicial de la trampa.
	 */
	public Box getCasillainicial() {
		return casillainicial;
	}

	/**
	 * Establece la trampa como objeto en las casillas inicial y final.
	 */
	@Override
	public void SetObject(){
		casillainicial.setItem(this);
		casillafin.setItem(this);
	}

	/**
	 * Realiza la acción de mover la ficha desde la casilla inicial a la casilla final.
	 * Elimina la ficha de la casilla inicial y la agrega a la casilla final.
	 * @param ficha La ficha que se moverá.
	 */
	public void DoAction(Ficha ficha){
		casillainicial.deleteToken(ficha);
		casillafin.addToken(ficha.getColor(), ficha);
	}
}
