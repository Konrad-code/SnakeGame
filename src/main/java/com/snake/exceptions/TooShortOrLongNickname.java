package com.snake.exceptions;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import javax.swing.JOptionPane;

public class TooShortOrLongNickname extends Exception{
	public TooShortOrLongNickname(){
		System.out.println("Nickname is too short or too long. Nickname has to contain at least 4 characters and not more than 15");
		JOptionPane.showMessageDialog(null, "Nickname is too short or too long. Nickname has to contain at least 4 characters and not more than 15");
	}
}