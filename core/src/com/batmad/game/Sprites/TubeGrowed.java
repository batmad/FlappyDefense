package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.game.FlappyDefense;

import javax.xml.soap.Text;


/**
 * Created by tm on 12.12.2015.
 */
public class TubeGrowed extends Tube {
    static private Texture topTube = new Texture("toptubegrowed.png");
    static private Texture bottomTube = new Texture("bottomtubegrowed.png");
    Vector2 posTopTube, posBotTube;
    Rectangle boundsBot, boundsTop, fieldOfView;
    static private int damage = 10;
    static private int fireRate = 1500;
    static private int value = 150;
    private int upgradeCost = 110;

    public TubeGrowed(float x) {
        super(x, topTube, bottomTube, damage, fireRate, value);
    }
    public TubeGrowed(float x, boolean isTop) {
        super(x, topTube, bottomTube, damage, fireRate, value);
        super.isTop = isTop;
    }

    @Override
    public int getUpgradeCost() {
        return upgradeCost;
    }

    @Override
    public void upgrade(){
        totalValue = totalValue + upgradeCost;
        damage = damage * 2;
        upgradeCost = upgradeCost * 2;
        fireRate = fireRate - 150;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }


}
