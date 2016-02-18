package com.batmad.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import java.util.Map;

/**
 * Created by tm on 19.11.2015.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 100;
    private static final int TUBE_COUNT = 4;
    private static int groundHeight;

    private PlayStateOptions options;
    private Bird target;
    private Preferences prefs;

    private int lifes = 5;
    private int birdCount;
    private int birdDead = 0;
    private int birdDeadTotal;
    private int money = 350;
    private int score;
    private int testTouchLeftBot, testTouchRightBot, testTouchLeftTop, testTouchRightTop = 0;
    private int idOfTube;
    private int numberOfWaves;
    private int currentWave;
    private int currentMaxLevel;
    private int currentLevelNumber;
    private int countNextWaveStart = -1;
    private float btnAnimTime;
    private long countNextWaveStartTimeMillis;

    private boolean isTouched = false;
    private boolean wonGame = false;
    private boolean loseGame = false;
    private boolean isPopup;
    private boolean passTutorial;
    private boolean passTutorialStartWave;
    private boolean passTutorialBuildTower;
    private boolean isPlayedWonSound;

    private Texture background;
    private Texture ground;
    private Texture menu;
    private Texture towerUpgradeMenu;
    private Texture cannonBullet;
    private Texture arrowBullet;
    private Texture fireBullet;
    private Texture startWaveBtn;
    private Texture replayBtn;
    private Texture nextLevelBtn;
    private Texture backBtn;
    private Texture pointer;
    private TextureRegion groundAbove;
    private Vector2 groundPos1, groundPos2;
    private Animation buttonClicked, buttonClickedReverse;
    private Sound win;
    private Sound lose;

    private ArrayList<Tube> tubes;
    private ArrayList<Bird> birds;
    private ArrayList<Bird> birdsInStock;
    private ArrayList<Bullet> bullets;
    private HashMap<Integer, Fire> fires;

    private BitmapFont font;
    private BitmapFont shortFont;
    private BitmapFont fontLetter;

    private Rectangle startWaveBounds;
    private Rectangle replatBtnRect;
    private Rectangle nextLevelBtnRect;
    private Rectangle backBtnRect;
    private Rectangle rectLeftBot;
    private Rectangle rectRightBot;
    private Rectangle rectLeftTop;
    private Rectangle rectRightTop;


    public PlayState(GameStateManager gsm, PlayStateOptions options) {
        super(gsm);
        prefs = Gdx.app.getPreferences("myPrefs");
        currentMaxLevel = prefs.getInteger("levels", 1);
        passTutorial = prefs.getBoolean("passTutorial", false);
        if(!passTutorial){
            passTutorialBuildTower = false;
            passTutorialStartWave = false;
        }
        this.currentWave = 0;
        this.options = options;
        this.numberOfWaves = options.waves.length;
        this.currentLevelNumber = options.levelNumber;
        birdCount = options.waves[currentWave].numberOfBirds();

        win = Gdx.audio.newSound(Gdx.files.internal("win.ogg"));
        lose = Gdx.audio.newSound(Gdx.files.internal("lose.ogg"));

        cam.setToOrtho(false, FlappyDefense.WIDTH, FlappyDefense.HEIGHT);

        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        menu = new Texture("menu/won.png");
        towerUpgradeMenu = new Texture("towerupgrademenu.png");
        cannonBullet = new Texture("ball.png");
        arrowBullet = new Texture("arrow.png");
        fireBullet = new Texture("fire.png");
        startWaveBtn = new Texture("playbtn.png");
        replayBtn = new Texture("menu/replay.png");
        nextLevelBtn = new Texture("menu/nextlevel.png");
        backBtn = new Texture("menu/back.png");
        pointer = new Texture("menu/pointer.png");
        groundAbove = new TextureRegion(ground);
        groundAbove.flip(true, true);

        startWaveBounds = new Rectangle(10, FlappyDefense.HEIGHT / 2, startWaveBtn.getWidth(), startWaveBtn.getHeight());
        backBtnRect = new Rectangle(cam.position.x - 3 * replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50, backBtn.getWidth(), backBtn.getHeight());
        replatBtnRect = new Rectangle(cam.position.x - replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50, replayBtn.getWidth(), replayBtn.getHeight());
        nextLevelBtnRect = new Rectangle(cam.position.x + replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50, nextLevelBtn.getWidth(), nextLevelBtn.getHeight());

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

        //groundHeight = FlappyDefense.GROUND_Y_OFFSET + ground.getHeight();

        tubes = new ArrayList<Tube>();
        birds = new ArrayList<Bird>();
        birdsInStock = new ArrayList<Bird>();
        bullets = new ArrayList<Bullet>();
        fires = new HashMap<Integer, Fire>();


        font = new BitmapFont(Gdx.files.internal("fonts/flappybird-32.fnt"));
        shortFont = new BitmapFont(Gdx.files.internal("fonts/flappybird-12.fnt"));
        fontLetter = new BitmapFont(Gdx.files.internal("fonts/neskid-white-32.fnt"));

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
            if(currentLevelNumber > 5)
                tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH), true));
        }

    }

    @Override
    protected void handleInput() {
        //if(Gdx.input.justTouched()){
        //bird.jump();
        //}
        if (touched(startWaveBounds) && !isTouched && currentWave < numberOfWaves && Gdx.input.justTouched()) {
            birdCreate();
            isTouched = true;
            countNextWaveStart = -1;
            passTutorialStartWave = true;
            //for(Tube tube:tubes){
            //tube.grow();
            //}
        } else if (wonGame) {
            if(!isPlayedWonSound) {
                win.play(0.1f);
                isPlayedWonSound = true;
            }
            if (touched(backBtnRect) && Gdx.input.justTouched()) {
                gsm.set(new MenuState(gsm));
            } else if (touched(replatBtnRect) && Gdx.input.justTouched()) {
                gsm.set(new PlayState(gsm, options));
            } else if (options.levelNumber < 15 && touched(nextLevelBtnRect) && Gdx.input.justTouched()) {
                PlayStateOptions options = new Levels().getLevelOptions(this.options.levelNumber + 1);
                gsm.set(new PlayState(gsm, options));
            }
        }else if (loseGame) {
            if (!isPlayedWonSound) {
                lose.play(0.1f);
                isPlayedWonSound = true;
            }
            if (touched(backBtnRect) && Gdx.input.justTouched()) {
                gsm.set(new MenuState(gsm));
            } else if (touched(replatBtnRect) && Gdx.input.justTouched()) {
                gsm.set(new PlayState(gsm, options));
            }
        }
    }

    public void countNextWaveStart() {
        if (countNextWaveStart > 0 & countNextWaveStartTimeMillis < System.currentTimeMillis() & !isTouched & currentWave < numberOfWaves) {
            countNextWaveStartTimeMillis = System.currentTimeMillis() + 1000;
            countNextWaveStart--;
        }
        if (countNextWaveStart == 0) {
            birdCreate();
            isTouched = true;
            countNextWaveStart = -1;
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

    private void birdCreate() {
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
                        birdsInStock.add(new Bird(70));
                    }
                    break;
                case SprintBird:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new SprintBird(70));
                    }
                    break;
                case SlowBird:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new SlowBird(70));
                    }
                    break;
                case FastBird:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new FastBird(70));
                    }
                    break;
            }
        }
        target = birdsInStock.get(0);
    }

    private Bird getLastBird() {
        return birds.get(birds.size() - 1);
    }


    @Override
    public void update(float dt) {
        if(!birdsInStock.isEmpty()){
            if(birds.isEmpty()) {
                birds.add(birdsInStock.get(0));
                birdsInStock.remove(0);
            }
            else if(getLastBird().getPosition().x > 0){
                birds.add(birdsInStock.get(0));
                birdsInStock.remove(0);
            }
        }
        handleInput();
        countNextWaveStart();

        btnAnimTime += dt;
        birdDead = 0;
        for (Bird bird : birds) {
            if (!bird.isDead()) {
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
                            if (!tube.getClass().getName().equals("com.batmad.game.Sprites.Tube")) {
                                if (tube.getClass().getName().equals("com.batmad.game.Sprites.FireTube")) {
//                                    Fire fire = fires.get(idOfTube);
                                    if (bird.isTarget()) {
//                                        fire.setTarget(bird.getBounds());
//                                        bird.setIsTarget(true);
//                                        fire.start();
//                                        fire.update(dt);
                                    }
                                } else {
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
                    if (!bullet.isFired()) {
                        if (bullet.collides(bird.getBounds())) {
                            bird.loseLife(bullet.getDamage());
                            bullet.setIsFired(true);
                        }
                    }
                }
                if (bird.getLifes() <= 0) {
                    money += bird.getGold();
                    score += bird.getGold();
                    score += FlappyDefense.WIDTH - bird.getPosition().x;
                    //bird.dispose();
                    bird.setIsDead(true);
                    birdDeadTotal++;
                }

            } else {
                birdDead++;
            }
        }

        if (!birds.isEmpty()) {
            int idOfTube = 0;
            for (Tube tube : tubes) {
                if (tube.getClass().getName().equals("com.batmad.game.Sprites.FireTube")) {
                    for (Bird bird : birds) {
                        if (!bird.isDead() && tube.collides(bird.getBounds())) {
                            if (!tube.isHasTarget()) {
                                tube.setTarget(bird.getBounds());
                                tube.setHasTarget(true);
                            }
                            Fire fire = fires.get(idOfTube);
                            fire.setTarget(tube.getTarget());
                            fire.start();
                            fire.update(dt);
                            if (System.currentTimeMillis() > tube.getClearSky()) {
                                bird.loseLife(fire.getDamage());
                                tube.setClearSky(System.currentTimeMillis() + fire.getNoDamageTime());
                            }
                        } else if (bird == target) {
                            tube.setHasTarget(false);
                        }
                        //else if(birds.get(birdCount-1).getPosition().x > tube.getPosBotTube().x + tube.getBottomTube().getWidth()){
                        else {
                            Fire fire = fires.get(idOfTube);
                            //System.out.println("fire invisible");
                            fire.stop(dt);
                            //fire.update(dt);
                            //fire.setIsFired(true);
                        }
                    }
                }
                idOfTube++;
            }
        } else {
            int idOfTube = 0;
            for (Tube tube : tubes) {
                if (tube.getClass().getName().equals("com.batmad.game.Sprites.FireTube")) {
                    Fire fire = fires.get(idOfTube);
                    //System.out.println("fire invisible");
                    fire.stop(dt);
                }
                idOfTube++;
            }
        }

        if (birdDead == birdCount) {
            restart();
        }

        if (currentWave == numberOfWaves) {
            if (options.levelNumber < Levels.NUMBER_OF_LEVELS & options.levelNumber >= currentMaxLevel) {
                prefs.putInteger("levels", options.levelNumber + 1);
                prefs.flush();
            }
            wonGame = true;
        }

        for (Bullet bullet : bullets) {
            if (!bullet.isFired() && !bullet.isDead()) {
                bullet.update(dt);
            }
        }

        //for(int i = 0; i < tubes.size(); i++){

        //if(tubes.get(i).collides(bird.getBounds()))
        //gsm.set(new MenuState(gsm));
        if (isPopup) {
            passTutorialBuildTower = true;
            if (touched(rectRightTop) && Gdx.input.justTouched()) {
                testTouchRightTop++;
                Tube upgradeTube;
                if(tubes.get(idOfTube).isTop)
                    upgradeTube = new ArrowTube(tubes.get(idOfTube).getPosTopTube().x, true);
                else
                    upgradeTube = new ArrowTube(tubes.get(idOfTube).getPosTopTube().x);
                if (money > upgradeTube.getValue()) {
                    tubes.set(idOfTube, upgradeTube);
                    money -= tubes.get(idOfTube).getValue();
                }
                isPopup = false;
            }
            if (touched(rectLeftTop) && Gdx.input.justTouched()) {
                testTouchLeftTop++;
                Tube upgradeTube;
                if(tubes.get(idOfTube).isTop)
                    upgradeTube = new TubeGrowed(tubes.get(idOfTube).getPosTopTube().x, true);
                else
                    upgradeTube = new TubeGrowed(tubes.get(idOfTube).getPosTopTube().x);
                if (money > upgradeTube.getValue()) {
                    tubes.set(idOfTube, upgradeTube);
                    money -= tubes.get(idOfTube).getValue();
                }
                isPopup = false;
            }
            if (Gdx.input.justTouched() && touched(rectLeftBot)) {
                testTouchLeftBot++;
                Tube upgradeTube;
                if(tubes.get(idOfTube).isTop)
                    upgradeTube = new FireTube(tubes.get(idOfTube).getPosTopTube().x, true);
                else
                    upgradeTube = new FireTube(tubes.get(idOfTube).getPosTopTube().x);
                Fire fire = new Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth() / 2, upgradeTube.getPosBotTube().y + upgradeTube.getBottomTube().getHeight(), upgradeTube.getDamage());
                if (money > upgradeTube.getValue()) {
                    fires.put(idOfTube, fire);
                    tubes.set(idOfTube, upgradeTube);
                    money -= tubes.get(idOfTube).getValue();
                }
                isPopup = false;
            }
//            if (Gdx.input.justTouched() && touched(rectRightBot)) {
//                testTouchRightBot++;
//                Tube upgradeTube = new FireTube(tubes.get(idOfTube).getPosTopTube().x);
//                Fire fire = new Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth() / 2, upgradeTube.getPosBotTube().y + upgradeTube.getBottomTube().getHeight(), upgradeTube.getDamage());
//                if (money > upgradeTube.getValue()) {
//                    fires.put(idOfTube, fire);
//                    tubes.set(idOfTube, upgradeTube);
//                    money -= tubes.get(idOfTube).getValue();
//                }
//                isPopup = false;
//            }
        }
        // }
        if (lifes < 0) {
            loseGame = true;
            birds = new ArrayList<Bird>();
        }
        cam.update();
        if(passTutorialBuildTower && passTutorialStartWave){
            passTutorial = true;
            prefs.putBoolean("passTutorial",true);
            prefs.flush();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        if(currentLevelNumber > 5) {
            sb.draw(groundAbove, 0, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET);
            sb.draw(groundAbove, ground.getWidth(), FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET);
            sb.draw(groundAbove, 2 * ground.getWidth(), FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET);
        }
        if (wonGame) {
            sb.draw(menu, cam.position.x - menu.getWidth() / 2, cam.position.y - menu.getHeight() / 2);
            fontLetter.draw(sb, "WIN!", FlappyDefense.WIDTH / 2 - 40, FlappyDefense.HEIGHT - 30);
            font.draw(sb, String.valueOf(score), cam.position.x, cam.position.y + 15);
            font.draw(sb, String.valueOf(birdDeadTotal), cam.position.x, cam.position.y - 45);
            sb.draw(backBtn, cam.position.x - 3 * replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
            sb.draw(replayBtn, cam.position.x - replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
            if(options.levelNumber < 15)
                sb.draw(nextLevelBtn, cam.position.x + replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
        } else if(loseGame){
            sb.draw(menu, cam.position.x - menu.getWidth() / 2, cam.position.y - menu.getHeight() / 2);
            fontLetter.draw(sb, "LOSE!", FlappyDefense.WIDTH / 2 - 40, FlappyDefense.HEIGHT - 30);
            font.draw(sb, String.valueOf(score), cam.position.x, cam.position.y + 15);
            font.draw(sb, String.valueOf(birdDeadTotal), cam.position.x, cam.position.y - 45);
            sb.draw(backBtn, cam.position.x - 3 * replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
            sb.draw(replayBtn, cam.position.x - replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
        }
        else {
            if (!isTouched) {
                sb.draw(startWaveBtn, 10, FlappyDefense.HEIGHT / 2);
            } else {
                sb.draw(buttonClicked.getKeyFrame(btnAnimTime), 10, FlappyDefense.HEIGHT / 2);
            }

            for (Bird bird : birds) {
                if (!bird.isDead()) {
                    sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
                    sb.setColor(Color.RED);
                    sb.draw(bird.getLifeTexture(), bird.getPosition().x + bird.getBounds().getWidth() / 2 - bird.getLifeTexture().getWidth() / 2, bird.getPosition().y - 5);
                    sb.setColor(Color.WHITE);
                    sb.draw(bird.getLifeTexture(), bird.getPosition().x + bird.getBounds().getWidth() / 2 - bird.getLifeTexture().getWidth() / 2, bird.getPosition().y - 5, 0, 0, bird.getLifePercentage(), 2);
                }
            }
            for (Tube tube : tubes) {
                //draw the Top Tube
//            if(touched(tube.getBoundsTop())){
//                //sb.draw(tube.getTopTubeGrowed(), tube.getPosTopTubeGrowed().x, tube.getPosTopTubeGrowed().y);
//                makeMenuBounds(tube.getPosTopTube().x - tube.getTopTube().getWidth()/2, FlappyDefense.HEIGHT - towerUpgradeMenu.getHeight(), tubes.indexOf(tube));
//                sb.draw(towerUpgradeMenu, tube.getPosTopTube().x - tube.getTopTube().getWidth()/2, FlappyDefense.HEIGHT - towerUpgradeMenu.getHeight());
//            }
//            else {
//                sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
//            }
                if (touched(tube.getBoundsBot())) {
                    if (tube.getClass().getName().equals("com.batmad.game.Sprites.Tube")) {
                        //sb.draw(tube.getBottomTubeGrowed(), tube.getPosBottomTubeGrowed().x, tube.getPosBottomTubeGrowed().y);
                        int costCannon = new TubeGrowed(0).getValue();
                        int costArrow = new ArrowTube(0).getValue();
                        int costFire = new FireTube(0).getValue();
                        float menuPositionX = tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2;
                        float menuPositionY = tube.getPosBotTube().y - 50;
                        float menuLeftPositionX = menuPositionX + towerUpgradeMenu.getWidth() / 4;
                        float menuRightPositionX = menuPositionX + towerUpgradeMenu.getWidth() * 3 / 4;
                        float menuTopPositionY = menuPositionY + towerUpgradeMenu.getHeight() * 3 / 4;
                        float menuBotPositionY = menuPositionY + towerUpgradeMenu.getHeight() / 4;
                        makeMenuBounds(menuPositionX, menuPositionY, tubes.indexOf(tube));
                        sb.draw(towerUpgradeMenu, menuPositionX, menuPositionY);
                        if (money < costCannon) {
                            Color c = sb.getColor();
                            sb.setColor(0, 0, 0, 0.5f);
                            sb.draw(cannonBullet, menuLeftPositionX - cannonBullet.getWidth() / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                            sb.setColor(c.r, c.g, c.b, 1f);
                        } else {
                            sb.draw(cannonBullet, menuLeftPositionX - cannonBullet.getWidth() / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                            shortFont.draw(sb, "" + ((int) costCannon), menuLeftPositionX - cannonBullet.getWidth() / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                        }
                        if (money < costArrow) {
                            Color c = sb.getColor();
                            sb.setColor(0, 0, 0, 0.5f);
                            sb.draw(arrowBullet, menuRightPositionX - arrowBullet.getWidth() / 2, menuTopPositionY - arrowBullet.getHeight() / 2);
                            sb.setColor(c.r, c.g, c.b, 1f);
                        } else {
                            sb.draw(arrowBullet, menuRightPositionX - arrowBullet.getWidth() / 2, menuTopPositionY - arrowBullet.getHeight() / 2);
                            shortFont.draw(sb, "" + ((int) costArrow), menuRightPositionX - cannonBullet.getWidth() / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                        }
                        if (money < costFire) {
                            Color c = sb.getColor();
                            sb.setColor(0, 0, 0, 0.5f);
                            sb.draw(fireBullet, menuLeftPositionX - arrowBullet.getWidth() / 2, menuBotPositionY - arrowBullet.getHeight() / 2);
                            sb.setColor(c.r, c.g, c.b, 1f);
                        } else {
                            sb.draw(fireBullet, menuLeftPositionX - arrowBullet.getWidth() / 2, menuBotPositionY - arrowBullet.getHeight() / 2);
                            shortFont.draw(sb, "" + ((int) costFire), menuLeftPositionX - cannonBullet.getWidth() / 2, menuBotPositionY - cannonBullet.getHeight() / 2);
                        }
                    } else {
                        sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                    }
                } else {
                    sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                }
            }
            for (Bullet bullet : bullets) {
                if (!bullet.isFired() && !bullet.isDead()) {
                    if (bullet.getClass().getName().equals("com.batmad.game.Sprites.ArrowBullet")) {
                        sb.draw(new TextureRegion(bullet.getTexture()), bullet.getPosition().x, bullet.getPosition().y, bullet.getTexture().getWidth() / 2, bullet.getTexture().getHeight() / 2, bullet.getTexture().getWidth(), bullet.getTexture().getHeight(), 1, 1, 200 + bullet.angle());
                    } else {
                        sb.draw(bullet.getTexture(), bullet.getPosition().x, bullet.getPosition().y);
                    }
                }

            }
            for (Map.Entry<Integer, Fire> entry : fires.entrySet()) {
                Integer key = entry.getKey();
                Fire fire = entry.getValue();

                fire.getFire().draw(sb);

            }


            font.draw(sb, "" + ((int) lifes), 750, 40);
            font.draw(sb, "" + ((int) money), 650, 40);
            font.draw(sb, "BD:" + ((int) birdDead), 400, 40);

            if (countNextWaveStart > 0)
                font.draw(sb, String.valueOf(countNextWaveStart), 0, 200);
        }
        if(!passTutorial){
            if(!passTutorialStartWave){
                sb.draw(pointer, 10 + startWaveBtn.getWidth() - 30, cam.position.y + startWaveBtn.getHeight() - 30);
                font.draw(sb,"start the wave",cam.position.x - 160, cam.position.y + 140);
            }
            if(!passTutorialBuildTower) {
                sb.draw(pointer, 190, 80);
                font.draw(sb, "build Towers", cam.position.x - 50, cam.position.y - 50);
            }
        }
        sb.end();
    }

    public void makeMenuBounds(float x, float y, int tubeIndex) {
        isPopup = true;
        idOfTube = tubeIndex;
        rectLeftBot = new Rectangle(x, y, towerUpgradeMenu.getWidth() / 2, towerUpgradeMenu.getHeight() / 2);
        rectRightBot = new Rectangle(x + towerUpgradeMenu.getWidth() / 2, y, towerUpgradeMenu.getWidth() / 2, towerUpgradeMenu.getHeight() / 2);
        rectLeftTop = new Rectangle(x, y + towerUpgradeMenu.getHeight() / 2, towerUpgradeMenu.getWidth() / 2, towerUpgradeMenu.getHeight() / 2);
        rectRightTop = new Rectangle(x + towerUpgradeMenu.getWidth() / 2, y + towerUpgradeMenu.getHeight() / 2, towerUpgradeMenu.getWidth() / 2, towerUpgradeMenu.getHeight() / 2);
        //Gdx.input.setCursorPosition(0,0);
    }

    private void gameOver() {

    }

    public void restart() {
        isTouched = false;
        currentWave++;
        if (currentWave < numberOfWaves) {
            birdCount = options.waves[currentWave].numberOfBirds();
            countNextWaveStart = 15;
        }
//        for(Map.Entry<Integer,Fire> entry: fires.entrySet()){
//            Integer key = entry.getKey();
//            Fire fire = entry.getValue();
//            fire.stop(dt);
//        }
        birds = new ArrayList<Bird>();
    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
        for (Tube tube : tubes) {
//            tube.dispose();
        }
        for (Bird bird : birds) {
            bird.dispose();
        }
        System.out.println("Play state disposed");
    }

}
