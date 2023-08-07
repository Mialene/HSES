package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mialene.hses.resources.GlobalVariables;

public class Food {
    private Texture texture;
    private Vector2 position = new Vector2();
    private float width, heigth;

    public Food(Texture texture,float xCentre,float yCentre, float width, float heigth) {
        this.texture = texture;
        position.x = xCentre - width / 2;
        position.y = yCentre - heigth / 2;
        this.width = width;
        this.heigth = heigth;
    }

    public void drawFood(Batch batch){
        batch.draw(texture,position.x,position.y,width * GlobalVariables.FOOD_SCALE,heigth * GlobalVariables.FOOD_SCALE);
    }
}
