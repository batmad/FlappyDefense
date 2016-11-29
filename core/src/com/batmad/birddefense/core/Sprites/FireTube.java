package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.birddefense.core.FlappyDefense;

/**
 * Created by tm on 08.01.2016.
 */
public class FireTube extends Tube {
    static private Texture topTube = new Texture("toptube.png");
    static private Texture bottomTube = new Texture("bottomtube.png");
    private int damage = damageFire;
    private int fireRate = 0;
    private int value = super.valueFire;
    static private long noDamageTime;
    private int upgradeCost = value + 50;
//    Vector2 posBotTube;


    public FireTube(float x, Texture topTube, Texture bottomTube, Texture destoyedTube, Texture bullet, Sound bulletSound){
        super(x,topTube, bottomTube, destoyedTube, bullet, bulletSound);
        super.topTube = topTube;
        super.bottomTube = bottomTube;
        super.damage = damage;
        super.upgradeDamage = damage + 1;
        super.fireRate = fireRate;
        super.value = value;
        posBotTube = new Vector2(x, FlappyDefense.GROUND_Y_OFFSET);
        super.posTopTube = new Vector2(x, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET - topTube.getHeight());
        super.boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        super.boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        super.range = 120;
        super.fieldOfView = new Rectangle(x + topTube.getWidth()/2 - range / 2, 0, range, 480);
    }

    public FireTube(float x, Texture topTube, Texture bottomTube, Texture destoyedTube, Texture bullet, Sound bulletSound, boolean isTop){
        this(x,topTube, bottomTube, destoyedTube, bullet, bulletSound);
        posBotTube = new Vector2(x, FlappyDefense.GROUND_Y_OFFSET);
        super.isTop = isTop;
    }

    @Override
    public void upgrade(){
        totalValue = totalValue + upgradeCost;
        damage = damage + 1;
        upgradeDamage = upgradeDamage + 1;
        upgradeCost = upgradeCost + value + 25;
        range = range + 7;
    }

    public int getUpgradeDamage() {
        return upgradeDamage;
    }

    @Override
    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
}
