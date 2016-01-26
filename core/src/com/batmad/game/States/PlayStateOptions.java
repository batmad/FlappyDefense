package com.batmad.game.States;
import java.util.HashMap;

/**
 * Created by tm on 24.01.2016.
 */
public class PlayStateOptions  {
    public Wave[] waves;

    PlayStateOptions(int wavesCount){
        waves = new Wave[wavesCount];
        for(int i=0; i < wavesCount; i++){
            waves[i] = new Wave();
        }
    }

    public enum Bird {
        Bird,
        SlowBird,
        FastBird,
        SprintBird;
    }

    class Wave{
        HashMap<Bird, Integer> mapOfBirds;

        public Wave(){
            mapOfBirds = new HashMap<Bird, Integer>();
        }

        public void put(Bird birdType, int numberOfBirds){
            mapOfBirds.put(birdType, numberOfBirds);
        }

        public HashMap<Bird, Integer> getMapOfBirds(){
            return mapOfBirds;
        }
    }

    public void put(int waveID, Bird birdType, int birdCount){
        //waves[waveID] = new Wave(birdType, birdCount);
        waves[waveID].put(birdType, birdCount);
    }



}
