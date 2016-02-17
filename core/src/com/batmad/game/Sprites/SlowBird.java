package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by tm on 20.01.2016.
 */
public class SlowBird extends Bird {
    static private Texture texture = new Texture("bird/birdanimationslow.png");
    private int MIN_HEIGHT = 225;
    private int MOVEMENT = 50;
    private int birdLifes = 30;
    private int birdLifesMax = 30;
    private int gold = 40;
    private boolean isOverEdge, isDead, isTarget = false;

    public SlowBird(int x){
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
