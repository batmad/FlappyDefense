package com.batmad.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.batmad.game.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 20.02.2016.
 */
public class BirdTeleport extends Bird {
    static private Texture texture = new Texture("bird/birdanimationteleport.png");
    private int MIN_HEIGHT = 240;
    private int MOVEMENT = 45;
    private int birdLifes = 20;
    private int birdLifesMax = birdLifes;
    private int gold = 100;
    Random rand;
    private Sound teleportSound;

    public BirdTeleport(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
        rand = new Random();
        teleportSound = Gdx.audio.newSound(Gdx.files.internal("teleport.ogg"));
    }

    @Override
    public void move(){
        if(position.y < MIN_HEIGHT) {
            teleport();
            jump();
        }
    }

    @Override
    public void jump(){
        velocity.y = 350;
        if (position.x > 0 && position.x < FlappyDefense.WIDTH )
            flap.play(0.05f);
    }
    public void teleport(){
        if(rand.nextInt(100) < 30) {
            this.position.x += 50;
            teleportSound.play(0.05f);
        }
    }
}
