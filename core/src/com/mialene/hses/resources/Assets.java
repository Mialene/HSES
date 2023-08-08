package com.mialene.hses.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public final AssetManager manager = new AssetManager();
    //gameplay assets
    public static final String BACKGROUND_TEXTURE = "textures/BG.png";
    public static final String IDLE_SPRITESHEET = "sprite/Idle.png";

    //enemies
    public static final String BLACK_TEXTURE = "textures/Black.png";
    public static final String SALAD_BOX_TEXTURE = "sprite/SaladBox.png";

    public void load(){
        //load all assets
        loadGameplayAssets();
    }

    private void loadGameplayAssets(){
        manager.load(BACKGROUND_TEXTURE, Texture.class);
        manager.load(IDLE_SPRITESHEET, Texture.class);
        manager.load(SALAD_BOX_TEXTURE, Texture.class);
        manager.load(BLACK_TEXTURE, Texture.class);
    }

    public void dispose(){
        manager.dispose();
    }
}
