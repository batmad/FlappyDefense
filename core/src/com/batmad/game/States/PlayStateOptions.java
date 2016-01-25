package com.batmad.game.States;
import java.util.HashMap;

/**
 * Created by tm on 24.01.2016.
 */
public class PlayStateOptions  {
    public Wave[] waves;

    public static void main(String[] args) {
        PlayStateOptions options = new PlayStateOptions(3);
    }

    PlayStateOptions(int wavesCount){
        waves = new Wave[wavesCount];
    }

    public enum Bird {
        Bird,
        SlowBird,
        FastBird,
        SprintBird;
    }

    class Wave{
        Bird birdType;
        int numberOfBirds;

        public Wave(Bird birdType, int numberOfBirds){
            this.birdType = birdType;
            this.numberOfBirds = numberOfBirds;
        }

        public Bird getBirdType(){
            return birdType;
        }

        public int getNumberOfBirds() {
            return numberOfBirds;
        }

    }

    public void put(int waveID, Bird birdType, int birdCount){
        waves[waveID] = new Wave(birdType, birdCount);
    }

    public Bird getBirdType(int waveID){
        return waves[waveID].getBirdType();
    }

    public int getNumberOfBirds(int waveID){
        return waves[waveID].getNumberOfBirds();
    }


}
