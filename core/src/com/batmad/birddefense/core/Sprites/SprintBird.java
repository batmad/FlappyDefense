package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.Texture;

/**
 * Created by tm on 21.01.2016.
 */
public class SprintBird extends Bird {
//    static Texture texture;
//    private int MIN_HEIGHT = 270;
////    private int sprintMovement = 300;
//    protected int birdLifes = 5;
//    protected int birdLifesMax = birdLifes;
//    protected int gold = 35;

    public SprintBird(int x, int y, AnimationBird animationBird, Texture life, Sound flap){
        super(x, y, animationBird, life, flap);
//        super.birdAnimation = animationBird;
        super.MIN_HEIGHT = 245;
        super.gold = 35;
        super.birdLifes = 5;
        super.birdLifesMax = 5;
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
        super.MOVEMENT = 300;
    }

    public void stopSprint(){
        super.MOVEMENT = 30;
    }

}
