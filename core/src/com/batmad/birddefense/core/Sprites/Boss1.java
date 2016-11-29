package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by tm on 19.02.2016.
 */
public class Boss1 extends Bird {
//    static private Texture texture = new Texture("bird/boss1.png");
//    private int MIN_HEIGHT = 225;
//    private int MOVEMENT = 30;
//    private int birdLifes = 400;
//    private int birdLifesMax = birdLifes;
//    private int gold = 300;
//    private Texture lifeTexture = new Texture("bird/bosslife.png");;

    public Boss1(int x, int y, AnimationBird animationBird, Texture life, Sound flap){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 30;
        super.MIN_HEIGHT = 225;
        super.birdLifes = 400;
        super.birdLifesMax = 400;
        super.gold = 300;
//        birdAnimation = new AnimationBird(new TextureRegion(texture), 2, 0.01f);
//        bounds = new Rectangle(x, this.position.y, texture.getWidth()/2, texture.getHeight());
    }


}
