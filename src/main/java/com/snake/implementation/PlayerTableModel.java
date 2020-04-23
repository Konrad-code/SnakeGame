package com.snake.implementation;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PlayerTableModel extends AbstractTableModel {

	private static final int ID = 0;
	private static final int LOGIN = 1;
	private static final int PASSWORD = 2;
	private static final int NICKNAME = 3;
	private static final int HIGHEST_SCORE = 4;
	private static final int ROOT = 5;
	
	private List<Player> players;
	private String[] names = {"Number", "Login", "Password", "Nickname", "High score", "Admin"};
	
	public PlayerTableModel(List<Player> players) {
		super();
		this.players = players;
	}

	@Override
	public int getRowCount() {
		return names.length;
	}

	@Override
	public int getColumnCount() {
		return players.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Player donorPlayer = players.get(rowIndex);
		
		if(columnIndex == ID)
			return donorPlayer.getId();
		if(columnIndex == LOGIN)
			return donorPlayer.getLogin();
		if(columnIndex == PASSWORD)
			return donorPlayer.getPassword();
		if(columnIndex == NICKNAME)
			return donorPlayer.getNickname();
		if(columnIndex == HIGHEST_SCORE)
			return donorPlayer.getHighestScore();
		if(columnIndex == ROOT)
			return donorPlayer.isRoot();
		else
			return donorPlayer.isRoot();
	}
}
