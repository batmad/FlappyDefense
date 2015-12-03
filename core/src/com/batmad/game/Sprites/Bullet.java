package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by tm on 03.12.2015.
 */
public class Bullet {
    private Texture bullet;
    private Vector2 position, velocity;
    private Rectangle bounds, target;


    public Bullet(float x, float y, Rectangle target){
        bullet = new Texture("ball.png");
        position = new Vector2(x, y);
        bounds = new Rectangle(x,y, bullet.getWidth(), bullet.getHeight());
        this.target = target;
        velocity = new Vector2(0,0);
    }

    public void update(float dt){
        velocity.add(target.getX(), target.getY());
        velocity.sub(position.x, position.y);
        velocity.scl(dt);
        position.add(dt * 500 * velocity.x, dt * 500 * velocity.y);
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
}
