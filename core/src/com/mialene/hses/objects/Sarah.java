package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mialene.hses.HSES;
import com.mialene.hses.resources.Assets;
import com.mialene.hses.resources.GlobalVariables;

import java.util.Random;


public class Sarah {
    private final HSES game;
    public enum SarahState {//determine the productivity bar progress
        WORKING,
        EATING,
        CELEBRATING,
        CRYING
    }

    public enum ProductivityState {//to display the state text
        STARTING,
        HALFWAY,
        ALMOST,
        DONE,
        STOP
    }

    public SarahState sarahState;
    public ProductivityState productivityState;
    public float workload;
    public float productivity;
    private float stateTime;
    Rectangle deathBox;

    //for animations
    private Texture workingTexture, eatingTexture, celebratingTexture;
    private Animation<TextureRegion> workingAnimation, eatingAnimation, celebratingAnimation;
    private TextureRegion[] allFrames;
    private TextureRegion currentFrame;

    public Sarah(HSES game, int dayCount) {
        this.game = game;

        productivity = 0;
        sarahState = SarahState.WORKING;
        productivityState = ProductivityState.STARTING;
        deathBox = new Rectangle(0,0, GlobalVariables.WORLD_WIDTH * 0.25f,GlobalVariables.WORLD_HEIGHT);

        initializeWorkingAnimation();
        initializeEatingAnimation();
        initializeCelebratingAnimation();

        stateTime = 0;

        setWorkloadByday(dayCount);
    }

    public void update(float stateTime) {

        if(isSarahFinishedEating(stateTime)){
            changeState(SarahState.WORKING);
        }
    }

    public void working(float deltaTime) {
        if (sarahState == SarahState.WORKING) {
            productivity += 15 * deltaTime;
        }
    }

    public void setProductivity(float num) {
        this.productivity = num;
    }

    public float getProductivity() {
        return productivity;
    }

    public float getWorkload() {
        return workload;
    }

    public void changeState(SarahState newState) {
        sarahState = newState;
        stateTime = 0;
    }

    public boolean intersectDeathBox(Rectangle otherRectangle){
        return deathBox.overlaps(otherRectangle);
    }

    public boolean isSarahFinishedEating(float stateTime) {
        return stateTime >= 3;
    }

    private void initializeWorkingAnimation(){
        workingTexture = game.assets.manager.get(Assets.SARAH_WORKING_SPRITESHEET);
        allFrames = splitToArray(workingTexture);
        workingAnimation = new Animation<>(0.09f,allFrames);
    }

    private void initializeEatingAnimation(){
        eatingTexture = game.assets.manager.get(Assets.SARAH_EATING_SPRITESHEET);
        allFrames = splitToArray(eatingTexture);
        eatingAnimation = new Animation<>(0.09f,allFrames);
    }

    private void initializeCelebratingAnimation(){
        celebratingTexture = game.assets.manager.get(Assets.SARAH_CELEBRATING_SPRITESHEET);
        allFrames = splitToArray(celebratingTexture);
        celebratingAnimation = new Animation<>(0.09f,allFrames);
    }

    private TextureRegion[] splitToArray(Texture texture){
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 2);
                allFrames = new TextureRegion[4];
                int index = 0;
                for(int i = 0; i < 2; i++){
                    for(int j = 0; j < 2; j++){
                        allFrames[index++] = tmp[i][j];
                    }
                }
                return allFrames;
    }

    public void renderSarah(Batch batch, float deltaTime){
        stateTime += deltaTime;
        update(stateTime);

        switch (sarahState){
            case WORKING:
                currentFrame = workingAnimation.getKeyFrame(stateTime,true);
                break;
            case CELEBRATING:
                currentFrame = celebratingAnimation.getKeyFrame(stateTime,true);
                break;
            case EATING:
                currentFrame = eatingAnimation.getKeyFrame(stateTime,true);
        }
        batch.draw(currentFrame,GlobalVariables.WORLD_WIDTH * 0.16f,GlobalVariables.WORLD_HEIGHT * 0.32f,
                currentFrame.getRegionWidth() * 0.56f,currentFrame.getRegionHeight() * 0.56f);
    }

    public void changeProductivityState(ProductivityState newState){
        productivityState = newState;
    }

    public void setWorkloadByday(int dayCount){
        this.workload = 1000 + dayCount * 20;
    }
}
