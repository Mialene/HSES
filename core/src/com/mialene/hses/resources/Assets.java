package com.mialene.hses.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public final AssetManager manager = new AssetManager();
    //gameplay assets
    public static final String BACKGROUND_TEXTURE = "textures/BG.png";
    public static final String IDLE_SPRITESHEET = "sprite/Idle.png";
    public static final String IDLE_RESIZE_SPRITESHEET = "sprite/IdleResize.png";
    public static final String EATING_BOX_SPRITESHEET = "sprite/EatingBox.png";

    //enemies
    public static final String SALAD_BOX_TEXTURE = "sprite/SaladBox.png";

    public void load(){
        //load all assets
        loadGameplayAssets();
    }

    private void loadGameplayAssets(){
        manager.load(BACKGROUND_TEXTURE, Texture.class);
        manager.load(IDLE_SPRITESHEET, Texture.class);
        manager.load(IDLE_RESIZE_SPRITESHEET, Texture.class);
        manager.load(SALAD_BOX_TEXTURE, Texture.class);
        manager.load(EATING_BOX_SPRITESHEET, Texture.class);
    }

    public void dispose(){
        manager.dispose();
    }
}
