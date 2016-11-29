package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.Texture;

/**
 * Created by tm on 20.01.2016.
 */
public class SlowBird extends Bird {
//    static private Texture texture = new Texture("bird/birdanimationslow.png");
//    static private int MIN_HEIGHT = 225;
//    static private int MOVEMENT = 30;
//    static private int birdLifes = 50;
//    static private int birdLifesMax = birdLifes;
//    static private int gold = 50;
//    private boolean isOverEdge, isDead, isTarget = false;
//    static int shield = 5;

    public SlowBird(int x, int y, AnimationBird animationBird, Texture life, Sound flap){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 30;
        super.MIN_HEIGHT = 225;
        super.birdLifes = 50;
        super.birdLifesMax = 50;
        super.gold = 50;
        super.shield = 10;
        super.fireShield = 5;
    }


}
