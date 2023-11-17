package domain;

public class Recoil extends Box{
	
	/**
	 * Crea una instancia de la clase Recoil.
	 * @param tbl el tablero asociado al movimiento de retroceso.
	 */
    public Recoil(Tablero tbl) {
        super(tbl);
    }
    
    /**
     * Verifica si la casilla tiene un poder asociado.
     * @return true si la casilla tiene un poder asociado, false en caso contrario.
     */
    @Override
    public boolean hasApower(){
        return true;
    }

    /**
     * Mueve el token de la ficha con el poder asociado de la serpiente.
     * @param ficha la ficha cuyo token se va a mover.
     * @throws StairsAndSnakesException si no se encuentra la serpiente asociada al poder.
     */
    @Override
    public void moveTokenWithPower(Ficha ficha) throws StairsAndSnakesException {
        Snake snake = tablero.searchSnake(ficha);
        if (snake == null) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_FOUND_SNAKE);
        Box casilla = ficha.getBox();
        super.moveTokenWithPower(ficha);
        Box reNew = snake.getCasillainicial();
        casilla.deleteToken(ficha);
        reNew.addToken(ficha.getColor(), ficha);
        ficha.setMaxCas(reNew.getValue());

        if (reNew.hasAnyTramp()) {
            Item trampa = reNew.getItem();
            trampa.DoAction(ficha);
        }
    }
}
