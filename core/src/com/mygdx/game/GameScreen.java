package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	private Camera camera;
	private In in;
	private ArrayList<Ball> ballList;

	@Override
	public void show() {
		in = new In(this);

		world = new World(new Vector2(0,0),true);
		world.setContactFilter(this);

		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = 0;
		camera.position.y = 0;
		camera.update();
		
		wall = new Wall(world);

		ballList = new ArrayList<Ball>();
		Ball ball = new Ball(world);
		ball.body.setLinearVelocity(new Vector2(0, 3f));
		ballList.add(ball);
		Ball ball2 = new Ball(world);
		ball2.body.setLinearVelocity(new Vector2(0, 2f));
		ball2.sprite.setTexture(Pix.texCircle(100, Color.BLUE));
		ballList.add(ball2);


		debugRender = new Box2DDebugRenderer(); 
	}

	public boolean input(In.COMMAND cmd){
		wall.input(cmd);
		return true;
	}

	@Override
	public void dispose() {
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Ball ball : ballList){
			ball.draw(batch, delta);
		}
		wall.draw(batch, delta);
		batch.end();

		// Debug
		// float scale = Gdx.graphics.getWidth() * 0.1f;
		// Matrix4 m = camera.combined.cpy();
		// m.scl(scale);
		// debugRender.render(world, m); 
		world.step(delta, 1, 3);
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
		return true;
	}

}
