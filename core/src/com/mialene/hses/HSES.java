package com.mialene.hses;

import com.badlogic.gdx.Game;
import com.mialene.hses.resources.Assets;

public class HSES extends Game {
    public Assets assets;

    public GameScreen gameScreen;
    public MenuScreen menuScreen;


    @Override
    public void create() {
        assets = new Assets();
        gameScreen = new GameScreen(this);

        //initialize menu screen
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);

        //initialize audio manager

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        assets.dispose();
    }
}
