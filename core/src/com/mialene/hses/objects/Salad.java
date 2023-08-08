package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Salad {
    public float positionX, positionY;
    public float width, height;
    public float moveSpeed;
    private Texture texture;

    public Salad(float positionX, float positionY, float moveSpeed, Texture texture,float width, float height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.moveSpeed = moveSpeed;
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public void drawSalad(Batch batch){
        batch.draw(texture,positionX - width / 2,positionY - height / 2,width,height);
    }
}
