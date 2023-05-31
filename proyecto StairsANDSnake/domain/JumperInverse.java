package domain;

import java.util.Random;

public class JumperInverse extends Box{

	/**
	 * Constructor de la clase JumperInverse.
	 * Crea una instancia de JumperInverse con el tablero especificado.
	 * @param tbl El tablero en el que se aplicará el modificador JumperInverse.
	 */
    public JumperInverse(Tablero tbl) {
        super(tbl);
    }
   
    /**
     * Verifica si el modificador JumperInverse tiene un poder especial.
     * @return true si el modificador JumperInverse tiene un poder especial,false de lo contrario.
     */
    @Override
    public boolean hasApower(){
        return true;
    }

 
    /**
     * Mueve el token de la ficha con el poder especial del modificador JumperInverse.
     * @param ficha La ficha a la que se le aplica el movimiento con poder especial.
     * @throws StairsAndSnakesException Si ocurre un error durante el movimiento.
     */
    @Override
    public void moveTokenWithPower(Ficha ficha) throws StairsAndSnakesException {
        super.moveTokenWithPower(ficha);
        int cantidad;
        Random random;
        random = new Random();
        cantidad = random.nextInt(getValue()-6,getValue()-1);
        VerifyReNew(ficha, tablero.searchBox(cantidad));
    }

    /**
     * Verifica y realiza el movimiento a una casilla renovada para la ficha.
     * @param ficha La ficha a la que se le realiza el movimiento.
     * @param box La casilla de destino renovada.
     * @throws StairsAndSnakesException Si ocurre un error durante el movimiento.
     */
    static void VerifyReNew(Ficha ficha, Box box) throws StairsAndSnakesException{
        Box renew = box;
        Box casilla = ficha.getBox();

        casilla.deleteToken(ficha);
        renew.addToken(ficha.getColor(),ficha);
        ficha.setMaxCas(renew.getValue());

        if (renew.hasAnyTramp()) {
            Item trampa = renew.getItem();
            trampa.DoAction(ficha);
        }else if(renew.hasApower()){
            renew.moveTokenWithPower(ficha);
        }
    }
}
