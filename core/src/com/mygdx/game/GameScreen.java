package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen, ContactFilter{
	private World world;
	private ShapeRenderer shapeRenderer;
	private Wall wall;
	private Camera camera;
	private Stage stage;
	private In in;
	private Viewport viewport;
	private ArrayList<Ball> ballList;
	private ArrayList<Ball> removeBallList = new ArrayList<Ball>();
	private ArrayList<Ball> addBallList = new ArrayList<Ball>();
	private float[] radiusArray = {1.5f, 1f, 0.5f};

	@Override
	public void show() {
		G.world2Screen = Gdx.graphics.getWidth() / G.wSize.x;
		in = new In(this);
		Gdx.input.setInputProcessor(in);

		world = new World(new Vector2(0,0),true);
		world.setContactFilter(this);
		shapeRenderer = new ShapeRenderer();


		camera = new OrthographicCamera();
		viewport = new FitViewport(G.wSize.x * G.world2Screen, G.wSize.y * G.world2Screen, camera);
		viewport.apply();
		
		// Walls 
		wall = new Wall(world);
		verticalWall(-1); 
		verticalWall(1); 

		// Initial balls 
		ballList = new ArrayList<Ball>();
		Ball ball = new Ball(world, G.RED, radiusArray[0]);
		ball.body.setLinearVelocity(new Vector2(0, 3f));
		ballList.add(ball);

		Ball ball2 = new Ball(world, G.BLUE, radiusArray[0]);
		ball2.body.setLinearVelocity(new Vector2(0, 2f));
		ballList.add(ball2);

	}

	private void verticalWall(int side){
		// body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		Body body = world.createBody(bodyDef);

		// FixtureShape 
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, G.wSize.y);		
		
		// BodyFixture 
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.restitution = 1.1f;
		fixture.friction = 0;
		body.createFixture(fixture);

		if (side == -1){
			body.setTransform(- G.wSize.x/2 - 1, G.wSize.y/2, 0);
		}
		else{
			body.setTransform(G.wSize.x/2 + 1, G.wSize.y/2, 0);
		}
	}


	public void window(){
		stage = new Stage(viewport);

		// Images
		SkinLib skinLib = new SkinLib();
		ImageButton buttonHome = new ImageButton(skinLib.getImageButtonRoundHouse(200, G.BLUE));
		ImageButton buttonPlay = new ImageButton(skinLib.getImageButtonRoundPlay(200, G.RED));
		Image imageDark = new Image(PixmapFactory.getDrawableMonocromatic(1, 1, new Color(0.5f, 0.5f, 0.5f, 0.5f), null));
		imageDark.setFillParent(true);

		// Listeners
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y); 
				routinePlay(); 
			}});
		buttonHome.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y); 
				G.routineHome(); 
			}});


		Table table = new Table();
		table.add(buttonHome).row();
		table.add(buttonPlay).row();
		table.setFillParent(true);
		stage.addActor(imageDark);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	public boolean input(In.COMMAND cmd){
		if (In.COMMAND.ESCAPE == cmd){
			window();
			return true;
		}
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


		shapeRenderer.setProjectionMatrix(camera.combined);
		for (Ball ball : ballList){
			ball.draw(shapeRenderer, delta);
		}
		wall.draw(shapeRenderer, delta);

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

		// Pause or Step
		if (null != stage){
			stage.act();
			stage.draw();
		}
		else{
			world.step(delta, 1, 3);
		}
	}


	@Override
	public void hide() { }

	@Override
	public void pause() { } 

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void resume() { }

	@Override
	public boolean shouldCollide(Fixture arg0, Fixture arg1) {
		return true;
	}

	public void routinePlay(){
		stage.dispose();
		stage = null;
		Gdx.input.setInputProcessor(in);
	}

}
