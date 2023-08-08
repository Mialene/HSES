package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mialene.hses.resources.GlobalVariables;

import java.util.LinkedList;
import java.util.ListIterator;

public class SaladBar {
    //bar Characteristics
    private float barPositionX, barPositionY;
    private float barWidth, barHeight;

    //salad Characteristics
    protected float saladWidth, saladHeight;
    public float saladMoveSpeed;
    public Texture saladTexture;
    public float timeBetweenServe;
    public float timeSinceLastServe = 0;
    public LinkedList<Salad> saladList;

    public SaladBar(float barPositionX, float barPositionY, float barWidth, float barHeight,
                    float saladWidth, float saladHeight,float saladMoveSpeed, Texture saladTexture,
                    float timeBetweenServe) {
        this.barPositionX = barPositionX; //bar characteristics
        this.barPositionY = barPositionY;
        this.barWidth = barWidth;
        this.barHeight = barHeight;

        this.saladWidth = saladWidth; //starting here is salad characteristics
        this.saladHeight = saladHeight;
        this.saladTexture = saladTexture;
        this.saladMoveSpeed = saladMoveSpeed;
        this.timeBetweenServe = timeBetweenServe;

        saladList = new LinkedList<>();
    }

    public void drawSaladBar(Batch batch, Texture texture){
        batch.draw(texture,barPositionX - barWidth / 2,barPositionY - barHeight / 2,barWidth,barHeight);
    }

    public void update(float deltaTime){
        timeSinceLastServe += deltaTime;
    }

    public boolean canYouServe(){
        return timeSinceLastServe - timeBetweenServe >= 0;
    }

    public Salad[] serveSalad(){
        Salad[] salads = new Salad[3];
        salads[0] = new Salad(barPositionX,barPositionY + barHeight * 0.02f,saladMoveSpeed,saladTexture,
                saladWidth,saladHeight);

        salads[1] = new Salad(barPositionX,barPositionY - barHeight * 0.25f,saladMoveSpeed,saladTexture,
                saladWidth,saladHeight);
        salads[2] = new Salad(barPositionX,barPositionY - barHeight * 0.33f,saladMoveSpeed,saladTexture,
                saladWidth,saladHeight);

        timeSinceLastServe = 0;

        return salads;
    }

    public void renderSalad(Batch batch, float deltaTime){
        //Salads
        //create new salads
        if(canYouServe()){
            Salad[] salads = serveSalad(); //this is a method that return an array, and assign it to an array??
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
            salad.positionY += salad.verticalMoveSpeed * deltaTime;

            //make it bounce
            if(salad.positionY + saladHeight * 0.3 >= GlobalVariables.WORLD_HEIGHT || salad.positionY - saladHeight * 0.3 <= 0){
                salad.verticalMoveSpeed *= -1;
            }

            if(salad.positionX + salad.width < 0){
                iterator.remove();
            }
        }
    }
}
