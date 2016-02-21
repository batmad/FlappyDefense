package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.batmad.game.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 20.02.2016.
 */
public class BirdAngry extends Bird {
    static private Texture texture = new Texture("bird/birdanimationangry.png");
    private int MIN_HEIGHT = 225;
    private int MOVEMENT = 70;
    private int birdLifes = 30;
    private int birdLifesMax = birdLifes;
    private int gold = 40;
    private boolean isOverEdge, isDead, isTarget = false;
    private boolean isFired = false;
    Random rand;
    private Rectangle fireRect;
    private int damage = 5;
    protected long clearSky;
    protected int fireRate;

    public BirdAngry(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
        super.MIN_HEIGHT = MIN_HEIGHT;
        rand = new Random();
        fireRect = new Rectangle(this.position.x, 0, texture.getWidth()/3, FlappyDefense.HEIGHT / 2);
        fireRate = 500;

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

    public Bullet fire(Rectangle target){
        //return damage;
        isFired = true;
        Bullet bullet = new BulletBird(this.position.x -1, this.position.y -20, target, damage);
        return bullet;
    }

    public int getFireRate() {
        return fireRate;
    }

    public long getClearSky() {
        return clearSky;
    }

    public void setClearSky(long clearSky) {
        this.clearSky = clearSky;
    }

    public boolean isFired() {
        return isFired;
    }
}
