package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by tm on 06.01.2016.
 */
public class Fire {
    ParticleEffect fire;
    ParticleEmitter emitter;
    Rectangle target;
    Vector2 velocity;
    float x, y;
    int damage;
    boolean isFired;
    int noDamageTime;
    int shieldPiercer = 0;
    int fireDamage = 1;

    public Fire(float x, float y, int damage){
        this.x = x;
        this.y = y;
        this.damage = damage;
        noDamageTime = 350;

        fire = new ParticleEffect();
        fire.load(Gdx.files.internal("fireFD"), Gdx.files.internal(""));
        fire.setPosition(x, y);
        emitter = fire.getEmitters().first();
        emitter.getScale().setHigh(5, 20);
        velocity = new Vector2(x,y);
        velocity.sub(x, y);
        //stop();
    }

    public void start(){
        //emitter.durationTimer = 3000;
        emitter.setContinuous(true);
    }

    public void stop(float dt){
        //emitter.durationTimer = 0;
        fire.update(dt);
        emitter.setContinuous(false);

    }

    public void setTarget(Rectangle target){
        this.target = target;
    }

    public void update(float dt){
        fire.update(dt);
        velocity.add(target.getX() + target.getWidth()/2, target.getY());
        velocity.sub(x,y);
        velocity.scl(dt);
        emitter.getAngle().setHigh(angle());
        emitter.getAngle().setLow(angle());
    }

    public int getShieldPiercer(){
        return shieldPiercer;
    }

    public int getFireDamage(){
        return fireDamage;
    }

    public int getDamage() {
        return damage;
    }

    public ParticleEffect getFire() {
        return fire;
    }

    public boolean isFired() {
        return isFired;
    }

    public void setIsFired(boolean isFiring) {
        this.isFired = isFiring;
    }

    public float angle(){
        return velocity.angle();
    }

    public int getNoDamageTime() {
        return noDamageTime;
    }
}
