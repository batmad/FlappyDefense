package com.batmad.birddefense.core;

/**
 * Created by tm on 09.02.2016.
 */
public interface PlayServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(String str);
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
    public void showOrLoadInterstital();
}