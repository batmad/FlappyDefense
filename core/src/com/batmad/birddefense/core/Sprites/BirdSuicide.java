package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.batmad.birddefense.core.FlappyDefense;

/**
 * Created by tm on 20.02.2016.
 */
public class BirdSuicide extends Bird {

////    static private Texture texture = new Texture("bird/birdanimationsuicide.png");
      private static Sound soundLanded ;
      private static Sound sound;
//    private int MIN_HEIGHT = 225;
//    private int MOVEMENT = 30;
//    private int birdLifes = 50;
//    private int birdLifesMax = birdLifes;
//    private int gold = 40;
    private Rectangle fireRect;
    private Rectangle target;
    private boolean hasTarget = false;

    public BirdSuicide(int x, int y, AnimationBird animationBird, Texture life, Sound flap, Sound landed, Sound kamikaze){
        super(x, y, animationBird, life, flap);
        super.MOVEMENT = 30;
        super.MIN_HEIGHT = 225;
        super.birdLifes = 50;
        super.birdLifesMax = 50;
        super.gold = 40;
///       TODO
//        private static Sound soundLanded = Gdx.audio.newSound(Gdx.files.internal("landed.ogg"));
//        private static Sound sound = Gdx.audio.newSound(Gdx.files.internal("kamikaze.ogg"));
        soundLanded = landed;
        sound = kamikaze;
        fireRect = new Rectangle(this.position.x, 0, birdAnimation.getFrameWidth(), FlappyDefense.HEIGHT / 2);
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

    @Override
    public void setIsDead(boolean isDead) {
        sound.stop();
        this.isDead = isDead;
    }
}
