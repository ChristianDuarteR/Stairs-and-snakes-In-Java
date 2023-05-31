package domain;

import java.awt.*;
import java.util.ArrayList;

public class Machine extends Player{

    private String dificultad;

    private int decision = 1;

    /**
     * Constructor de la clase Machine.
     * @param name Nombre de la máquina.
     * @param color Color de la ficha de la máquina.
     * @param dificultad Dificultad de la máquina.
     * @param dados Lista de dados utilizados por la máquina.
     */
    public Machine(String name, Color color,String dificultad, ArrayList<Dado> dados) {
        super(name, color, dados);
        this.dificultad = dificultad;
    }

    /**
     * Obtiene la decisión tomada por la máquina.
     * @return Entero que representa la decisión tomada por la máquina.
     */
    public int getDecision(){
        return decision;
    }

    /**
     * Toma una decisión de casilla basada en las opciones disponibles.
     * @param opciones Lista de enteros que representa las opciones disponibles.
     * @return Entero que representa la decisión tomada por la máquina.
     */
    public int DecideBox(ArrayList<Integer> opciones){
        int decision = 0;
        if (dificultad.equals("Aprendiz")){
           decision = DecideExpertBox(opciones);
        }
        else{
            for (int i = 0; i < opciones.size(); i++) {
                if (opciones.get(i) > decision) decision = opciones.get(i);
            }

        }
        return decision;
    }

    /**
     * Toma una decisión de casilla experta basada en las opciones disponibles.
     * @param opciones Lista de enteros que representa las opciones disponibles.
     * @return Entero que representa la decisión tomada por la máquina.
     */
    private int DecideExpertBox(ArrayList<Integer> opciones){
        int decision = opciones.get(opciones.size()-1);
        Tablero tablero = getFichas().get(0).getBox().getTablero();
        for (Integer op: opciones){
            Box casilla = tablero.searchBox(op);
            if (casilla.getItem() instanceof Movement){
                decision = op;
            }else if (casilla instanceof Jumper){
                decision = op;
            }else{
                decision = opciones.get(opciones.size()-1);
            }
        }
        return decision;
    }

    /**
     * Mueve el token de la máquina en el tablero.
     * @throws StairsAndSnakesException Si ocurre un error durante el movimiento.
     */
    @Override
    public void moveToken() throws StairsAndSnakesException {
        if (dificultad.equals("Principiante")){
            super.moveToken();
        }else {
            decideMovement();
        }
    }

    /**
     * Toma la decisión de movimiento de la máquina en función del estado actual del juego y las reglas de dificultad.
     * @throws StairsAndSnakesException Si ocurre un error durante el movimiento.
     */
    private void decideMovement() throws StairsAndSnakesException {
        Ficha ficha = getFichas().get(0);
        Box casilla = ficha.getBox();
        Tablero tablero = casilla.getTablero();
        Valor cara = dados.get(0).getDado();

        if (cara.getModifier() != null){
            decideModifier(tablero,cara);
            super.moveToken();
        } else {
            super.moveToken();
        }
    }

    /**
     * Toma la decisión de utilizar un modificador de movimiento de acuerdo a las reglas de la dificultad y la situación actual del juego.
     * @param tablero El tablero del juego.
     * @param cara El valor del dado que contiene el modificador de movimiento.
     */
    private void decideModifier(Tablero tablero, Valor cara){
        Modifier mod = cara.getModifier();
        if (mod instanceof MovementBuff){
            decision = 0;
        } else if (mod instanceof MovementNerf) {
            decision = 1;
        } else {
            Ficha myToken = getFichas().get(0);
            Ficha jugador2 = tablero.searchOpponent(getColor());
            if (jugador2.getBox().getValue() > myToken.getBox().getValue()){
                decision = 0;
            }else {
                decision = 1;
            }
        }
    }

}