package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Salad {
    public float positionX, positionY; //store the lower left position
    public float width, height;
    public float moveSpeed;
    public float verticalMoveSpeed;
    private Texture texture;

    public Salad(float xCentre, float yCentre, float moveSpeed, Texture texture,float width, float height) {
        this.positionX = xCentre - width / 2;
        this.positionY = yCentre - height / 2;
        this.moveSpeed = moveSpeed;
        this.texture = texture;
        this.width = width;
        this.height = height;

        Random random = new Random();
        float randomNum = random.nextFloat() * 800 - 400;
        verticalMoveSpeed = randomNum;
    }

    public void drawSalad(Batch batch){
        batch.draw(texture,positionX,positionY,width,height);
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(positionX,positionY,width,height);
    }


}