package com.batmad.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.batmad.game.FlappyDefense;
import com.batmad.game.Sprites.Bird;
import com.batmad.game.Sprites.Tube;

/**
 * Created by tm on 19.11.2015.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int BIRD_COUNT = 20;
    private static final int GROUND_Y_OFFSET = -50;
    private int lifes = 20;

    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;
    private Array<Bird> birds;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, 800, FlappyDefense.HEIGHT);

        bird = new Bird(50,300);
        background = new Texture("bg.png");
        ground = new Texture("ground.png");

        tubes = new Array<Tube>();
        birds = new Array<Bird>();

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
        for(int i = 1; i <= BIRD_COUNT; i++){
            birds.add(new Bird());
        }
    }

    @Override
    protected void handleInput() {
        //if(Gdx.input.justTouched()){
            //bird.jump();
        //}
    }

    @Override
    public void update(float dt) {
        handleInput();
        for(Bird bird: birds) {
            bird.update(dt);
        }

        for(int i =0; i < tubes.size; i++){
            if(cam.position.x - (cam.viewportWidth / 2) > tubes.get(i).getPosTopTube().x + tubes.get(i).getTopTube().getWidth()){
                tubes.get(i).reposition(tubes.get(i).getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            //if(tubes.get(i).collides(bird.getBounds()))
                //gsm.set(new MenuState(gsm));
        }
        for(Bird bird: birds) {
            if (bird.getPosition().x > FlappyDefense.WIDTH) {
                lifes--;
                bird.dispose();
            }
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
            sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        }
        for(Tube tube: tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, 0, GROUND_Y_OFFSET);
        sb.draw(ground, ground.getWidth(), GROUND_Y_OFFSET);
        sb.draw(ground, 2 * ground.getWidth(), GROUND_Y_OFFSET);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube: tubes){
            tube.dispose();
        }
        System.out.println("Play state disposed");
    }

}
