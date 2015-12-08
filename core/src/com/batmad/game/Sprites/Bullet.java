package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by tm on 03.12.2015.
 */
public class Bullet {
    private static final int BULLET_SPEED = 60;
    private Texture bullet;
    private Vector2 position, velocity;
    private Rectangle bounds, target;
    private int damage;
    private boolean isFired;
    private double range;


    public Bullet(float x, float y, Rectangle target, int damage){
        bullet = new Texture("ball.png");
        position = new Vector2(x, y);
        bounds = new Rectangle(x,y, bullet.getWidth(), bullet.getHeight());
        velocity = new Vector2(target.getX(), target.getY());
        velocity.sub(position.x, position.y);
        //range = Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);
        this.target = target;
        this.damage = damage;
        isFired = false;
    }

    public void update(float dt){
        //velocity.add(BULLET_SPEED * velocity.x, BULLET_SPEED * velocity.y);
        // velocity.scl(dt);
        position.add(dt * velocity.x, dt  * velocity.y);
        bounds.setPosition(position.x, position.y);
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

    public boolean isFired() {
        return isFired;
    }

    public void setIsFired(boolean isFired) {
        this.isFired = isFired;
    }
}
