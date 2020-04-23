package com.snake.implementation;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.snake.templates.IConnectDatabase;

public class ConnectDatabase implements IConnectDatabase{

	public Connection connection = null;
	
	public void loadConnection() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("sql\\Snake_properties"));
		
			final String LOGIN = properties.getProperty("login");
			final String PASSWORD = properties.getProperty("password");
			final String URL = properties.getProperty("url");
			final String JDBC_DRIVER = properties.getProperty("jdbc_driver");
			
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find Configuration File under specified path: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Loading File failed: " + e.getMessage());
		}catch(ClassNotFoundException e) {
			System.err.println("Could not find the Driver under specified path: " + e.getMessage());
		}catch(SQLException e) {
			System.err.println("Inproper Configuration Data or incorrect Driver for SQL database: " + e.getMessage());
		}
		if(connection != null)
			System.out.println("Connection established successfully!");
		else
			System.out.println("Failed to establish the connection");
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Failed to close Connection: " + e.getMessage());
		} 
	}
}
