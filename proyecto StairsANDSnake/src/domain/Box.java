package domain;

import java.awt.Color;
import java.util.HashMap;

public class Box {
	private Tablero tablero;

	private int row,column;
	
	private int value ;
	
	private Item item;

	private HashMap<String,Player> players;
	
	public Box(Tablero tbl,int row,int column) {
		players = new HashMap<String, Player>();
		tablero = tbl;
		this.column = column;
		this.row = row;
		tbl.setBox(row,column,this);
	}
	
	public int getColumn() {
		return column;	
	}
	
	public int getRow( ) {
		return row;
	}
	
	public int getValue() {
		return value;
	}
	
	public void mustMove(String name) {
		item.getpower(name);
	}
	
	public Player getPlayer(String name) {
		return players.get(name);
	}
	
	public void deletePlayer(Player player) {
		players.remove(player);
		player.setPositionBox(null);
	}
	
	public void addPlayer(String name, Player player) {
		players.put(name, player);
		player.setPositionBox(this);
	}
		
	public boolean hasAplayer() {
		return players.isEmpty();
	}
	
	public boolean NotHasAnyItem() {
		return item.equals(null);
	}


	public void setItem(Item item) {
		this.item = item;
	}

}