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
        level1 = new PlayStateOptions(1,1);
        level1.put(0, PlayStateOptions.Bird.Bird, 40);
        levels[0] = level1;

        level2 = new PlayStateOptions(2,2);
        level2.put(0, PlayStateOptions.Bird.Bird, 10);
        level2.put(0, PlayStateOptions.Bird.Bird, 10);
        level2.put(1, PlayStateOptions.Bird.SlowBird, 5);
        levels[1] = level2;

        level3 = new PlayStateOptions(3,3);
        level3.put(0, PlayStateOptions.Bird.Bird, 20);
        level3.put(0, PlayStateOptions.Bird.FastBird, 10);
        level3.put(0, PlayStateOptions.Bird.SprintBird, 10);
        level3.put(1, PlayStateOptions.Bird.Bird, 15);
        level3.put(1, PlayStateOptions.Bird.FastBird, 15);
        level3.put(2, PlayStateOptions.Bird.Boss1, 1);
        levels[2] = level3;

        level4 = new PlayStateOptions(1,4);
        level4.put(0, PlayStateOptions.Bird.Bird, 10);
        level4.put(0, PlayStateOptions.Bird.SprintBird, 10);
        level4.put(0, PlayStateOptions.Bird.FastBird, 10);
        levels[3] = level4;

        level5 = new PlayStateOptions(1,5);
        level5.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[4] = level5;

        level6 = new PlayStateOptions(3,6);
        level6.put(0, PlayStateOptions.Bird.Bird, 15);
        level6.put(0, PlayStateOptions.Bird.BirdZerg, 5);
        level6.put(1, PlayStateOptions.Bird.BirdStupid, 10);
        level6.put(1, PlayStateOptions.Bird.Bird, 10);
        level6.put(1, PlayStateOptions.Bird.BirdZerg, 6);
        level6.put(1, PlayStateOptions.Bird.BirdHealer, 5);
        level6.put(2, PlayStateOptions.Bird.Boss2, 1);
        levels[5] = level6;

        level7 = new PlayStateOptions(1,7);
        level7.put(0, PlayStateOptions.Bird.Bird, 20);
        level7.put(0, PlayStateOptions.Bird.BirdTeleport, 5);
        levels[6] = level7;

        level8 = new PlayStateOptions(1,8);
        level8.put(0, PlayStateOptions.Bird.Bird, 10);
        level8.put(0, PlayStateOptions.Bird.BirdAngry, 3);
        levels[7] = level8;

        level9 = new PlayStateOptions(2,9);
        level9.put(0, PlayStateOptions.Bird.Bird, 10);
        level9.put(1, PlayStateOptions.Bird.Boss3, 1);
        levels[8] = level9;

        level10 = new PlayStateOptions(1,10);
        level10.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[9] = level10;

        level11 = new PlayStateOptions(1,11);
        level11.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[10] = level11;

        level12 = new PlayStateOptions(2,12);
        level12.put(0, PlayStateOptions.Bird.Bird, 10);
        level12.put(1, PlayStateOptions.Bird.Boss4, 1);
        levels[11] = level12;

        level13 = new PlayStateOptions(1,13);
        level13.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[12] = level13;

        level14 = new PlayStateOptions(1,14);
        level14.put(0, PlayStateOptions.Bird.Bird, 10);
        levels[13] = level14;

        level15 = new PlayStateOptions(2,15);
        level15.put(0, PlayStateOptions.Bird.Bird, 10);
        level15.put(1, PlayStateOptions.Bird.Boss5, 1);
        levels[14] = level15;
    }

    public PlayStateOptions[] getLevels(){
        return levels;
    }

    public PlayStateOptions getLevelOptions(int levelNumber){
        return levels[levelNumber-1];
    }

}
