/*
 *  A subset of my PixmapFactory
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public class Pix{



	public static Texture texCircle(int radius, Color color) {
		Pixmap res = new Pixmap(radius ,radius ,Format.RGBA8888);
		
		res.setColor(color);
		res.fillCircle(radius/2, radius/2, radius/2);
		
		return new Texture(res); 
	}


}
