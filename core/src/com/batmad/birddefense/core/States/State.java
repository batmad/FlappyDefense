package com.batmad.birddefense.core.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.batmad.birddefense.core.FlappyDefense;

import java.util.Locale;

/**
 * Created by tm on 19.11.2015.
 */
public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected FlappyDefense game;
    protected static I18NBundle myBundle;
    protected static final String FONT_CHARS =  "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    protected static final String FONT_PATH = "fonts/RussoOne-Regular.ttf";
    protected FreeTypeFontGenerator generator;
    protected FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    protected State(GameStateManager gsm, FlappyDefense game){
        this.game = game;
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
        FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle");
//        Locale locale = new Locale("en");
        myBundle = I18NBundle.createBundle(baseFileHandle);
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}

