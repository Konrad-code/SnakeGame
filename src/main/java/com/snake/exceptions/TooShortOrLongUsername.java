package com.snake.exceptions;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import javax.swing.JOptionPane;

public class TooShortOrLongUsername extends Exception{
	public TooShortOrLongUsername(){
		System.out.println("Username is too short or too long. Username has to contain at least 4 characters and not more than 20");
		JOptionPane.showMessageDialog(null, "Username is too short or too long. Username has to contain at least 4 characters and not more than 20");
	}
}