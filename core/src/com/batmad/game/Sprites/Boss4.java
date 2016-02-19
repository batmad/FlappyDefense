package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.batmad.game.Sprites.Bird;

/**
 * Created by tm on 19.02.2016.
 */
public class Boss4 extends Bird {
    static private Texture texture = new Texture("bird/boss4.png");
    private int MIN_HEIGHT = 150;
    private int MOVEMENT = 30;
    private int birdLifes = 800;
    private int birdLifesMax = birdLifes;
    private int gold = 700;
    private Texture lifeTexture = new Texture("bird/bosslife.png");;

    public Boss4(int x){
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
