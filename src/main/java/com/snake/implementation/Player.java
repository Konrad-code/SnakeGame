package com.snake.implementation;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

public class Player {
	
	{
		highestScore = 0;
		root = false;
	}
	
	private int id;
	private String nickname;
	private String login;
	private String password;
	private int highestScore;
	private boolean root;
	
	public Player(int id, String nickname, String login, String password, int highestScore, boolean root) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.login = login;
		this.password = password;
		this.highestScore = highestScore;
		this.root = root;
	}
	
	public Player(String nickname, String login, String password) {
		this.nickname = nickname;
		this.login = login;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getHighestScore() {
		return highestScore;
	}
	
	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}
	
	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	@Override
	public String toString() {
		return "Player [nickname=" + nickname + ", highestScore=" + highestScore + "]";
	}
}
