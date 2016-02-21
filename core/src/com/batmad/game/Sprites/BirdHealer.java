package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.batmad.game.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 19.02.2016.
 */
public class BirdHealer extends Bird {
    static private Texture texture = new Texture("bird/birdanimationhealer.png");
    private int MIN_HEIGHT = 240;
    private int MOVEMENT = 60;
    private int birdLifes = 30;
    private int birdLifesMax = birdLifes;
    private int gold = 100;
    public int heal = 10;
    private Rectangle healRect;
    private int healRate = 1000;


    public BirdHealer(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
        healRect = new Rectangle(this.position.x - 2 * texture.getWidth()/3, this.position.y - 2 * texture.getWidth()/3, 5 * texture.getWidth()/3, 5 * texture.getHeight()/3);
    }
    @Override
    public void move(){
        healRect.setPosition(this.position.x,this.position.y);
        if(position.y < MIN_HEIGHT) {
            jump();
        }
    }

    @Override
    public void jump(){
        velocity.y = 350;
        if (position.x > 0 && position.x < FlappyDefense.WIDTH )
            flap.play(0.05f);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(healRect);
    }

    public int getHealRate() {
        return healRate;
    }
}
