package com.batmad.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.batmad.game.FlappyDefense;


/**
 * Created by tm on 19.11.2015.
 */
public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Texture defaultBtn;
    private BitmapFont font;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDefense.WIDTH, FlappyDefense.HEIGHT);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        defaultBtn = new Texture("defaultbtn.png");
        font = new BitmapFont(Gdx.files.internal("flappybird-32.fnt"));
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }


    @Override
    public void update(float dt) {
        handleInput();
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(background, background.getWidth(), 0);
        sb.draw(background, background.getWidth() * 2, 0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        sb.draw(defaultBtn, cam.position.x - defaultBtn.getWidth() / 2, cam.position.y - 70);
        float width = setFontWidth("START");
        font.draw(sb, "START", cam.position.x - width/2,cam.position.y - 25);
        width = setFontWidth("OPTIONS");
        sb.draw(defaultBtn, cam.position.x - defaultBtn.getWidth() / 2, cam.position.y - 140);
        font.draw(sb, "OPTIONS", cam.position.x - width/2,cam.position.y - 95);
        width = setFontWidth("HISCORE");
        sb.draw(defaultBtn, cam.position.x - defaultBtn.getWidth() / 2, cam.position.y - 210);
        font.draw(sb, "HISCORE", cam.position.x - width/2,cam.position.y - 165);
        sb.end();
    }

    public float setFontWidth(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.width;
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu state disposed");
    }
}
