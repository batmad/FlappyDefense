package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.batmad.birddefense.core.FlappyDefense;


/**
 * Created by tm on 19.02.2016.
 */
public class BirdHealer extends Bird {
//    static private Texture texture = new Texture("bird/birdanimationhealer.png");
//    private int MIN_HEIGHT = 240;
//    private int MOVEMENT = 60;
//    private int birdLifes = 30;
//    private int birdLifesMax = birdLifes;
//    private int gold = 100;
    static public int heal;
    static private int healRate;
    private Rectangle healRect;


    public BirdHealer(int x, int y, AnimationBird animationBird, Texture life, Sound flap){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 60;
        super.MIN_HEIGHT = 240;
        super.birdLifes = 30;
        super.birdLifesMax = 30;
        super.gold = 100;
        heal = 5;
        healRate = 1500;
        healRect = new Rectangle(this.position.x - 2 * animationBird.getFrameWidth(), this.position.y - 2 * animationBird.getFrameWidth(), 5 * animationBird.getFrameHeight(), 5 * animationBird.getFrameHeight());
    }

    @Override
    public void move(){
        healRect.setPosition(this.position.x,this.position.y);
        if(position.y < MIN_HEIGHT) {
            jump();
        }
    }

    public boolean collides(Rectangle player){
        return player.overlaps(healRect);
    }

    public int getHealRate() {
        return healRate;
    }
}
