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
import com.batmad.game.Sprites.BirdAngry;
import com.batmad.game.Sprites.BirdHealer;
import com.batmad.game.Sprites.BirdStupid;
import com.batmad.game.Sprites.BirdSuicide;
import com.batmad.game.Sprites.BirdTeleport;
import com.batmad.game.Sprites.BirdZerg;
import com.batmad.game.Sprites.Boss1;
import com.batmad.game.Sprites.Boss2;
import com.batmad.game.Sprites.Boss3;
import com.batmad.game.Sprites.Boss4;
import com.batmad.game.Sprites.Boss5;
import com.batmad.game.Sprites.Bullet;
import com.batmad.game.Sprites.BulletBird;
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
    private int TUBE_SPACING = 100;
    private int TUBE_COUNT = 4;
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
    private int testUpgradeRect, testDestroyRect  = 0;
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
    private boolean isPopupTowerBuildMenu;
    private boolean isPopupTowerUpgradeMenu;
    private boolean isPopupRepairMenu;
    private boolean passTutorial;
    private boolean passTutorialStartWave;
    private boolean passTutorialBuildTower;
    private boolean isPlayedWonSound;

    private Texture background;
    private Texture ground;
    private Texture menu;
    private Texture towerBuildMenu;
    private Texture towerUpgradeMenu;
    private Texture repairMenu;
    private Texture upgrade;
    private Texture destroy;
    private Texture repair;
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
    private Rectangle rectLeftTop;
    private Rectangle rectRightTop;
    private Rectangle rectLeftBot;
    private Rectangle rectRightBot;
    private Rectangle upgradeRect;
    private Rectangle destroyRect;
    private Rectangle repairRect;

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
        towerBuildMenu = new Texture("menu/towerupgrademenu.png");
        towerUpgradeMenu = new Texture("menu/towerupgrademenulil.png");
        repairMenu = new Texture("menu/repairmenu.png");
        upgrade = new Texture("menu/upgrade.png");
        destroy = new Texture("menu/destroy.png");
        repair = new Texture("menu/repair.png");
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

        tubes = new ArrayList<Tube>();
        birds = new ArrayList<Bird>();
        birdsInStock = new ArrayList<Bird>();
        bullets = new ArrayList<Bullet>();
        fires = new HashMap<Integer, Fire>();

        font = new BitmapFont(Gdx.files.internal("fonts/flappybird-32.fnt"));
        shortFont = new BitmapFont(Gdx.files.internal("fonts/flappybird-12.fnt"));
        fontLetter = new BitmapFont(Gdx.files.internal("fonts/neskid-white-32.fnt"));

        if(currentLevelNumber > 10){
            TUBE_COUNT = 7;
            TUBE_SPACING = 45;
        }

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
            if(currentLevelNumber > 5)
                tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH), true));
        }

    }

    @Override
    protected void handleInput() {
        if (touched(startWaveBounds) && !isTouched && currentWave < numberOfWaves && Gdx.input.justTouched()) {
            birdCreate();
            isTouched = true;
            countNextWaveStart = -1;
            passTutorialStartWave = true;
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
                case BirdStupid:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new BirdStupid(70));
                    }
                    break;
                case BirdHealer:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add( (birdsInStock.size() / numberOfBirds) * j, new BirdHealer(70));
                    }
                    break;
                case BirdZerg:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birds.add(new BirdZerg(0));
                    }
                    break;
                case BirdTeleport:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add((birdsInStock.size() / numberOfBirds) * j, new BirdTeleport(70));
                    }
                    break;
                case BirdAngry:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add((birdsInStock.size() / numberOfBirds) * j, new BirdAngry(70));
                    }
                    break;
                case Boss1:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new Boss1(70));
                    }
                    break;
                case Boss2:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new Boss2(70));
                    }
                    break;
                case Boss3:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new Boss3(70));
                    }
                    break;
                case Boss4:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new Boss4(70));
                    }
                    break;
                case Boss5:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new Boss5(70));
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
                }
//                int idOfTube = 0;
                for (Tube tube : tubes) {
                    if (!tube.isBroken & !tube.getClass().getName().equals("com.batmad.game.Sprites.Tube") & !tube.getClass().getName().equals("com.batmad.game.Sprites.FireTube") & tube.collides(bird.getBounds()) & System.currentTimeMillis() > tube.getClearSky() ) {
                        tube.setClearSky(System.currentTimeMillis() + tube.getFireRate());
                        Bullet bullet = tube.fire(bird.getBounds());
                        bullet.sound();
                        bullets.add(bullet);
                    }
                    if(bird.getClass().getName().contains("BirdAngry") & !tube.getClass().getName().equals("com.batmad.game.Sprites.Tube")){
                        BirdAngry birdAngry = (BirdAngry) bird;
                        if(!birdAngry.isFired() & !tube.isBroken & birdAngry.collides(tube.getBoundsBot()) ){
                            Bullet bullet = birdAngry.fire(tube.getBoundsBot());
                            bullet.sound();
                            bullets.add(bullet);
                        }
                    }
                    if(bird.getClass().getName().equals("com.batmad.game.Sprites.BirdSuicide") & !tube.getClass().getName().equals("com.batmad.game.Sprites.Tube")){
                        BirdSuicide birdSuicide = (BirdSuicide) bird;
                        if(!tube.isBroken & birdSuicide.collides(tube.getBoundsBot()) ){
                            birdSuicide.setTarget(tube.getBoundsBot());
//                            birdSuicide.sound();
                        }
                    }

//                    idOfTube++;
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

                if(bird.getClass().getName().contains("BirdHealer")){
                    BirdHealer birdHealer = (BirdHealer)bird;
                    for (Bird birdToHeal : birds){
                        if(birdToHeal.getLifes() + birdHealer.heal < birdToHeal.getBirdLifesMax() & birdHealer.collides(birdToHeal.getBounds()) & System.currentTimeMillis() > bird.getLastHealedTimeStamp()) {
                            birdToHeal.healLife(birdHealer.heal);
                            bird.setLastHealedTimeStamp(System.currentTimeMillis() + birdHealer.getHealRate());
                        }
                    }
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
                            Fire fire = fires.get(idOfTube);
                            tube.setHasTarget(false);
                            fire.stop(dt);
                        }
                        else {
                            Fire fire = fires.get(idOfTube);
                            fire.stop(dt);
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
            if(!bullet.isFired() && bullet.getClass().getName().contains("Bird")) {
                BulletBird bulletBird = (BulletBird) bullet;
                for (Tube tube : tubes) {
                    if (!tube.isBroken & bullet.collides(tube.getBoundsBot())) {
                        tube.isBroken = true;
                        bulletBird.setIsFired(true);
                        bulletBird.playLanded();
                    }
                }
            }
        }

        if (isPopupTowerBuildMenu) {
            passTutorialBuildTower = true;
            if (touched(rectRightTop) && Gdx.input.justTouched()) {
                testTouchRightTop++;
                Tube upgradeTube;
                if(tubes.get(idOfTube).isTop)
                    upgradeTube = new ArrowTube(tubes.get(idOfTube).getPosTopTube().x, true);
                else
                    upgradeTube = new ArrowTube(tubes.get(idOfTube).getPosTopTube().x);
                if (money >= upgradeTube.getValue()) {
                    tubes.set(idOfTube, upgradeTube);
                    money -= tubes.get(idOfTube).getValue();
                }
                isPopupTowerBuildMenu = false;
            }
            if (touched(rectLeftTop) && Gdx.input.justTouched() & currentLevelNumber > 2) {
                testTouchLeftTop++;
                Tube upgradeTube;
                if(tubes.get(idOfTube).isTop)
                    upgradeTube = new TubeGrowed(tubes.get(idOfTube).getPosTopTube().x, true);
                else
                    upgradeTube = new TubeGrowed(tubes.get(idOfTube).getPosTopTube().x);
                if (money >= upgradeTube.getValue()) {
                    tubes.set(idOfTube, upgradeTube);
                    money -= tubes.get(idOfTube).getValue();
                }
                isPopupTowerBuildMenu = false;
            }
            if (Gdx.input.justTouched() && touched(rectLeftBot) &currentLevelNumber > 3) {
                testTouchLeftBot++;
                Tube upgradeTube;
                Fire fire;
                if(tubes.get(idOfTube).isTop) {
                    upgradeTube = new FireTube(tubes.get(idOfTube).getPosTopTube().x, true);
                    fire = new Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth() / 2, upgradeTube.getPosBotTube().y, upgradeTube.getDamage());
                }
                else {
                    upgradeTube = new FireTube(tubes.get(idOfTube).getPosTopTube().x);
                    fire = new Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth() / 2, upgradeTube.getPosBotTube().y + upgradeTube.getBottomTube().getHeight(), upgradeTube.getDamage());
                }
                if (money >= upgradeTube.getValue()) {
                    fires.put(idOfTube, fire);
                    tubes.set(idOfTube, upgradeTube);
                    money -= tubes.get(idOfTube).getValue();
                }
                isPopupTowerBuildMenu = false;
            }
//            TODO возможно добавить четвертый вид башни для стрельбы
//            if (Gdx.input.justTouched() && touched(rectRightBot)) {
//                testTouchRightBot++;
//                Tube upgradeTube = new FireTube(tubes.get(idOfTube).getPosTopTube().x);
//                Fire fire = new Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth() / 2, upgradeTube.getPosBotTube().y + upgradeTube.getBottomTube().getHeight(), upgradeTube.getDamage());
//                if (money > upgradeTube.getValue()) {
//                    fires.put(idOfTube, fire);
//                    tubes.set(idOfTube, upgradeTube);
//                    money -= tubes.get(idOfTube).getValue();
//                }
//                isPopupTowerBuildMenu = false;
//            }
        }
        if(isPopupTowerUpgradeMenu){
            //TODO tutorial for upgrade and destroy
            //TODO не показывать когда урвоенье меньше 2 и 3
            if (touched(upgradeRect) && Gdx.input.justTouched()) {
                testUpgradeRect++;
                if (money >= tubes.get(idOfTube).getUpgradeCost()) {
                    money -= tubes.get(idOfTube).getUpgradeCost();
                    tubes.get(idOfTube).upgrade();
                }
                isPopupTowerUpgradeMenu = false;
            }
            if (touched(destroyRect) && Gdx.input.justTouched()) {
                if (touched(destroyRect) && Gdx.input.justTouched()) {
                    money += tubes.get(idOfTube).getTotalValue();
                    tubes.set(idOfTube, new Tube(tubes.get(idOfTube).getPosTopTube().x, tubes.get(idOfTube).isTop));
                }
                testDestroyRect++;
                isPopupTowerUpgradeMenu = false;
            }
        }
        if(isPopupRepairMenu){
            //TODO tutorial for repair
            if (touched(repairRect) && Gdx.input.justTouched()) {
                if (money >= tubes.get(idOfTube).getRepairValue()) {
                    money -= tubes.get(idOfTube).getRepairValue();
                    tubes.get(idOfTube).repair();
                }
                isPopupRepairMenu = false;
            }
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
//                makeTowerBuildMenuBounds(tube.getPosTopTube().x - tube.getTopTube().getWidth()/2, FlappyDefense.HEIGHT - towerBuildMenu.getHeight(), tubes.indexOf(tube));
//                sb.draw(towerBuildMenu, tube.getPosTopTube().x - tube.getTopTube().getWidth()/2, FlappyDefense.HEIGHT - towerBuildMenu.getHeight());
//            }
//            else {
//                sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
//            }
                if(tube.isBroken) {
                    sb.draw(tube.getDestoyedTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                    if (touched(tube.getBoundsBot())) {
                        int cost = tube.getRepairValue();
                        float menuPositionX = tube.getPosBotTube().x + tube.getBottomTube().getWidth() / 2 - repairMenu.getWidth() /2;
                        float menuPositionY = tube.getPosBotTube().y + repairMenu.getHeight();
                        float menuCenterPositionX = menuPositionX + repairMenu.getWidth() / 2;
                        float menuCenterPositionY = menuPositionY + repairMenu.getHeight() / 2;
                        makeRepairMenuBounds(menuPositionX, menuPositionY, tubes.indexOf(tube));
                        sb.draw(repairMenu, menuPositionX, menuPositionY);
                        if (money < cost) {
                            Color c = sb.getColor();
                            sb.setColor(0, 0, 0, 0.5f);
                            sb.draw(repair, menuCenterPositionX - repair.getWidth() / 2, menuCenterPositionY - repair.getHeight() / 2);
                            sb.setColor(c.r, c.g, c.b, 1f);
                        } else {
                            sb.draw(repair, menuCenterPositionX - repair.getWidth() / 2, menuCenterPositionY - repair.getHeight() / 2);
                            shortFont.draw(sb, "" + ((int) cost), menuCenterPositionX - repair.getWidth() / 2, menuCenterPositionY - repair.getHeight() / 2);
                        }
                    }
                }
                else {
                    sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                    if (touched(tube.getBoundsBot())) {
                        if (tube.getClass().getName().equals("com.batmad.game.Sprites.Tube")) {
                            //sb.draw(tube.getBottomTubeGrowed(), tube.getPosBottomTubeGrowed().x, tube.getPosBottomTubeGrowed().y);
                            int costCannon = new TubeGrowed(0).getValue();
                            int costArrow = new ArrowTube(0).getValue();
                            int costFire = new FireTube(0).getValue();
                            float menuPositionX = tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2;
                            float menuPositionY = tube.getPosBotTube().y - 50;
                            float menuLeftPositionX = menuPositionX + towerBuildMenu.getWidth() / 4;
                            float menuRightPositionX = menuPositionX + towerBuildMenu.getWidth() * 3 / 4;
                            float menuTopPositionY = menuPositionY + towerBuildMenu.getHeight() * 3 / 4;
                            float menuBotPositionY = menuPositionY + towerBuildMenu.getHeight() / 4;
                            makeTowerBuildMenuBounds(menuPositionX, menuPositionY, tubes.indexOf(tube));
                            sb.draw(towerBuildMenu, menuPositionX, menuPositionY);
                            //TODO настроить чтобы не показывало башни, которые недоступны
                            if (money < costCannon & currentLevelNumber > 2) {
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
                            if (money < costFire & currentLevelNumber > 3) {
                                Color c = sb.getColor();
                                sb.setColor(0, 0, 0, 0.5f);
                                sb.draw(fireBullet, menuLeftPositionX - arrowBullet.getWidth() / 2, menuBotPositionY - arrowBullet.getHeight() / 2);
                                sb.setColor(c.r, c.g, c.b, 1f);
                            } else {
                                sb.draw(fireBullet, menuLeftPositionX - arrowBullet.getWidth() / 2, menuBotPositionY - arrowBullet.getHeight() / 2);
                                shortFont.draw(sb, "" + ((int) costFire), menuLeftPositionX - cannonBullet.getWidth() / 2, menuBotPositionY - cannonBullet.getHeight() / 2);
                            }
                        } else {
                            int cost = tube.getUpgradeCost();
                            float menuPositionX = tube.getPosBotTube().x - tube.getBottomTube().getWidth() / 2;
                            float menuPositionY = tube.getPosBotTube().y;
                            float menuLeftPositionX = menuPositionX + towerUpgradeMenu.getWidth() / 4;
                            float menuRightPositionX = menuPositionX + towerUpgradeMenu.getWidth() * 3 / 4;
                            float menuCenterPositionY = menuPositionY + towerUpgradeMenu.getHeight() / 2;
                            makeUpgradeTowerMenuBounds(menuPositionX, menuPositionY, tubes.indexOf(tube));
//                        sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                            sb.draw(towerUpgradeMenu, menuPositionX, menuPositionY);
                            if (money < cost) {
                                Color c = sb.getColor();
                                sb.setColor(0, 0, 0, 0.5f);
                                sb.draw(upgrade, menuLeftPositionX - upgrade.getWidth() / 2, menuCenterPositionY - upgrade.getHeight() / 2);
                                sb.setColor(c.r, c.g, c.b, 1f);
                            } else {
                                sb.draw(upgrade, menuLeftPositionX - upgrade.getWidth() / 2, menuCenterPositionY - upgrade.getHeight() / 2);
                                shortFont.draw(sb, "" + ((int) cost), menuLeftPositionX - upgrade.getWidth() / 2, menuCenterPositionY - upgrade.getHeight() / 2);
                            }
                            sb.draw(destroy, menuRightPositionX - destroy.getWidth() / 2, menuCenterPositionY - destroy.getHeight() / 2);
                        }
                    }
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
            font.draw(sb, "U:" + ((int) testUpgradeRect), 400, 40);
            font.draw(sb, "D:" + ((int) testDestroyRect), 300, 40);

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

    public void makeTowerBuildMenuBounds(float x, float y, int tubeIndex) {
        isPopupTowerBuildMenu = true;
        idOfTube = tubeIndex;
        rectLeftBot = new Rectangle(x, y, towerBuildMenu.getWidth() / 2, towerBuildMenu.getHeight() / 2);
        rectRightBot = new Rectangle(x + towerBuildMenu.getWidth() / 2, y, towerBuildMenu.getWidth() / 2, towerBuildMenu.getHeight() / 2);
        rectLeftTop = new Rectangle(x, y + towerBuildMenu.getHeight() / 2, towerBuildMenu.getWidth() / 2, towerBuildMenu.getHeight() / 2);
        rectRightTop = new Rectangle(x + towerBuildMenu.getWidth() / 2, y + towerBuildMenu.getHeight() / 2, towerBuildMenu.getWidth() / 2, towerBuildMenu.getHeight() / 2);
        //Gdx.input.setCursorPosition(0,0);
    }

    public void makeUpgradeTowerMenuBounds(float x, float y, int tubeIndex) {
        isPopupTowerUpgradeMenu = true;
        idOfTube = tubeIndex;
        upgradeRect = new Rectangle(x, y, towerUpgradeMenu.getWidth() / 2, towerUpgradeMenu.getHeight());
        destroyRect = new Rectangle(x + towerUpgradeMenu.getWidth() / 2, y, towerUpgradeMenu.getWidth() / 2, towerUpgradeMenu.getHeight());
    }

    public void makeRepairMenuBounds(float x, float y, int tubeIndex) {
        isPopupRepairMenu = true;
        idOfTube = tubeIndex;
        repairRect = new Rectangle(x, y, repairMenu.getWidth(), repairMenu.getHeight());
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
