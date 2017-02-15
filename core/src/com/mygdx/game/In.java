package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class In implements InputProcessor {
	enum COMMAND{TAP}
	private COMMAND command;
	private GameScreen gameScreen;


 	public In(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int key) {
		if (key == Keys.S){
			G.screenShot();
			return true;
		}
		return gameScreen.input(COMMAND.TAP);
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return gameScreen.input(COMMAND.TAP);
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
