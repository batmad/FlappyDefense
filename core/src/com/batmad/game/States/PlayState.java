package com.batmad.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.batmad.game.FlappyDefense;
import com.batmad.game.Sprites.ArrowTube;
import com.batmad.game.Sprites.Bird;
import com.batmad.game.Sprites.Bullet;
import com.batmad.game.Sprites.FastBird;
import com.batmad.game.Sprites.Fire;
import com.batmad.game.Sprites.FireTube;
import com.batmad.game.Sprites.SlowBird;
import com.batmad.game.Sprites.SprintBird;
import com.batmad.game.Sprites.Tube;
import com.batmad.game.Sprites.TubeGrowed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tm on 19.11.2015.
 */
public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static  int groundHeight;

    private PlayStateOptions options;
    private int lifes = 20;
    private int birdCount;
    private int birdDead = 0;
    private int money = 350;
    private int testTouchLeftBot, testTouchRightBot, testTouchLeftTop, testTouchRightTop = 0;
    private int idOfTube;
    private int numberOfWaves;
    private int currentWave;

    private boolean isTouched = false;

    private Texture background;
    private Texture ground;
    private Texture towerUpgradeMenu;
    private Texture cannonBullet;
    private Texture arrowBullet;
    private Texture startWaveBtn;
    private Vector2 groundPos1, groundPos2;
    private Rectangle startWaveBounds;
    private Animation buttonClicked, buttonClickedReverse;
    private ParticleEffect fire;
    private ParticleEmitter emitter;

    private float btnAnimTime;

    private ArrayList<Tube> tubes;
    private ArrayList<Bird> birds;
    private ArrayList<Bullet> bullets;
    private HashMap<Integer, Fire> fires;
    private Iterator<Bird> birdIterator;

    private BitmapFont font, shortFont;

    private Rectangle rectLeftBot,rectRightBot,rectLeftTop,rectRightTop;
    private boolean isPopup;

    Bird target;
    boolean hasTarget;

    public PlayState(GameStateManager gsm, PlayStateOptions options) {
        super(gsm);

        this.currentWave = 0;
        this.options = options;
        this.numberOfWaves = options.waves.length;
        birdCount = options.waves[currentWave].numberOfBirds();

        cam.setToOrtho(false, FlappyDefense.WIDTH, FlappyDefense.HEIGHT);

        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        towerUpgradeMenu = new Texture("towerupgrademenu.png");
        cannonBullet = new Texture("ball.png");
        arrowBullet = new Texture("arrow.png");
        startWaveBtn = new Texture("playbtn.png");
        startWaveBounds = new Rectangle(10,FlappyDefense.HEIGHT/2,startWaveBtn.getWidth(),startWaveBtn.getHeight());

        buttonClicked = new Animation(0.1f, new TextureRegion(new Texture("playbtn.png")),
                new TextureRegion(new Texture("playbtn1.png")),
                new TextureRegion(new Texture("playbtn2.png")),
                new TextureRegion(new Texture("playbtn3.png"))
        );

        buttonClickedReverse = new Animation(0.1f, new TextureRegion(new Texture("playbtn3.png")),
                new TextureRegion(new Texture("playbtn2.png")),
                new TextureRegion(new Texture("playbtn1.png")),
                new TextureRegion(new Texture("playbtn.png"))
        );

        groundHeight = FlappyDefense.GROUND_Y_OFFSET + ground.getHeight();

        tubes = new ArrayList<Tube>();
        birds = new ArrayList<Bird>();
        bullets = new ArrayList<Bullet>();
        fires = new HashMap<Integer, Fire>();

        birdIterator = birds.iterator();

        font = new BitmapFont(Gdx.files.internal("fonts/flappybird-32.fnt"));
        shortFont = new BitmapFont(Gdx.files.internal("fonts/flappybird-12.fnt"));

        fire = new ParticleEffect();
        fire.load(Gdx.files.internal("fireFD"), Gdx.files.internal(""));


        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

    }

    @Override
    protected void handleInput() {
        //if(Gdx.input.justTouched()){
            //bird.jump();
        //}
        if(touched(startWaveBounds) && !isTouched && currentWave < numberOfWaves){
            birdCreate();
            isTouched = true;
            //for(Tube tube:tubes){
                //tube.grow();
            //}
        }

    }

    public boolean touched(Rectangle rect) {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        if (rect.contains(touchPos.x, touchPos.y))
            return true;
        else
            return false;
    }

    private void birdCreate(){
//        for(int i = 1; i <= birdCount; i++) {
//            if(i % 3 == 0){
//                birds.add(new FastBird(i * 3 * 20));
//            }
//            else if(i % 4 == 0){
//                birds.add(new SlowBird(i * 20));
//            }else if(i % 5 ==0){
//                birds.add(new SprintBird(i * 3 * 20));
//            }
//            else {
//                birds.add(new Bird(i * 3 * 20));
//            }
//        }
        int i = 0;
        PlayStateOptions.Wave wave = options.waves[currentWave];
        for (Map.Entry<PlayStateOptions.Bird, Integer> entry : wave.getMapOfBirds().entrySet()) {
            PlayStateOptions.Bird birdType = entry.getKey();
            int numberOfBirds = entry.getValue();
            switch (birdType) {
                case Bird:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birds.add(new Bird(i * 3 * 20));
                    }
                    break;
                case SprintBird:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birds.add(new SprintBird(i * 3 * 20));
                    }
                    break;
                case SlowBird:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birds.add(new SlowBird(i * 3 * 20));
                    }
                    break;
                case FastBird:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birds.add(new FastBird(i * 3 * 20));
                    }
                    break;
            }
        }
        target = birds.get(0);
    }

    private Bird getLastBird(){
        return birds.get(birds.size()-1);
    }


    @Override
    public void update(float dt) {
        handleInput();

        btnAnimTime += dt;
        birdDead = 0;
        for(Bird bird: birds) {
            if(!bird.isDead()) {
                bird.update(dt);
                if (bird.getPosition().x > FlappyDefense.WIDTH && !bird.isOverEdge()) {
                    lifes--;
                    bird.setIsOverEdge(true);
                    bird.setIsDead(true);
                    //bird.dispose();
                }
                int idOfTube = 0;
                for (Tube tube : tubes) {
                    if (tube.collides(bird.getBounds())) {
                        if (System.currentTimeMillis() > tube.getClearSky()) {
                            if (!tube.getClass().getName().equals("com.batmad.game.Sprites.Tube")){
                                if(tube.getClass().getName().equals("com.batmad.game.Sprites.FireTube")){
//                                    Fire fire = fires.get(idOfTube);
                                    if(bird.isTarget()) {
//                                        fire.setTarget(bird.getBounds());
//                                        bird.setIsTarget(true);
//                                        fire.start();
//                                        fire.update(dt);
                                    }
                                }
                                else {
                                    tube.setClearSky(System.currentTimeMillis() + tube.getFireRate());
                                    Bullet bullet = tube.fire(bird.getBounds());
                                    bullet.sound();
                                    bullets.add(bullet);
                                    //bird.dispose();
                                }
                            }
                        }
                    }
                    idOfTube++;
                }
                for (Bullet bullet : bullets) {
                    //bullet.update(dt);
                    if(!bullet.isFired()) {
                        if (bullet.collides(bird.getBounds())) {
                            bird.loseLife(bullet.getDamage());
                            bullet.setIsFired(true);
                        }
                    }
                }
                if (bird.getBirdLifes() <= 0) {
                    money += bird.getGold();
                    //bird.dispose();
                    bird.setIsDead(true);
                }

            }else{
                birdDead++;
            }
        }

        if(!birds.isEmpty()) {
            int idOfTube = 0;
            for (Tube tube : tubes) {
                if (tube.getClass().getName().equals("com.batmad.game.Sprites.FireTube")) {
                    for(Bird bird: birds){
                        if(!bird.isDead() && tube.collides(bird.getBounds())){
                            if(!tube.isHasTarget()) {
                                tube.setTarget(bird.getBounds());
                                tube.setHasTarget(true);
                            }
                            Fire fire = fires.get(idOfTube);
                            fire.setTarget(tube.getTarget());
                            fire.start();
                            fire.update(dt);
                            if(System.currentTimeMillis()>tube.getClearSky()) {
                                bird.loseLife(fire.getDamage());
                                tube.setClearSky(System.currentTimeMillis() + fire.getNoDamageTime());
                            }
                        }
                        else if(bird == target){
                            tube.setHasTarget(false);
                        }
                        //else if(birds.get(birdCount-1).getPosition().x > tube.getPosBotTube().x + tube.getBottomTube().getWidth()){
                        else{
                            Fire fire = fires.get(idOfTube);
                            System.out.println("fire invisible");
                            fire.stop(dt);
                            //fire.update(dt);
                            //fire.setIsFired(true);
                        }
                    }

                }
                idOfTube++;
            }
        }

        if(birdDead == birdCount){
            isTouched = false;
            currentWave++;
            if(currentWave < numberOfWaves) {
                birdCount = options.waves[currentWave].numberOfBirds();
            }
            //birdCount += 5;
            birds = new ArrayList<Bird>();
            for(Map.Entry<Integer,Fire> entry: fires.entrySet()){
                Integer key = entry.getKey();
                Fire fire = entry.getValue();
                //fire.setIsFired(false);

            }
            //emitter.duration = 0;
            //emitter.durationTimer = 0;
        }




        for(Bullet bullet:bullets){
            if(!bullet.isFired() && !bullet.isDead()) {
                bullet.update(dt);
            }
        }

        //for(int i = 0; i < tubes.size(); i++){

            //if(tubes.get(i).collides(bird.getBounds()))
                //gsm.set(new MenuState(gsm));
            if(isPopup){
                if(Gdx.input.justTouched() && touched(rectRightTop)) {
                    testTouchRightTop++;
                    Tube upgradeTube = new ArrowTube(tubes.get(idOfTube).getPosTopTube().x);
                    if (money > upgradeTube.getValue()) {
                        tubes.set(idOfTube, upgradeTube);
                        money -= tubes.get(idOfTube).getValue();
                    }
                    isPopup = false;
                }
                if(Gdx.input.justTouched() && touched(rectLeftTop)) {
                    testTouchLeftTop++;
                    Tube upgradeTube = new TubeGrowed(tubes.get(idOfTube).getPosTopTube().x);
                    if (money > upgradeTube.getValue()) {
                        tubes.set(idOfTube, upgradeTube);
                        money -= tubes.get(idOfTube).getValue();
                    }
                    isPopup = false;
                }
                if(Gdx.input.justTouched() && touched(rectLeftBot)) {
                    testTouchLeftBot++;
                    Tube upgradeTube = new FireTube(tubes.get(idOfTube).getPosTopTube().x);
                    Fire fire = new Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth()/2, upgradeTube.getPosBotTube().y + upgradeTube.getBottomTube().getHeight(), upgradeTube.getDamage());
                    fires.put(idOfTube, fire);
                    if (money > upgradeTube.getValue()) {
                        tubes.set(idOfTube, upgradeTube);
                        money -= tubes.get(idOfTube).getValue();
                    }
                    isPopup = false;
                }
                if(Gdx.input.justTouched() && touched(rectRightBot)) {
                    testTouchRightBot++;
                    Tube upgradeTube = new FireTube(tubes.get(idOfTube).getPosTopTube().x);
                    Fire fire = new Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth()/2, upgradeTube.getPosBotTube().y + upgradeTube.getBottomTube().getHeight(), upgradeTube.getDamage());
                    fires.put(idOfTube, fire);
                    if (money > upgradeTube.getValue()) {
                        tubes.set(idOfTube, upgradeTube);
                        money -= tubes.get(idOfTube).getValue();
                    }
                    isPopup = false;
                }
            }
       // }
        fire.setPosition(10, 40);
        fire.update(dt);
        emitter = fire.getEmitters().first();
        emitter.getScale().setHigh(5, 20);
        emitter.getAngle().setHigh(90);
        emitter.getAngle().setLow(90);
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(background, background.getWidth(), 0);
        sb.draw(background, background.getWidth() * 2, 0);
        sb.draw(ground, 0, FlappyDefense.GROUND_Y_OFFSET);
        sb.draw(ground, ground.getWidth(), FlappyDefense.GROUND_Y_OFFSET);
        sb.draw(ground, 2 * ground.getWidth(), FlappyDefense.GROUND_Y_OFFSET);
        if(!isTouched) {
            sb.draw(startWaveBtn, 10, FlappyDefense.HEIGHT / 2);
        }
        else{
            sb.draw(buttonClicked.getKeyFrame(btnAnimTime),10, FlappyDefense.HEIGHT / 2);
        }

        for(Bird bird: birds) {
            if(!bird.isDead()) {
                sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
            }
        }
        for(Tube tube: tubes) {
            //draw the Top Tube
//            if(touched(tube.getBoundsTop())){
//                //sb.draw(tube.getTopTubeGrowed(), tube.getPosTopTubeGrowed().x, tube.getPosTopTubeGrowed().y);
//                makeMenuBounds(tube.getPosTopTube().x - tube.getTopTube().getWidth()/2, FlappyDefense.HEIGHT - towerUpgradeMenu.getHeight(), tubes.indexOf(tube));
//                sb.draw(towerUpgradeMenu, tube.getPosTopTube().x - tube.getTopTube().getWidth()/2, FlappyDefense.HEIGHT - towerUpgradeMenu.getHeight());
//            }
//            else {
//                sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
//            }
            if( touched(tube.getBoundsBot())){
                if (tube.getClass().getName().equals("com.batmad.game.Sprites.Tube")) {
                    //sb.draw(tube.getBottomTubeGrowed(), tube.getPosBottomTubeGrowed().x, tube.getPosBottomTubeGrowed().y);
                    int costCannon = new TubeGrowed(0).getValue();
                    int costArrow = new ArrowTube(0).getValue();
                    makeMenuBounds(tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2, groundHeight, tubes.indexOf(tube));
                    sb.draw(towerUpgradeMenu, tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2, groundHeight);
                    if (money < costCannon) {
                        Color c = sb.getColor();
                        sb.setColor(0, 0, 0, 0.5f);
                        sb.draw(cannonBullet, tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2 + towerUpgradeMenu.getWidth() / 4 - cannonBullet.getWidth() / 2, groundHeight + towerUpgradeMenu.getHeight() * 3 / 4 - cannonBullet.getHeight() / 2);
                        sb.setColor(c.r, c.g, c.b, 1f);
                    } else {
                        sb.draw(cannonBullet, tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2 + towerUpgradeMenu.getWidth() / 4 - cannonBullet.getWidth() / 2, groundHeight + towerUpgradeMenu.getHeight() * 3 / 4 - cannonBullet.getHeight() / 2);
                        shortFont.draw(sb, "" + ((int) costCannon), tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2 + towerUpgradeMenu.getWidth() / 4 - cannonBullet.getWidth() / 2, groundHeight + towerUpgradeMenu.getHeight() * 3 / 4 - cannonBullet.getHeight() / 2);
                    }
                    if (money < costArrow) {
                        Color c = sb.getColor();
                        sb.setColor(0, 0, 0, 0.5f);
                        sb.draw(arrowBullet, tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2 + towerUpgradeMenu.getWidth() * 3 / 4 - arrowBullet.getWidth() / 2, groundHeight + towerUpgradeMenu.getHeight() * 3 / 4 - arrowBullet.getHeight() / 2);
                        sb.setColor(c.r, c.g, c.b, 1f);
                    } else {
                        sb.draw(arrowBullet, tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2 + towerUpgradeMenu.getWidth() * 3 / 4 - arrowBullet.getWidth() / 2, groundHeight + towerUpgradeMenu.getHeight() * 3 / 4 - arrowBullet.getHeight() / 2);
                        shortFont.draw(sb, "" + ((int) costArrow), tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2 + towerUpgradeMenu.getWidth() * 3 / 4 - cannonBullet.getWidth() / 2, groundHeight + towerUpgradeMenu.getHeight() * 3 / 4 - cannonBullet.getHeight() / 2);
                    }
                }
                else{
                    sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, groundHeight);
                }
            }
            else {
                sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, groundHeight);
            }
        }
        for(Bullet bullet: bullets) {
            if(!bullet.isFired() && !bullet.isDead()) {
                if(bullet.getClass().getName().equals("com.batmad.game.Sprites.ArrowBullet")){
                    sb.draw(new TextureRegion(bullet.getTexture()),bullet.getPosition().x,bullet.getPosition().y, bullet.getTexture().getWidth()/2, bullet.getTexture().getHeight()/2, bullet.getTexture().getWidth(),bullet.getTexture().getHeight(),1,1,200 + bullet.angle());
                }else {
                    sb.draw(bullet.getTexture(), bullet.getPosition().x, bullet.getPosition().y);
                }
            }

        }
        for(Map.Entry<Integer,Fire> entry: fires.entrySet()){
            Integer key = entry.getKey();
            Fire fire = entry.getValue();
            if(!fire.isFired()) {
                fire.getFire().draw(sb);
            }
        }

        fire.draw(sb);
        font.draw(sb, "" + ((int)lifes), 750,40);
        font.draw(sb, "" + ((int)money), 650,40);
        font.draw(sb, "BD:" + ((int)birdDead), 400,40);
        font.draw(sb, "BC:" + ((int)birdCount), 500,40);
        font.draw(sb, "LB:" + ((int)testTouchLeftBot), 300,40);
        font.draw(sb, "LT:" + ((int)testTouchLeftTop), 200,40);
        font.draw(sb, "RB:" + ((int)testTouchRightBot), 100,40);
        font.draw(sb, "RT:" + ((int)testTouchRightTop), 0,40);
        sb.end();
    }

    public void makeMenuBounds(float x, float y, int tubeIndex){
        isPopup = true;
        idOfTube = tubeIndex;
        rectLeftBot = new Rectangle(x,y,towerUpgradeMenu.getWidth()/2,towerUpgradeMenu.getHeight()/2);
        rectRightBot = new Rectangle(x + towerUpgradeMenu.getWidth()/2,y,towerUpgradeMenu.getWidth()/2,towerUpgradeMenu.getHeight()/2);
        rectLeftTop = new Rectangle(x,y + towerUpgradeMenu.getHeight()/2,towerUpgradeMenu.getWidth()/2,towerUpgradeMenu.getHeight()/2);
        rectRightTop = new Rectangle(x + towerUpgradeMenu.getWidth()/2,y + towerUpgradeMenu.getHeight()/2,towerUpgradeMenu.getWidth() / 2,towerUpgradeMenu.getHeight()/2);
        //Gdx.input.setCursorPosition(0,0);
    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
        for(Tube tube: tubes){
            tube.dispose();
        }
        for(Bird bird: birds){
            bird.dispose();
        }
        System.out.println("Play state disposed");
    }

}
