package com.snake.templates;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

public interface ISelect {
	public void showRank();
	public boolean checkLogin(String login);
	public boolean checkNick(String nick);
	public boolean tryLogin(String login, String password);
}
