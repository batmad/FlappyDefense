package com.batmad.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by tm on 22.12.2015.
 */
public class ArrowBullet extends Bullet{
    private static Texture bullet = new Texture("arrow.png");
    private static Sound sound = Gdx.audio.newSound(Gdx.files.internal("arrow.wav"));

    public ArrowBullet(float x, float y, Rectangle target, int damage) {
        super(x, y, target, damage, bullet, sound);
    }


}
