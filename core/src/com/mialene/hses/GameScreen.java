package com.mialene.hses;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mialene.hses.objects.Food;
import com.mialene.hses.objects.Golf;
import com.mialene.hses.resources.Assets;
import com.mialene.hses.resources.GlobalVariables;

public class GameScreen implements Screen, InputProcessor {
    //game variable
    private final HSES game;


    //Graphics
    public SpriteBatch batch;
    private Viewport viewport;

    //All Texture;
    private Texture bgTexture, saladBoxTexture;


    //game objects
    Golf golf;
    Food saladBox;
    GameScreen(HSES game){
        this.game = game;
        viewport = new FitViewport(2436,1125);
        batch = new SpriteBatch();

        game.assets.load();
        game.assets.manager.finishLoading();

        //
        createGameArea();
        initializeFood();
        getGolfReady();
    }

    private void createGameArea(){
        bgTexture = game.assets.manager.get(Assets.BACKGROUND_TEXTURE);
    }
    private void getGolfReady(){
        golf = new Golf(game);
    }

    //(tmp) draw still image of a test food
    private void initializeFood(){
        saladBoxTexture = game.assets.manager.get(Assets.SALAD_BOX_TEXTURE);

        //initialize food
        saladBox = new Food(saladBoxTexture,2000,1000,saladBoxTexture.getWidth(),saladBoxTexture.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        batch.begin();
        batch.draw(bgTexture,0,0, GlobalVariables.WORLD_WIDTH,GlobalVariables.WORLD_HEIGHT);
        golf.renderGolf(batch);
        saladBox.drawFood(batch);

        batch.end();
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
        if(keycode == Input.Keys.A){
            golf.moveLeft();
        } else if (keycode == Input.Keys.D) {
            golf.moveRight();
        }

        if(keycode == Input.Keys.W || keycode == Input.Keys.UP){
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
