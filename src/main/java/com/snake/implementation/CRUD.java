package com.snake.implementation;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.snake.templates.IDelete;
import com.snake.templates.IInsert;
import com.snake.templates.ISelect;
import com.snake.templates.IUpdate;

public abstract class CRUD extends ConnectDatabase implements IInsert, ISelect, IUpdate, IDelete{

	public static boolean checkIfTableExistsInDatabase() {
		ConnectDatabase dbConn = new ConnectDatabase();
		dbConn.loadConnection();
		
		String trueOutput = "true";
		String hasTable = "";
		Statement statement = null;
		ResultSet resultCheck = null;
		String sqlQuery = "SELECT EXISTS("
							+ "SELECT FROM information_schema.tables "
							+ "WHERE table_schema = 'public' "
							+ "AND table_name = 'players'"
							+ ")::text AS if_has_table;";
		try {
			statement = dbConn.connection.createStatement();
			resultCheck = statement.executeQuery(sqlQuery);
			if(resultCheck.next())
				hasTable = resultCheck.getString("if_has_table");
		} catch (SQLException e) {
			System.err.println("Failed to finalize method `checkIfTableExistsInDatabase` on database: " + e.getMessage());
		}finally {
			try { statement.close(); } catch (Exception e) { /* leave action */ }
			try { resultCheck.close(); } catch (Exception e) { /* leave action */ }
			dbConn.closeConnection();
		}
		if(hasTable.equalsIgnoreCase(trueOutput))
			return true;
		else
			return false;
	}
	
	@Override
	public void dropTable() {
		loadConnection();
		CallableStatement dropTableCallStatement = null;
		
		try {
			String table = "players";
			dropTableCallStatement = connection.prepareCall("CALL dropTable('players')");
//			dropTableCallStatement.setString(1, table);
			System.out.println("Calling stored procedure `dropTable`('" + table + "')");
			dropTableCallStatement.execute();
			System.out.println("Stored procedure `dropTable` called successfully");
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `dropTable` from database: " + e.getMessage());
		} finally {
			try { dropTableCallStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public boolean checkNick(String nick) {
		loadConnection();
		ResultSet rs = null;
		PreparedStatement checkNickStatement = null;
		boolean nickFree = false;
		
		try {
			checkNickStatement = connection.prepareStatement("SELECT nick FROM customer WHERE nick=?;");
			checkNickStatement.setString(1, nick);
			System.out.println("Executing query `checknick`('" + nick + "')");
			rs = checkNickStatement.executeQuery();
			if(rs.next())
				System.out.println("Query `checknick` executed successfully but it means - nick is occupied");
			else
				nickFree = true;
		} catch (SQLException e) {
			System.err.println("Failed to execute query `checknick` on database: " + e.getMessage());
		} finally {
			try { rs.close(); } catch (Exception e) { /* leave action */ }
			try { checkNickStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return nickFree;
	}
	
	@Override
	public boolean checkLogin(String login) {
		loadConnection();
		PreparedStatement checkLoginStatement = null;
		ResultSet rs = null;
		boolean loginFree = false;
		try {
			checkLoginStatement = connection.prepareStatement("SELECT login FROM players WHERE login=?;");
			checkLoginStatement.setString(1, login);
			System.out.println("Calling stored procedure `checklogin`('" + login + "')");
			rs = checkLoginStatement.executeQuery();
			if(rs.next())
				System.out.println("Stored procedure `checklogin` called successfully but it means - login is occupied");
			else
				loginFree = true;
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `checklogin` from database: " + e.getMessage());
		} finally {
			try { rs.close(); } catch (Exception e) { /* leave action */ }
			try { checkLoginStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return loginFree;
	}
	
	@Override
	public void deletePlayer(String userToRemove) {
		loadConnection();
		CallableStatement deletePlayerCallStatement = null;
		
		try {
//			String userToRemove = "piotrolot1";
			deletePlayerCallStatement = connection.prepareCall("CALL deleteplayer(?)");
			deletePlayerCallStatement.setString(1, userToRemove);
			System.out.println("Calling stored procedure `deleteplayer`('" + userToRemove + "')");
			boolean ifRowsAffected = deletePlayerCallStatement.execute();
			if(ifRowsAffected)
				System.out.println("Stored procedure `deleteplayer` called successfully");
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `deleteplayer` from database: " + e.getMessage());
		} finally {
			try { deletePlayerCallStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public void deletePlayers() {
		loadConnection();
		CallableStatement deletePlayersCallStatement = null;
		
		try {
			String table = "players";
			deletePlayersCallStatement = connection.prepareCall("CALL deleteplayers('players')");
//			dropTableCallStatement.setString(1, table);
			System.out.println("Calling stored procedure `deleteplayers`('" + table + "')");
			deletePlayersCallStatement.execute();
			System.out.println("Stored procedure `deleteplayers` called successfully");
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `deleteplayers` from database: " + e.getMessage());
		} finally {
			try { deletePlayersCallStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public void editPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearRank() {
		loadConnection();
		CallableStatement clearRankCallStatement = null;
		
		try {
			clearRankCallStatement = connection.prepareCall("CALL clearrank('players')");
			System.out.println("Calling stored procedure `clearrank`");
			clearRankCallStatement.execute();
			System.out.println("Stored procedure `clearrank` called successfully");
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `clearrank` from database: " + e.getMessage());
		} finally {
			try { clearRankCallStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public void addPlayer(Player newPlayer) {
		loadConnection();
		CallableStatement addPlayerCallStatement = null;
		
		try {
			addPlayerCallStatement = connection.prepareCall("CALL addplayer(?,?,?)");
			addPlayerCallStatement.setString(1, newPlayer.getNickname());
			addPlayerCallStatement.setString(2, newPlayer.getLogin());
			addPlayerCallStatement.setString(3, newPlayer.getPassword());
			// MAY COMMENT IN FUTURE
			System.out.println("Calling stored procedure `addplayer`('" + newPlayer.getNickname() + "', '" + newPlayer.getLogin() + "', '" + newPlayer.getPassword() + "')");
			addPlayerCallStatement.executeUpdate();
			System.out.println("Stored procedure `addplayer` called successfully");
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `addplayer` from database: " + e.getMessage());
		} finally {
			try { addPlayerCallStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public void createTable() {
		loadConnection();
		CallableStatement createTableCallStatement = null;
		
		try {
			String table = "players";
			createTableCallStatement = connection.prepareCall("CALL createtable('players')");
//			createTableCallStatement.setString(1, table);
			System.out.println("Calling stored procedure `createtable`('" + table + "')");
			createTableCallStatement.execute();
			System.out.println("Stored procedure `createtable` called successfully");
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `createtable` from database: " + e.getMessage());
		} finally {
			try { createTableCallStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}
	
	
	// Methods that failed job
	/*
	
	@Override
	public boolean checkNick(String nick) {
		loadConnection();
		ResultSet rs = null;
		CallableStatement checkNickCallStatement = null;
		boolean nickFree = false;
		
		try {
			checkNickCallStatement = connection.prepareCall("CALL checknick(?)");
			checkNickCallStatement.setString(1, nick);
			System.out.println("Calling stored procedure `checknick`('" + nick + "')");
			rs = checkNickCallStatement.executeQuery();
			if(rs.next())
				System.out.println("Stored procedure `checknick` called successfully but it means - nick is occupied");
			else
				nickFree = true;
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `checknick` from database: " + e.getMessage());
		} finally {
			try { rs.close(); } catch (Exception e) {  }
			try { checkNickCallStatement.close(); } catch (Exception e) {  }
			closeConnection();
		}
		return nickFree;
	}
	 
	@Override
	public boolean checkLogin(String login) {
		loadConnection();
		ResultSet rs = null;
		CallableStatement checkLoginCallStatement = null;
		boolean loginFree = false;
		try {
			checkLoginCallStatement = connection.prepareCall("CALL checklogin(?)");
			checkLoginCallStatement.setString(1, login);
			System.out.println("Calling stored procedure `checklogin`('" + login + "')");
			rs = checkLoginCallStatement.executeQuery();
//			rs = checkLoginCallStatement.getObject(index)
			if(rs.next())
				System.out.println("Stored procedure `checklogin` called successfully but it means - login is occupied");
			else
				loginFree = true;
		} catch (SQLException e) {
			System.err.println("Failed to call Procedure `checklogin` from database: " + e.getMessage());
		} finally {
			try { rs.close(); } catch (Exception e) {  }
			try { checkLoginCallStatement.close(); } catch (Exception e) {  }
			closeConnection();
		}
		return loginFree;
	}
	*/
}
