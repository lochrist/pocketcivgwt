package com.bbg.client;

import java.util.Vector;

import com.bbg.client.State.GameEngine;
import com.bbg.client.defaultdata.DefaultMap;
import com.bbg.client.defaultdata.DefaultRegions;
import com.bbg.client.ui.GameUi;
import com.bbg.client.model.GameModel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Pocketciv implements EntryPoint, GWT.UncaughtExceptionHandler {
	private GameEngine engine;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(this);

		createCivilization();
		// new Test().execute();
	}

	void createCivilization() {
		Vector regions = DefaultRegions.generateRegions();
		GameModel model = new GameModel(regions);
		GameUi ui = new GameUi(model, new DefaultMap(regions));
		engine = new GameEngine(model, ui);

		RootPanel.get().add(ui.getUi());

		engine.start();
	}

	public void onUncaughtException(Throwable e) {
		GameOverException isGameOver = (GameOverException) e;
		if (e != null) {
			engine.gameOver().transition();
			System.out.println(e);
		} else {
			System.out.println(e);
		}
	}
}
