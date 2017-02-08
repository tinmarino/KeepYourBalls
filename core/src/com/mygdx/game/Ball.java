package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	public void draw(SpriteBatch batch, float delta){
		float x =  body.getPosition().x / 10 * Gdx.graphics.getWidth() - sprite.getWidth() / 2;
		float y =  body.getPosition().y / 15 * Gdx.graphics.getHeight() - sprite.getHeight() / 2;
		sprite.setPosition(x, y);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.draw(batch);
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
}
