package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mialene.hses.resources.GlobalVariables;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class SaladBar {
    //bar Characteristics
    private float barPositionX, barPositionY;
    private float barWidth, barHeight;
    //bar random
    private Random random = new Random();

    //salad Characteristics
    protected float saladWidth, saladHeight;
    public float saladMaxMoveSpeed;
    public Texture saladTexture;
    public float timeBetweenServe;
    public float timeSinceLastServe = 0;
    public static LinkedList<Salad> saladList;

    public SaladBar(float barPositionX, float barPositionY, float barWidth, float barHeight,
                    float saladWidth, float saladHeight, float saladMaxMoveSpeed, Texture saladTexture,
                    float timeBetweenServe,int dayCount) {
        this.barPositionX = barPositionX; //bar characteristics
        this.barPositionY = barPositionY;
        this.barWidth = barWidth;
        this.barHeight = barHeight;

        this.saladWidth = saladWidth; //starting here is salad characteristics
        this.saladHeight = saladHeight;
        this.saladTexture = saladTexture;
        this.saladMaxMoveSpeed = saladMaxMoveSpeed;
        this.timeBetweenServe = timeBetweenServe - 0.1f * dayCount;

        saladList = new LinkedList<>();
    }

    public void update(float deltaTime){
        timeSinceLastServe += deltaTime;
    }

    public boolean canYouServe(){
        return timeSinceLastServe - timeBetweenServe >= 0 && saladList.size() <= 5;
    }

    public Salad[] serveSalad(int dayCount){ //return an array of salad instance in a random number
        int saladCount = Math.min(3 + (dayCount - 1) / 5, 5);
        Salad[] salads = new Salad[(random.nextInt(saladCount) + 1)];
        for(int index = 0; index < salads.length; index++){

            float randomSpeedScale; //half the instant move speed by * 0.5 or retain it by * 1;
            float minScale = 0.5f;
            float maxScale = 1.0f;

            randomSpeedScale = minScale + random.nextFloat() * (maxScale - minScale);
            float speedModifier = dayCount * 5;

            salads[index] = new Salad(barPositionX,barPositionY + barHeight * ((random.nextFloat() * 0.96f) - 0.48f),
                    (saladMaxMoveSpeed + speedModifier) * randomSpeedScale,saladTexture,saladWidth,saladHeight);
        }

        timeSinceLastServe = 0;

        return salads;
    }

    public void renderSalad(Batch batch, float deltaTime, int dayCount){
        //Salads
        //create new salads
        if(canYouServe()){
            Salad[] salads = serveSalad(dayCount); //this is a method that return an array, and assign it to an array??
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
            if(salad.positionY + saladHeight * 0.7 >= GlobalVariables.WORLD_HEIGHT || salad.positionY + saladHeight * 0.3 <= 0){
                salad.verticalMoveSpeed *= -1;
            }

            if(salad.positionX + salad.width < 0){
                iterator.remove();
            }
        }
    }


}
