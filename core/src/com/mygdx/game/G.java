package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
// not html
// import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class G{
	public static MainGame game;

	public static float world2Screen;
	public static Vector2 wSize = new Vector2(10, 15);

	public static final Color BLUE = new Color(0x00bcd4ff);
	public static final Color GREEN = new Color(0xcddc39ff);
	public static final Color RED = new Color(0xe91e63ff);
	public static final Color WALL = new Color(0xbc8470ff);


	public static  void screenShot(){
		// Save on my home folder
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
		Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
		// PixmapIO.writePNG(Gdx.files.external("screenshot" + TimeUtils.millis() + ".png"), pixmap);
		pixmap.dispose();
	}



	public static void routinePlay(){
		G.game.setScreen(new GameScreen());
	}

	public static void routineHome(){
		G.game.setScreen(new MenuScreen());
	}
}
