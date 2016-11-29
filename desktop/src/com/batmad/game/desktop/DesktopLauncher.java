package com.batmad.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.batmad.birddefense.core.FlappyDefense;
import com.batmad.birddefense.core.PlayServices;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = FlappyDefense.HEIGHT;
		config.width = FlappyDefense.WIDTH;
		config.title = FlappyDefense.TITLE;
		new LwjglApplication(new FlappyDefense(new PlayServices() {
			@Override
			public void signIn() {

			}

			@Override
			public void signOut() {

			}

			@Override
			public void rateGame() {

			}

			@Override
			public void unlockAchievement(String str) {

			}

			@Override
			public void submitScore(int highScore) {

			}

			@Override
			public void showAchievement() {

			}

			@Override
			public void showScore() {

			}

			@Override
			public boolean isSignedIn() {
				return false;
			}

			@Override
			public void showOrLoadInterstital() {

			}
		}), config);
	}
}
