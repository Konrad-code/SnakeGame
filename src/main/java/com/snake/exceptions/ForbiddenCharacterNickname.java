package com.snake.exceptions;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import javax.swing.JOptionPane;

public class ForbiddenCharacterNickname extends Exception{
	public ForbiddenCharacterNickname(){
		System.out.println("Nickname with forbidden sign entered");
		JOptionPane.showMessageDialog(null, "Nickname with forbidden sign entered");
	}
}