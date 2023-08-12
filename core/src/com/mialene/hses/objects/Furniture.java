package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Furniture {
    private Texture texture;
    private float xPosition, yPosition; //store the lower left corner
    private float width, height;

    public Furniture(Texture texture, float xCentre, float yCentre, float width, float height) {
        this.texture = texture;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
    }

    public void draw(Batch batch){
        batch.draw(texture,xPosition,yPosition,width,height);
    }
}
