package com.batmad.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.batmad.game.FlappyDefense;

import java.util.HashMap;

/**
 * Created by tm on 23.01.2016.
 */
public class LevelState extends State {
    Texture background;
    Texture menu;
    Texture lockedBtn;
    Texture unlockedBtn;
    BitmapFont fontNumbers, fontLetter;
    float row1, row2, row3;
    float column1, column2, column3, column4, column5;
    HashMap<String, Float> columnMap;
    HashMap<String, Float> rowMap;
    Preferences prefs;


    public LevelState(GameStateManager gsm) {
        super(gsm);
        prefs = Gdx.app.getPreferences("myPrefs");
        background = new Texture("bg.png");
        fontNumbers = new BitmapFont(Gdx.files.internal("fonts/shadow-white-blue-50.fnt"));
        fontLetter = new BitmapFont(Gdx.files.internal("fonts/neskid-white-32.fnt"));
        menu = new Texture("menu/levels.png");
        lockedBtn = new Texture("menu/blockedlevelbtn.png");
        unlockedBtn = new Texture("menu/unblockedlevelbtn.png");

        columnMap = new HashMap<String, Float>(5);
        columnMap.put("column1", (float)FlappyDefense.WIDTH/2 - menu.getWidth()/2 + 40);
        columnMap.put("column2", (float)FlappyDefense.WIDTH/2 - menu.getWidth()/2 + 143);
        columnMap.put("column3", (float)FlappyDefense.WIDTH/2 - menu.getWidth()/2 + 246);
        columnMap.put("column4", (float)FlappyDefense.WIDTH/2 - menu.getWidth()/2 + 349);
        columnMap.put("column5", (float)FlappyDefense.WIDTH/2 - menu.getWidth()/2 + 452);

        rowMap = new HashMap<String, Float>(3);
        rowMap.put("row1", (float)menu.getHeight() - lockedBtn.getHeight() - 120);
        rowMap.put("row2", (float)menu.getHeight() - lockedBtn.getHeight() - 220);
        rowMap.put("row3", (float)menu.getHeight() - lockedBtn.getHeight() - 320);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(background, background.getWidth(), 0);
        sb.draw(background, background.getWidth() * 2, 0);
        sb.draw(menu, FlappyDefense.WIDTH / 2 - menu.getWidth() / 2, 0);
        drawFont(fontLetter, sb, "выберите уровень", FlappyDefense.WIDTH / 2 - 50, FlappyDefense.HEIGHT - 80);

//        float width = setFontWidth("I");
//        float height = setFontHeight("I");
//        font.draw(sb,"I",column1 + unlockedBtn.getWidth()/2 - width/2,row1 + unlockedBtn.getHeight()/2 + height/2);
        int levelID = 0;
        for(int rowID = 1; rowID <= 3; rowID++){
            for(int columnID = 1; columnID <= 5; columnID++){
                levelID++;
                if(levelID <= prefs.getInteger("levels",1)){
                    sb.draw(unlockedBtn, columnMap.get("column" + columnID), rowMap.get("row" + rowID));
                    drawFont(fontNumbers, sb, String.valueOf(levelID), columnMap.get("column" + columnID), rowMap.get("row" + rowID));
                }else{
                    sb.draw(lockedBtn, columnMap.get("column" + columnID), rowMap.get("row" + rowID));
                }

            }
        }
//        sb.draw(unlockedBtn, columnMap.get("column1"), row1);
//        drawFont(fontNumbers, sb,"1",columnMap.get("column1"),row1);
//        sb.draw(unlockedBtn, columnMap.get("column2"), row1);
//        drawFont(fontNumbers, sb, "2", columnMap.get("column2"), row1);
//        sb.draw(unlockedBtn, columnMap.get("column3"), row1);
//        drawFont(fontNumbers, sb, "3", columnMap.get("column3"), row1);
//        sb.draw(unlockedBtn, columnMap.get("column4"), row1);
//        drawFont(fontNumbers, sb, "4", columnMap.get("column4"), row1);
//        sb.draw(unlockedBtn, columnMap.get("column5"), row1);
//        drawFont(fontNumbers, sb, "5", columnMap.get("column5"), row1);
//        sb.draw(lockedBtn, column1, row2);
//        sb.draw(lockedBtn, column2, row2);
//        sb.draw(lockedBtn, column3, row2);
//        sb.draw(lockedBtn, column4, row2);
//        sb.draw(lockedBtn, column5, row2);
//        sb.draw(lockedBtn, column1, row3);
//        sb.draw(lockedBtn, column2, row3);
//        sb.draw(lockedBtn, column3, row3);
//        sb.draw(lockedBtn, column4, row3);
//        sb.draw(lockedBtn, column5, row3);
        sb.end();
    }

    public void drawFont(BitmapFont font, SpriteBatch sb, String text, float column, float row){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
        font.draw(sb, text, column + unlockedBtn.getWidth() / 2 - glyphLayout.width / 2, row + unlockedBtn.getHeight() / 2 + glyphLayout.height / 2);
    }

    @Override
    public void dispose() {

    }
}
