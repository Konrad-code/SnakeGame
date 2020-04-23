package com.snake.exceptions;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import javax.swing.JOptionPane;

public class NoUppercaseLetter extends Exception{
	public NoUppercaseLetter(){
		System.out.println("Password with no uppercase letters. Password has to contain at least 1 uppercase letter");
		JOptionPane.showMessageDialog(null, "Password with no uppercase letters. Password has to contain at least 1 uppercase letter");
	}
}