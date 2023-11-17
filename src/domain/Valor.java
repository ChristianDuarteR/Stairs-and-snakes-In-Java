package domain;

import java.io.Serializable;

public class Valor implements Serializable{
    private final int numero;
    private Modifier modificador = null;
    
    /**
     * Constructs a Valor object with the specified value.
     * @param valuethe value of the Valor object
     */
    public Valor(int value){
        this.numero = value;
    }

    /**
     * Devuelve el número asociado a este objeto.
     * @return El número asociado.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Devuelve el modificador asociado a este objeto.
     * @return El modificador asociado.
     */
    public Modifier getModifier(){
        return modificador;
    }

    /**
     * Establece el modificador asociado a este objeto.
     * @param modificador
     */
    public void setModifier(Modifier modificador) {
        this.modificador = modificador;
    }
}
