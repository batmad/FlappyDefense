package com.batmad.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.game.FlappyDefense;

import java.util.Random;

/**
 * Created by tm on 19.11.2015.
 */
public class Tube {
    public static final int TUBE_WIDTH = 52;
    protected Texture topTube = new Texture("toptubeclosed.png");
    protected Texture bottomTube = new Texture("bottomtubeclosed.png");
    protected Texture destoyedTube = new Texture("bottomtubedestroyed.png");
    protected Vector2 posTopTube, posBotTube;
    protected Rectangle boundsTop, boundsBot, fieldOfView;
    protected int damage;
    protected int value;
    protected int totalValue;
    protected int repairValue;
    protected int fireRate;
    protected long clearSky;
    protected Rectangle target;
    protected boolean hasTarget;
    public boolean isTop;
    public boolean isBroken;




    public Tube(float x){
        posTopTube = new Vector2(x, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET - topTube.getHeight());
        posBotTube = new Vector2(x, FlappyDefense.GROUND_Y_OFFSET);

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

        fieldOfView = new Rectangle(x + topTube.getWidth()/2 - 50, posBotTube.y, 100, 480);

        //damage = 5;
        //fireRate = 500;
        clearSky = 0;
        value = 120;
        repairValue = 50;
        isBroken = false;
        totalValue = value;
    }

    public Tube(float x, boolean isTop){
        this(x);
        this.isTop = isTop;
    }


    public Tube(float x, Texture topTube, Texture bottomTube, int damage, int fireRate, int value) {
        this(x);
        this.topTube = topTube;
        this.bottomTube = bottomTube;
        this.damage = damage;
        this.fireRate = fireRate;
        this.value = value;
        totalValue = value;
        posTopTube = new Vector2(x, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET - topTube.getHeight());
    }


    public Rectangle getFieldOfView() {
        return fieldOfView;
    }

    public Texture getTopTube() {
        return topTube;
    }


    public Texture getBottomTube() {
        if(isTop)
            return topTube;
        else
            return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        if(isTop)
            return posTopTube;
        else
            return posBotTube;
    }

    public Rectangle getBoundsBot() {
        if(isTop)
            return boundsTop;
        else
            return boundsBot;
    }

    public Rectangle getBoundsTop() {
        return boundsTop;
    }

    public int getFireRate() {
        return fireRate;
    }

    public long getClearSky() {
        return clearSky;
    }

    public void setClearSky(long clearSky) {
        this.clearSky = clearSky;
    }

    public int getValue() {
        return value;
    }

    public boolean collides(Rectangle player){
        return player.overlaps(fieldOfView);
    }

    public Bullet fire(Rectangle target){
        //return damage;
        Bullet bullet;
        if(isTop)
            bullet = new Bullet(posBotTube.x + bottomTube.getWidth()/2, posTopTube.y, target, damage);
        else
            bullet = new Bullet(posBotTube.x + bottomTube.getWidth()/2, posBotTube.y + bottomTube.getHeight(), target, damage);
        return bullet;
    }

    public int getDamage(){
        return damage;
    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
        destoyedTube.dispose();
    }

    public void upgrade(){
        //NOP
    }

    public void repair(){
        isBroken = false;
    }

    public Texture getDestoyedTube() {
        return destoyedTube;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public int getUpgradeCost(){
        return 0;
    }

    public Rectangle getTarget() {
        return target;
    }

    public void setTarget(Rectangle target) {
        this.target = target;
    }

    public boolean isHasTarget() {
        return hasTarget;
    }

    public void setHasTarget(boolean hasTarget) {
        this.hasTarget = hasTarget;
    }

    public int getRepairValue() {
        return repairValue;
    }

}
