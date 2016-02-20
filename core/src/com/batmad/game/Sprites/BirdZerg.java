package com.batmad.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by tm on 19.02.2016.
 */
public class BirdZerg extends Bird {
    static private Texture texture = new Texture("bird/birdanimationzerg.png");
    private int MIN_HEIGHT = 250;
    private int MOVEMENT = 150;
    private int birdLifes = 10;
    private int birdLifesMax = birdLifes;
    private int gold = 30;
    private boolean isOverEdge, isDead, isTarget = false;


    public BirdZerg(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;

    }
}
