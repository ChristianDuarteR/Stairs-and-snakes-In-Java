package domain;

import java.util.Random;
import java.io.Serializable;

public class Jumper extends Box  {
    public int salto;
    
    /**
     * Crea una nueva ficha Jumper asociada al tablero especificado.
     * @param tbl el tablero al que se asociará la ficha Jumper
     */
    public Jumper(Tablero tbl) {
        super(tbl);
    }

    /**
     * Verifica si la ficha Jumper tiene un poder especial.
     * @return true si la ficha Jumper tiene un poder especial, false en caso contrario
     */
    @Override
    public boolean hasApower(){
        return true;
    }
    
    /**
     * Mueve la ficha Jumper con su poder especial.
     * @param ficha la ficha Jumper que se va a mover
     * @throws StairsAndSnakesException si ocurre un error durante el movimiento de la ficha
     */
    @Override
    public void moveTokenWithPower(Ficha ficha) throws StairsAndSnakesException{
        super.moveTokenWithPower(ficha);
        int cantidad;
        Random random;
        random = new Random();
        cantidad = random.nextInt(getValue()+ 1,getValue() + 6);
        JumperInverse.VerifyReNew(ficha, tablero.searchBox(cantidad));
    }
}
