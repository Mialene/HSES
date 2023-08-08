package com.mialene.hses;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mialene.hses.HSES;
import com.mialene.hses.resources.GlobalVariables;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Help Sarah Eat Salad");
		config.setWindowedMode(1000,500);
		new Lwjgl3Application(new HSES(), config);
	}
}
