package com.batmad.birddefense.core.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.batmad.birddefense.core.FlappyDefense;


/**
 * Created by tm on 19.11.2015.
 */
public class MenuState extends State implements InputProcessor {
    private Texture background;
    private Texture defaultBtn;
    private BitmapFont fontMenu;
    private Rectangle startBtn, levelsBtn, hiscoreBtn, adsfreeBtn, achievmentsBtn;
    private Preferences prefs;
    private int level;
    private boolean isAdsRemoved;
    PlayStateOptions levelOptions;
    String textStart =  myBundle.get("menuStart");
    String textLevels =  myBundle.get("menuLevels");
    String textHiscore =  myBundle.get("menuHiscore");
    String textAchievments =  myBundle.get("menuAchievments");
    String textAdsFree =  myBundle.get("menuAdsFree");

    public MenuState(GameStateManager gsm, FlappyDefense game) {
        super(gsm, game);
        prefs = Gdx.app.getPreferences("myPrefs");
        level = prefs.getInteger("levels", 1);
        isAdsRemoved = prefs.getBoolean("adsRemoved", false);
        levelOptions = new Levels().getLevelOptions(level);
        cam.setToOrtho(false, FlappyDefense.WIDTH, FlappyDefense.HEIGHT);
        background = new Texture("bg.png");
        defaultBtn = new Texture("defaultbtn.png");

        generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 32;
        parameter.color = Color.BLACK;
        fontMenu = generator.generateFont(parameter);
        generator.dispose();

        startBtn = new Rectangle(cam.position.x - defaultBtn.getWidth()/2,cam.position.y + 70, defaultBtn.getWidth(), defaultBtn.getHeight());
        levelsBtn = new Rectangle(cam.position.x - defaultBtn.getWidth()/2,cam.position.y,defaultBtn.getWidth(), defaultBtn.getHeight());
        hiscoreBtn = new Rectangle(cam.position.x - defaultBtn.getWidth()/2,cam.position.y - 70,defaultBtn.getWidth(), defaultBtn.getHeight());
        achievmentsBtn = new Rectangle(cam.position.x - defaultBtn.getWidth()/2,cam.position.y - 140,defaultBtn.getWidth(), defaultBtn.getHeight());
        adsfreeBtn = new Rectangle(cam.position.x - defaultBtn.getWidth()/2,cam.position.y - 210,defaultBtn.getWidth(), defaultBtn.getHeight());
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }


    @Override
    public void handleInput() {
        if(touched(startBtn) && Gdx.input.justTouched()){
            if(!isAdsRemoved)
                FlappyDefense.playServices.showOrLoadInterstital();
            gsm.set(new PlayState(gsm, game, levelOptions));
        } else if(touched(levelsBtn) && Gdx.input.justTouched()){
            gsm.set(new LevelState(gsm, game));
        }else if(touched(hiscoreBtn) && Gdx.input.justTouched()){
            FlappyDefense.playServices.showScore();
        }else if(touched(achievmentsBtn) && Gdx.input.justTouched()){
            FlappyDefense.playServices.showAchievement();
        }
        else if(!isAdsRemoved & touched(adsfreeBtn) && Gdx.input.justTouched()){
            FlappyDefense.iabServices.removeAds();
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
        sb.draw(defaultBtn, cam.position.x - defaultBtn.getWidth() / 2, cam.position.y + 70 );
        float width = setFontWidth(textStart);
        fontMenu.draw(sb, textStart, cam.position.x - width/2,cam.position.y + 115);

        width = setFontWidth(textLevels);
        sb.draw(defaultBtn, cam.position.x - defaultBtn.getWidth() / 2, cam.position.y);
        fontMenu.draw(sb, textLevels, cam.position.x - width/2,cam.position.y + 45);

        width = setFontWidth(textHiscore);
        sb.draw(defaultBtn, cam.position.x - defaultBtn.getWidth() / 2, cam.position.y - 70);
        fontMenu.draw(sb, textHiscore, cam.position.x - width/2,cam.position.y - 25);

        width = setFontWidth(textAchievments);
        sb.draw(defaultBtn, cam.position.x - defaultBtn.getWidth() / 2, cam.position.y - 140);
        fontMenu.draw(sb, textAchievments, cam.position.x - width/2,cam.position.y - 95);

        if(!isAdsRemoved) {
            width = setFontWidth(textAdsFree);
            sb.draw(defaultBtn, cam.position.x - defaultBtn.getWidth() / 2, cam.position.y - 210);
            fontMenu.draw(sb, textAdsFree, cam.position.x - width / 2, cam.position.y - 165);
        }
        sb.end();
    }

    public float setFontWidth(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(fontMenu,text);
        return glyphLayout.width;
    }

    public boolean touched(Rectangle rect) {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        if (rect.contains(touchPos.x, touchPos.y))
            return true;
        else
            return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            Gdx.app.exit();
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

    @Override
    public void dispose() {
        background.dispose();
    }
}
