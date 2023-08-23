package com.mialene.hses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mialene.hses.resources.Assets;
import com.mialene.hses.resources.AudioManager;
import com.mialene.hses.resources.GlobalVariables;

public class MenuScreen implements Screen {
    private final HSES game;
    public AudioManager audioManager;
    private final Stage stage;
    private final TextureAtlas upDownButtonAtlas;

    //image widgets
    private Texture logoTexture;
    //button widgets
    private Button newGameButton;

    public MenuScreen(HSES game){
        this.game = game;
        audioManager = new AudioManager(game.assets.manager);
        audioManager.playJazzyMusic();

        //set up the stage
        stage = new Stage();
        stage.setViewport(new FitViewport(GlobalVariables.WORLD_WIDTH,GlobalVariables.WORLD_HEIGHT, stage.getCamera()));

        //get the menu atlas from the asset manager
        upDownButtonAtlas = game.assets.manager.get(Assets.UPDOWN_BUTTONS_ATLAS);

        //create the widgets
        createImage();
        createButtons();

        //try to create table
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setRound(false);
        stage.addActor(mainTable);

        mainTable.add(newGameButton);
    }

    private void createImage(){
        logoTexture = game.assets.manager.get(Assets.LOGO);
    }

    private void createButtons(){
        //create button style
        Button.ButtonStyle newGameButtonStyle = new Button.ButtonStyle();
        newGameButtonStyle.up = new TextureRegionDrawable(upDownButtonAtlas.findRegion("New game Button"));
        newGameButtonStyle.down = new TextureRegionDrawable(upDownButtonAtlas.findRegion("New Game  col_Button"));
        newGameButton = new Button(newGameButtonStyle);
        newGameButton.setSize(newGameButton.getWidth(),newGameButton.getHeight());

        //add the button listener
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(GlobalVariables.GREEN_BACKGROUND);

        //tell the stage to do actions and draw itself
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //update the stage's viewport with the new screen size
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
