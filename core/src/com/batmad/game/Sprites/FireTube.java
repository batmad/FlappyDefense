package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.game.FlappyDefense;

/**
 * Created by tm on 08.01.2016.
 */
public class FireTube extends Tube {
    static private Texture topTube = new Texture("toptubegrowed.png");
    static private Texture bottomTube = new Texture("bottomtubegrowed.png");
    static private int damage = 4;
    static private int fireRate = 0;
    static private int value = 160;
    static private long noDamageTime;
    Vector2 posBotTube;


    public FireTube(float x){
        super(x, topTube, bottomTube, damage, fireRate, value);
        posBotTube = new Vector2(x, - FlappyDefense.GROUND_Y_OFFSET);
    }


}
