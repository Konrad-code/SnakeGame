package com.snake.templates;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import java.util.List;

import com.snake.implementation.Player;

public interface IPlayerDAO {
	public List<Player> getAllPlayers();
	public List<Player> searchPlayers(String nickname);
	public int getHighscore(String nickname);
}
