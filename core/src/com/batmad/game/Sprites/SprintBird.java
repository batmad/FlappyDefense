package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by tm on 21.01.2016.
 */
public class SprintBird extends Bird {
    static Texture texture = new Texture("bird/birdred.png");
    private int MIN_HEIGHT = 270;
    private int sprintMovement = 150;
    protected int birdLifes = 20;
    protected int gold = 40;

    public SprintBird(int x){
        super(x, texture);
        super.birdLifes = birdLifes;
        super.gold = gold;
    }

    @Override
    public void move() {
        if(this.getPosition().y < MIN_HEIGHT ) {
            sprint();
            super.jump();
        }
        if(this.velocity.y < 0){
            stopSprint();
        }
    }

    public void sprint(){
        super.MOVEMENT = sprintMovement;
    }

    public void stopSprint(){
        super.MOVEMENT = 50;
    }

}
