package com.blubblub.gravity;

import java.util.Random;

public class WorldProvider {

	Game game;
	Random r;
	Element lastElement;
	public WorldProvider(Game game)
	{
		this.game = game;
		r = new Random();
		lastElement = Element.AIR;
	}
	
	/**
	 * difficulty from 1-100
	 * 
	 * @param difficulty
	 * @return
	 */
	public Platform[] getNextPlatform(int difficulty)
	{
		int pieces;
		if(difficulty/10 > 0)
			pieces = r.nextInt(difficulty/10) + 1;
		else
			pieces = 1;
		Platform[] platforms = new Platform[pieces];
		int lastx = 0;
		int y;
		//TODO critique numbers
		if(50.0/difficulty > 0)
			try{
				y = game.height + r.nextInt((int)(50.0/difficulty)) + 150;
			}catch(Exception e){
				y = game.height + 150;
			}
		else
			y = game.height + 150;//TODO random y after lvl 200.

		for(int i = 0; i < pieces; i++)
		{
			int x = lastx;
			lastx = x + game.width/pieces;
			Element element = Element.rand(lastElement);
			lastElement = element;
			Platform platform = new Platform(game, x, y, game.width / pieces, element);
			platforms[i] = platform;
		}

		return platforms;
	}
	
}
