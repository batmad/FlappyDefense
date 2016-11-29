package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


/**
 * Created by tm on 19.01.2016.
 */
public class FastBird extends Bird {
//    static private Texture texture = new Texture("bird/birdanimationfast.png");
//    private int MIN_HEIGHT = 250;
//    private int MOVEMENT = 150;
//    private int birdLifes = 15;
//    private int birdLifesMax = birdLifes;
//    private int gold = 30;
//    private boolean isOverEdge, isDead, isTarget = false;

    public FastBird(int x, int y, AnimationBird animationBird, Texture life, Sound flap){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 150;
        super.MIN_HEIGHT = 250;
        super.birdLifes = 7;
        super.birdLifesMax = 7;
        super.gold = 30;
    }

}
