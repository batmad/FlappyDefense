package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.game.FlappyDefense;

/**
 * Created by tm on 22.12.2015.
 */
public class ArrowTube extends Tube{
    static private Texture topTube = new Texture("toptubegrowed.png");
    static private Texture bottomTube = new Texture("bottomtubegrowed.png");
    Vector2 posTopTube, posBotTube;
    static private int damage = 5;
    static private int fireRate = 500;
    static private int value = 120;

    public ArrowTube(float x) {
        super(x, topTube, bottomTube, damage, fireRate, value);
        posBotTube = new Vector2(x, FlappyDefense.GROUND_Y_OFFSET);
        posTopTube = new Vector2(x, FlappyDefense.HEIGHT);
    }

    public ArrowTube(float x, boolean isTop) {
        this(x);
        super.isTop = isTop;
    }

    public Bullet fire(Rectangle target){
        //return damage;
        Bullet bullet;
        if(isTop)
            bullet = new ArrowBullet(posBotTube.x , posTopTube.y - bottomTube.getHeight(), target, damage);
        else
            bullet = new ArrowBullet(posBotTube.x , posBotTube.y + bottomTube.getHeight(), target, damage);
        return bullet;
    }
}
