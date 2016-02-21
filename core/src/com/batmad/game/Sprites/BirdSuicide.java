package com.batmad.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.batmad.game.FlappyDefense;

/**
 * Created by tm on 20.02.2016.
 */
public class BirdSuicide extends Bird {
    //TODO logic of attack
    static private Texture texture = new Texture("bird/birdanimationsuicide.png");
    private static Sound soundLanded = Gdx.audio.newSound(Gdx.files.internal("landed.ogg"));
    private static Sound sound = Gdx.audio.newSound(Gdx.files.internal("kamikaze.ogg"));
    private int MIN_HEIGHT = 225;
    private int MOVEMENT = 30;
    private int birdLifes = 50;
    private int birdLifesMax = birdLifes;
    private int gold = 40;
    private Rectangle fireRect;
    private Rectangle target;
    private boolean hasTarget = false;

    public BirdSuicide(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
        fireRect = new Rectangle(this.position.x, 0, texture.getWidth()/3, FlappyDefense.HEIGHT / 2);
    }


    @Override
    public void move() {
        if(this.getPosition().y < MIN_HEIGHT ) {
            super.jump();
        }
    }

    public boolean inAttackRange(Rectangle player){
        return player.overlaps(fireRect);
    }
    public boolean collides(Rectangle player){
        return player.overlaps(this.getBounds());
    }

    @Override
    public void update(float dt){
        if(!isOverEdge && !isDead) {
            if(hasTarget){
                velocity.add(target.getX() + target.getWidth()/2, target.getY(),0);
                velocity.sub(position.x, position.y,0);
                velocity.scl(dt);
                position.add(dt * 100 * velocity.x, dt * 100 * velocity.y,0);
                bounds.setPosition(position.x, position.y);
                if(Math.sqrt(Math.pow(velocity.x,2) + Math.pow(velocity.y,2)) < 0.05 ){
                    isDead = true;
                }
            }else {
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
                fireRect.setPosition(position.x,0);
                move();
            }
        }
    }

    public boolean isHasTarget() {
        return hasTarget;
    }

    public void setTarget(Rectangle target) {
        sound.play();
        this.target = target;
        hasTarget = true;
    }

    public void playLanded(){
        sound.stop();
        soundLanded.play();
    }
}
