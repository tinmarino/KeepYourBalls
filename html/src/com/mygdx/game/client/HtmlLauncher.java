package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.game.MainGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
				int height = 750;
				int width = 500;
                return new GwtApplicationConfiguration(width, height);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new MainGame();
        }
}
