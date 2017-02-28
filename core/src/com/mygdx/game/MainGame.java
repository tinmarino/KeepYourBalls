package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
	
	@Override
	public void create () {
		G.game = this;
		// screen = new GameScreen();
		screen = new MenuScreen();
		screen.show();
	}
}
