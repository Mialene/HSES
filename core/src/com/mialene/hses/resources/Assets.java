package com.mialene.hses.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class Assets {
    public final AssetManager manager = new AssetManager();
    //gameplay assets: golf
    public static final String BACKGROUND_TEXTURE = "textures/BG.png";
    public static final String DESK_TEXTURE = "textures/desk.png";
    public static final String IDLE_SPRITESHEET = "sprite/Idle.png";
    public static final String IDLE_RESIZE_SPRITESHEET = "sprite/IdleResize.png";
    public static final String EATING_BOX_SPRITESHEET = "sprite/EatingBox.png";

    //enemies
    public static final String SALAD_BOX_TEXTURE = "sprite/SaladBox.png";

    //Sarah
    public static final String SARAH_WORKING_SPRITESHEET = "sprite/SarahWorking.png";
    public static final String SARAH_EATING_SPRITESHEET = "sprite/SarahEating.png";
    public static final String SARAH_CELEBRATING_SPRITESHEET = "sprite/SarahCelebrating.png";

    //fonts
    public static final String OPENSANS_REGULAR = "fonts/OpenSans-Regular.ttf";
    public static final String SMALL_FONT = "smallFont.ttf";
    public static final String MEDIUM_FONT = "mediumFont.ttf";
    public static final String LARGE_FONT = "largeFont.ttf";

    //buttons
    public static final String GAMEPLAY_BUTTONS_ATLAS = "sprite/GameplayButtons.atlas";
    public static final String UPDOWN_BUTTONS_ATLAS = "sprite/UpDownButton.atlas";
    //audio
    public static final String BITE_SOUND = "audio/bite.wav";
    public static final String CHEW_SOUND = "audio/chew.wav";
    public static final String NOM_SOUND = "audio/nom.ogg";
    public static final String SWALLOW_SOUND = "audio/swallow.ogg";
    //Sarah Interrupted sounds
    public static final String GIRL_AH_SOUND = "audio/girlAh.ogg";
    public static final String GIRL_SIGN_SOUND = "audio/girlSign.ogg";
    public static final String GIRL_WHAT_SOUND = "audio/girlWhat.ogg";
    //music
    public static final String JAZZY_FRENCHY_MUSIC = "audio/jazzyfrenchy.ogg";
    public static final String FUNKY_ELEMENT_MUSIC = "audio/funkyelement.ogg";
    // win / lose game
    public static final String COMPLETED_SERIES = "audio/completedSeries.ogg";
    public static final String GAME_OVER_SOUND_MUSIC = "audio/gameOverHigh.mp3";
    //logo
    public static final String LOGO = "textures/HSESLogo.png";

    public void load(){
        //load all assets
        loadGameplayAssets();
        loadFonts();
        loadAudio();
        loadMenuAssets();
    }

    private void loadGameplayAssets(){
        TextureLoader.TextureParameter parameter = new TextureLoader.TextureParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;

        manager.load(BACKGROUND_TEXTURE, Texture.class,parameter);
        manager.load(DESK_TEXTURE,Texture.class,parameter);
        manager.load(SALAD_BOX_TEXTURE, Texture.class,parameter);

        //load Golf sprite
        manager.load(IDLE_SPRITESHEET, Texture.class,parameter);
        manager.load(IDLE_RESIZE_SPRITESHEET, Texture.class,parameter);
        manager.load(EATING_BOX_SPRITESHEET, Texture.class,parameter);

        //load Sarah sprite
        manager.load(SARAH_WORKING_SPRITESHEET, Texture.class,parameter);
        manager.load(SARAH_EATING_SPRITESHEET, Texture.class,parameter);
        manager.load(SARAH_CELEBRATING_SPRITESHEET, Texture.class,parameter);

        //load buttons
        manager.load(GAMEPLAY_BUTTONS_ATLAS, TextureAtlas.class);
    }

    private void loadFonts(){
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class,".ttf",new FreetypeFontLoader(resolver));

        //load the small fonts
        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFont.fontFileName = OPENSANS_REGULAR;
        smallFont.fontParameters.size = 48;
        smallFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        smallFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        manager.load(SMALL_FONT, BitmapFont.class,smallFont);

        //load the medium fonts
        FreetypeFontLoader.FreeTypeFontLoaderParameter mediumFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mediumFont.fontFileName = OPENSANS_REGULAR;
        mediumFont.fontParameters.size = 106;
        mediumFont.fontParameters.borderWidth = 4;
        mediumFont.fontParameters.borderColor = Color.DARK_GRAY;
        mediumFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        mediumFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        manager.load(MEDIUM_FONT, BitmapFont.class,mediumFont);

        //load the large fonts
        FreetypeFontLoader.FreeTypeFontLoaderParameter largeFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        largeFont.fontFileName = OPENSANS_REGULAR;
        largeFont.fontParameters.size = 150;
        largeFont.fontParameters.borderWidth = 6;
        largeFont.fontParameters.borderColor = Color.CORAL;
        largeFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        largeFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        manager.load(LARGE_FONT, BitmapFont.class,largeFont);
    }

    private void loadAudio(){
        manager.load(BITE_SOUND, Sound.class);
        manager.load(CHEW_SOUND, Sound.class);
        manager.load(NOM_SOUND, Sound.class);
        manager.load(SWALLOW_SOUND, Sound.class);

        //Sarah interrupted sound
        manager.load(GIRL_AH_SOUND, Sound.class);
        manager.load(GIRL_SIGN_SOUND, Sound.class);
        manager.load(GIRL_WHAT_SOUND, Sound.class);

        manager.load(JAZZY_FRENCHY_MUSIC, Music.class);
        manager.load(FUNKY_ELEMENT_MUSIC, Music.class);

        manager.load(COMPLETED_SERIES, Sound.class);
        manager.load(GAME_OVER_SOUND_MUSIC, Music.class);
    }

    public void loadMenuAssets(){
        manager.load(LOGO, Texture.class);
        manager.load(UPDOWN_BUTTONS_ATLAS, TextureAtlas.class);
    }

    public void dispose(){
        manager.dispose();
    }
}
