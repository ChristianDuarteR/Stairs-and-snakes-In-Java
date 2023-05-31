package domain;

public class Death extends Box{
	
	/**
	 * Constructor de la clase Death.
	 * @param tbl el tablero en el que se encuentra la casilla de muerte
	 */
    public Death(Tablero tbl) {
        super(tbl);
    }
  
    /**
     * Verifica si la casilla de muerte tiene un poder asociado.
     * @return true si la casilla de muerte tiene un poder, false en caso contrario
     */
    @Override
    public boolean hasApower(){
        return true;
    }


    /**
     * Mueve el token de la ficha a la casilla de muerte con poder.
     * @param ficha la ficha a mover
     * @throws StairsAndSnakesException si ocurre un error relacionado con las escaleras y serpientes
     */
    @Override
    public void moveTokenWithPower(Ficha ficha) throws StairsAndSnakesException{
        super.moveTokenWithPower(ficha);
        Box reNew = tablero.searchBox(1);
        Box casilla = ficha.getBox();
        casilla.deleteToken(ficha);
        reNew.addToken(ficha.getColor(),ficha);
        ficha.setMaxCas(reNew.getValue());
    }
}
