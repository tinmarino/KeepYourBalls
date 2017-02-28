package com.mygdx.game;

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



	public boolean input(In.COMMAND cmd){
		switch (position){
		case DOWN:
			body.setTransform(0, -G.wSize.y / 2, 0);
			position = POSITION.UP; 
			break;
		case UP:
			body.setTransform(0, G.wSize.y / 2, 0);
			position = POSITION.DOWN; 
			break;
		default:
			break;
		}
		return true;

	}

	public void draw(ShapeRenderer shapeRenderer, float delta){
		shapeRenderer.setColor(G.WALL);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(-0.5f * G.wSize.x * G.world2Screen, 
				(body.getPosition().y - height/2) * G.world2Screen, 
				G.wSize.x * G.world2Screen, 
				height * G.world2Screen);
		shapeRenderer.end();
	}


	public Wall(World world){
		init(world);
		input(In.COMMAND.TAP);
	}

	public void init(World world){
		width = G.wSize.x;
		height = G.wSize.y / 10f;

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
