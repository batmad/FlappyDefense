package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.batmad.birddefense.core.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 20.02.2016.
 */
public class BirdAngry extends Bird {
//    static private Texture texture = new Texture("bird/birdanimationangry.png");
//    private int MIN_HEIGHT = 225;
//    private int MOVEMENT = 70;
//    private int birdLifes = 30;
//    private int birdLifesMax = birdLifes;
//    private int gold = 40;
//    private boolean isOverEdge, isDead, isTarget = false;
    private boolean isFired = false;
    Random rand;
    private Rectangle fireRect;
    private Texture textureBullet;
    private Sound bulletSound;
    private Sound landed;
//    private int damage;
//    protected long clearSky;
//    protected int fireRate;

    public BirdAngry(int x, int y, AnimationBird animationBird, Texture life, Sound flap, Texture textureBullet, Sound bulletSound, Sound landed){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 70;
        super.MIN_HEIGHT = 225;
        super.birdLifes = 30;
        super.birdLifesMax = 30;
        super.gold = 40;

        this.textureBullet = textureBullet;
        this.bulletSound = bulletSound;
        this.landed = landed;
        rand = new Random();
        fireRect = new Rectangle(this.position.x, 0, birdAnimation.getFrameWidth(), FlappyDefense.HEIGHT / 2);
//        fireRate = 500;
//        damage = 5;
    }

    @Override
    public void update(float dt){
        if(!isOverEdge && !isDead) {
            birdAnimation.update(dt);
            velocity.scl(dt);
            position.add(dt * MOVEMENT, velocity.y, 0);
            if (position.y < 0) {
                position.y = 0;
            }
            velocity.scl(1 / dt);
            bounds.setPosition(position.x, position.y);
            fireRect.setPosition(this.position.x, 0);
            if((int)this.position.x % 50 == 0)
                flap.play(0.05f);
        }
    }

    public boolean collides(Rectangle player){
        return player.overlaps(fireRect);
    }

    public com.batmad.birddefense.core.Sprites.Bullet fire(Rectangle target){
        //return damage;
        isFired = true;
        com.batmad.birddefense.core.Sprites.Bullet bullet = new com.batmad.birddefense.core.Sprites.BulletBird(this.position.x -1, this.position.y -20, target, 0, textureBullet, bulletSound, landed);
        return bullet;
    }

//    public int getFireRate() {
//        return fireRate;
//    }
//
//    public long getClearSky() {
//        return clearSky;
//    }
//
//    public void setClearSky(long clearSky) {
//        this.clearSky = clearSky;
//    }

    public boolean isFired() {
        return isFired;
    }
}
