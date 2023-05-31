package domain;

public interface Behavior extends Item{
    String normal = "normal";
    String debil = "debil";
    String sper = "Super";
    String dual = "dual";

    /**
     * Establece el comportamiento especificado para el objeto.
     * @param comportamiento El comportamiento a establecer.
     */
    default void setBehavior(String comportamiento){

    }

}

