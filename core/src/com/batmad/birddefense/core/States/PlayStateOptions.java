package com.batmad.birddefense.core.States;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tm on 24.01.2016.
 */
public class PlayStateOptions  {
    public Wave[] waves;
    public int levelNumber;

    PlayStateOptions(int wavesCount, int levelNumber){
        this.levelNumber = levelNumber;
        waves = new Wave[wavesCount];
        for(int i=0; i < wavesCount; i++){
            waves[i] = new Wave();
        }
    }

    public enum Bird {
        Bird,
        SlowBird,
        FastBird,
        SprintBird,
        BirdStupid,
        BirdHealer,
        BirdZerg,
        BirdTeleport,
        BirdAngry,
        BirdFire,
        BirdSuicide,
        Boss1,
        Boss2,
        Boss3,
        Boss4,
        Boss5,
        Bird2,
        SlowBird2,
        FastBird2,
        SprintBird2,
        BirdStupid2,
        BirdZerg2,
        BirdTeleport2,
        BirdAngry2,
        BirdFire2,
        Bird3,
        SlowBird3,
        BirdStupid3,
        BirdTeleport3,
        Bird4,
        Bird5;
    }

    class Wave{
        TreeMap<Bird, Integer> mapOfBirds;

        public Wave(){
            mapOfBirds = new TreeMap<Bird, Integer>();
        }

        public void put(Bird birdType, int numberOfBirds){
            mapOfBirds.put(birdType, numberOfBirds);
        }

        public TreeMap<Bird, Integer> getMapOfBirds(){
            return mapOfBirds;
        }

        public int numberOfBirds(){
            int sum = 0;
            for(Map.Entry<Bird, Integer> entry: mapOfBirds.entrySet()){
                sum += entry.getValue();
            }
            return sum;
        }
    }

    public void put(int waveID, Bird birdType, int birdCount){
        waves[waveID].put(birdType, birdCount);
    }



}
