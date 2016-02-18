package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by tm on 21.01.2016.
 */
public class SprintBird extends Bird {
    static Texture texture = new Texture("bird/birdanimationsprint.png");
    private int MIN_HEIGHT = 270;
    private int sprintMovement = 300;
    protected int birdLifes = 5;
    protected int birdLifesMax = birdLifes;
    protected int gold = 40;

    public SprintBird(int x){
        super(x, texture);
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
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
