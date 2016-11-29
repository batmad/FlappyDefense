package com.batmad.birddefense.core.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.batmad.birddefense.core.FlappyDefense;
import com.batmad.birddefense.core.Sprites.AnimationBird;
import com.batmad.birddefense.core.Sprites.ArrowTube;
import com.batmad.birddefense.core.Sprites.BirdFire;
import com.batmad.birddefense.core.Sprites.BirdZerg;
import com.batmad.birddefense.core.Sprites.Boss1;
import com.batmad.birddefense.core.Sprites.FastBird;
import com.batmad.birddefense.core.Sprites.SlowBird;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tm on 19.11.2015.
 */
public class PlayState extends State implements InputProcessor{
    private int TUBE_SPACING = 100;
    private int TUBE_COUNT = 4;
    private static int groundHeight;

    private com.batmad.birddefense.core.States.PlayStateOptions options;
    private com.batmad.birddefense.core.Sprites.Bird target;
    private Preferences prefs;

    private int highscore;
    private int lifes = 5;
    private int birdCount;
    private int birdDead = 0;
    private int birdDeadTotal;
    private int money = 350;
    private int score;
    private int testTouchLeftBot, testTouchRightBot, testTouchLeftTop, testTouchRightTop = 0;
    private int testUpgradeRect, testDestroyRect = 0;
    private int idOfTube;
    private int numberOfWaves;
    private int currentWave;
    private int currentMaxLevel;
    private int currentLevelNumber;
    private int countNextWaveStart = -1;

    private float btnAnimTime;
    private long countNextWaveStartTimeMillis;

    private String textStartWave;
    private String textBuildTower;
    private String textLevel;
    private String textWave;
    private String textLose;
    private String textWin;
    private String textCost;
    private String textDamage;

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
//    private boolean showMenuBuildTowerTouchUp;
//    private boolean showMenuUpgradeTowerTouchUp;
    private boolean isConquestAchievmentUnlocked;
    private boolean isWarAchievmentUnlocked;
    private boolean isFamineAchievmentUnlocked;
    private boolean isDeathAchievmentUnlocked;
    private boolean isApocalypseAchievmentUnlocked;
    private boolean isAdsRemoved;

    public Texture textureBird;
    public Texture textureBirdSprint;
    public Texture textureBirdSlow;
    public Texture textureBirdFast;
    public Texture textureBirdStupid;
    public Texture textureBirdHealer;
    public Texture textureBirdZerg;
    public Texture textureBirdTeleport;
    public Texture textureBirdAngry;
    public Texture textureBirdFire;
    public Texture textureBirdSuicide;
    public Texture textureBoss1;
    public Texture textureBoss2;
    public Texture textureBoss3;
    public Texture textureBoss4;
    public Texture textureBoss5;

    public Texture lifeTextureBird;
    public Texture lifeTextureBirdBoss;

    public Texture textureTopTubeClosed;
    public Texture textureBottomTubeClosed;
    public Texture textureDestoyedTube;
    public Texture textureTopTube;
    public Texture textureBottomTube;

    protected AnimationBird birdAnimation;
    protected AnimationBird birdAnimationSprint;
    protected AnimationBird birdAnimationSlow;
    protected AnimationBird birdAnimationFast;
    protected AnimationBird birdAnimationStupid;
    protected AnimationBird birdAnimationHealer;
    protected AnimationBird birdAnimationZerg;
    protected AnimationBird birdAnimationTeleport;
    protected AnimationBird birdAnimationAngry;
    protected AnimationBird birdAnimationFire;
    protected AnimationBird birdAnimationSuicide;
    protected AnimationBird birdAnimationBoss1;
    protected AnimationBird birdAnimationBoss2;
    protected AnimationBird birdAnimationBoss3;
    protected AnimationBird birdAnimationBoss4;
    protected AnimationBird birdAnimationBoss5;

    protected Sound flap;
    protected Sound teleportSound;
    private Sound soundLanded;
    private Sound soundKamikaze;
    protected Sound soundBulletArrow;
    protected Sound soundBulletCannon;
    private  Sound soundIncoming;

    private Texture background;
    private Texture ground;
    private Texture heart;
    private Texture coin;
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
    private Texture birdBullet;
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

    private ArrayList<com.batmad.birddefense.core.Sprites.Tube> tubes;
    private ArrayList<com.batmad.birddefense.core.Sprites.Bird> birds;
    private ArrayList<com.batmad.birddefense.core.Sprites.Bird> birdsInStock;
    private ArrayList<com.batmad.birddefense.core.Sprites.Bullet> bullets;
    private HashMap<Integer, com.batmad.birddefense.core.Sprites.Fire> fires;

    private BitmapFont font;
    private BitmapFont shortFont;
    private BitmapFont shortFontGrey;
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

    public PlayState(GameStateManager gsm, FlappyDefense game, com.batmad.birddefense.core.States.PlayStateOptions options) {
        super(gsm, game);
        prefs = Gdx.app.getPreferences("myPrefs");
        currentMaxLevel = prefs.getInteger("levels", 1);
        passTutorial = prefs.getBoolean("passTutorial", false);
        highscore = prefs.getInteger("highscore", 0);
        isConquestAchievmentUnlocked = prefs.getBoolean("isConquestAchievmentUnlocked", false);
        isWarAchievmentUnlocked = prefs.getBoolean("isWarAchievmentUnlocked", false);
        isFamineAchievmentUnlocked = prefs.getBoolean("isFamineAchievmentUnlocked", false);
        isDeathAchievmentUnlocked = prefs.getBoolean("isDeathAchievmentUnlocked", false);
        isApocalypseAchievmentUnlocked = prefs.getBoolean("isApocalypseAchievmentUnlocked", false);
        isAdsRemoved = prefs.getBoolean("adsRemoved", false);



        if (!passTutorial) {
            passTutorialBuildTower = false;
            passTutorialStartWave = false;
        }
        this.currentWave = 0;
        this.options = options;
        this.numberOfWaves = options.waves.length;
        this.currentLevelNumber = options.levelNumber;
        birdCount = options.waves[currentWave].numberOfBirds();

        textStartWave = myBundle.get("gameStartWave");
        textBuildTower = myBundle.get("gameBuildTower");
        textLevel = myBundle.get("gameLevel");
        textWave = myBundle.get("gameWave");
        textLose = myBundle.get("gameLose");
        textWin = myBundle.get("gameWin");
        textCost = myBundle.get("gameCost");
        textDamage = myBundle.get("gameDamage");

        win = Gdx.audio.newSound(Gdx.files.internal("win.ogg"));
        lose = Gdx.audio.newSound(Gdx.files.internal("lose.ogg"));

        cam.setToOrtho(false, FlappyDefense.WIDTH, FlappyDefense.HEIGHT);

        textureBird = new Texture("bird/birdanimation.png");
        textureBirdSprint = new Texture("bird/birdanimationsprint.png");
        textureBirdSlow = new Texture("bird/birdanimationslow.png");
        textureBirdFast = new Texture("bird/birdanimationfast.png");
        textureBirdStupid = new Texture("bird/birdanimationstupid.png");;
        textureBirdHealer = new Texture("bird/birdanimationhealer.png");
        textureBirdZerg = new Texture("bird/birdanimationzerg.png");
        textureBirdTeleport = new Texture("bird/birdanimationteleport.png");
        textureBirdAngry = new Texture("bird/birdanimationangry.png");
        textureBirdFire = new Texture("bird/birdanimationfire.png");
        textureBirdSuicide = new Texture("bird/birdanimationsuicide.png");
        textureBoss1 = new Texture("bird/boss1.png");
        textureBoss2 = new Texture("bird/boss2.png");
        textureBoss3 = new Texture("bird/boss3.png");
        textureBoss4 = new Texture("bird/boss4.png");
        textureBoss5 = new Texture("bird/boss5.png");

        textureTopTubeClosed = new Texture("toptubeclosed.png");
        textureBottomTubeClosed = new Texture("bottomtubeclosed.png");
        textureDestoyedTube = new Texture("bottomtubedestroyed.png");
        textureTopTube = new Texture("toptube.png");
        textureBottomTube = new Texture("bottomtube.png");

        lifeTextureBird = new Texture("bird/life.png");
        lifeTextureBirdBoss = new Texture("bird/bosslife.png");;

        birdAnimation = new AnimationBird(new TextureRegion(textureBird), 3, 0.5f);
        birdAnimationSprint = new AnimationBird(new TextureRegion(textureBirdSprint), 3, 0.5f);
        birdAnimationSlow = new AnimationBird(new TextureRegion(textureBirdSlow), 3, 0.5f);
        birdAnimationFast = new AnimationBird(new TextureRegion(textureBirdFast), 3, 0.5f);
        birdAnimationStupid = new AnimationBird(new TextureRegion(textureBirdStupid), 3, 0.5f);
        birdAnimationHealer = new AnimationBird(new TextureRegion(textureBirdHealer), 3, 0.5f);
        birdAnimationZerg = new AnimationBird(new TextureRegion(textureBirdZerg), 3, 0.5f);
        birdAnimationTeleport = new AnimationBird(new TextureRegion(textureBirdTeleport), 3, 0.5f);
        birdAnimationAngry = new AnimationBird(new TextureRegion(textureBirdAngry), 3, 0.5f);
        birdAnimationFire = new AnimationBird(new TextureRegion(textureBirdFire), 3, 0.5f);
        birdAnimationSuicide = new AnimationBird(new TextureRegion(textureBirdSuicide), 3, 0.5f);
        birdAnimationBoss1 = new AnimationBird(new TextureRegion(textureBoss1), 2, 0.01f);
        birdAnimationBoss2 = new AnimationBird(new TextureRegion(textureBoss2), 3, 0.5f);
        birdAnimationBoss3 = new AnimationBird(new TextureRegion(textureBoss3), 3, 0.5f);
        birdAnimationBoss4 = new AnimationBird(new TextureRegion(textureBoss4), 3, 0.5f);
        birdAnimationBoss5 = new AnimationBird(new TextureRegion(textureBoss5), 3, 0.5f);

        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        teleportSound = Gdx.audio.newSound(Gdx.files.internal("teleport.ogg"));
        soundLanded = Gdx.audio.newSound(Gdx.files.internal("landed.ogg"));
        soundKamikaze = Gdx.audio.newSound(Gdx.files.internal("kamikaze.ogg"));
        soundBulletArrow = Gdx.audio.newSound(Gdx.files.internal("arrow.ogg"));
        soundBulletCannon = Gdx.audio.newSound(Gdx.files.internal("cannon.ogg"));
        soundIncoming = Gdx.audio.newSound(Gdx.files.internal("incoming.ogg"));

        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        heart = new Texture("heart.png");
        coin = new Texture("coin.png");
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
        birdBullet = new Texture("missle.png");
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

        tubes = new ArrayList<com.batmad.birddefense.core.Sprites.Tube>();
        birds = new ArrayList<com.batmad.birddefense.core.Sprites.Bird>();
        birdsInStock = new ArrayList<com.batmad.birddefense.core.Sprites.Bird>();
        bullets = new ArrayList<com.batmad.birddefense.core.Sprites.Bullet>();
        fires = new HashMap<Integer, com.batmad.birddefense.core.Sprites.Fire>();

        generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 32;
        parameter.color = Color.GREEN;
        parameter.borderWidth = 0.3f;
        font = generator.generateFont(parameter);

        parameter.color = Color.WHITE;
        fontLetter = generator.generateFont(parameter);

        parameter.size = 12;
//        parameter.color = Color.FIREBRICK;
        parameter.color = new Color(0x706d4b);
        parameter.borderWidth = 0.1f;
        shortFont = generator.generateFont(parameter);

        parameter.color = new Color(0x706d4bff);
        shortFontGrey = generator.generateFont(parameter);

        generator.dispose();

        if (currentLevelNumber > 10) {
            TUBE_COUNT = 7;
            TUBE_SPACING = 45;
        }

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new com.batmad.birddefense.core.Sprites.Tube(i * (TUBE_SPACING + com.batmad.birddefense.core.Sprites.Tube.TUBE_WIDTH), textureTopTubeClosed, textureBottomTubeClosed, textureDestoyedTube,cannonBullet,soundBulletCannon));
            if (currentLevelNumber > 5)
                tubes.add(new com.batmad.birddefense.core.Sprites.Tube(i * (TUBE_SPACING + com.batmad.birddefense.core.Sprites.Tube.TUBE_WIDTH), textureTopTubeClosed, textureBottomTubeClosed, textureDestoyedTube,cannonBullet,soundBulletCannon, true));
        }
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    protected void handleInput() {
        if (touched(startWaveBounds) && !isTouched && currentWave < numberOfWaves && Gdx.input.justTouched()) {
            birdCreate();
            isTouched = true;
            countNextWaveStart = -1;
            passTutorialStartWave = true;
        } else if (wonGame) {
            if (!isPlayedWonSound) {
                win.play(0.1f);
                isPlayedWonSound = true;
            }
            if (touched(backBtnRect) && Gdx.input.justTouched()) {
                gsm.set(new MenuState(gsm, game));
            } else if (touched(replatBtnRect) && Gdx.input.justTouched()) {
                gsm.set(new PlayState(gsm, game, options));
            } else if (options.levelNumber < 15 && touched(nextLevelBtnRect) && Gdx.input.justTouched()) {
                if(!isAdsRemoved) {
                    FlappyDefense.playServices.showOrLoadInterstital();
                }
                com.batmad.birddefense.core.States.PlayStateOptions options = new Levels().getLevelOptions(this.options.levelNumber + 1);
                gsm.set(new PlayState(gsm, game, options));
            }
        } else if (loseGame) {
            if (!isPlayedWonSound) {
                lose.play(0.1f);
                isPlayedWonSound = true;
            }
            if (touched(backBtnRect) && Gdx.input.justTouched()) {
                gsm.set(new MenuState(gsm, game));
            } else if (touched(replatBtnRect) && Gdx.input.justTouched()) {
                gsm.set(new PlayState(gsm, game, options));
            }
        }else {
            if (isPopupTowerBuildMenu ) {
                passTutorialBuildTower = true;
                if (touched(rectRightTop) && Gdx.input.justTouched()) {
                    testTouchRightTop++;
                    com.batmad.birddefense.core.Sprites.Tube upgradeTube;
                    if (tubes.get(idOfTube).isTop)
                        upgradeTube = new ArrowTube(tubes.get(idOfTube).getPosTopTube().x,textureTopTube, textureBottomTube,textureDestoyedTube,arrowBullet, soundBulletArrow, true);
                    else
                        upgradeTube = new ArrowTube(tubes.get(idOfTube).getPosTopTube().x, textureTopTube, textureBottomTube,textureDestoyedTube,arrowBullet, soundBulletArrow);
                    if (money >= upgradeTube.getValue()) {
                        tubes.set(idOfTube, upgradeTube);
                        money -= tubes.get(idOfTube).getValue();
                    }
                    isPopupTowerBuildMenu = false;
                }
                if (touched(rectLeftTop) && Gdx.input.justTouched()) {
                    testTouchLeftTop++;
                    com.batmad.birddefense.core.Sprites.Tube upgradeTube;
                    if (tubes.get(idOfTube).isTop)
                        upgradeTube = new com.batmad.birddefense.core.Sprites.TubeGrowed(tubes.get(idOfTube).getPosTopTube().x, textureTopTube, textureBottomTube,textureDestoyedTube,cannonBullet, soundBulletCannon, true);
                    else
                        upgradeTube = new com.batmad.birddefense.core.Sprites.TubeGrowed(tubes.get(idOfTube).getPosTopTube().x, textureTopTube, textureBottomTube,textureDestoyedTube,cannonBullet, soundBulletCannon);
                    if (money >= upgradeTube.getValue()) {
                        tubes.set(idOfTube, upgradeTube);
                        money -= tubes.get(idOfTube).getValue();
                    }
                    isPopupTowerBuildMenu = false;
                }
                if (Gdx.input.justTouched() && touched(rectLeftBot)) {
                    testTouchLeftBot++;
                    com.batmad.birddefense.core.Sprites.Tube upgradeTube;
                    com.batmad.birddefense.core.Sprites.Fire fire;
                    if (tubes.get(idOfTube).isTop) {
                        upgradeTube = new com.batmad.birddefense.core.Sprites.FireTube(tubes.get(idOfTube).getPosTopTube().x, textureTopTube, textureBottomTube,textureDestoyedTube,cannonBullet, soundBulletCannon, true);
                        fire = new com.batmad.birddefense.core.Sprites.Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth() / 2, upgradeTube.getPosBotTube().y, upgradeTube.getDamage());
                    } else {
                        upgradeTube = new com.batmad.birddefense.core.Sprites.FireTube(tubes.get(idOfTube).getPosTopTube().x, textureTopTube, textureBottomTube,textureDestoyedTube,cannonBullet, soundBulletCannon);
                        fire = new com.batmad.birddefense.core.Sprites.Fire(upgradeTube.getPosBotTube().x + upgradeTube.getBottomTube().getWidth() / 2, upgradeTube.getPosBotTube().y + upgradeTube.getBottomTube().getHeight(), upgradeTube.getDamage());
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
            } else {
                if (isPopupTowerUpgradeMenu) {
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
                            if (tubes.get(idOfTube).getClass().getName().equals("FireTube")) {
                                com.batmad.birddefense.core.Sprites.Fire fire = fires.get(idOfTube);
                                fire.stop(Gdx.graphics.getDeltaTime());
                                fires.remove(idOfTube);
                            }
                            money += tubes.get(idOfTube).getTotalValue();
                            tubes.set(idOfTube, new com.batmad.birddefense.core.Sprites.Tube(tubes.get(idOfTube).getPosTopTube().x, textureTopTubeClosed, textureBottomTubeClosed, textureDestoyedTube,cannonBullet,soundBulletCannon, tubes.get(idOfTube).isTop));
                        }
                        testDestroyRect++;
                        isPopupTowerUpgradeMenu = false;
                    }
                }
                if (isPopupRepairMenu) {
                    if (touched(repairRect) && Gdx.input.justTouched()) {
                        if (money >= tubes.get(idOfTube).getRepairValue()) {
                            money -= tubes.get(idOfTube).getRepairValue();
                            tubes.get(idOfTube).repair();
                        }
                        isPopupRepairMenu = false;
                    }
                }
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
        com.batmad.birddefense.core.States.PlayStateOptions.Wave wave = options.waves[currentWave];
        for (Map.Entry<com.batmad.birddefense.core.States.PlayStateOptions.Bird, Integer> entry : wave.getMapOfBirds().entrySet()) {
            com.batmad.birddefense.core.States.PlayStateOptions.Bird birdType = entry.getKey();
            int numberOfBirds = entry.getValue();
            switch (birdType) {
                case Bird:
                case Bird2:
                case Bird3:
                case Bird4:
                case Bird5:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new com.batmad.birddefense.core.Sprites.Bird(70, 200, birdAnimation, lifeTextureBird, flap));
                    }
                    break;
                case SprintBird:
                case SprintBird2:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new com.batmad.birddefense.core.Sprites.SprintBird(70, 200, birdAnimationSprint, lifeTextureBird, flap));
                    }
                    break;
                case SlowBird:
                case SlowBird2:
                case SlowBird3:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new SlowBird(70, 200, birdAnimationSlow, lifeTextureBird, flap));
                    }
                    break;
                case FastBird:
                case FastBird2:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new FastBird(70, 200, birdAnimationFast, lifeTextureBird, flap));
                    }
                    break;
                case BirdStupid:
                case BirdStupid2:
                case BirdStupid3:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new com.batmad.birddefense.core.Sprites.BirdStupid(70, 200, birdAnimationStupid, lifeTextureBird, flap));
                    }
                    break;
                case BirdHealer:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add((birdsInStock.size() / numberOfBirds) * j, new com.batmad.birddefense.core.Sprites.BirdHealer(70, 200, birdAnimationHealer, lifeTextureBird, flap));
                    }
                    break;
                case BirdZerg:
                case BirdZerg2:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birds.add(new BirdZerg(70, 200, birdAnimationZerg, lifeTextureBird, flap));
                    }
                    break;
                case BirdTeleport:
                case BirdTeleport2:
                case BirdTeleport3:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add((birdsInStock.size() / numberOfBirds) * j, new com.batmad.birddefense.core.Sprites.BirdTeleport(70, 200, birdAnimationTeleport, lifeTextureBird, flap, teleportSound));
                    }
                    break;
                case BirdAngry:
                case BirdAngry2:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add((birdsInStock.size() / numberOfBirds) * j, new com.batmad.birddefense.core.Sprites.BirdAngry(70, 200, birdAnimationAngry, lifeTextureBird, flap, birdBullet, soundIncoming, soundLanded));
                    }
                    break;
                case BirdFire:
                case BirdFire2:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new BirdFire(70, 200, birdAnimationFire, lifeTextureBird, flap));
                    }
                    break;
                case BirdSuicide:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add((birdsInStock.size() / numberOfBirds) * j, new com.batmad.birddefense.core.Sprites.BirdSuicide(70, 200, birdAnimationSuicide, lifeTextureBird, flap,soundLanded, soundKamikaze));
                    }
                    break;
                case Boss1:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new Boss1(70, 200, birdAnimationBoss1, lifeTextureBirdBoss, flap));
                    }
                    break;
                case Boss2:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new com.batmad.birddefense.core.Sprites.Boss2(70, 200, birdAnimationBoss2, lifeTextureBirdBoss, flap));
                    }
                    break;
                case Boss3:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new com.batmad.birddefense.core.Sprites.Boss3(70, 200, birdAnimationBoss3, lifeTextureBirdBoss, flap));
                    }
                    break;
                case Boss4:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new com.batmad.birddefense.core.Sprites.Boss4(70, 200, birdAnimationBoss4, lifeTextureBirdBoss, flap));
                    }
                    break;
                case Boss5:
                    for (int j = 0; j < numberOfBirds; j++) {
                        i++;
                        birdsInStock.add(new com.batmad.birddefense.core.Sprites.Boss5(70, 200, birdAnimationBoss5, lifeTextureBirdBoss, flap));
                    }
                    break;
            }
        }
        target = birdsInStock.get(0);
    }

    private com.batmad.birddefense.core.Sprites.Bird getLastBird() {
        return birds.get(birds.size() - 1);
    }


    @Override
    public void update(float dt) {
        if (!birdsInStock.isEmpty()) {
            if (birds.isEmpty()) {
                birds.add(birdsInStock.get(0));
                birdsInStock.remove(0);
            } else if (getLastBird().getPosition().x > 0) {
                birds.add(birdsInStock.get(0));
                birdsInStock.remove(0);
            }
        }
        handleInput();
        countNextWaveStart();

        btnAnimTime += dt;
        birdDead = 0;
        for (com.batmad.birddefense.core.Sprites.Bird bird : birds) {
            if (!bird.isDead()) {
                bird.update(dt);
                if (bird.getPosition().x > FlappyDefense.WIDTH && !bird.isOverEdge()) {
                    lifes--;
                    bird.setIsOverEdge(true);
                    bird.setIsDead(true);
                }
//                int idOfTube = 0;
                for (com.batmad.birddefense.core.Sprites.Tube tube : tubes) {
                    if (!tube.isBroken & !tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Tube") & !tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.FireTube") & tube.collides(bird.getBounds()) & System.currentTimeMillis() > tube.getClearSky()) {
                        tube.setClearSky(System.currentTimeMillis() + tube.getFireRate());
                        com.batmad.birddefense.core.Sprites.Bullet bullet = tube.fire(bird.getBounds());
                        bullet.sound();
                        bullets.add(bullet);
                    }
                    if (bird.getClass().getName().contains("BirdAngry") & !tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Tube")) {
                        com.batmad.birddefense.core.Sprites.BirdAngry birdAngry = (com.batmad.birddefense.core.Sprites.BirdAngry) bird;
                        if (!birdAngry.isFired() & !tube.isBroken & birdAngry.collides(tube.getBoundsBot())) {
                            com.batmad.birddefense.core.Sprites.Bullet bullet = birdAngry.fire(tube.getBoundsBot());
                            bullet.sound();
                            bullets.add(bullet);
                        }
                    }
                    if (bird.getClass().getName().equals("com.batmad.birddefense.core.Sprites.BirdSuicide") & !tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Tube")) {
                        com.batmad.birddefense.core.Sprites.BirdSuicide birdSuicide = (com.batmad.birddefense.core.Sprites.BirdSuicide) bird;
                        if (!birdSuicide.isHasTarget() & !tube.isBroken & birdSuicide.inAttackRange(tube.getBoundsBot())) {
                            birdSuicide.setTarget(tube.getBoundsBot());
//                            birdSuicide.sound();
                        }
                        if (!tube.isBroken & birdSuicide.collides(tube.getBoundsBot())) {
                            birdSuicide.playLanded();
                            birdSuicide.setIsDead(true);
                            tube.isBroken = true;
                            if (tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.FireTube")) {
                                com.batmad.birddefense.core.Sprites.Fire fire = fires.get(tubes.indexOf(tube));
                                fire.stop(dt);
                            }
                        }
                    }

//                    idOfTube++;
                }
                for (com.batmad.birddefense.core.Sprites.Bullet bullet : bullets) {
                    //bullet.update(dt);
                    if (!bullet.isFired()) {
                        if (bullet.collides(bird.getBounds())) {
                            bird.loseLife(bullet.getDamage(), bullet.getShieldPiercer(), bullet.getFireDamage());
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
                    if(!isConquestAchievmentUnlocked & bird.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Boss1")) {
                        FlappyDefense.playServices.unlockAchievement("achievement_conquest");
                        prefs.putBoolean("isConquestAchievmentUnlocked", true);
                        prefs.flush();
                    } else if(!isWarAchievmentUnlocked & bird.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Boss2")) {
                        FlappyDefense.playServices.unlockAchievement("achievement_war");
                        prefs.putBoolean("isWarAchievmentUnlocked", true);
                        prefs.flush();
                    }else if(!isFamineAchievmentUnlocked & bird.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Boss3")) {
                        FlappyDefense.playServices.unlockAchievement("achievement_famine");
                        prefs.putBoolean("isFamineAchievmentUnlocked", true);
                        prefs.flush();
                    }else if(!isDeathAchievmentUnlocked & bird.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Boss4")) {
                        FlappyDefense.playServices.unlockAchievement("achievement_death");
                        prefs.putBoolean("isDeathAchievmentUnlocked", true);
                        prefs.flush();
                    }else if(!isApocalypseAchievmentUnlocked & bird.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Boss5")) {
                        FlappyDefense.playServices.unlockAchievement("achievement_apocalypse");
                        prefs.putBoolean("isApocalypseAchievmentUnlocked", true);
                        prefs.flush();
                    }
                    birdDeadTotal++;
                }

                if (bird.getClass().getName().contains("BirdHealer")) {
                    com.batmad.birddefense.core.Sprites.BirdHealer birdHealer = (com.batmad.birddefense.core.Sprites.BirdHealer) bird;
                    for (com.batmad.birddefense.core.Sprites.Bird birdToHeal : birds) {
                        if (birdToHeal.getLifes() < birdToHeal.getBirdLifesMax() & birdHealer.collides(birdToHeal.getBounds()) & System.currentTimeMillis() > bird.getLastHealedTimeStamp()) {
                            if(birdToHeal.getLifes() + birdHealer.heal < birdToHeal.getBirdLifesMax() ) {
                                birdToHeal.healLife(birdHealer.heal);
                                bird.setLastHealedTimeStamp(System.currentTimeMillis() + birdHealer.getHealRate());
                            }else {
                                birdToHeal.healLife(birdToHeal.getBirdLifesMax() - birdToHeal.getLifes());
                                bird.setLastHealedTimeStamp(System.currentTimeMillis() + birdHealer.getHealRate());
                            }
                        }
                    }
                }

            } else {
                birdDead++;
            }
        }

        if (!birds.isEmpty()) {
            int idOfTube = 0;
            for (com.batmad.birddefense.core.Sprites.Tube tube : tubes) {
                if (tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.FireTube")) {
                    for (com.batmad.birddefense.core.Sprites.Bird bird : birds) {
                        if (!bird.isDead() && tube.collides(bird.getBounds())) {
                            if (!tube.isHasTarget()) {
                                tube.setTarget(bird.getBounds());
                                tube.setHasTarget(true);
                            }
                            com.batmad.birddefense.core.Sprites.Fire fire = fires.get(idOfTube);
                            fire.setTarget(tube.getTarget());
                            fire.start();
                            fire.update(dt);
                            if (System.currentTimeMillis() > tube.getClearSky()) {
                                bird.loseLife(fire.getDamage(), fire.getShieldPiercer(), fire.getFireDamage());
                                tube.setClearSky(System.currentTimeMillis() + fire.getNoDamageTime());
                            }
                        } else if (bird == target) {
                            com.batmad.birddefense.core.Sprites.Fire fire = fires.get(idOfTube);
                            tube.setHasTarget(false);
                            fire.stop(dt);
                        } else {
                            com.batmad.birddefense.core.Sprites.Fire fire = fires.get(idOfTube);
                            fire.stop(dt);
                        }
                    }
                }
                idOfTube++;
            }
        } else {
            int idOfTube = 0;
            for (com.batmad.birddefense.core.Sprites.Tube tube : tubes) {
                if (tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.FireTube")) {
                    com.batmad.birddefense.core.Sprites.Fire fire = fires.get(idOfTube);
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

        for (com.batmad.birddefense.core.Sprites.Bullet bullet : bullets) {
            if (!bullet.isFired() && !bullet.isDead()) {
                bullet.update(dt);
            }
            if (!bullet.isFired() && !bullet.isDead() && bullet.getClass().getName().contains("Bird")) {
                com.batmad.birddefense.core.Sprites.BulletBird bulletBird = (com.batmad.birddefense.core.Sprites.BulletBird) bullet;
                for (com.batmad.birddefense.core.Sprites.Tube tube : tubes) {
                    if (!tube.isBroken & bullet.collides(tube.getBoundsBot())) {
                        tube.isBroken = true;
                        bulletBird.setIsFired(true);
                        bulletBird.playLanded();
                        if (tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.FireTube")) {
                            com.batmad.birddefense.core.Sprites.Fire fire = fires.get(tubes.indexOf(tube));
                            fire.stop(dt);
                        }
                    }
                }
            }
        }



        // }
        if (lifes < 0) {
            loseGame = true;
            birds = new ArrayList<com.batmad.birddefense.core.Sprites.Bird>();
        }
//        cam.update();
        if (passTutorialBuildTower && passTutorialStartWave) {
            passTutorial = true;
            prefs.putBoolean("passTutorial", true);
            prefs.flush();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
//        sb.setProjectionMatrix(cam.combined);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(heart, 717, 12);
        sb.draw(coin, 617, 12);
        font.draw(sb, "" + ((int) lifes), 750, 40);
        font.draw(sb, "" + ((int) money), 650, 40);
        font.draw(sb, textLevel + " " + currentLevelNumber + " " + textWave + " " + (currentWave+1), 10, 40);

        if (currentLevelNumber > 5) {
            sb.draw(groundAbove, 0, FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET);
            sb.draw(groundAbove, ground.getWidth(), FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET);
            sb.draw(groundAbove, 2 * ground.getWidth(), FlappyDefense.HEIGHT - FlappyDefense.GROUND_Y_OFFSET);
        }
        if (wonGame) {
            sb.draw(menu, cam.position.x - menu.getWidth() / 2, cam.position.y - menu.getHeight() / 2);
            fontLetter.draw(sb, textWin, cam.position.x - setFontWidth(fontLetter, textWin) / 2, FlappyDefense.HEIGHT - 30);
            font.draw(sb, String.valueOf(score), cam.position.x, cam.position.y + 15);
            font.draw(sb, String.valueOf(birdDeadTotal), cam.position.x, cam.position.y - 45);
            sb.draw(backBtn, cam.position.x - 3 * replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
            sb.draw(replayBtn, cam.position.x - replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
            if (options.levelNumber < 15)
                sb.draw(nextLevelBtn, cam.position.x + replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
        } else if (loseGame) {
            sb.draw(menu, cam.position.x - menu.getWidth() / 2, cam.position.y - menu.getHeight() / 2);
            fontLetter.draw(sb, textLose, cam.position.x - setFontWidth(fontLetter,textLose) / 2, FlappyDefense.HEIGHT - 30);
            font.draw(sb, String.valueOf(score), cam.position.x, cam.position.y + 15);
            font.draw(sb, String.valueOf(birdDeadTotal), cam.position.x, cam.position.y - 45);
            sb.draw(backBtn, cam.position.x - 3 * replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
            sb.draw(replayBtn, cam.position.x - replayBtn.getWidth() / 2, cam.position.y - menu.getHeight() / 2 + 50);
        } else {
            if (!isTouched) {
                sb.draw(startWaveBtn, 10, FlappyDefense.HEIGHT / 2);
            } else {
                sb.draw(buttonClicked.getKeyFrame(btnAnimTime), 10, FlappyDefense.HEIGHT / 2);
            }

            for (com.batmad.birddefense.core.Sprites.Bird bird : birds) {
                if (!bird.isDead()) {
                    sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
                    sb.setColor(Color.RED);
                    sb.draw(bird.getLifeTexture(), bird.getPosition().x + bird.getBounds().getWidth() / 2 - bird.getLifeTexture().getWidth() / 2, bird.getPosition().y - 5);
                    sb.setColor(Color.WHITE);
                    sb.draw(bird.getLifeTexture(), bird.getPosition().x + bird.getBounds().getWidth() / 2 - bird.getLifeTexture().getWidth() / 2, bird.getPosition().y - 5, 0, 0, bird.getLifePercentage(), 2);
                }
            }
            for (com.batmad.birddefense.core.Sprites.Tube tube : tubes) {
                //draw the Top Tube
//            if(touched(tube.getBoundsTop())){
//                //sb.draw(tube.getTopTubeGrowed(), tube.getPosTopTubeGrowed().x, tube.getPosTopTubeGrowed().y);
//                makeTowerBuildMenuBounds(tube.getPosTopTube().x - tube.getTopTube().getWidth()/2, FlappyDefense.HEIGHT - towerBuildMenu.getHeight(), tubes.indexOf(tube));
//                sb.draw(towerBuildMenu, tube.getPosTopTube().x - tube.getTopTube().getWidth()/2, FlappyDefense.HEIGHT - towerBuildMenu.getHeight());
//            }
//            else {
//                sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
//            }
                if (tube.isBroken) {
                    sb.draw(tube.getDestoyedTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                    int cost = tube.getRepairValue();
                    float menuPositionX = tube.getPosBotTube().x + tube.getBottomTube().getWidth() / 2 - repairMenu.getWidth() / 2;
                    float menuPositionY = tube.getPosBotTube().y + repairMenu.getHeight();
                    float menuCenterPositionX = menuPositionX + repairMenu.getWidth() / 2;
                    float menuCenterPositionY = menuPositionY + repairMenu.getHeight() / 2;
                    if(money > cost){
                        sb.draw(repair, menuCenterPositionX - repair.getWidth() / 2, menuCenterPositionY - repair.getHeight() / 2);
                    }
                    if (touched(tube.getBoundsBot())) {
                        if(Gdx.input.justTouched())
                            makeRepairMenuBounds(menuPositionX, menuPositionY, tubes.indexOf(tube));
                        sb.draw(repairMenu, menuPositionX, menuPositionY);
                        if (money < cost) {
                            Color c = sb.getColor();
                            sb.setColor(0, 0, 0, 0.5f);
                            sb.draw(repair, menuCenterPositionX - repair.getWidth() / 2, menuCenterPositionY - repair.getHeight() / 2);
                            shortFontGrey.draw(sb, "" + ((int) cost), menuCenterPositionX - cannonBullet.getWidth() / 2, menuCenterPositionY - cannonBullet.getHeight() / 2);
                            sb.setColor(c.r, c.g, c.b, 1f);
                        } else {
                            sb.draw(repair, menuCenterPositionX - repair.getWidth() / 2, menuCenterPositionY - repair.getHeight() / 2);
                            shortFont.draw(sb, "" + ((int) cost), menuCenterPositionX - cannonBullet.getWidth() / 2, menuCenterPositionY - cannonBullet.getHeight() / 2);
                        }
                    }
                } else {
                    sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                    int costCannon = tube.getValueCannon();
                    int costArrow = tube.getValueArrow();
                    int costFire = tube.getValueFire();
                    int damageCannon = tube.getDamageCannon();
                    int damageFire = tube.getDamageFire();
                    int damageArrow = tube.getDamageArrow();
                    float menuPositionX = tube.getPosBotTube().x + tube.getBottomTube().getWidth() / 2 - towerBuildMenu.getWidth() / 2;
                    float menuPositionY;
                    if(tube.isTop) {
                        menuPositionY = tube.getPosBotTube().y - 120;
                    }
                    else{
                        menuPositionY = tube.getPosBotTube().y - 50;
                    }
                    float menuLeftPositionX = menuPositionX + towerBuildMenu.getWidth() / 4;
                    float menuRightPositionX = menuPositionX + towerBuildMenu.getWidth() * 3 / 4;
                    float menuTopPositionY = menuPositionY + towerBuildMenu.getHeight() * 9 / 10;
                    float menuBotPositionY = menuPositionY + towerBuildMenu.getHeight() * 4 / 10;
                    if (!tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Tube") & money > tube.getUpgradeCost()) {
                        sb.draw(upgrade, tube.getPosBotTube().x + tube.getBottomTube().getWidth() / 2 - upgrade.getWidth() /2, tube.getPosBotTube().y + tube.getBottomTube().getHeight() / 2 - upgrade.getHeight() / 2);
                    }
                    if (touched(tube.getBoundsBot())) {
                        if (tube.getClass().getName().equals("com.batmad.birddefense.core.Sprites.Tube")) {
                            //sb.draw(tube.getBottomTubeGrowed(), tube.getPosBottomTubeGrowed().x, tube.getPosBottomTubeGrowed().y);
//                            showMenuBuildTowerTouchUp = false;
                            if(Gdx.input.justTouched())
                                makeTowerBuildMenuBounds(menuPositionX, menuPositionY, tubes.indexOf(tube));
                            sb.draw(towerBuildMenu, menuPositionX, menuPositionY);
                            if (money < costCannon) {
                                Color c = sb.getColor();
                                sb.setColor(0, 0, 0, 0.5f);
                                sb.draw(cannonBullet, menuLeftPositionX - cannonBullet.getWidth() / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                                shortFontGrey.draw(sb, "" + ((int) costCannon), menuLeftPositionX - setFontWidth(shortFontGrey, Integer.toString(costCannon)) / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                                sb.setColor(c.r, c.g, c.b, 1f);
                            } else {
                                sb.draw(cannonBullet, menuLeftPositionX - cannonBullet.getWidth() / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                                shortFont.draw(sb, textCost + " " + costCannon, menuLeftPositionX - setFontWidth(shortFont, textCost + " " + Integer.toString(costCannon)) / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                                shortFont.draw(sb, textDamage + " " + damageCannon, menuLeftPositionX - setFontWidth(shortFont, textDamage + " " + Integer.toString(damageCannon) ) / 2, menuTopPositionY - cannonBullet.getHeight() / 2 - 10);
                            }
                            if (money < costArrow) {
                                Color c = sb.getColor();
                                sb.setColor(0, 0, 0, 0.5f);
                                sb.draw(arrowBullet, menuRightPositionX - arrowBullet.getWidth() / 2, menuTopPositionY - arrowBullet.getHeight() / 2);
                                shortFontGrey.draw(sb, "" + ((int) costArrow), menuRightPositionX - setFontWidth(shortFontGrey, Integer.toString(costArrow)) / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                                sb.setColor(c.r, c.g, c.b, 1f);
                            } else {
                                sb.draw(arrowBullet, menuRightPositionX - arrowBullet.getWidth() / 2, menuTopPositionY - arrowBullet.getHeight() / 2);
                                shortFont.draw(sb, textCost + " " + ((int) costArrow), menuRightPositionX - setFontWidth(shortFont, textCost + " " + Integer.toString(costArrow)) / 2, menuTopPositionY - cannonBullet.getHeight() / 2);
                                shortFont.draw(sb, textDamage + " " + ((int) damageArrow), menuRightPositionX - setFontWidth(shortFont, textDamage + " " + Integer.toString(damageArrow)) / 2, menuTopPositionY - cannonBullet.getHeight() / 2 - 10);
                            }
                            if (money < costFire) {
                                Color c = sb.getColor();
                                sb.setColor(0, 0, 0, 0.5f);
                                sb.draw(fireBullet, menuLeftPositionX - fireBullet.getWidth() / 2, menuBotPositionY - fireBullet.getHeight() / 2);
                                shortFontGrey.draw(sb, "" + ((int) costFire), menuLeftPositionX - fireBullet.getWidth() / 2, menuBotPositionY - fireBullet.getHeight() / 2);
                                sb.setColor(c.r, c.g, c.b, 1f);
                            } else {
                                sb.draw(fireBullet, menuLeftPositionX - arrowBullet.getWidth() / 2, menuBotPositionY - arrowBullet.getHeight() / 2);
                                shortFont.draw(sb, textCost + " " + ((int) costFire), menuLeftPositionX - setFontWidth(shortFont, textCost + " " + Integer.toString(costFire)) / 2, menuBotPositionY - cannonBullet.getHeight() / 2);
                                shortFont.draw(sb, textDamage + " " + ((int) damageFire), menuLeftPositionX - setFontWidth(shortFont, textDamage + " " + Integer.toString(damageFire)) / 2, menuBotPositionY - cannonBullet.getHeight() / 2 - 10);
                            }
                        } else {
                            int costUpgrade = tube.getUpgradeCost();
                            menuPositionX = tube.getPosBotTube().x + tube.getBottomTube().getWidth() / 2 - towerUpgradeMenu.getWidth() / 2;
                            menuPositionY = tube.getPosBotTube().y;
                            menuLeftPositionX = menuPositionX + towerUpgradeMenu.getWidth() / 4;
                            menuRightPositionX = menuPositionX + towerUpgradeMenu.getWidth() * 3 / 4;
                            float menuCenterPositionY = menuPositionY + towerUpgradeMenu.getHeight() * 7 / 10;
//                            showMenuUpgradeTowerTouchUp = false;
                            if(Gdx.input.justTouched())
                                makeUpgradeTowerMenuBounds(menuPositionX, menuPositionY, tubes.indexOf(tube));
//                        sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                            sb.draw(towerUpgradeMenu, menuPositionX, menuPositionY);
                            if (money < costUpgrade) {
                                Color c = sb.getColor();
                                sb.setColor(0, 0, 0, 0.5f);
                                sb.draw(upgrade, menuLeftPositionX - upgrade.getWidth() / 2, menuCenterPositionY - upgrade.getHeight() / 2);
                                shortFontGrey.draw(sb, "" + ((int) costUpgrade), menuLeftPositionX - setFontWidth(shortFontGrey,  Integer.toString(costUpgrade)) / 2, menuCenterPositionY - cannonBullet.getHeight() / 2 - 5);
                                sb.setColor(c.r, c.g, c.b, 1f);
                            } else {
                                sb.draw(upgrade, menuLeftPositionX - upgrade.getWidth() / 2, menuCenterPositionY - upgrade.getHeight() / 2);
                                shortFont.draw(sb, textCost + " " + ((int) costUpgrade), menuLeftPositionX - setFontWidth(shortFont, textCost + " " + Integer.toString(costUpgrade)) / 2, menuCenterPositionY - cannonBullet.getHeight() / 2 - 5);
                            }
                            sb.draw(destroy, menuRightPositionX - destroy.getWidth() / 2, menuCenterPositionY - destroy.getHeight() / 2);
                        }
                    }
                }
            }
            for (com.batmad.birddefense.core.Sprites.Bullet bullet : bullets) {
                if (!bullet.isFired() && !bullet.isDead()) {
                    if (bullet.getClass().getName().equals("com.batmad.birddefense.core.Sprites.ArrowBullet")) {
                        sb.draw(new TextureRegion(bullet.getTexture()), bullet.getPosition().x, bullet.getPosition().y, bullet.getTexture().getWidth() / 2, bullet.getTexture().getHeight() / 2, bullet.getTexture().getWidth(), bullet.getTexture().getHeight(), 1, 1, 200 + bullet.angle());
                    } else {
                        sb.draw(bullet.getTexture(), bullet.getPosition().x, bullet.getPosition().y);
                    }
                }
            }
            for (Map.Entry<Integer, com.batmad.birddefense.core.Sprites.Fire> entry : fires.entrySet()) {
                Integer key = entry.getKey();
                com.batmad.birddefense.core.Sprites.Fire fire = entry.getValue();
                fire.getFire().draw(sb);
            }


            if (countNextWaveStart > 0)
                font.draw(sb, String.valueOf(countNextWaveStart), 0, 200);
        }
        if (!passTutorial) {
            if (!passTutorialStartWave) {
                sb.draw(pointer, 10 + startWaveBtn.getWidth() - 30, cam.position.y + startWaveBtn.getHeight() - 30);
                font.draw(sb, textStartWave, cam.position.x - 160, cam.position.y + 140);
            }
            if (!passTutorialBuildTower) {
                sb.draw(pointer, 190, 80);
                font.draw(sb, textBuildTower, cam.position.x - 50, cam.position.y - 50);
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
        birds = new ArrayList<com.batmad.birddefense.core.Sprites.Bird>();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            gsm.set(new MenuState(gsm, game));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        showMenuBuildTowerTouchUp = true;
//        showMenuUpgradeTowerTouchUp = true;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public float setFontWidth(BitmapFont font, String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.width;
    }


    @Override
    public void dispose() {
        highscore = highscore + score;
        prefs.putInteger("highscore", highscore);
        prefs.flush();
        FlappyDefense.playServices.submitScore(highscore);
        birds.clear();
        bullets.clear();
        tubes.clear();
//        background.dispose();
//        ground.dispose();
//        for (Tube tube : tubes) {
//            tube.dispose();
//        }
//        for (Bird bird : birds) {
//            bird.dispose();
//        }
//        for(Bullet bullet: bullets){
//            bullet.dispose();
//        }
//        menu.dispose();
//        towerBuildMenu.dispose();
//        towerUpgradeMenu.dispose();
//        repairMenu.dispose();
//        upgrade.dispose();
//        destroy.dispose();
//        repair.dispose();
//        cannonBullet.dispose();
//        arrowBullet.dispose();
//        fireBullet.dispose();
//        startWaveBtn.dispose();
//        replayBtn.dispose();
//        nextLevelBtn.dispose();
//        backBtn.dispose();
//        pointer.dispose();
//        win.dispose();
//        lose.dispose();
        System.out.println("Play state disposed");
    }

}
