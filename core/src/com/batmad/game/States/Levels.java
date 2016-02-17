package com.batmad.game.States;

/**
 * Created by tm on 16.02.2016.
 */
public class Levels {
    public static final int NUMBER_OF_LEVELS = 15;
    public PlayStateOptions[] levels;
    public PlayStateOptions level1, level2, level3, level4, level5, level6, level7, level8, level9, level10, level11, level12, level13, level14, level15;

    public Levels() {
        levels = new PlayStateOptions[NUMBER_OF_LEVELS];
        level1 = new PlayStateOptions(3);
        level1.put(0, PlayStateOptions.Bird.SlowBird, 2);
        level1.put(0, PlayStateOptions.Bird.Bird, 6);
        level1.put(0, PlayStateOptions.Bird.FastBird, 3);
        level1.put(0, PlayStateOptions.Bird.SprintBird, 10);
        level1.put(1, PlayStateOptions.Bird.FastBird, 5);
        level1.put(2, PlayStateOptions.Bird.Bird, 4);
        levels[0] = level1;

        level2 = new PlayStateOptions(4);
        level2.put(0, PlayStateOptions.Bird.Bird, 10);
        level2.put(1, PlayStateOptions.Bird.SlowBird, 10);
        level2.put(2, PlayStateOptions.Bird.FastBird, 10);
        level2.put(3, PlayStateOptions.Bird.SprintBird, 10);
        levels[1] = level2;

        level3 = new PlayStateOptions(1);
        level3.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[2] = level3;

        level4 = new PlayStateOptions(1);
        level4.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[3] = level4;

        level5 = new PlayStateOptions(1);
        level5.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[4] = level5;

        level6 = new PlayStateOptions(1);
        level6.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[5] = level6;

        level7 = new PlayStateOptions(1);
        level7.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[6] = level7;

        level8 = new PlayStateOptions(1);
        level8.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[7] = level8;

        level9 = new PlayStateOptions(1);
        level9.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[8] = level9;

        level10 = new PlayStateOptions(1);
        level10.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[9] = level10;

        level11 = new PlayStateOptions(1);
        level11.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[10] = level11;

        level12 = new PlayStateOptions(1);
        level12.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[11] = level12;

        level13 = new PlayStateOptions(1);
        level13.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[12] = level13;

        level14 = new PlayStateOptions(1);
        level14.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[13] = level14;

        level15 = new PlayStateOptions(1);
        level15.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[14] = level15;
    }

    public PlayStateOptions[] getLevels(){
        return levels;
    }

    public PlayStateOptions getLevelOptions(int levelNumber){
        return levels[levelNumber-1];
    }

}
