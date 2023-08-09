package com.mialene.hses.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mialene.hses.HSES;
import com.mialene.hses.resources.Assets;
import com.mialene.hses.resources.GlobalVariables;

public class Golf {
    private final HSES game;
    //materials for animations
    public enum GolfState{
        IDLE,
        WALK
    }
    GolfState golfState;
    private Texture idleTexture;
    Animation<TextureRegion> idleAnimation, walkAnimation;
    private TextureRegion[] allFrames;
    private TextureRegion currentFrame;
    private float stateTime;
    //variables related to position
    private float startX = 1000, startY = 500;
    public Vector2 golfPosition = new Vector2();
    private int facing;
    private  float golfWidth = 486 * 0.5f, golfHeight = 450 * 0.5f;
    //for movement
    private final float MOVE_SPEED = 10;
    private Vector2 movementDirection = new Vector2();


    public Golf(HSES game) {
        this.game = game;
        golfState = GolfState.IDLE;
        golfPosition.set(startX,startY);
        movementDirection.set(0,0);
        facing = 1;

        initializeIdleAnimation();

        stateTime = 0;
    }

    private void changeState(GolfState newState){
        golfState = newState;
        stateTime = 0;
    }

    private void update(){
        golfPosition.x += movementDirection.x;
        golfPosition.y += movementDirection.y;

        if(golfPosition.y >= GlobalVariables.WORLD_HEIGHT){
            golfPosition.y = GlobalVariables.WORLD_HEIGHT;
        } else if (golfPosition.y - golfHeight <= 0) {
            golfPosition.y = golfHeight;
        }

        if(golfPosition.x - golfWidth <= 0){
            golfPosition.x = golfWidth;
        } else if (golfPosition.x >= GlobalVariables.WORLD_WIDTH) {
            golfPosition.x = GlobalVariables.WORLD_WIDTH;
        }
    }

    public void moveLeft(){
        setMovement(-1 * MOVE_SPEED,movementDirection.y);
        facing = -1;
    }

    public void moveRight(){
        setMovement(1 * MOVE_SPEED,movementDirection.y);
        facing = 1;
    }

    public void moveUp(){
        setMovement(movementDirection.x,1 * MOVE_SPEED);
    }

    public void moveDown(){
        setMovement(movementDirection.x, -1 * MOVE_SPEED);
    }

    public void stopMovingLeft(){
        if(movementDirection.x == -MOVE_SPEED){
            setMovement(0, movementDirection.y);
        }
    }

    public void stopMovingRight(){
        if(movementDirection.x == MOVE_SPEED){
            setMovement(0, movementDirection.y);
        }
    }

    public void stopMovingUp(){
        if(movementDirection.y == MOVE_SPEED){
            setMovement(movementDirection.x,0);
        }
    }

    public void stopMovingDown(){
        if(movementDirection.y == -MOVE_SPEED){
            setMovement(movementDirection.x,0);
        }
    }

    private void setMovement(float x, float y){
        movementDirection.set(x,y);
    }




    private void initializeIdleAnimation(){
        idleTexture = game.assets.manager.get(Assets.IDLE_SPRITESHEET);
        allFrames = splitToArray(idleTexture);
        idleAnimation = new Animation<>(0.09f,allFrames);
    }

    private TextureRegion[] splitToArray(Texture texture){
        TextureRegion[][] tmp = TextureRegion.split(texture, idleTexture.getWidth() / 2, idleTexture.getHeight() / 2);
        allFrames = new TextureRegion[4];
        int index = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                allFrames[index++] = tmp[i][j];
            }
        }
        return allFrames;
    }

    public void renderGolf(Batch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        update();
        switch (golfState){
            case IDLE:
                currentFrame = idleAnimation.getKeyFrame(stateTime,true);
                break;
        }
        //below is from chatGPT


        int frameX = currentFrame.getRegionX();
        int frameY = currentFrame.getRegionY();

        batch.draw(currentFrame,golfPosition.x - golfWidth,
                golfPosition.y - golfHeight,golfWidth / 2,golfHeight / 2,
                golfWidth,
                golfHeight,facing,1,0);
    }
}
