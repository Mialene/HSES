package com.mialene.hses.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sarah {
    public enum SarahState{
        WORKING,
        EATING,
        CELEBRATING,
        CRYING
    }

    public enum ProductivityState{
        STARTING,
        HALFWAY,
        ALMOST,
        DONE,
        STOP
    }
    public SarahState sarahState;
    private TextureRegion textureRegion;
    private float sarahPostionX, getSarahPostionY, sarahWidth, sarahHeight;
    public float workload = 100;
    public float productivity;

    public Sarah() {

        productivity = 0;
    }
}
