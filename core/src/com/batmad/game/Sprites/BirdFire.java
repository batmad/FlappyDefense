package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

/**
 * Created by tm on 20.02.2016.
 */
public class BirdFire extends Bird {
    static private Texture texture = new Texture("bird/birdanimationfire.png");
    private int MIN_HEIGHT = 225;
    private int MOVEMENT = 100;
    private int birdLifes = 30;
    private int birdLifesMax = birdLifes;
    private int gold = 40;
    private boolean isOverEdge, isDead, isTarget = false;

    public BirdFire(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
        super.MIN_HEIGHT = MIN_HEIGHT;
    }
}
