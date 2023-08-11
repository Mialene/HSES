package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sarah {
    public enum SarahState{//determine the productivity bar progress
        WORKING,
        EATING,
        CELEBRATING,
        CRYING
    }

    public enum ProductivityState{//to display the state text
        STARTING,
        HALFWAY,
        ALMOST,
        DONE,
        STOP
    }
    public SarahState sarahState;
    private TextureRegion textureRegion;
    private float sarahPostionX, getSarahPostionY, sarahWidth, sarahHeight;
    public float workload = 1000;
    public float productivity;

    public Sarah() {

        productivity = 0;
        sarahState = SarahState.WORKING;
    }

    public void update(float deltaTime){
        isSarahWorking(deltaTime);
    }

    private void isSarahWorking(float deltaTime){
        switch(sarahState){
            case EATING:
                productivity = productivity;
                break;
            default:
                if(productivity <= workload) {
                    productivity += 15 * deltaTime;
                }
        }
    }

    public float getProductivity(){
        return productivity;
    }

    public float getWorkload(){
        return workload;
    }
}
