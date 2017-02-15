package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball{

	public Body body;
	private Texture texture;
	public 	Sprite sprite;
	public float radius = 1.5f;
	private float x, y;
	



	public void act(float delta){
		x =  body.getPosition().x / 10 * Gdx.graphics.getWidth();
		y =  body.getPosition().y / 15 * Gdx.graphics.getHeight();
	}

	public void draw(SpriteBatch batch, float delta){
		act(delta);
		sprite.setPosition(x  - sprite.getHeight() / 2, y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.draw(batch);
	}

	public void draw(ShapeRenderer shapeRenderer, float delta){
		act(delta);
		shapeRenderer.setColor(G.RED);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.circle(x, y, radius * Gdx.graphics.getWidth() / 10);
		shapeRenderer.end();
	}


	public Ball(World world){
		// body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 0);

		// BodyShape 
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);

		// BodyFixture 
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 1;
		fixtureDef.friction = 0;

		// Create Body 
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);

		// Add Body Sprite 
		texture = new Texture("img/ball.png");
		sprite = new Sprite(texture);
		sprite.setSize( radius * 2 / 10 * Gdx.graphics.getWidth(), 
			radius * 2 / 10 * Gdx.graphics.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		body.setUserData(this); 
	}

	public void dispose(){
		
	}
}
