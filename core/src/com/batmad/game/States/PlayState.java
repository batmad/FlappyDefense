package com.batmad.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.batmad.game.FlappyDefense;
import com.batmad.game.Sprites.Bird;
import com.batmad.game.Sprites.Bullet;
import com.batmad.game.Sprites.Tube;

import java.util.ArrayList;

/**
 * Created by tm on 19.11.2015.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private int lifes = 20;
    private int birdCount = 15;

    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private ArrayList<Tube> tubes;
    private ArrayList<Bird> birds;
    private ArrayList<Bullet> bullets;

    private BitmapFont font;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDefense.WIDTH, FlappyDefense.HEIGHT);

        background = new Texture("bg.png");
        ground = new Texture("ground.png");

        tubes = new ArrayList<Tube>();
        birds = new ArrayList<Bird>();
        bullets = new ArrayList<Bullet>();

        font = new BitmapFont(Gdx.files.internal("berlin-40.fnt"));

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
        for(int i = 1; i <= birdCount; i++) {
            birdCreate(i*3);
        }

    }

    @Override
    protected void handleInput() {
        //if(Gdx.input.justTouched()){
            //bird.jump();
        //}
    }

    public boolean touched(Rectangle tube) {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        if (tube.contains(touchPos.x, touchPos.y))
            return true;
        else
            return false;
    }

    private void birdCreate(int num){
        birds.add(new Bird(num * 20));
    }

    private Bird getLastBird(){
        return birds.get(birds.size()-1);
    }


    @Override
    public void update(float dt) {
        handleInput();

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
                            tube.setClearSky(System.currentTimeMillis() + tube.getFireRate());
                            Bullet bullet = tube.fire(bird.getBounds());
                            bullets.add(bullet);
                            //bird.dispose();
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

        for(int i =0; i < tubes.size(); i++){
            if(cam.position.x - (cam.viewportWidth / 2) > tubes.get(i).getPosTopTube().x + tubes.get(i).getTopTube().getWidth()){
                tubes.get(i).reposition(tubes.get(i).getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            //if(tubes.get(i).collides(bird.getBounds()))
                //gsm.set(new MenuState(gsm));
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(background, background.getWidth(), 0);
        sb.draw(background, background.getWidth() * 2, 0);
        for(Bird bird: birds) {
            if(!bird.isDead()) {
                sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
            }
        }
        for(Tube tube: tubes) {
            if(touched(tube.getBoundsTop())){
                sb.draw(tube.getTopTubeGrowed(), tube.getPosTopTubeGrowed().x, tube.getPosTopTubeGrowed().y);
            }
            else {
                sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            }
            if(touched(tube.getBoundsBot())){
                sb.draw(tube.getBottomTubeGrowed(), tube.getPosBottomTubeGrowed().x, tube.getPosBottomTubeGrowed().y);
            }
            else {
                sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
            }
            for(Bird bird: birds) {
                if(!bird.isDead()) {
                    if (tube.collides(bird.getBounds())) {
                        sb.draw(tube.getTopTubeGrowed(), tube.getPosTopTubeGrowed().x, tube.getPosTopTubeGrowed().y);
                        sb.draw(tube.getBottomTubeGrowed(), tube.getPosBottomTubeGrowed().x, tube.getPosBottomTubeGrowed().y);
                    } else {
                        sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
                        sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
                    }
                }
            }
        }
        for(Bullet bullet: bullets) {
            if(!bullet.isFired()) {
                sb.draw(bullet.getTexture(), bullet.getPosition().x, bullet.getPosition().y);
            }
        }
        sb.draw(ground, 0, FlappyDefense.GROUND_Y_OFFSET);
        sb.draw(ground, ground.getWidth(), FlappyDefense.GROUND_Y_OFFSET);
        sb.draw(ground, 2 * ground.getWidth(), FlappyDefense.GROUND_Y_OFFSET);
        font.draw(sb, "" + ((int)lifes), 50,50);
        sb.end();
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
