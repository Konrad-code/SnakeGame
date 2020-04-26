package com.snake.implementation;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.snake.templates.IDelete;
import com.snake.templates.IInsert;
import com.snake.templates.ISelect;
import com.snake.templates.IDropStoredProcedures;
import com.snake.templates.IUpdate;

public class StoredProcedures extends ConnectDatabase implements IInsert, ISelect, IUpdate, IDelete, IDropStoredProcedures{
	
	private static StoredProcedures single_instance = null;
	
	public static StoredProcedures getInstance()
    {
        if (single_instance == null)
            single_instance = new StoredProcedures();
        return single_instance;
    }
	
	private StoredProcedures() {
		loadConnection();
		this.dropTable();
		this.createTable();
		this.deletePlayer("just overloading initializer method");
		this.deletePlayers();
		this.updatePlayerScore(0);
//		this.editPlayer();		// FUNCTIONALLITY FOR FURTHER DEVELOPMENT
		this.clearRank();
		this.showRank();
		this.checkLogin("just overloading initializer method");
		this.checkNick("just overloading initializer method");
		this.addPlayer(null);
		this.tryLogin("just overloading initializer method", "just overloading initializer method");
		closeConnection();
	}
	
	@Override
	public void dropTableDropProcedure(){
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS dropTable(tablename VARCHAR(40));");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `dropTable` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public void dropTable() {
		PreparedStatement dropTableProcedureStatement = null;
		dropTableDropProcedure();
		
		try {
			dropTableProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE dropTable("
					+ "tablename VARCHAR(40))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" DROP TABLE IF EXISTS players;\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			dropTableProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `dropTable` to database: " + e.getMessage());
		} finally {
			try { dropTableProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}
	
	@Override
	public void createTableDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS createtable(tablename VARCHAR(40))");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `createtable` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}

	@Override
	public void createTable() {
		PreparedStatement createTableProcedureStatement = null;
		createTableDropProcedure();
		
		try {
			createTableProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE createtable("
					+ "tablename VARCHAR(40))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ "	CREATE TABLE IF NOT EXISTS players (\n"
					+ "		 id_player SERIAL,\n"
					+ "		 nick VARCHAR(64) DEFAULT NULL,\n"
					+ "		 login VARCHAR(64) DEFAULT NULL,\n"
					+ "		 password VARCHAR(64) DEFAULT NULL,\n"
					+ "		 score INTEGER DEFAULT 0,\n"
					+ "		 root BOOLEAN DEFAULT FALSE\n"
					+ "	);\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			createTableProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `createtable` to database: " + e.getMessage());
		} finally {
			try { createTableProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}	
	
	@Override
	public void deletePlayerDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS deleteplayer(IN givenlogin VARCHAR(64));");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `deleteplayer` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public void deletePlayer(String userToRemove) {
		PreparedStatement deletePlayerProcedureStatement = null;
		deletePlayerDropProcedure();
		
		try {
			deletePlayerProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE deleteplayer("
					+ "IN givenlogin VARCHAR(64))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" DELETE FROM players WHERE login=givenlogin AND root IS NOT TRUE;\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			deletePlayerProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `deleteplayer` to database: " + e.getMessage());
		} finally {
			try { deletePlayerProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}
	
	@Override
	public void deletePlayersDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS deleteplayers(tablename VARCHAR(40));");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `deleteplayers` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}

	@Override
	public void deletePlayers() {
		PreparedStatement deletePlayersProcedureStatement = null;
		deletePlayersDropProcedure();
		
		try {
			deletePlayersProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE deleteplayers("
					+ "tablename VARCHAR(40))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" DELETE FROM players;\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			deletePlayersProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `deleteplayers` to database: " + e.getMessage());
		} finally {
			try { deletePlayersProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public void updatePlayerScoreDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS updatescore(IN givennick VARCHAR(64), IN givenscore INT);");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `updatescore` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public void updatePlayerScore(int newScore) {
		PreparedStatement updateScoreProcedureStatement = null;
		updatePlayerScoreDropProcedure();
		
		try {
			updateScoreProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE updatescore("
					+ "IN givennick VARCHAR(64), IN givenscore INT)\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" UPDATE players SET score=givenscore WHERE nick=givennick;\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			updateScoreProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `updatescore` to database: " + e.getMessage());
		} finally {
			try { updateScoreProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public void editPlayerDropProcedure() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void editPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearRankDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS clearrank(tablename VARCHAR(40));");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `clearrank` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public void clearRank() {
		PreparedStatement clearRankProcedureStatement = null;
		clearRankDropProcedure();
		
		try {
			clearRankProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE clearrank(tablename VARCHAR(40))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" UPDATE players SET score=0;\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			clearRankProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `clearrank` to database: " + e.getMessage());
		} finally {
			try { clearRankProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public void showRankDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS showrank(tablename VARCHAR(40))");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `showrank` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public void showRank() {
		PreparedStatement showRankProcedureStatement = null;
		showRankDropProcedure();
		
		try {
			showRankProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE showrank(tablename VARCHAR(40))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" SELECT nick, score FROM players ORDER BY score DESC;\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			showRankProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `showrank` to database: " + e.getMessage());
		} finally {
			try { showRankProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}
		
	@Override
	public void checkLoginDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS checklogin(IN searched_login VARCHAR(64));");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `checklogin` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public boolean checkLogin(String login) {
		PreparedStatement checkLoginProcedureStatement = null;
		checkLoginDropProcedure();
		
		try {
			checkLoginProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE checklogin(\n"
					+ "IN searched_login VARCHAR(64))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" SELECT login FROM players WHERE login=searched_login;\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			checkLoginProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `checklogin` to database: " + e.getMessage());
		} finally {
			try { checkLoginProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return true;
	}
	
	@Override
	public void checkNickDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS checknick(IN searched_nick VARCHAR(64));");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `checknick` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public boolean checkNick(String nick) {
		PreparedStatement checkNickProcedureStatement = null;
		checkNickDropProcedure();
		
		try {
			checkNickProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE checknick(\n"
					+ "IN searched_nick VARCHAR(64))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" SELECT nick FROM players WHERE nick=searched_nick;\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			checkNickProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `checknick` to database: " + e.getMessage());
		} finally {
			try { checkNickProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return true;
	}
	
	@Override
	public void addPlayerDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS addplayer("
					+"IN givennick VARCHAR(64), IN givenlogin VARCHAR(64), IN givenpassword VARCHAR(64))");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `addplayer` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public void addPlayer(Player newPlayer) {
		PreparedStatement addPlayerProcedureStatement = null;
		addPlayerDropProcedure();
		
		try {
			addPlayerProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE addplayer("
					+ "IN givennick VARCHAR(64), IN givenlogin VARCHAR(64), IN givenpassword VARCHAR(64))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ " INSERT INTO players (nick, login, password) VALUES (givennick, givenlogin, givenpassword);\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			addPlayerProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `addplayer` to database: " + e.getMessage());
		} finally {
			try { addPlayerProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
	}

	@Override
	public void tryLoginDropProcedure() {
		PreparedStatement dropStatement = null;
		loadConnection();
		
		try {
			dropStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS trylogin(IN login VARCHAR(64), IN password VARCHAR(64));");
			dropStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to drop Stored Procedure `trylogin` from database: " + e.getMessage());
		} finally {
			try { dropStatement.close(); } catch (Exception e) { /* leave action */ }
		}
	}
	
	@Override
	public boolean tryLogin(String login, String password) {
		PreparedStatement tryLoginProcedureStatement = null;
		tryLoginDropProcedure();

		try {
			tryLoginProcedureStatement = connection.prepareStatement("CREATE OR REPLACE PROCEDURE trylogin(\n"
					+ "IN login_check VARCHAR(64), IN password_check VARCHAR(64))\n"
					+ " AS $$\n"
					+ " BEGIN\n\t"
					+ 	" SELECT (nick, score, root) FROM players WHERE (login=login_check AND password=password_check);\n"
					+ " COMMIT;\n"
					+ " END;\n"
					+ " $$ LANGUAGE plpgsql;");
			tryLoginProcedureStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to add Stored Procedure `trylogin` to database: " + e.getMessage());
		} finally {
			try { tryLoginProcedureStatement.close(); } catch (Exception e) { /* leave action */ }
			closeConnection();
		}
		return true;
	}
}
