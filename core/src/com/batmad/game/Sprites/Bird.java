package com.batmad.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.batmad.game.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 19.11.2015.
 */
public class Bird {
    public static final int GRAVITY = -15;
    public static final int MOVEMENT = 100;
    private static final int MIN_HEIGHT = 200;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture texture;
    private Sound flap;
    private int birdY;

    public Bird(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth()/3, texture.getHeight()/3);
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public Bird(){
        Random rand = new Random();
        int y = MIN_HEIGHT + rand.nextInt(FlappyDefense.HEIGHT - MIN_HEIGHT);
        position = new Vector3(-rand.nextInt(100),y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(0, y, texture.getWidth()/3, texture.getHeight()/3);
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt){
        birdAnimation.update(dt);
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(dt * MOVEMENT, velocity.y, 0);
        if(position.y < 0){
            position.y =0;
        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
        move();
    }

    public Vector3 getPosition(){
        return position;
    }

    public TextureRegion getTexture(){
        return birdAnimation.getFrame();
    }

    public void jump(){
        Random rand = new Random();
        velocity.y = 250 + rand.nextInt(100);

    }

    public void move(){
        Random rand = new Random();
        if(position.y < MIN_HEIGHT) {
            jump();
        }
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
        flap.dispose();
    }
}
