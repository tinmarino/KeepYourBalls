package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall{
	public Texture texture; 
	public Sprite sprite;
	public Body body;
	public float width, height;
	enum POSITION{UP, DOWN};
	public POSITION position = POSITION.DOWN;



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

	public void draw(SpriteBatch batch, float delta){
		float x =  body.getPosition().x / 10 * Gdx.graphics.getWidth() - sprite.getWidth() / 2;
		float y =  body.getPosition().y / 15 * Gdx.graphics.getHeight() - sprite.getHeight() / 2;
		sprite.setPosition(x, y);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.draw(batch);
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
		texture = new Texture("img/wall.png");
		sprite = new Sprite(texture);
		sprite.setSize(width / 10 * Gdx.graphics.getWidth(), 
				height / 15 * Gdx.graphics.getHeight());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2); // to resize and rotate around the origin, here center of the sprite
		body.setUserData(this); 
	}	
}
