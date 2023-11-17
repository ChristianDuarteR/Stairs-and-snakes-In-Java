package domain;

public class Movement extends Box{
	
	/**
	 * Crea una instancia de la clase Movement con el tablero especificado.
	 * @param tbl el tablero en el que se aplicará el movimiento.
	 */
    public Movement(Tablero tbl){
        super(tbl);
    }
    
    /**
     * Verifica si el objeto tiene un poder especial.
     * @return true si el objeto tiene un poder especial, false en caso contrario.
     */
    @Override
    public boolean hasApower(){
        return true;
    }

    /**
     * Mueve el token de la ficha con el poder especial de una escalera.
     * @param ficha La ficha a mover.
     * @throws StairsAndSnakesException Si no se encuentra la escalera.
     */
    @Override
    public void moveTokenWithPower(Ficha ficha) throws StairsAndSnakesException {
        Stair stair = tablero.searchStair(ficha);
        if (stair == null) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_FOUND_STAIR);
        super.moveTokenWithPower(ficha);
        Box casilla = ficha.getBox();
        Box reNew = stair.getCasillainicial();
        casilla.deleteToken(ficha);
        reNew.addToken(ficha.getColor(), ficha);
        ficha.setMaxCas(reNew.getValue());

        if (reNew.hasAnyTramp()) {
            Item trampa = reNew.getItem();
            trampa.DoAction(ficha);
        }
    }
}
