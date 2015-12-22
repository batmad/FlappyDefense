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
    static private int damage = 2;

    public ArrowTube(float x) {
        super(x, topTube, bottomTube, damage);
        posBotTube = new Vector2(x, - FlappyDefense.GROUND_Y_OFFSET);
    }

    public Bullet fire(Rectangle target){
        //return damage;
        Bullet bullet = new ArrowBullet(posBotTube.x , posBotTube.y + bottomTube.getHeight(), target, damage);
        return bullet;
    }
}
