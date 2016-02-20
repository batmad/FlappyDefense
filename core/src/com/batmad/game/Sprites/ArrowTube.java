package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.game.FlappyDefense;

/**
 * Created by tm on 22.12.2015.
 */
public class ArrowTube extends Tube{
    static private Texture topTube = new Texture("toptube.png");
    static private Texture bottomTube = new Texture("bottomtube.png");
//    Vector2 posBotTube;
    private int damage = 5;
    private int fireRate = 700;
    private int value = 120;
    private int upgradeCost = value + 50;

    public ArrowTube(float x) {
        super(x);
        super.topTube = topTube;
        super.bottomTube = bottomTube;
        super.damage = damage;
        super.fireRate = fireRate;
        super.value = value;
//        posBotTube = new Vector2(x, FlappyDefense.GROUND_Y_OFFSET);
//        posTopTube = new Vector2(x, FlappyDefense.HEIGHT);
        super.posTopTube = new Vector2(x, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET - topTube.getHeight());
        super.boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public ArrowTube(float x, boolean isTop) {
        this(x);
        super.isTop = isTop;
    }

    @Override
    public void upgrade(){
        totalValue = totalValue + upgradeCost;
        damage = damage * 2;
        upgradeCost = upgradeCost * 2;
        fireRate = fireRate - 70;
    }

    public Bullet fire(Rectangle target){
        //return damage;
        Bullet bullet;
        if(isTop)
            bullet = new ArrowBullet(posBotTube.x , posTopTube.y , target, damage);
        else
            bullet = new ArrowBullet(posBotTube.x , posBotTube.y + bottomTube.getHeight(), target, damage);
        return bullet;
    }

    @Override
    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
}
