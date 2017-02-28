package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverScreen implements Screen{
	SkinLib skinLib;
	Stage stage;
	Table table, tableScore;
	ImageButton buttonPlay, buttonMusic, buttonStat;
	Label labelGameOver, labelScore, labelBestScore;
	float width, height;



	@Override
	public void show() { 
		width = Gdx.graphics.getWidth();
		height= Gdx.graphics.getHeight();
		stage = new Stage();
		table = new Table();
		table.setWidth(width);
		table.setHeight(height);
		tableScore = new Table();
		Gdx.input.setInputProcessor(stage);
		skinLib = new SkinLib();

		// Images 
		labelGameOver = new Label("GAME OVER", skinLib.getLabel());
		buttonStat = new ImageButton(skinLib.getImageButtonRoundStat(400, G.RED));
		buttonPlay = new ImageButton(skinLib.getImageButtonRoundPlay(400, G.GREEN));
		buttonMusic = new ImageButton(skinLib.getImageButtonRoundMusic(400, G.BLUE));

		// Listeners
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y); 
				routinePlay(); 
			}});
		buttonStat.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y); 
				routineStat(); 
			}});
		buttonMusic.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y); 
				routineMusic(); 
			}});

		// Packing 
		table.add(labelGameOver ).height(height/5).colspan(2).row();
		table.add(tableScore	).height(height/5).colspan(2).row();
		table.add(buttonPlay	).size(height/7).colspan(2).row();
		table.add(buttonMusic	).size(0.10f * height);
		table.add(buttonStat	).size(0.10f * height).padLeft(0.5f * width);


		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() { }

	@Override
	public void hide() { }

	@Override
	public void pause() { }

	@Override
	public void resize(int arg0, int arg1) { }

	@Override
	public void resume() { }

	public void routinePlay(){
		G.game.setScreen(new GameScreen());
	}

	public void routineStat(){
	}

	public void routineMusic(){
	}

}
