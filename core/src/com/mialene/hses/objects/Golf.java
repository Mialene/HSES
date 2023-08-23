package com.mialene.hses.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mialene.hses.HSES;
import com.mialene.hses.resources.Assets;
import com.mialene.hses.resources.GlobalVariables;

public class Golf {
    private final HSES game;

    //materials for animations
    public enum GolfState {
        IDLE,
        WALK,
        EATINGBOX
    }

    public GolfState golfState;
    private Texture idleTexture, eatingBoxTexture;
    Animation<TextureRegion> idleAnimation, walkAnimation, eatingBoxAnimation;
    private TextureRegion[] allFrames;
    private TextureRegion currentFrame;
    private final float LEFT_BOUND = GlobalVariables.WORLD_WIDTH * 0.32f;
    private float stateTime;
    //variables related to position
    private float startX = 1000, startY = 500;
    public Vector2 golfPosition = new Vector2(); // for now it looks like this store centre
    public Rectangle golfBoundingBox;
    public int facing;
    private float golfWidth = 300 * 0.5f, golfHeight = 450 * 0.5f;
    //for movement
    public final float MOVE_SPEED = 10;
    private Vector2 movementDirection = new Vector2();


    public Golf(HSES game) {
        this.game = game;
        golfState = GolfState.IDLE;
        golfPosition.set(startX, startY);
        golfBoundingBox = new Rectangle(golfPosition.x - golfWidth * 0.6f, golfPosition.y - golfHeight * 0.7f,
                golfWidth * 0.6f, golfHeight * 0.6f);
        movementDirection.set(0, 0);
        facing = 1;

        initializeIdleAnimation();
        initializeEatingBoxAnimation();

        stateTime = 0;
    }

    private void changeState(GolfState newState) {
        golfState = newState;
        stateTime = 0;
    }

    private void update(float stateTime) {
        golfPosition.add(movementDirection);

        if (golfPosition.y >= GlobalVariables.WORLD_HEIGHT) {
            golfPosition.y = GlobalVariables.WORLD_HEIGHT;
        } else if (golfPosition.y - golfHeight <= 0) {
            golfPosition.y = golfHeight;
        }

        if (golfPosition.x - golfWidth <= LEFT_BOUND) {
            golfPosition.x = LEFT_BOUND + golfWidth;
        } else if (golfPosition.x >= GlobalVariables.WORLD_WIDTH) {
            golfPosition.x = GlobalVariables.WORLD_WIDTH;
        }

        golfBoundingBox.set(golfPosition.x - golfWidth * 0.6f, golfPosition.y - golfHeight * 0.7f,
                golfWidth * 0.3f, golfHeight * 0.6f);

        //check if eating is finished then change state to IDLE
        if (isEatingFinished(stateTime)) {
            changeState(GolfState.IDLE);
        }
    }

    public void moveLeft() {
        setMovement(-1 * MOVE_SPEED, movementDirection.y);
        facing = -1;
    }

    public void moveRight() {
        setMovement(1 * MOVE_SPEED, movementDirection.y);
        facing = 1;
    }

    public void moveUp() {
        setMovement(movementDirection.x, 1 * MOVE_SPEED);
    }

    public void moveDown() {
        setMovement(movementDirection.x, -1 * MOVE_SPEED);
    }

    public void stopMovingLeft() {
        if (movementDirection.x == -MOVE_SPEED) {
            setMovement(0, movementDirection.y);
        }
    }

    public void stopMovingRight() {
        if (movementDirection.x == MOVE_SPEED) {
            setMovement(0, movementDirection.y);
        }
    }

    public void stopMovingUp() {
        if (movementDirection.y == MOVE_SPEED) {
            setMovement(movementDirection.x, 0);
        }
    }

    public void stopMovingDown() {
        if (movementDirection.y == -MOVE_SPEED) {
            setMovement(movementDirection.x, 0);
        }
    }

    public void setMovement(float x, float y) {
        movementDirection.set(x, y);
    }

    private void initializeIdleAnimation() {
        idleTexture = game.assets.manager.get(Assets.IDLE_RESIZE_SPRITESHEET);
        allFrames = splitToArray(idleTexture);
        idleAnimation = new Animation<>(0.09f, allFrames);
    }

    private void initializeEatingBoxAnimation() {
        eatingBoxTexture = game.assets.manager.get(Assets.EATING_BOX_SPRITESHEET);
        allFrames = splitToArray(eatingBoxTexture);
        eatingBoxAnimation = new Animation<>(0.09f, allFrames);
    }

    private TextureRegion[] splitToArray(Texture texture) {
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 2);
        allFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                allFrames[index++] = tmp[i][j];
            }
        }
        return allFrames;
    }

    public boolean isEatingFinished(float stateTime) {
        return stateTime >= 2;
    }

    public void makeGolfEatBox() {
        changeState(GolfState.EATINGBOX);
    }

    public void renderGolf(Batch batch, float deltaTime) {
        stateTime += deltaTime;
        update(stateTime);
        switch (golfState) {
            case IDLE:
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                break;
            case EATINGBOX:
                currentFrame = eatingBoxAnimation.getKeyFrame(stateTime, true);
        }
        //below is from chatGPT to make the character flip right / left perfectly

        int frameX = currentFrame.getRegionX();
        int frameY = currentFrame.getRegionY();

        batch.draw(currentFrame, golfPosition.x - golfWidth,
                golfPosition.y - golfHeight, golfWidth / 2, golfHeight / 2,
                golfWidth,
                golfHeight, facing, 1, 0);
    }

    //check if intersect with a salad
    public boolean intersectsSalad(Rectangle otherRectangle) {
        return golfBoundingBox.overlaps(otherRectangle);
    }

    public void translate(float deltaX, float deltaY){
        golfPosition.x += deltaX;
        golfPosition.y += deltaY;
    }
}
