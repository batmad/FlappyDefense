package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by tm on 20.02.2016.
 */
public class BulletBird extends Bullet {
//    private static Texture bullet = new Texture("missle.png");
//    private static Sound sound = Gdx.audio.newSound(Gdx.files.internal("incoming.ogg"));
//    private static Sound soundLanded = Gdx.audio.newSound(Gdx.files.internal("landed.ogg"));
    private Sound soundLanded;


    public BulletBird(float x, float y, Rectangle target, int damage, Texture bullet, Sound sound, Sound landed) {
        super(x, y, target, damage, bullet, sound);
        soundLanded = landed;
    }


    @Override
    public void update(float dt){
        //velocity.add(BULLET_SPEED * velocity.x, BULLET_SPEED * velocity.y);
        // velocity.scl(dt);
        velocity.add(target.getX() + target.getWidth()/2, target.getY());
        velocity.sub(position.x, position.y);
        velocity.scl(dt);
        position.add(dt * 100 * velocity.x, dt * 100 * velocity.y);
        bounds.setPosition(position.x, position.y);
        if(Math.sqrt(Math.pow(velocity.x,2) + Math.pow(velocity.y,2)) < 0.05 ){
            isDead = true;
        }
    }

    public void playLanded(){
        sound.stop();
        soundLanded.play();
    }
}
