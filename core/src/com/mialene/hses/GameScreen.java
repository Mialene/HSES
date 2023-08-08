package com.mialene.hses;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mialene.hses.objects.Golf;
import com.mialene.hses.objects.Salad;
import com.mialene.hses.objects.SaladBar;
import com.mialene.hses.resources.Assets;
import com.mialene.hses.resources.GlobalVariables;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen, InputProcessor {
    //game variable
    private final HSES game;


    //Graphics
    public SpriteBatch batch;
    private Viewport viewport;

    //All Texture;
    private Texture bgTexture,barTexture, saladBoxTexture;


    //game objects
    Golf golf;
    SaladBar saladBar;
    private LinkedList<Salad> saladList;

    private float elapsedSeconds = 0;
    GameScreen(HSES game){
        this.game = game;
        viewport = new FitViewport(2436,1125);
        batch = new SpriteBatch();

        game.assets.load();
        game.assets.manager.finishLoading();

        //
        createGameArea();
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
        barTexture = game.assets.manager.get(Assets.BLACK_TEXTURE);
        saladBoxTexture = game.assets.manager.get(Assets.SALAD_BOX_TEXTURE);
        saladList = new LinkedList<>();

        saladBar = new SaladBar(2200f,555f,5f,1125f,
                saladBoxTexture.getWidth() * 0.5f,saladBoxTexture.getHeight() * 0.5f,500
        ,saladBoxTexture,1f);

    }


    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(0,0,0,1);


        batch.begin();
        saladBar.update(deltaTime);
        batch.draw(bgTexture,0,0, GlobalVariables.WORLD_WIDTH,GlobalVariables.WORLD_HEIGHT);
        golf.renderGolf(batch);

        //drawSaladBar
        saladBar.drawSaladBar(batch,barTexture);
        //Salads
        //create new salads
        if(saladBar.canYouServe()){
            Salad[] salads = saladBar.serveSalad(); //this is a method that return an array, and assign it to an array??
            for(Salad salad : salads){
                saladList.add(salad);
            }
        }
        //draw and remove old salads
        ListIterator<Salad> iterator = saladList.listIterator();
        while (iterator.hasNext()){
            Salad salad = iterator.next();
            salad.drawSalad(batch);
            salad.positionX -= salad.moveSpeed * deltaTime;
            if(salad.positionX + salad.width < 0){
                iterator.remove();
            }
        }

        batch.end();

        elapsedSeconds += deltaTime;

        if (elapsedSeconds >= 1) {
            System.out.println(saladList.size());
            elapsedSeconds = 0; // Reset the elapsed time
        }
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
