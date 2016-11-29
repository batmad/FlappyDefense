package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.batmad.birddefense.core.FlappyDefense;

import java.util.Random;


/**
 * Created by tm on 19.11.2015.
 */
public class Bird {
    public static final int GRAVITY = -15;
    public int MOVEMENT = 100;
    protected int MIN_HEIGHT = 200;
    protected Vector3 position;
    protected Vector3 velocity;
    protected Rectangle bounds;
    protected AnimationBird birdAnimation;
//    protected Texture texture;
    protected Texture lifeTexture;
    protected Sound flap;
    protected int birdLifes = 15;
    protected int birdLifesMax = birdLifes;
    protected int gold = 20;
    protected boolean isOverEdge, isDead, isTarget = false;
    private long lastHealedTimeStamp;
    private Random rand;
    protected int shield = 0;
    protected int fireShield = 0;

    public Bird(int x, int y, AnimationBird birdAnimation, Texture lifeTexture, Sound flap){
        this.birdAnimation = birdAnimation;
        this.lifeTexture = lifeTexture;
        this.flap = flap;
        position = new Vector3(-x,y,0);
        velocity = new Vector3(0,0,0);
        bounds = new Rectangle(-x, y, birdAnimation.getFrameWidth(), birdAnimation.getFrameHeight());
        isOverEdge = false;
        rand = new Random();
    }

//    public Bird(){
//        this(-(new Random().nextInt(100)), MIN_HEIGHT - (new Random().nextInt(FlappyDefense.HEIGHT - MIN_HEIGHT*2)));
//    }

//    public Bird(int x){
//        this(-x, MIN_HEIGHT );
//    }

//    public Bird(int x, Texture texture){
//        this(x);
//        this.texture = texture;
//        birdAnimation = new AnimationBird(new TextureRegion(texture), 3,0.5f);
//    }

    public void update(float dt){
        if(!isOverEdge && !isDead) {
            birdAnimation.update(dt);
            if (position.y > 0)
                velocity.add(0, GRAVITY, 0);
            velocity.scl(dt);
            position.add(dt * MOVEMENT, velocity.y, 0);
            if (position.y < 0) {
                position.y = 0;
            }
            velocity.scl(1 / dt);
            bounds.setPosition(position.x, position.y);
            move();
        }
    }

    public Vector3 getPosition(){
        return position;
    }

    public int getBirdLifesMax() {
        return birdLifesMax;
    }

    public TextureRegion getTexture(){
        return birdAnimation.getFrame();
    }

    public Texture getLifeTexture(){
        return lifeTexture;
    }

    public int getLifePercentage(){
        return lifeTexture.getWidth() * getLifes() / birdLifesMax;
    }

    public boolean isOverEdge() {
        return isOverEdge;
    }

    public void setIsOverEdge(boolean isOverEdge) {
        this.isOverEdge = isOverEdge;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void jump(){
        velocity.y = 250 + rand.nextInt(100);
        if (position.x > 0 && position.x < FlappyDefense.WIDTH )
            flap.play(0.05f);
    }

    public void move(){
        if(position.y < MIN_HEIGHT) {
            jump();
        }
    }

    public void loseLife(int damage, int shieldPiercer, int fireDamage){
        if(fireShield > 0 ){
            fireShield = fireShield - fireDamage;
            double damageReducePercentFire = (0.06 * fireShield) / (1 + 0.06 * fireShield);
            damage = damage - (int) Math.ceil(damageReducePercentFire * damage);
        }else{
            damage += fireDamage;
        }
        if (shield > 0 ) {
            shield = shield - shieldPiercer;
            double damageReducePercent = (0.06 * shield) / (1 + 0.06 * shield);
            damage = damage - (int) Math.ceil(damageReducePercent * damage);
        } else{
            damage += shieldPiercer;
        }
        birdLifes -= damage;
    }

    public void healLife(int heal){
        birdLifes += heal;
    }

    public int getLifes() {
        return birdLifes;
    }

    public int getGold() {
        return gold;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setIsTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    public long getLastHealedTimeStamp() {
        return lastHealedTimeStamp;
    }

    public void setLastHealedTimeStamp(long lastHealedTimeStamp) {
        this.lastHealedTimeStamp = lastHealedTimeStamp;
    }

    public void dispose(){
//        texture.dispose();
        flap.dispose();
    }
}
