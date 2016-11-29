package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.birddefense.core.FlappyDefense;


/**
 * Created by tm on 12.12.2015.
 */
public class TubeGrowed extends Tube {
//    static private Texture topTube = new Texture("toptube.png");
//    static private Texture bottomTube = new Texture("bottomtube.png");
//    Vector2 posTopTube, posBotTube;
    Rectangle boundsBot, boundsTop, fieldOfView;
    private int damage = damageCannon;
    private int fireRate = 1100;
    private int value = super.valueCannon;
    private int upgradeCost = value + 50;

    public TubeGrowed(float x, Texture topTube, Texture bottomTube, Texture destoyedTube, Texture bullet, Sound bulletSound) {
        super(x,topTube, bottomTube, destoyedTube, bullet, bulletSound);
//        super.topTube = topTube;
//        super.bottomTube = bottomTube;
        super.damage = damage;
        super.upgradeDamage = damage + 1;
        super.fireRate = fireRate;
        super.value = value;
        super.posTopTube = new Vector2(x, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET - topTube.getHeight());
        super.boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        super.boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        super.range = 140;
        super.fieldOfView = new Rectangle(x + topTube.getWidth()/2 - range / 2, 0, range, 480);
    }

    public TubeGrowed(float x, Texture topTube, Texture bottomTube, Texture destoyedTube, Texture bullet, Sound bulletSound, boolean isTop) {
        this(x,topTube, bottomTube, destoyedTube, bullet, bulletSound);
        super.isTop = isTop;
    }

    @Override
    public int getUpgradeCost() {
        return upgradeCost;
    }

    @Override
    public void upgrade(){
        totalValue = totalValue + upgradeCost;
        damage = damage + 1;
        upgradeDamage = upgradeDamage + 1;
        upgradeCost = upgradeCost + value + 25;
        fireRate = fireRate - 150;
        range = range + 10;
    }


    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }


}
