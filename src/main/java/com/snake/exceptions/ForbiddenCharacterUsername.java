package com.snake.exceptions;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import javax.swing.JOptionPane;

public class ForbiddenCharacterUsername extends Exception{
	public ForbiddenCharacterUsername(){
		System.out.println("Username with forbidden sign entered");
		JOptionPane.showMessageDialog(null, "Username with forbidden sign entered");
	}
}