package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.batmad.birddefense.core.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 20.02.2016.
 */
public class BirdTeleport extends Bird {
//    static private Texture texture = new Texture("bird/birdanimationteleport.png");
//    private int MIN_HEIGHT = 240;
//    private int MOVEMENT = 45;
//    private int birdLifes = 20;
//    private int birdLifesMax = birdLifes;
//    private int gold = 100;
    Random rand;
    private Sound teleportSound;

    public BirdTeleport(int x, int y, AnimationBird animationBird, Texture life, Sound flap, Sound teleport){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 45;
        super.MIN_HEIGHT = 240;
        super.birdLifes = 20;
        super.birdLifesMax = 20;
        super.gold = 100;

        rand = new Random();
        //TODO
        teleportSound = Gdx.audio.newSound(Gdx.files.internal("teleport.ogg"));
        teleportSound = teleport;
    }

    @Override
    public void move(){
        if(position.y < MIN_HEIGHT) {
            teleport();
            jump();
        }
    }

    public void teleport(){
        if(rand.nextInt(100) < 30) {
            this.position.x += 65;
            teleportSound.play(0.05f);
        }
    }
}
