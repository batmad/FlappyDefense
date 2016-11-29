package com.batmad.birddefense.core.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by tm on 19.11.2015.
 */
public class AnimationBird {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private int frameWidth;
    private int frameHeight;

    public AnimationBird(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        frameWidth = region.getRegionWidth() / frameCount;
        frameHeight = region.getRegionHeight();
        for (int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth,region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime /frameCount;
        frame = 0;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount)
            frame =0;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
