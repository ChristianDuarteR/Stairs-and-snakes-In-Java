package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;
import java.util.Random;

public class Tablero {

	private final static int width = 10;

	private HashMap<String,Player> players;

	private ArrayList<Snake> snakes;

	private ArrayList<Stair> stairs;
	
	private Box[][] boxs;

	private Dado dado ;
	
	public Tablero(int numJugadores,String[] nombres, int numSnakes, int numStairs) {
		players = new HashMap<String, Player>();
		
		makeBoxes();
		makePlayers(numJugadores, nombres);
		makeSnakes(numSnakes);
		makeStairs(numStairs);
		
	}
	
	public void makePlayers(int numJugadores, String[] nombres) {
		
		for(int i=0; i<numJugadores; i++) {
			
			Player jugador = new Player(this,nombres[i]);
			players.put(nombres[i], jugador);
		}
	}
	
	public void makeSnakes(int numSnakes) {
		for(int i=0; i<numSnakes; i++) {
			
			int[] ramBoxF = getRamdoms();
			int[] ramBoxI = getRamdoms();
			
			Box ibox = boxs[ramBoxF[0]][ramBoxF[1]];
			Box fbox = boxs[ramBoxI[1]][ramBoxI[1]];
			
			Snake snake = new Snake(ibox, fbox);
			snakes.add(snake);
		}
	}
	
	
	public void makeStairs(int numStairs) {
		for(int i=0; i<numStairs; i++) {
			
			int[] ramBoxF = getRamdoms();
			int[] ramBoxI = getRamdoms();
			
			Box ibox = boxs[ramBoxF[0]][ramBoxF[1]];
			Box fbox = boxs[ramBoxI[1]][ramBoxI[1]];
			
			Stair stair = new Stair(ibox, fbox);
			stairs.add(stair);
		}
	}

	private int[] getRamdoms() {
		
		Random ramdom1 = new Random();
		int[] numeros = new int[2];
		
		int miNumero1 = ramdom1.nextInt(99)+1;
		int miNumero2 = ramdom1.nextInt(99)+1;
		numeros[0] = miNumero1;
		numeros[1] = miNumero2;
		
		return numeros;
	}
	
	private void makeBoxes() {
		boxs = new Box[width][width];
		
		for(int i=0;i<width;i++) {
			for(int j=0;j<width;j++) {
				boxs[i][j] = new Box(this,i,j);
			}
		}
	}
	
	public Box getBox(int fila,int columna) {
		return boxs[fila][columna];
	}

	public void setBox(int fila,int columna, Box box) {
		boxs[fila][columna] = box;
	}

	public void changePlayerTo(String name, Box newPosition) {
		
		Player player = players.get(name);
		Box boxGoingFree = player.getPosition();
		Box boxToPlayer = newPosition;
		
		if(newPosition.NotHasAnyItem() ) {
			boxGoingFree.deletePlayer(player);
			boxToPlayer.addPlayer(name, player);
		}else {
			newPosition.mustMove(name);
		}

	}
	
	public void changePlayerToSpecial(String name, Box newPosition) {
		Player player = players.get(name);
		Box boxGoingFree = player.getPosition();
		Box boxToPlayer = newPosition;

		boxGoingFree.deletePlayer(player);
		boxToPlayer.addPlayer(name, player);
	}

	public void realizarJugada(String name,int jugada) {
		for (int i=0;i<width;i++) {
			for(Box e: boxs[i]) {
				
				if(e.getValue()==jugada) {
					
					changePlayerTo(name, e);
				}
			}
		}
	}
}

