package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by tm on 03.12.2015.
 */
public class Bullet {
//    private static final int BULLET_SPEED = 60;
//    private Texture bullet = new Texture("ball.png");
    private Texture bullet;
    protected Vector2 position, velocity;
    protected Rectangle bounds, target;
    private int damage;
    private boolean isFired;
    protected boolean isDead;
    private double range;
    protected Sound sound;
    protected int shieldPiercer = 5;
    protected int fireDamage = 0;

//    public Bullet(float x, float y, Rectangle target, int damage){
//        this.bullet = bullet;
//        position = new Vector2(x, y);
//        bounds = new Rectangle(x,y, bullet.getWidth(), bullet.getHeight());
//        velocity = new Vector2(target.getX() + target.getWidth()/2, target.getY());
//        velocity.sub(position.x, position.y);
//        //range = Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);
//        this.target = target;
//        this.damage = damage;
//        isFired = false;
//        sound = Gdx.audio.newSound(Gdx.files.internal("cannon.ogg"));
//    }

    public Bullet(float x, float y, Rectangle target, int damage, Texture bullet, Sound sound){
//        this(x, y, target, damage);
        this.bullet = bullet;
        this.sound = sound;
        position = new Vector2(x, y);
        bounds = new Rectangle(x,y, bullet.getWidth(), bullet.getHeight());
        velocity = new Vector2(target.getX() + target.getWidth()/2, target.getY());
        velocity.sub(position.x, position.y);
        this.target = target;
        this.damage = damage;
        isFired = false;
    }

    public void update(float dt){
        //velocity.add(BULLET_SPEED * velocity.x, BULLET_SPEED * velocity.y);
        // velocity.scl(dt);
        velocity.add(target.getX() + target.getWidth()/2, target.getY());
        velocity.sub(position.x, position.y);
        velocity.scl(dt);
        position.add(dt * 400 * velocity.x, dt * 400 * velocity.y);
        bounds.setPosition(position.x, position.y);
        if(Math.sqrt(Math.pow(velocity.x,2) + Math.pow(velocity.y,2)) < 0.05 ){
            isDead = true;
        }
    }

    public float angle(){
        return velocity.angle();
    }

    public Vector2 getPosition(){
        return position;
    }

    public Texture getTexture(){
        return bullet;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public boolean collides(Rectangle player){
        return player.overlaps(bounds);
    }

    public int getDamage() {
        return damage;
    }

    public int getFireDamage(){return fireDamage;}

    public int getShieldPiercer() {
        return shieldPiercer;
    }

    public boolean isFired() {
        return isFired;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsFired(boolean isFired) {
        this.isFired = isFired;
    }

    public void sound(){
        sound.play(0.7f);
    }

    public void dispose(){
        bullet.dispose();
        sound.dispose();
    }
}
