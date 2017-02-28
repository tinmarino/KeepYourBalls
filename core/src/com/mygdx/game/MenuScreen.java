package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {
	SkinLib skinLib;
	Stage stage;
	Table table;
	ImageButton buttonPlay, buttonMusic, buttonStat;
	ImageButton buttonRate;
	Image imageStudio, imageTitle; 
	float width, height;



	@Override
	public void show() { 
		width = Gdx.graphics.getWidth();
		height= Gdx.graphics.getHeight();
		stage = new Stage();
		table = new Table();
		table.setWidth(width);
		table.setHeight(height);
		Gdx.input.setInputProcessor(stage);
		skinLib = new SkinLib();

		// Images 
		imageTitle = new Image(new Texture("img/title.png"));
		buttonStat = new ImageButton(skinLib.getImageButtonRoundStat(400, G.RED));
		buttonPlay = new ImageButton(skinLib.getImageButtonRoundPlay(400, G.GREEN));
		buttonMusic = new ImageButton(skinLib.getImageButtonRoundMusic(400, G.BLUE));
		imageStudio = new Image(new Texture("img/studio.png"));

		// Listeners
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y); 
				routinePlay(); 
			}});


		// Packing 
		table.add(imageTitle	).height(height/5).row();
		table.add(buttonStat	).size(height/7).padLeft(0.5f * width).row();
		table.add(buttonPlay	).size(height/7).row();
		table.add(buttonMusic	).size(height/7).padRight(0.5f * width).row();
		table.add(imageStudio	).height(height/10).width(0.7f * width).row();


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

}
