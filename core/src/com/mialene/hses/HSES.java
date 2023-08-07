package com.mialene.hses;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mialene.hses.resources.Assets;

public class HSES extends Game {
	public Assets assets;
	private GameScreen gameScreen;

	
	@Override
	public void create () {
		assets = new Assets();
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		gameScreen.dispose();
		assets.dispose();
	}
}
