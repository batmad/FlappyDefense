package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.batmad.birddefense.core.FlappyDefense;

/**
 * Created by tm on 19.11.2015.
 */
public class Tube {
    public static final int TUBE_WIDTH = 52;
//    protected Texture topTube = new Texture("toptubeclosed.png");
//    protected Texture bottomTube = new Texture("bottomtubeclosed.png");
//    protected Texture textureDestoyedTube = new Texture("bottomtubedestroyed.png");
    protected Texture topTube;
    protected Texture bottomTube;
    protected Texture destoyedTube;
    protected Texture bulletTexture;
    protected Sound bulletSound;
    protected Vector2 posTopTube, posBotTube;
    protected Rectangle boundsTop, boundsBot, fieldOfView;
    protected int damage;
    protected int value;
    protected int upgradeDamage;
    protected int valueArrow;
    protected int damageArrow;
    protected int valueFire;
    protected int damageFire;
    protected int valueCannon;
    protected int damageCannon;
    protected int totalValue;
    protected int repairValue;
    protected int fireRate;
    protected int range;
    protected long clearSky;
    protected Rectangle target;
    protected boolean hasTarget;
    public boolean isTop;
    public boolean isBroken;

    public Tube(float x, Texture topTube, Texture bottomTube, Texture destoyedTube, Texture bullet, Sound bulletSound){
        this.topTube = topTube;
        this.bottomTube = bottomTube;
        this.destoyedTube = destoyedTube;
        this.bulletTexture = bullet;
        this.bulletSound = bulletSound;
        posTopTube = new Vector2(x, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET - topTube.getHeight());
        posBotTube = new Vector2(x, FlappyDefense.GROUND_Y_OFFSET);

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

        range = 100;
        fieldOfView = new Rectangle(x + topTube.getWidth()/2 - range / 2, 0, range, 480);

        //damage = 5;
        //fireRate = 500;
        clearSky = 0;
        value = 120;
        valueArrow = 120;
        valueCannon = 150;
        valueFire = 160;
        damageArrow = 4;
        damageCannon = 7;
        damageFire = 2;
        repairValue = 50;
        isBroken = false;
        totalValue = value;
    }

    public Tube(float x, Texture topTube, Texture bottomTube, Texture destoyedTube, Texture bullet, Sound bulletSound, boolean isTop){
        this(x, topTube, bottomTube, destoyedTube, bullet, bulletSound);
        this.isTop = isTop;
    }


//    public Tube(float x, Texture topTube, Texture bottomTube, int damage, int fireRate, int value) {
//        this(x);
//        this.topTube = topTube;
//        this.bottomTube = bottomTube;
//        this.damage = damage;
//        this.fireRate = fireRate;
//        this.value = value;
//        totalValue = value;
//        posTopTube = new Vector2(x, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET - topTube.getHeight());
//    }


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
            bullet = new Bullet(posBotTube.x + bottomTube.getWidth()/2, posTopTube.y, target, damage, bulletTexture, bulletSound);
        else
            bullet = new Bullet(posBotTube.x + bottomTube.getWidth()/2, posBotTube.y + bottomTube.getHeight(), target, damage, bulletTexture, bulletSound);
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

    public int getValueArrow() {
        return valueArrow;
    }

    public int getValueCannon() {
        return valueCannon;
    }

    public int getValueFire() {
        return valueFire;
    }

    public int getDamageArrow() {
        return damageArrow;
    }

    public int getDamageCannon() {
        return damageCannon;
    }

    public int getDamageFire() {
        return damageFire;
    }

    public int getUpgradeDamage() {
        return upgradeDamage;
    }
}
