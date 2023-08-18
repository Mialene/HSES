package com.mialene.hses.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.Collections;

public class AudioManager {
    //music

    //UI sound

    //Gameplay sound
    private final Sound golfChewSound;
    private final Sound golfBiteSound;
    private ArrayList<Sound> golfEatingSound;


    public AudioManager(AssetManager assetManager) {
        //load all audio assets from asset manager
        golfBiteSound = assetManager.get(Assets.BITE_SOUND);
        golfChewSound = assetManager.get(Assets.CHEW_SOUND);

        golfEatingSound = new ArrayList<>();

        golfEatingSound.add(golfChewSound);
        golfEatingSound.add(golfBiteSound);

        //set music to loop
    }

    public void playGolfEatingsound(){
        Collections.shuffle(golfEatingSound);
        golfEatingSound.get(0).play();
    }
}
