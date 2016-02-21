package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.batmad.game.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 19.02.2016.
 */
public class BirdStupid extends Bird {
    static private Texture texture = new Texture("bird/birdanimationstupid.png");
    private int MIN_HEIGHT = 175;
    private int MOVEMENT = 20;
    private int birdLifes = 100;
    private int birdLifesMax = birdLifes;
    private int gold = 100;
    private boolean isBack = true;
    Random rand = new Random();

    public BirdStupid(int x){
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

    @Override
    public void jump(){
        velocity.y = 150;
        if (position.x > 0 && position.x < FlappyDefense.WIDTH )
            flap.play(0.05f);
    }





}
