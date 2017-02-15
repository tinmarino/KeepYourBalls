package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen, ContactFilter{
	private SpriteBatch batch;
	private World world;
	private ShapeRenderer shapeRenderer;
	private Box2DDebugRenderer debugRender;
	private Wall wall;
	private Camera camera;
	private In in;
	private ArrayList<Ball> ballList;
	private ArrayList<Ball> removeBallList = new ArrayList<Ball>();
	private ArrayList<Ball> addBallList = new ArrayList<Ball>();
	private float[] radiusArray = {1.5f, 1f, 0.5f};

	@Override
	public void show() {
		in = new In(this);

		world = new World(new Vector2(0,0),true);
		world.setContactFilter(this);

		shapeRenderer = new ShapeRenderer();

		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = 0;
		camera.position.y = 0;
		camera.update();
		
		wall = new Wall(world);

		// Initial balls 
		ballList = new ArrayList<Ball>();
		Ball ball = new Ball(world, G.RED, radiusArray[0]);
		ball.body.setLinearVelocity(new Vector2(0, 3f));
		ballList.add(ball);

		Ball ball2 = new Ball(world, G.BLUE, radiusArray[0]);
		ball2.body.setLinearVelocity(new Vector2(0, 2f));
		ballList.add(ball2);

		// verticl walll 
		verticalWall(-1); 
		verticalWall(1); 

		debugRender = new Box2DDebugRenderer(); 
	}

	private void verticalWall(int side){
		// body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		Body body = world.createBody(bodyDef);

		// FixtureShape 
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 15);		
		
		// BodyFixture 
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.restitution = 1.2f;
		fixture.friction = 0;
		body.createFixture(fixture);

		if (side == -1){
			body.setTransform(-6f, 7.5f, 0);
		}
		else{
			body.setTransform(6f, 7.5f, 0);
		}
	}

	public boolean input(In.COMMAND cmd){
		wall.input(cmd);
		return true;
	}

	@Override
	public void dispose() {
	}


	public void die(Ball ball){
		Ball ball1 = new Ball(world, ball.color, ball.radius / 2);
		ball1.body.setLinearVelocity(new Vector2(2, 3f));
		addBallList.add(ball1);

		Ball ball2 = new Ball(world, ball.color, ball.radius / 2);
		addBallList.add(ball2);

		world.destroyBody(ball.body);
		removeBallList.add(ball);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		for (Ball ball : ballList){
			ball.draw(shapeRenderer, delta);
		}
		wall.draw(shapeRenderer, delta);

		// Debug
		// float scale = Gdx.graphics.getWidth() * 0.1f;
		// Matrix4 m = camera.combined.cpy();
		// m.scl(scale);
		// debugRender.render(world, m); 
		for (Ball ball : ballList){
			if (ball.body.getPosition().y < -7.5f || ball.body.getPosition().y > 7.5f)
			{
				die(ball);
			}
		}
		ballList.removeAll(removeBallList);
		ballList.addAll(addBallList);
		addBallList.clear();
		removeBallList.clear();

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
