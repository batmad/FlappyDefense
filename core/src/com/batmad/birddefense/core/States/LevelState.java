package com.batmad.birddefense.core.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.batmad.birddefense.core.FlappyDefense;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tm on 23.01.2016.
 */
public class LevelState extends State implements InputProcessor{
    public final int NUMBER_OF_LEVELS = Levels.NUMBER_OF_LEVELS;
    Texture background;
    Texture menu;
    Texture lockedBtn;
    Texture unlockedBtn;
    BitmapFont fontNumbers;
    BitmapFont fontLevels;
    HashMap<String, Float> columnMap;
    HashMap<String, Float> rowMap;
    LinkedHashMap<Rectangle, PlayStateOptions> levelMap;
    Preferences prefs;
    Rectangle[] rectangles;
    Rectangle rect1;
    Rectangle rect2;
    public PlayStateOptions[] levels;
    private boolean isAdsRemoved;
    String textChooseLevel = myBundle.get("chooseLevel");

    public LevelState(GameStateManager gsm, FlappyDefense game) {
        super(gsm, game);
        cam.setToOrtho(false, FlappyDefense.WIDTH, FlappyDefense.HEIGHT);
        prefs = Gdx.app.getPreferences("myPrefs");
        isAdsRemoved = prefs.getBoolean("adsRemoved", false);
        background = new Texture("bg.png");

        generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 30;
        parameter.color = Color.WHITE;
        fontLevels = generator.generateFont(parameter);

        parameter.size = 40;
        fontNumbers = generator.generateFont(parameter);
        generator.dispose();

        menu = new Texture("menu/levels.png");
        lockedBtn = new Texture("menu/blockedlevelbtn.png");
        unlockedBtn = new Texture("menu/unblockedlevelbtn.png");

        columnMap = new HashMap<String, Float>(5);
        columnMap.put("column1", (float) FlappyDefense.WIDTH / 2 - menu.getWidth() / 2 + 40);
        columnMap.put("column2", (float) FlappyDefense.WIDTH / 2 - menu.getWidth() / 2 + 143);
        columnMap.put("column3", (float) FlappyDefense.WIDTH / 2 - menu.getWidth() / 2 + 246);
        columnMap.put("column4", (float) FlappyDefense.WIDTH / 2 - menu.getWidth() / 2 + 349);
        columnMap.put("column5", (float) FlappyDefense.WIDTH / 2 - menu.getWidth() / 2 + 452);

        rowMap = new HashMap<String, Float>(3);
        rowMap.put("row1", (float) menu.getHeight() - lockedBtn.getHeight() - 120);
        rowMap.put("row2", (float) menu.getHeight() - lockedBtn.getHeight() - 220);
        rowMap.put("row3", (float) menu.getHeight() - lockedBtn.getHeight() - 320);

        levels = new Levels().getLevels();

        levelMap = new LinkedHashMap<Rectangle, PlayStateOptions>();

        int levelID = 0;
        rectangles = new Rectangle[NUMBER_OF_LEVELS];
        for (int rowID = 1; rowID <= 3; rowID++) {
            for (int columnID = 1; columnID <= 5; columnID++) {
                rectangles[levelID] = new Rectangle(columnMap.get("column" + columnID), rowMap.get("row" + rowID), 90, 90);
                levelID++;
            }
        }

        for(int i=0; i < NUMBER_OF_LEVELS; i++) {
            if (i < prefs.getInteger("levels", 1)) {
                levelMap.put(rectangles[i],levels[i]);
            }
        }


        rect1 = new Rectangle(columnMap.get("column1"), rowMap.get("row1"), 90, 90);
        rect2 = new Rectangle(columnMap.get("column2"), rowMap.get("row1"), 90, 90);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

    }

    @Override
    protected void handleInput() {
        for(Map.Entry<Rectangle, PlayStateOptions> entry: levelMap.entrySet()){
            Rectangle rect = entry.getKey();
            PlayStateOptions options = entry.getValue();
            if(touched(rect) && Gdx.input.justTouched()){
                if(!isAdsRemoved)
                    FlappyDefense.playServices.showOrLoadInterstital();
                gsm.set(new PlayState(gsm, game, options));
            }
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

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(background, background.getWidth(), 0);
        sb.draw(background, background.getWidth() * 2, 0);
        sb.draw(menu, FlappyDefense.WIDTH / 2 - menu.getWidth() / 2, 0);
        drawFont(fontLevels, sb, textChooseLevel, FlappyDefense.WIDTH / 2 - 50, FlappyDefense.HEIGHT - 80);

        int levelID = 0;
        for (int rowID = 1; rowID <= 3; rowID++) {
            for (int columnID = 1; columnID <= 5; columnID++) {
                levelID++;
                if (levelID <= prefs.getInteger("levels", 1)) {
                    sb.draw(unlockedBtn, columnMap.get("column" + columnID), rowMap.get("row" + rowID));
                    drawFont(fontNumbers, sb, String.valueOf(levelID), columnMap.get("column" + columnID), rowMap.get("row" + rowID));
                } else {
                    sb.draw(lockedBtn, columnMap.get("column" + columnID), rowMap.get("row" + rowID));
                }
            }
        }

        sb.end();
    }


    public void drawFont(BitmapFont font, SpriteBatch sb, String text, float column, float row) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
        font.draw(sb, text, column + unlockedBtn.getWidth() / 2 - glyphLayout.width / 2, row + unlockedBtn.getHeight() / 2 + glyphLayout.height / 2);
    }

    @Override
    public void dispose() {
        menu.dispose();
        lockedBtn.dispose();
        unlockedBtn.dispose();
        background.dispose();
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
}
