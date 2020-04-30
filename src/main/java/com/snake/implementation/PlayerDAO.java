package com.snake.implementation;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.snake.templates.IPlayerDAO;

public class PlayerDAO extends CRUD implements IPlayerDAO {
	
	{
		playerNickname = "";
		playerHighestScore = 0;
		ifPlayerRoot = false;
		gameSettings = new GameSettings();
		ranking = new LinkedList<RankPosition>();
	}

	private String playerNickname;
	private int playerHighestScore;
	private boolean ifPlayerRoot;
	
	private StoredProcedures preparingProceduresInDatabase;
	private GameSettings gameSettings;
	private LinkedList<RankPosition> ranking;
	
	public PlayerDAO() {
	}
	
	public PlayerDAO(boolean ifPlayerRoot) {
		setIfPlayerRoot(ifPlayerRoot);
	}
	
	public void setPreparingProceduresInDatabase(StoredProcedures preparingProceduresInDatabase) {
		this.preparingProceduresInDatabase = preparingProceduresInDatabase;
	}

	public StoredProcedures getPreparingProceduresInDatabase() {
		return preparingProceduresInDatabase;
	}

	public GameSettings getGameSettings() {
		return gameSettings;
	}

	public LinkedList<RankPosition> getRanking() {
		return ranking;
	}

	public boolean isIfPlayerRoot() {
		return ifPlayerRoot;
	}

	private void setIfPlayerRoot(boolean ifPlayerRoot) {
		this.ifPlayerRoot = ifPlayerRoot;
	}

	public String getPlayerNickname() {
		return playerNickname;
	}

	public void setPlayerNickname(String playerNickname) {
		this.playerNickname = playerNickname;
	}

	public int getPlayerHighestScore() {
		return playerHighestScore;
	}

	public void setPlayerHighestScore(int playerHighestScore) {
		this.playerHighestScore = playerHighestScore;
	}

	private Player changeRowToPlayer(ResultSet result) throws SQLException {
		int id = result.getInt("id_player");
		String nickname = result.getString("nick");
		String login = result.getString("login");
		String password = result.getString("password");
		int highestScore = result.getInt("score");
		boolean root = result.getBoolean("root");
		
		Player donorPlayer = new Player(id, nickname, login, password, highestScore, root);
		return donorPlayer;	
	}

	@Override
	public boolean tryLogin(String login, String password) {
		boolean ifSuccessfullyLogged = false;
		PreparedStatement tryLoginStatement = null;
		ResultSet loggedData = null;
		loadConnection();
		
		try {
			tryLoginStatement = connection.prepareStatement("SELECT nick, score, root FROM players WHERE (login=? AND password=?);");
			
			tryLoginStatement.setString(1, login);
			tryLoginStatement.setString(2, password);
			System.out.println("Calling query for `trylogin`('" + login + "', '" + password + "')");
			loggedData = tryLoginStatement.executeQuery();
			
			if(loggedData.next()) {
				System.out.println("Query `trylogin` executed successfully");
				playerNickname = loggedData.getString("nick");
				playerHighestScore = loggedData.getInt("score");
				ifPlayerRoot = loggedData.getBoolean("root");
				System.out.println("Player nickname: " + playerNickname + " | High score: " + playerHighestScore + " | Admin: " + ifPlayerRoot);
				ifSuccessfullyLogged = true;
			}
		} catch (SQLException e) {
			System.err.println("Failed to execute query `trylogin` on database: " + e.getMessage());
		} finally {
			try { loggedData.close(); } catch (Exception e) { /* leave action */ }
			try { tryLoginStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return ifSuccessfullyLogged;
	}
	
	@Override
	public List<Player> getAllPlayers() {
		loadConnection();
		List<Player> list = new ArrayList<>();
		PreparedStatement getAllStatement = null;
		ResultSet results = null;
		
		try {
			getAllStatement = connection.prepareStatement("SELECT * FROM players");
			results = getAllStatement.executeQuery();
			while(results.next()) {
				Player donorPlayer = changeRowToPlayer(results);
				list.add(donorPlayer);
			}
		} catch (SQLException e) {
			System.err.println("Failed to carry `getAllPlayers` method: " + e.getMessage());
		} finally {
			try { results.close(); } catch (Exception e) { /* leave action */ }
			try { getAllStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return list;
	}

	@Override
	public List<Player> searchPlayers(String nickname) {
		List<Player> list = new ArrayList<>();
		PreparedStatement searchStatement = null;
		ResultSet results = null;
		loadConnection();
		
		try {
			nickname = nickname.concat("%");
			searchStatement = connection.prepareStatement("SELECT * FROM players WHERE nick LIKE ?");
			searchStatement.setString(1, nickname);
			results = searchStatement.executeQuery();
			while(results.next()) {
				Player donorPlayer = changeRowToPlayer(results);
				list.add(donorPlayer);
			}
		} catch (SQLException e) {
			System.err.println("Failed to carry `searchPlayers` method: " + e.getMessage());
		} finally {
			try { results.close(); } catch (Exception e) { /* leave action */ }
			try { searchStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return list;
	}
	
	@Override
	public int getHighscore(String nickname) {
		int highscore = 0;
		PreparedStatement searchStatement = null;
		ResultSet result = null;
		loadConnection();
		
		try {
			searchStatement = connection.prepareStatement("SELECT score FROM players WHERE nick=?");
			searchStatement.setString(1, nickname);
			result = searchStatement.executeQuery();
			if(result.next()) {
				highscore = result.getInt("score");
			}
		} catch (SQLException e) {
			System.err.println("Failed to carry `searchPlayers` method: " + e.getMessage());
		} finally {
			try { result.close(); } catch (Exception e) { /* leave action */ }
			try { searchStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return highscore;
	}
	
	@Override
	public void updatePlayerScore(int newScore) {
		if(newScore > playerHighestScore)
			if(playerNickname.length() > 0) {
				boolean ifSuccessfulUpdateToDb = false;
				CallableStatement updateScoreCallStatement = null;
				loadConnection();
				
				try {
					updateScoreCallStatement = connection.prepareCall("CALL updatescore(?,?)");
					updateScoreCallStatement.setString(1, playerNickname);
					updateScoreCallStatement.setInt(2, newScore);
					System.out.println("Calling stored procedure `updatescore`('" + newScore + "')");
					int numberOfRowsAffected = updateScoreCallStatement.executeUpdate();
					if(numberOfRowsAffected > 0) {
						System.out.println("Stored procedure `updatescore` called successfully");
						ifSuccessfulUpdateToDb = true;
					}
				} catch (SQLException e) {
					System.err.println("Failed to call Procedure `updatescore` from database: " + e.getMessage());
				} finally {
					try { updateScoreCallStatement.close(); } catch (Exception e) { /* leave action */ }
					closeConnection();
				}
				
				if(ifSuccessfulUpdateToDb)
					playerHighestScore = newScore;
			}
		}
	
	@Override
	public void showRank() {
		LinkedList<RankPosition> tempRanking = new LinkedList<RankPosition>();
		String tempNick = "";
		int counter = 1, tempScore = 0;
		PreparedStatement showRankStatement = null;
		ResultSet results = null;
		loadConnection();
		
		try {
			showRankStatement = connection.prepareStatement("SELECT nick, score FROM players ORDER BY score DESC;");
			results = showRankStatement.executeQuery();
			while(results.next() && counter < 11) {
				tempNick = results.getString("nick");
				tempScore = results.getInt("score");
				System.out.println("Added new position to rank: " + tempNick + " " + tempScore);
				tempRanking.add(new RankPosition(counter, tempNick, tempScore));
				counter++;
			}
			ranking.clear();
			ranking.addAll(tempRanking);
			System.out.println("Stored procedure `showrank` called successfully");
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `showrank` from database: " + e.getMessage());
		} finally {
			try { results.close(); } catch (Exception e) { /* leave action */ }
			try { showRankStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}
	
	// Methods that failed job - inproper developer ability to retrieve data from called procedure using SELECT
	/*
	
	@Override
	public void showRank() {
		LinkedList<RankPosition> tempRanking = new LinkedList<RankPosition>();
		String tempNick = "";
		int counter = 1, tempScore = 0;
		CallableStatement showRankCallStatement = null;
		ResultSet results = null;
		loadConnection();
		
		try {
			showRankCallStatement = connection.prepareCall("CALL showrank");
			results = showRankCallStatement.executeQuery();
			while(results.next() && counter < 11) {
				tempNick = results.getString("nick");
				tempScore = results.getInt("score");
				tempRanking.add(new RankPosition(tempNick, tempScore));
				counter++;
			}
			ranking.clear();
			ranking.addAll(tempRanking);
			System.out.println("Stored procedure `showrank` called successfully");
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `showrank` from database: " + e.getMessage());
		} finally {
			try { results.close(); } catch (Exception e) {  }
			try { showRankCallStatement.close(); } catch (Exception e) {  }
			closeConnection();
		} 
	
	@Override
	public boolean tryLogin(String login, String password) {
		boolean ifSuccessfullyLogged = false;
		CallableStatement tryLoginCallStatement = null;
		ResultSet loggedData = null;
		
		try {
			tryLoginCallStatement = connection.prepareCall("CALL trylogin(?,?)");
			tryLoginCallStatement.setString(1, login);
			tryLoginCallStatement.setString(2, password);
			System.out.println("Calling stored procedure `trylogin`('" + login + "', '" + password + "')");
			loggedData = tryLoginCallStatement.executeQuery();
			if(loggedData.next()) {
				System.out.println("Stored procedure `trylogin` called successfully");
				playerNickname = loggedData.getString("nick");
				playerHighestScore = loggedData.getInt("score");
				ifPlayerRoot = loggedData.getBoolean("root");
				ifSuccessfullyLogged = true;
			}
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `trylogin` from database: " + e.getMessage());
		} finally {
			try { loggedData.close(); } catch (Exception e) {  }
			try { tryLoginCallStatement.close(); } catch (Exception e) {  }
//					closeConnection();
		}
		return ifSuccessfullyLogged;
	}
	*/
		
}
