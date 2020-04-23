package com.snake.apk;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import com.snake.implementation.PlayerDAO;
import com.snake.gui.AdminFrame;
import com.snake.gui.GameFrame;
import com.snake.gui.LoginFrame;
import com.snake.gui.RankingFrame;
import com.snake.implementation.ConnectDatabase;
import com.snake.implementation.StoredProcedures;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
 *
 * @author Janek Misiórski | https://github.com/janekjmf 	| janekjmf@gmail.com 		| +48 883 483 807
 */

public class Main
{	
    public static void main(String[] args)
    {
    	StoredProcedures preparingProceduresInDatabase = StoredProcedures.getInstance();
    	PlayerDAO player1 = new PlayerDAO();
//    	player1.dropTable();
//    	player1.createTable();
//    	player1.deletePlayers();
    	System.out.println(player1.getAllPlayers());
    	System.out.println("\n\n");
    	System.out.println(player1.searchPlayers("Brown"));
//    	player1.deletePlayer("DavidDuhovny");
    	

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	LoginFrame frame = new LoginFrame(true);
                frame.setVisible(true);
                frame.pack();
                frame.setLocationRelativeTo(null);
            }
        });
        
        // OLD GAME EXECUTION
        /*
        JFrame ramka = new JFrame();
        GameFrame okno = new GameFrame();

        ramka.add(okno);
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setTitle("Zaskroniec");
        ramka.setResizable(false);
        ramka.pack();
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
        */
    }
}