package com.mialene.hses.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.Collections;

public class AudioManager {
    //music
    private final Music JazzyFrenchyMusic;
    private final Music FunkyElementMusic;
    private final float musicVolume = 0.1f;

    //UI sound

    //Gameplay sound
    private final Sound golfChewSound;
    private final Sound golfBiteSound;
    private final Sound golfNomSound;
    private final Sound golfSwallowSound;
    private final Sound sarahCompletedSeries;
    private final Sound sarahAhSound;
    private final Sound sarahSignSound;
    private final Sound sarahWhatSound;
    private final Music gameOverSoundMusic;
    private ArrayList<Sound> golfEatingSoundList;
    private ArrayList<Sound> sarahCompleteSoundList;
    private ArrayList<Sound> sarahInterruptedSoundList;


    public AudioManager(AssetManager assetManager) {
        //load all audio assets from asset manager
        golfBiteSound = assetManager.get(Assets.BITE_SOUND);
        golfChewSound = assetManager.get(Assets.CHEW_SOUND);
        golfNomSound = assetManager.get(Assets.NOM_SOUND);
        golfSwallowSound = assetManager.get(Assets.SWALLOW_SOUND);
        JazzyFrenchyMusic = assetManager.get(Assets.JAZZY_FRENCHY_MUSIC);
        FunkyElementMusic = assetManager.get(Assets.FUNKY_ELEMENT_MUSIC);
        sarahCompletedSeries = assetManager.get(Assets.COMPLETED_SERIES);
        sarahAhSound = assetManager.get(Assets.GIRL_AH_SOUND);
        sarahSignSound = assetManager.get(Assets.GIRL_SIGN_SOUND);
        sarahWhatSound = assetManager.get(Assets.GIRL_WHAT_SOUND);
        gameOverSoundMusic = assetManager.get(Assets.GAME_OVER_SOUND_MUSIC);

        //initialize golf eating sound ArrayList
        golfEatingSoundList = new ArrayList<>();
        golfEatingSoundList.add(golfChewSound);
        golfEatingSoundList.add(golfBiteSound);
        golfEatingSoundList.add(golfSwallowSound);

        //initailize Sarah interrupted sound ArrayList
        sarahInterruptedSoundList = new ArrayList<>();
        sarahInterruptedSoundList.add(sarahAhSound);
        sarahInterruptedSoundList.add(sarahSignSound);
        sarahInterruptedSoundList.add(sarahWhatSound);

        //initialize Sarah complete sound ArrayList;
        sarahCompleteSoundList = new ArrayList<>();
        sarahCompleteSoundList.add(sarahCompletedSeries);

        //set music to loop
        gameOverSoundMusic.setLooping(false);
        JazzyFrenchyMusic.setLooping(true);
        FunkyElementMusic.setLooping(true);
        JazzyFrenchyMusic.setVolume(0.33f);
        FunkyElementMusic.setVolume(musicVolume);
    }

    public void playGolfEatingsound() {
        Collections.shuffle(golfEatingSoundList);
        golfEatingSoundList.get(0).play();
    }

    public void playSarahCompleteSound() {
        Collections.shuffle(sarahCompleteSoundList);
        sarahCompleteSoundList.get(0).play();
    }

    public void playSarahInterruptedSound() {
        Collections.shuffle(sarahInterruptedSoundList);
        sarahInterruptedSoundList.get(0).play();
    }

    public void playJazzyMusic() {
        if (FunkyElementMusic.isPlaying()) {
            FunkyElementMusic.stop();
        }
        JazzyFrenchyMusic.play();
    }

    public void playFunkyMusic() {
        if (JazzyFrenchyMusic.isPlaying()) {
            JazzyFrenchyMusic.stop();
        }
        FunkyElementMusic.play();
    }

    public void playGameOverSoundMusic() {
        if (!gameOverSoundMusic.isPlaying()) {
            gameOverSoundMusic.play();
        }
    }
}
