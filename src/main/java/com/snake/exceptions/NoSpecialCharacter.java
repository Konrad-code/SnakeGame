package com.snake.exceptions;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import javax.swing.JOptionPane;

public class NoSpecialCharacter extends Exception{
	public NoSpecialCharacter(){
		System.out.println("Password with no special character. Password has to contain at least 1 special character");
		JOptionPane.showMessageDialog(null, "Password with no special character. Password has to contain at least 1 special character");
	}
}