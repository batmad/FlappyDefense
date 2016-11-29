package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by tm on 19.02.2016.
 */
public class Boss2 extends Bird {
//    static private Texture texture = new Texture("bird/boss2.png");
//    private int MIN_HEIGHT = 150;
//    private int MOVEMENT = 30;
//    private int birdLifes = 800;
//    private int birdLifesMax = birdLifes;
//    private int gold = 400;
//    private Texture lifeTexture = new Texture("bird/bosslife.png");;

    public Boss2(int x, int y, AnimationBird animationBird, Texture life, Sound flap){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 30;
        super.MIN_HEIGHT = 150;
        super.birdLifes = 700;
        super.birdLifesMax = 700;
        super.gold = 400;
//        bounds = new Rectangle(x, this.position.y, texture.getWidth()/3, texture.getHeight());
    }

}
