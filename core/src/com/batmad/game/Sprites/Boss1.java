package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by tm on 19.02.2016.
 */
public class Boss1 extends Bird {
    static private Texture texture = new Texture("bird/boss1.png");
    private int MIN_HEIGHT = 225;
    private int MOVEMENT = 30;
    private int birdLifes = 500;
    private int birdLifesMax = birdLifes;
    private int gold = 300;
    private Texture lifeTexture = new Texture("bird/bosslife.png");;

    public Boss1(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
        super.lifeTexture = lifeTexture;
        birdAnimation = new Animation(new TextureRegion(texture), 2, 0.01f);
        bounds = new Rectangle(x, this.position.y, texture.getWidth()/2, texture.getHeight());
    }

    @Override
    public void move() {
        if(this.getPosition().y < MIN_HEIGHT ) {
            super.jump();
        }
    }
}
