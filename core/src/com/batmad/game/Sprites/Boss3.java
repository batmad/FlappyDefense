package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by tm on 19.02.2016.
 */
public class Boss3 extends Bird {
    static private Texture texture = new Texture("bird/boss3.png");
    private int MIN_HEIGHT = 150;
    private int MOVEMENT = 30;
    private int birdLifes = 700;
    private int birdLifesMax = birdLifes;
    private int gold = 500;
    private Texture lifeTexture = new Texture("bird/bosslife.png");;

    public Boss3(int x){
        super(x, texture);
        super.MOVEMENT = MOVEMENT;
        super.birdLifes = birdLifes;
        super.gold = gold;
        super.birdLifesMax = birdLifesMax;
        super.lifeTexture = lifeTexture;
        bounds = new Rectangle(x, this.position.y, texture.getWidth()/3, texture.getHeight());
    }

    @Override
    public void move() {
        if(this.getPosition().y < MIN_HEIGHT ) {
            super.jump();
        }
    }
}
