package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen extends InputAdapter implements Screen, ContactFilter{
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer debugRender;
	private Wall wall;
	private Ball ball;
	public Camera camera;

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();

		world = new World(new Vector2(0,0),true);
		world.setContactFilter(this);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = 0;
		camera.position.y = 0;
		camera.update();
		
		wall = new Wall(world);
		ball = new Ball(world);

		debugRender = new Box2DDebugRenderer(); 
	}

	@Override
	public void dispose() {
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(60, 8, 3);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		ball.draw(batch, delta);
		wall.draw(batch, delta);
		batch.end();

		// Debug
		float scale = Gdx.graphics.getWidth() * 0.1f;
		Matrix4 m = camera.combined.cpy();
		m.scl(scale);
		debugRender.render(world, m); 
	}




	@Override
	public void hide() { }

	@Override
	public void pause() { } 

	@Override
	public void resize(int arg0, int arg1) { }

	@Override
	public void resume() { }

	@Override
	public boolean shouldCollide(Fixture arg0, Fixture arg1) {
		return false;
	}

}
