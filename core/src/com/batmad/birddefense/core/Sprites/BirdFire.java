package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by tm on 20.02.2016.
 */
public class BirdFire extends Bird {
//    static private Texture texture = new Texture("bird/birdanimationfire.png");
//    private int MIN_HEIGHT = 225;
//    private int MOVEMENT = 100;
//    private int birdLifes = 30;
//    private int birdLifesMax = birdLifes;
//    private int gold = 40;
//    private boolean isOverEdge, isDead, isTarget = false;

    public BirdFire(int x, int y, AnimationBird animationBird, Texture life, Sound flap){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 100;
        super.MIN_HEIGHT = 225;
        super.birdLifes = 30;
        super.birdLifesMax = 30;
        super.gold = 40;
    }
}
