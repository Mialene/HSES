package com.mialene.hses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private final TextureAtlas menuWidgetAtlas;

    //image widgets
    private Image logo;
    //button widgets
    private Button newGameButton;
    //label widgets
    private Label songCreditLabel;

    public MenuScreen(HSES game) {
        this.game = game;
        audioManager = new AudioManager(game.assets.manager);
        audioManager.playJazzyMusic();

        //set up the stage
        stage = new Stage();
        stage.setViewport(new FitViewport(GlobalVariables.WORLD_WIDTH, GlobalVariables.WORLD_HEIGHT, stage.getCamera()));

        //get the menu atlas from the asset manager
        menuWidgetAtlas = game.assets.manager.get(Assets.MENU_WIDGETS);

        //create the widgets
        createImage();
        createButtons();
        createLabel();

        createTable();
    }

    private void createImage() {
        logo = new Image(menuWidgetAtlas.findRegion("HSESLogo"));
        logo.setSize(logo.getWidth(), logo.getHeight());
    }

    private void createButtons() {
        //create button style
        Button.ButtonStyle newGameButtonStyle = new Button.ButtonStyle();
        newGameButtonStyle.up = new TextureRegionDrawable(menuWidgetAtlas.findRegion("New game Button"));
        newGameButtonStyle.down = new TextureRegionDrawable(menuWidgetAtlas.findRegion("New Game  col_Button"));
        newGameButton = new Button(newGameButtonStyle);
        newGameButton.setSize(newGameButton.getWidth(), newGameButton.getHeight());

        //add the button listener
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
    }

    private void createLabel() {
        //get the small font
        BitmapFont smallFont = game.assets.manager.get(Assets.SMALL_FONT);
        smallFont.setUseIntegerPositions(false);

        //create the label style
        Label.LabelStyle songCreditLabelStyle = new Label.LabelStyle();
        songCreditLabelStyle.font = smallFont;
        songCreditLabelStyle.fontColor = Color.BLACK;

        String creditText = "Jazzy Frenchy\n" +
                "Music by Bensound.com/royalty-free-music\n" +
                "License code: LDIZOJRIDCLDLF5I\n" +
                "\n" +
                "Funky Element\n" +
                "Music I use: https://www.bensound.com\n" +
                "License code: 9XN4TIAWGYRELKHU\n";

        //create the display label
        songCreditLabel = new Label(creditText, songCreditLabelStyle);
    }

    private void createTable() {
        //stage.setDebugAll(true);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setRound(false);
        stage.addActor(mainTable);

        Table leftTable = new Table();
        //leftTable.setFillParent(true);
        leftTable.setRound(false);

        //add logo to left side table
        leftTable.add(logo).size(logo.getWidth() * 0.9f, logo.getHeight() * 0.9f);
        leftTable.row().padTop(5f);

        leftTable.add(newGameButton);
        leftTable.row();


        //add left table to the main table
        mainTable.add(leftTable);
        mainTable.add(songCreditLabel);
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
        stage.getViewport().update(width, height, true);
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
