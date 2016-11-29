package com.batmad.birddefense.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.batmad.birddefense.core.States.MenuState;

public class FlappyDefense extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final int GROUND_Y_OFFSET = 62;
	public static PlayServices playServices;
	public static IabServices iabServices;

	public static final String TITLE = "Bird Defense";
	private com.batmad.birddefense.core.States.GameStateManager gsm;
	private SpriteBatch batch;
	private Music music;

	public FlappyDefense(PlayServices playServices){
		this.playServices = playServices;
		this.iabServices = (IabServices)playServices;
	}

	@Override
	public void create () {
		Preferences prefs = Gdx.app.getPreferences("myPrefs");
//		prefs.putInteger("levels",14);
//		prefs.flush();
//		prefs.remove("levels");
		batch = new SpriteBatch();
		gsm = new com.batmad.birddefense.core.States.GameStateManager();
//		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
//		music.setLooping(true);
//		music.setVolume(0.1f);
//		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm, this));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();

		//music.dispose();
	}
}
