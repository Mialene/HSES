package com.mialene.hses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mialene.hses.objects.Golf;
import com.mialene.hses.objects.Salad;
import com.mialene.hses.objects.SaladBar;
import com.mialene.hses.objects.Sarah;
import com.mialene.hses.resources.Assets;
import com.mialene.hses.resources.GlobalVariables;

import java.util.ListIterator;

public class GameScreen implements Screen, InputProcessor {
    //game variable
    private final HSES game;
    //Graphics
    public SpriteBatch batch;
    private Viewport viewport;

    //game
    private Sarah.ProductivityState productivityState = Sarah.ProductivityState.STARTING;
    //day
    private int currentDay = 1;
    private static final float MAX_REMAINING_TIME = 99.99f;
    private float remainingTime = MAX_REMAINING_TIME;
    //fonts
    private BitmapFont smallFont, mediumFont,largeFont;
    private static final Color DEFAULT_FONT_COLOR = Color.WHITE;
    //HUD
    private float HUDMargin = 20f;
    private static final Color PRODUCTIVITY_BAR_COLOR = GlobalVariables.GOLD;
    private static final Color PRODUCTIVITY_BACKGROUND_COLOR = GlobalVariables.BLUEISH;


    //All Texture;
    private Texture bgTexture, saladBoxTexture;


    //game objects
    Golf golf;
    SaladBar saladBar;

    private float elapsedSeconds = 0;
    GameScreen(HSES game){
        this.game = game;
        viewport = new FitViewport(2436,1125);
        batch = new SpriteBatch();

        game.assets.load();
        game.assets.manager.finishLoading();

        //
        createGameArea();
        setUpFonts();
        prepareSalad();

        getGolfReady();
    }

    private void createGameArea(){
        bgTexture = game.assets.manager.get(Assets.BACKGROUND_TEXTURE);
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
        batch.draw(bgTexture,0,0, GlobalVariables.WORLD_WIDTH,GlobalVariables.WORLD_HEIGHT);
        golf.renderGolf(batch, deltaTime);

        //drawSaladBar
        saladBar.renderSalad(batch,deltaTime);
        //detect collisiion
        detectCollision(deltaTime);

        //draw the HUD
        renderHUD();


        batch.end();

        elapsedSeconds += deltaTime;

        if (elapsedSeconds >= 1) {
            System.out.println(saladBar.saladList.size());
            elapsedSeconds = 0; // Reset the elapsed time
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
            }
            }
    }

    private void renderHUD(){

        //draw the current day
        smallFont.draw(batch,"Day: " + "1",HUDMargin,viewport.getWorldHeight() - HUDMargin);

        //draw the productivity state
        String text;
        switch (productivityState){
            case STARTING:
                text = "STARTING";
                break;
            case HALFWAY:
                text = "HAlFWAY";
                break;
            case ALMOST:
                text = "ALMOST DONE";
                break;
            case DONE:
                text = "FINISHED";
            default:
                text = "EATING";
        }
        smallFont.draw(batch,"Productivity: " + text,HUDMargin,HUDMargin + smallFont.getCapHeight());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {

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
