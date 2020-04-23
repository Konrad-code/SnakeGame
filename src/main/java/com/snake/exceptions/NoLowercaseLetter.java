package com.snake.exceptions;

/**
 * @author Konrad �o�y�ski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import javax.swing.JOptionPane;

public class NoLowercaseLetter extends Exception{
	public NoLowercaseLetter(){
		System.out.println("Password with no lowercase letters. Password has to contain at least 1 lowercase letter");
		JOptionPane.showMessageDialog(null, "Password with no lowercase letters. Password has to contain at least 1 lowercase letter");
	}
}