package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall{
	public Texture texture; 
	public Body body;
	public float width, height;
	enum POSITION{UP, DOWN};
	public POSITION position = POSITION.DOWN;
	private float x, y;



	public boolean input(In.COMMAND cmd){
		switch (position){
		case DOWN:
			body.setTransform(0, -15 / 2, 0);
			position = POSITION.UP; 
			break;
		case UP:
			body.setTransform(0, 15 / 2, 0);
			position = POSITION.DOWN; 
			break;
		default:
			break;
		}
		return true;

	}

	public void act(float delta){
		x =  body.getPosition().x / 10 * Gdx.graphics.getWidth();
		y =  body.getPosition().y / 15 * Gdx.graphics.getHeight();
	}

	public void draw(ShapeRenderer shapeRenderer, float delta){
		act(delta);
		shapeRenderer.setColor(G.WALL);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(-Gdx.graphics.getWidth()/2, y - Gdx.graphics.getHeight()/40f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/20f);
		shapeRenderer.end();
	}


	public Wall(World world){
		init(world);
		input(In.COMMAND.TAP);
	}

	public void init(World world){
		width = 10;
		height = 15 / 20f;

		// body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		body = world.createBody(bodyDef);

		// FixtureShape 
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2, height/2);		
		
		// BodyFixture 
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.restitution = 1.2f;
		fixture.friction = 0;
		body.createFixture(fixture);

		// Add Body Sprite 
		body.setUserData(this); 
	}	
}
