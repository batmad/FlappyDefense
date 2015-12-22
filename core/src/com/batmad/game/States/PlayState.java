package com.batmad.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.batmad.game.FlappyDefense;
import com.batmad.game.Sprites.ArrowTube;
import com.batmad.game.Sprites.Bird;
import com.batmad.game.Sprites.Bullet;
import com.batmad.game.Sprites.Tube;
import com.batmad.game.Sprites.TubeGrowed;

import java.util.ArrayList;

/**
 * Created by tm on 19.11.2015.
 */
public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static  int groundHeight;

    private int lifes = 20;
    private int birdCount = 15;
    private int money = 350;
    private int testTouchLeftBot, testTouchRightBot, testTouchLeftTop, testTouchRightTop = 0;
    private int idOfTube;

    private boolean isTouched = false;

    private Texture background;
    private Texture ground;
    private Texture towerUpgradeMenu;
    private Texture startWaveBtn;
    private Vector2 groundPos1, groundPos2;
    private Rectangle startWaveBounds;
    private Animation buttonClicked;
    private float btnAnimTime;

    private ArrayList<Tube> tubes;
    private ArrayList<Bird> birds;
    private ArrayList<Bullet> bullets;

    private BitmapFont font;

    private Rectangle rectLeftBot,rectRightBot,rectLeftTop,rectRightTop;
    private boolean isPopup;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDefense.WIDTH, FlappyDefense.HEIGHT);

        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        towerUpgradeMenu = new Texture("towerupgrademenu.png");
        startWaveBtn = new Texture("playbtn.png");
        startWaveBounds = new Rectangle(10,FlappyDefense.HEIGHT/2,startWaveBtn.getWidth(),startWaveBtn.getHeight());

        buttonClicked = new Animation(0.1f, new TextureRegion(new Texture("playbtn.png")),
                new TextureRegion(new Texture("playbtn1.png")),
                new TextureRegion(new Texture("playbtn2.png")),
                new TextureRegion(new Texture("playbtn3.png"))
        );

        groundHeight = FlappyDefense.GROUND_Y_OFFSET + ground.getHeight();

        tubes = new ArrayList<Tube>();
        birds = new ArrayList<Bird>();
        bullets = new ArrayList<Bullet>();

        font = new BitmapFont(Gdx.files.internal("berlin-40.fnt"));

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

    }

    @Override
    protected void handleInput() {
        //if(Gdx.input.justTouched()){
            //bird.jump();
        //}
        if(touched(startWaveBounds) && !isTouched){
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
        for(int i = 1; i <= birdCount; i++) {
            birds.add(new Bird(i * 3 * 20));
        }
    }

    private Bird getLastBird(){
        return birds.get(birds.size()-1);
    }


    @Override
    public void update(float dt) {
        handleInput();

        btnAnimTime += dt;

        for(Bird bird: birds) {
            if(!bird.isDead()) {
                bird.update(dt);
                if (bird.getPosition().x > FlappyDefense.WIDTH && !bird.isOverEdge()) {
                    lifes--;
                    bird.setIsOverEdge(true);
                }

                for (Tube tube : tubes) {
                    if (tube.collides(bird.getBounds())) {
                        if (System.currentTimeMillis() > tube.getClearSky()) {
                            if (!tube.getClass().getName().equals("com.batmad.game.Sprites.Tube")){
                                System.out.println(tube.getClass().getName());
                                tube.setClearSky(System.currentTimeMillis() + tube.getFireRate());
                                Bullet bullet = tube.fire(bird.getBounds());
                                bullets.add(bullet);
                                //bird.dispose();
                            }
                        }
                    }
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
                    bird.dispose();
                    bird.setIsDead(true);
                }
            }
        }

        for(Bullet bullet:bullets){
            if(!bullet.isFired()) {
                bullet.update(dt);
            }
        }

        //for(int i = 0; i < tubes.size(); i++){

            //if(tubes.get(i).collides(bird.getBounds()))
                //gsm.set(new MenuState(gsm));
            if(isPopup){
                if(touched(rectRightTop)) {
                    testTouchRightTop++;
                    tubes.set(idOfTube, new ArrowTube(tubes.get(idOfTube).getPosTopTube().x + 10));
                    isPopup = false;
                }
                if(touched(rectLeftTop)) {
                    testTouchLeftTop++;
                    tubes.set(idOfTube,new ArrowTube(tubes.get(idOfTube).getPosTopTube().x));
                    isPopup = false;
                }
                if(touched(rectLeftBot)) {
                    testTouchLeftBot++;
                    tubes.set(idOfTube,new ArrowTube(tubes.get(idOfTube).getPosTopTube().x));
                    isPopup = false;
                }
                if(touched(rectRightBot)) {
                    testTouchRightBot++;
                    tubes.set(idOfTube,new ArrowTube(tubes.get(idOfTube).getPosTopTube().x));
                    isPopup = false;
                }
            }
       // }

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
            if(touched(tube.getBoundsBot())){
                //sb.draw(tube.getBottomTubeGrowed(), tube.getPosBottomTubeGrowed().x, tube.getPosBottomTubeGrowed().y);
                makeMenuBounds(tube.getPosBotTube().x - tube.getBottomTube().getWidth()/2,groundHeight, tubes.indexOf(tube) );
                sb.draw(towerUpgradeMenu, tube.getPosBotTube().x - tube.getBottomTube().getWidth()/2, groundHeight);
            }
            else {
                sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, groundHeight);
            }
        }
        for(Bullet bullet: bullets) {
            if(!bullet.isFired()) {
                if(bullet.getClass().getName().equals("com.batmad.game.Sprites.ArrowBullet")){
                    sb.draw(new TextureRegion(bullet.getTexture()),bullet.getPosition().x,bullet.getPosition().y, bullet.getTexture().getWidth()/2, bullet.getTexture().getHeight()/2, bullet.getTexture().getWidth(),bullet.getTexture().getHeight(),1,1,200 + bullet.angle());
                }else {
                    sb.draw(bullet.getTexture(), bullet.getPosition().x, bullet.getPosition().y);
                }
            }

        }

        font.draw(sb, "" + ((int)lifes), 750,40);
        font.draw(sb, "" + ((int)money), 650,40);
        font.draw(sb, "LB:" + ((int)testTouchLeftBot), 600,40);
        font.draw(sb, "LT:" + ((int)testTouchLeftTop), 400,40);
        font.draw(sb, "RB:" + ((int)testTouchRightBot), 200,40);
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
        Gdx.input.setCursorPosition(0,0);
    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
        for(Tube tube: tubes){
            tube.dispose();
        }
        System.out.println("Play state disposed");
    }

}
