package com.batmad.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.batmad.game.FlappyDefense;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = FlappyDefense.HEIGHT;
		config.width = 800;
		config.title = FlappyDefense.TITLE;
		new LwjglApplication(new FlappyDefense(), config);
	}
}
