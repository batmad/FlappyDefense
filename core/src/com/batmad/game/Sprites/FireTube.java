package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.game.FlappyDefense;

/**
 * Created by tm on 08.01.2016.
 */
public class FireTube extends Tube {
    static private Texture topTube = new Texture("toptube.png");
    static private Texture bottomTube = new Texture("bottomtube.png");
    static private int damage = 4;
    static private int fireRate = 0;
    static private int value = 160;
    static private long noDamageTime;
    private int upgradeCost = value + 50;
//    Vector2 posBotTube;


    public FireTube(float x){
        super(x);
        super.topTube = topTube;
        super.bottomTube = bottomTube;
        super.damage = damage;
        super.fireRate = fireRate;
        super.value = value;
        posBotTube = new Vector2(x, FlappyDefense.GROUND_Y_OFFSET);
        super.posTopTube = new Vector2(x, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET - topTube.getHeight());
        super.boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        super.boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
    }

    public FireTube(float x, boolean isTop){
        this(x);
        posBotTube = new Vector2(x, FlappyDefense.GROUND_Y_OFFSET);
        super.isTop = isTop;
    }

    @Override
    public void upgrade(){
        totalValue = totalValue + upgradeCost;
        damage = damage * 2;
        upgradeCost = upgradeCost * 2;
        totalValue = totalValue + upgradeCost;
    }

    @Override
    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
}
