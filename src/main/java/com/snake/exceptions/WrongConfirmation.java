package com.snake.exceptions;

/**
 * @author Konrad �o�y�ski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import javax.swing.JOptionPane;

public class WrongConfirmation extends Exception{
	public WrongConfirmation(){
		System.out.println("Password doesn't match confirm password");
		JOptionPane.showMessageDialog(null, "Password doesn't match confirm password");
	}
}