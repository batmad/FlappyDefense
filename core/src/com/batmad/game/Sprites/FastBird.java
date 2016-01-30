package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by tm on 19.01.2016.
 */
public class FastBird extends Bird {
    static private Texture texture = new Texture("bird/birdfullgreen.png");
    private int MIN_HEIGHT = 250;
    private int MOVEMENT = 150;
    private int birdLifes = 5;
    private int birdLifesMax = 5;
    private int gold = 30;
    private boolean isOverEdge, isDead, isTarget = false;

    public FastBird(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
    }


    @Override
    public void move() {
        if(this.getPosition().y < MIN_HEIGHT ) {
            super.jump();
        }
    }

}
