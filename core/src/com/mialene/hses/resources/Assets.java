package com.mialene.hses.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class Assets {
    public final AssetManager manager = new AssetManager();
    //gameplay assets
    public static final String BACKGROUND_TEXTURE = "textures/BG.png";
    public static final String DESK_TEXTURE = "textures/desk.png";
    public static final String IDLE_SPRITESHEET = "sprite/Idle.png";
    public static final String IDLE_RESIZE_SPRITESHEET = "sprite/IdleResize.png";
    public static final String EATING_BOX_SPRITESHEET = "sprite/EatingBox.png";

    //enemies
    public static final String SALAD_BOX_TEXTURE = "sprite/SaladBox.png";

    //fonts
    public static final String OPENSANS_REGULAR = "fonts/OpenSans-Regular.ttf";
    public static final String SMALL_FONT = "smallFont.ttf";
    public static final String MEDIUM_FONT = "mediumFont.ttf";
    public static final String LARGE_FONT = "largeFont.ttf";

    public void load(){
        //load all assets
        loadGameplayAssets();
        loadFonts();
    }

    private void loadGameplayAssets(){
        manager.load(BACKGROUND_TEXTURE, Texture.class);
        manager.load(DESK_TEXTURE,Texture.class);

        manager.load(IDLE_SPRITESHEET, Texture.class);
        manager.load(IDLE_RESIZE_SPRITESHEET, Texture.class);
        manager.load(SALAD_BOX_TEXTURE, Texture.class);
        manager.load(EATING_BOX_SPRITESHEET, Texture.class);
    }

    private void loadFonts(){
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class,".ttf",new FreetypeFontLoader(resolver));

        //load the small fonts
        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFont.fontFileName = OPENSANS_REGULAR;
        smallFont.fontParameters.size = 48;
        manager.load(SMALL_FONT, BitmapFont.class,smallFont);

        //load the medium fonts
        FreetypeFontLoader.FreeTypeFontLoaderParameter mediumFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mediumFont.fontFileName = OPENSANS_REGULAR;
        mediumFont.fontParameters.size = 106;
        mediumFont.fontParameters.borderWidth = 4;
        mediumFont.fontParameters.borderColor = Color.CORAL;
        manager.load(MEDIUM_FONT, BitmapFont.class,mediumFont);

        //load the large fonts
        FreetypeFontLoader.FreeTypeFontLoaderParameter largeFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        largeFont.fontFileName = OPENSANS_REGULAR;
        largeFont.fontParameters.size = 150;
        mediumFont.fontParameters.borderWidth = 6;
        mediumFont.fontParameters.borderColor = Color.DARK_GRAY;
        manager.load(LARGE_FONT, BitmapFont.class,largeFont);
    }

    public void dispose(){
        manager.dispose();
    }
}
