package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.batmad.birddefense.core.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 19.02.2016.
 */
public class BirdStupid extends Bird {
//    static private Texture texture = new Texture("bird/birdanimationstupid.png");
//    private int MIN_HEIGHT = 175;
////    private int MOVEMENT = 20;
//    private int birdLifes = 100;
//    private int birdLifesMax = birdLifes;
//    private int gold = 100;
//    private boolean isBack = true;
//    Random rand = new Random();

    public BirdStupid(int x, int y, AnimationBird animationBird, Texture life, Sound flap){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 30;
        super.MIN_HEIGHT = 175;
        super.birdLifes = 100;
        super.birdLifesMax = 100;
        super.gold = 100;
        super.shield = 20;
        super.fireShield = 10;
    }
}
