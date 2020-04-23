package com.snake.implementation;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

public class GameSettings {
	
	{
		ifMusic = true;
		ifMoveThroughWalls = true;
		ifNarrowedView = false;
		gameSpeed = 80;
		scoreForApple = 1;
		spawnedApplesNo = 1;
		spawnedPowerupsNo = 1;
	}
	
	protected boolean ifMusic;
	protected boolean ifMoveThroughWalls;
	protected boolean ifNarrowedView;
	protected int gameSpeed;					// Repaint period in ms
	protected int scoreForApple;				// equals snake's length increases
	protected int spawnedApplesNo;
	protected int spawnedPowerupsNo;
	
	public GameSettings() {
		
	}

	public boolean isIfMusic() {
		return ifMusic;
	}

	public void setIfMusic(boolean ifMusic) {
		this.ifMusic = ifMusic;
	}

	public boolean isIfMoveThroughWalls() {
		return ifMoveThroughWalls;
	}

	public void setIfMoveThroughWalls(boolean ifMoveThroughWalls) {
		this.ifMoveThroughWalls = ifMoveThroughWalls;
	}

	public boolean isIfNarrowedView() {
		return ifNarrowedView;
	}

	public void setIfNarrowedView(boolean ifNarrowedView) {
		this.ifNarrowedView = ifNarrowedView;
	}

	public int getGameSpeed() {
		return gameSpeed;
	}

	public void setGameSpeed(int gameSpeed) {
		this.gameSpeed = gameSpeed;
	}

	public int getScoreForApple() {
		return scoreForApple;
	}

	public void setScoreForApple(int scoreForApple) {
		this.scoreForApple = scoreForApple;
	}

	public int getSpawnedApplesNo() {
		return spawnedApplesNo;
	}

	public void setSpawnedApplesNo(int spawnedApplesNo) {
		this.spawnedApplesNo = spawnedApplesNo;
	}

	public int getSpawnedPowerupsNo() {
		return spawnedPowerupsNo;
	}

	public void setSpawnedPowerupsNo(int spawnedPowerupsNo) {
		this.spawnedPowerupsNo = spawnedPowerupsNo;
	}
}
