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
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    public static final int TUBE_WIDTH = 52;
    private Texture topTube, bottomTube, topTubeGrowed, bottomTubeGrowed;
    private Vector2 posTopTube, posBotTube, posTopTubeGrowed, posBotTubeGrowed;
    private Rectangle boundsTop, boundsBot, fieldOfView;
    private Random rand;
    private boolean isGrowed;
    private int damage;
    private int fireRate;
    private long clearSky;




    public Tube(float x){
        topTube = new Texture("toptubeclosed.png");
        bottomTube = new Texture("bottomtubeclosed.png");
        topTubeGrowed = new Texture("toptubegrowed.png");
        bottomTubeGrowed = new Texture("bottomtubegrowed.png");

        rand = new Random();

        posTopTube = new Vector2(x, FlappyDefense.HEIGHT - topTube.getHeight());
        posBotTube = new Vector2(x, - FlappyDefense.GROUND_Y_OFFSET);
        posTopTubeGrowed = new Vector2(x, FlappyDefense.HEIGHT - topTubeGrowed.getHeight());
        posBotTubeGrowed = new Vector2(x, -FlappyDefense.GROUND_Y_OFFSET );

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

        fieldOfView = new Rectangle(x + topTube.getWidth()/2 - 50, posBotTube.y, 100, 480);

        damage = 5;
        fireRate = 500;
        clearSky = 0;

    }

    public Rectangle getFieldOfView() {
        return fieldOfView;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getTopTubeGrowed() {
        return topTubeGrowed;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Texture getBottomTubeGrowed() {
        return bottomTubeGrowed;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Vector2 getPosTopTubeGrowed() {
        return posTopTubeGrowed;
    }

    public Vector2 getPosBottomTubeGrowed() {
        return posBotTubeGrowed;
    }

    public Rectangle getBoundsBot() {
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

    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(fieldOfView);
    }

    public Bullet fire(Rectangle target){
        //return damage;
        Bullet bullet = new Bullet(posTopTubeGrowed.x + topTubeGrowed.getWidth()/2, posTopTubeGrowed.y, target, damage);
        return bullet;
    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }
}
