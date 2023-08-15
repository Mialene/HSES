package com.mialene.hses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mialene.hses.objects.*;
import com.mialene.hses.resources.Assets;
import com.mialene.hses.resources.GlobalVariables;

import java.util.ListIterator;

public class GameScreen implements Screen, InputProcessor {
    //game variable
    private final HSES game;
    //Graphics
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    private Viewport viewport;
    //Sarah's state

    //game state and day state and timer
    //I don't under each part yet but let's make my game replayable !!First step!!
    private enum GameState{
        RUNNING,
        PAUSED,
        GAME_OVER
    }
    private GameState gameState;
    private enum DayState{
        STARTING,
        IN_PROGRESS,
        ENDING
    }
    private DayState dayState;
    private final float START_DAY_DELAY = 2f;
    private final float END_DAY_DELAY = 3f;
    private int currentDay;
    private float dayStateTime;
    private int life = 1;

    //remaining time of the day
    private static final float MAX_REMAINING_TIME = 99.99f;
    private float dayTimer;
    //fonts
    private BitmapFont smallFont, mediumFont,largeFont;
    private static final Color DEFAULT_FONT_COLOR = Color.WHITE;
    //HUD
    private float HUDMargin = 20f;
    private static final Color PRODUCTIVITY_BAR_COLOR = GlobalVariables.GOLD;
    private static final Color MAX_BACKGROUND_COLOR = GlobalVariables.BLUEISH;
    private static final Color YUCK_BACKGROUND_COLOR = GlobalVariables.YUCK;
    //HUD productivity bar
    private float productivityBarPadding; //for gold bar vs the percent text
    private float productivityBarMaxHeight; //the main one when it progressed to the max
    private float productivityBarWidth; //the gold bar width
    private float maxBackgroundBarPadding; //the blueish bar vs the gold bar
    private float maxBackgroundBarHeight; //the maximum blueish
    private float maxBackgroundBarWidth; //the width of the blueish
    private float maxBackgroundBarMarginBottom; //space from bottom text


    //All Texture;
    private Texture bgTexture, deskTexture, saladBoxTexture;


    //game objects
    Furniture desk;
    Golf golf;
    Sarah sarah;
    SaladBar saladBar;

    private float elapsedSeconds = 0;
    GameScreen(HSES game){
        this.game = game;
        viewport = new FitViewport(2436,1125);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        game.assets.load();
        game.assets.manager.finishLoading();

        //
        createGameArea();
        setUpFonts();
        sarah = new Sarah(game);
    }

    private void createGameArea(){
        bgTexture = game.assets.manager.get(Assets.BACKGROUND_TEXTURE);
        deskTexture = game.assets.manager.get(Assets.DESK_TEXTURE);

        desk = new Furniture(deskTexture,
                GlobalVariables.WORLD_WIDTH * 0.22f,
                GlobalVariables.WORLD_HEIGHT * 0.5f,
                deskTexture.getWidth(),
                deskTexture.getHeight());
    }
    private void getGolfReady(){
        golf = new Golf(game);
    }

    //prepare the salads
    private void prepareSalad(){
        saladBoxTexture = game.assets.manager.get(Assets.SALAD_BOX_TEXTURE);
        //saladList = new LinkedList<>();

        saladBar = new SaladBar(2436f,555f,5f,1125f,
                saladBoxTexture.getWidth() * 0.5f,saladBoxTexture.getHeight() * 0.5f,200f
        ,saladBoxTexture,5f);
    }

    private void setUpFonts(){
        smallFont = game.assets.manager.get(Assets.SMALL_FONT);
        //smallFont.getData().setScale(GlobalVariables.WORLD_SCALE);
        smallFont.setColor(DEFAULT_FONT_COLOR);
        smallFont.setUseIntegerPositions(false);

        mediumFont = game.assets.manager.get(Assets.MEDIUM_FONT);
        //mediumFont.getData().setScale(GlobalVariables.WORLD_SCALE);
        mediumFont.setColor(DEFAULT_FONT_COLOR);
        smallFont.setUseIntegerPositions(false);

        largeFont = game.assets.manager.get(Assets.LARGE_FONT);
        //largeFont.getData().setScale(GlobalVariables.WORLD_SCALE);
        largeFont.setColor(DEFAULT_FONT_COLOR);
        largeFont.setUseIntegerPositions(false);
    }


    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(0,0,0,1);
        batch.begin();


        saladBar.update(deltaTime);

        update(deltaTime);
        batch.draw(bgTexture,0,0, GlobalVariables.WORLD_WIDTH,GlobalVariables.WORLD_HEIGHT);
        golf.renderGolf(batch, deltaTime);
        sarah.renderSarah(batch,deltaTime);
        desk.draw(batch);

        //drawSaladBar
        saladBar.renderSalad(batch,deltaTime);
        //detect collisiion
        detectCollision(deltaTime);

        //draw the HUD
        renderHUD();
        if(dayState == DayState.STARTING){
            renderStartRoundText();
        }


        batch.end();

        elapsedSeconds += deltaTime;

        if (elapsedSeconds >= 1) {
            System.out.println(saladBar.saladList.size());
            elapsedSeconds = 0; // Reset the elapsed time
        }
    }

    private void update(float deltaTime){
        //set things in motion after starting delay
        if(dayState == DayState.STARTING && dayStateTime >= START_DAY_DELAY){
            //if the 3 seconds delay has passed, start the day
            sarah.setProductivity(0);
            dayState = DayState.IN_PROGRESS;
            dayStateTime = 0f;
            sarah.changeState(Sarah.SarahState.WORKING);
        } else if (dayState == DayState.ENDING && dayStateTime >= END_DAY_DELAY) {
            //game over or restart after ending delay
            if(life <= 0){
                gameState = GameState.GAME_OVER;
            }else {
                startDay();
            }
        }else{
            dayStateTime += deltaTime;
        }

        //block 2, determine whether this day should lose or win
        if(sarah.productivity >= sarah.workload && dayState == DayState.IN_PROGRESS){
            winDay();
        } else if (dayTimer <= 0) {
            loseDay();
        }

        //the cog of timer and working during the day "IN_PROGRESS" (only decrease timer during IN_PROGRESS)
        if(dayState == DayState.IN_PROGRESS){
            dayTimer -= deltaTime;
            sarah.working(deltaTime);
        }


    }

    private void detectCollision(float delTatime){
        //check if the rectangle is intersects
        ListIterator<Salad> iterator = SaladBar.saladList.listIterator();
        while (iterator.hasNext()){
            Salad salad = iterator.next();
            if(golf.intersectsSalad(salad.getBoundingBox()) && golf.golfState != Golf.GolfState.EATINGBOX) {
                iterator.remove();
                golf.makeGolfEatBox();
            }else if(sarah.intersectDeathBox(salad.getBoundingBox()) && dayState == DayState.IN_PROGRESS){
                iterator.remove();
                sarah.changeState(Sarah.SarahState.EATING);
            }
            }
    }

    private void renderHUD(){

        //draw the current day
        smallFont.draw(batch,"Day: " + currentDay,HUDMargin,viewport.getWorldHeight() - HUDMargin);

        //draw the productivity state
        String text;
        switch (sarah.productivityState){
            case STARTING:
                text = "STARTING";
                break;
            case HALFWAY:
                text = "HALFWAY";
                break;
            case ALMOST:
                text = "ALMOST DONE";
                break;
            default:
                text = "FINISHED";
        }
        smallFont.draw(batch,"Productivity: " + text,HUDMargin,HUDMargin + smallFont.getCapHeight());

        //set up the productivity bar
        productivityBarPadding = 10;
        productivityBarMaxHeight = 400; //the max productivity
        productivityBarWidth = 40;

        maxBackgroundBarPadding = 10;
        maxBackgroundBarHeight = productivityBarMaxHeight + maxBackgroundBarPadding * 2f;
        maxBackgroundBarWidth = productivityBarWidth + maxBackgroundBarPadding * 2f;
        maxBackgroundBarMarginBottom = 5;

        float maxBackgroundBarPositionY;
        maxBackgroundBarPositionY = smallFont.getCapHeight() + maxBackgroundBarMarginBottom;
        float productivityBarPositionY;
        productivityBarPositionY = maxBackgroundBarPositionY + maxBackgroundBarPadding;
        float percentTextPositionY = maxBackgroundBarHeight * 0.75f;

        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //draw the max background bar
        if(sarah.sarahState == Sarah.SarahState.EATING){
            shapeRenderer.setColor(YUCK_BACKGROUND_COLOR);
        }else {
            shapeRenderer.setColor(MAX_BACKGROUND_COLOR);
        }
        shapeRenderer.rect(HUDMargin,maxBackgroundBarPositionY,maxBackgroundBarWidth,maxBackgroundBarHeight);

        shapeRenderer.setColor(PRODUCTIVITY_BAR_COLOR);
        shapeRenderer.rect(HUDMargin + productivityBarPadding,productivityBarPositionY,productivityBarWidth,
                productivityBarMaxHeight * sarah.getProductivity() / sarah.getWorkload());

        shapeRenderer.end();
        batch.begin();

        //draw the percent
        int percent = (int) (sarah.productivity / sarah.workload * 100);
        smallFont.draw(batch,percent + "%",100, viewport.getWorldHeight() / 2f + smallFont.getCapHeight() / 2f,
                productivityBarWidth, Align.center,false);

        //draw the day timer
        mediumFont.draw(batch,Integer.toString((int) dayTimer),
                viewport.getWorldWidth() / 2f,viewport.getWorldHeight() - HUDMargin,
                0,Align.center,false);

        //change productivity state
        if (percent >= 50 && percent < 80) {
            sarah.changeProductivityState(Sarah.ProductivityState.HALFWAY);
        } else if (percent >= 80 && percent < 100) {
            sarah.changeProductivityState(Sarah.ProductivityState.ALMOST);
        } else if (percent == 100) {
            sarah.changeProductivityState(Sarah.ProductivityState.DONE);
        } else {
            sarah.changeProductivityState(Sarah.ProductivityState.STARTING);
        }
    }

    private void renderStartRoundText(){
        String text;
        if(dayStateTime < START_DAY_DELAY * 0.5){
            text = "Day " + currentDay;
        }else {
            text = "START";
        }
        mediumFont.draw(batch,text,viewport.getWorldWidth() / 2f,(viewport.getWorldHeight() + mediumFont.getCapHeight()) / 2f,
                0,Align.center,false);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        startGame();
    }

    private void startGame(){
        gameState = GameState.RUNNING;
        currentDay = 1;
        startDay();
    }

    private void startDay(){
        /* Because we have to
        1. Reset Golf and Sarah's state and position
        2. the SaladBar needs to be reconstructed cause more days = more speed and frequency of salads
        that's smart, Brandon.
         */
        prepareSalad();
        getGolfReady();

        dayState = DayState.STARTING;
        dayStateTime = 0f;
        dayTimer = MAX_REMAINING_TIME;
    }

    private void winDay(){
        //call the method that will refer to Sarah's win animation later

        currentDay++;
        sarah.changeState(Sarah.SarahState.CELEBRATING);
        endDay();
    }

    private void loseDay(){
        //call the method that will refer to Sarah's lose animation later

        life--;
        endDay();
    }

    private void endDay(){
        dayState = DayState.ENDING;
        dayStateTime = 0;
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
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
    public boolean keyDown(int keycode) {
        //movement

            if (keycode == Input.Keys.A) {
                golf.moveLeft();
            } else if (keycode == Input.Keys.D) {
                golf.moveRight();
            }

            if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
                golf.moveUp();
            } else if (keycode == Input.Keys.S) {
                golf.moveDown();
            }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A){
            golf.stopMovingLeft();
        } else if (keycode == Input.Keys.D) {
            golf.stopMovingRight();
        }
        if(keycode == Input.Keys.W || keycode == Input.Keys.UP){
            golf.stopMovingUp();
        }else if(keycode == Input.Keys.S){
            golf.stopMovingDown();
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
