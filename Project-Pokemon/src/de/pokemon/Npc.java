package de.pokemon;

import org.newdawn.slick.Input;

public class Npc extends Entity {
	private int[][] route;
	private String name;

	private int counter = 0;

	private int step = 0;

	private int lastTileX;
	private int lastTileY;

	/**
	 * Konstruktor Npc
	 * 
	 * @param posX
	 *            (int) Start X Position des Npc's gemessen Kopf oben links
	 * @param posY
	 *            (int) Start Y Position des Npc's gemessen Kopf oben links
	 * @param name
	 *            (String) Name des Npc's und Referenz für übrige Werte
	 * @return void
	 */
	Npc(int posX, int posY, String name) {
		super(posX, posY, Core.tileSize, Core.tileSize * 2, ResourceManager
				.getNpcImagePath(name));
		route = ResourceManager.getNpcRoute(name);
		this.name = name;
		lastTileX = super.getTileX();
		lastTileY = super.getTileY();
	}

	/*
	 * @Override protected void renderTurnAnimation(int input) {
	 * 
	 * }
	 * 
	 * @Override protected void renderMoveAnimation(int input) {
	 * 
	 * }
	 */

	private boolean moveNpc(int command, int duration, int delta) {
		switch (command) {
		// Warten
		case 0:
			if (counter >= duration) {
				counter = 0;
				return true;
			} else {
				counter += delta;
				return false;
			}
			// Links drehen
		case 1:
			renderTurnAnimation(Input.KEY_A);
			return true;

			// Hoch drehen
		case 2:
			renderTurnAnimation(Input.KEY_W);
			return true;

			// Rechts drehen
		case 3:
			renderTurnAnimation(Input.KEY_D);
			return true;

			// Runter drehen
		case 4:
			renderTurnAnimation(Input.KEY_S);
			return true;

			// links laufen
		case 5:
			if (counter < duration) {
				if (Map.isBlocked(getTileX() - 1, getTileY())){
					if(currentView != Input.KEY_A)
						renderTurnAnimation(Input.KEY_A);
					return false;
				}
				renderMoveAnimation(Input.KEY_A);
				counter++;
				return false;
			} else {
				counter = 0;
				return true;
			}

			// Hoch laufen
		case 6:
			if (counter < duration) {
				if (Map.isBlocked(getTileX(), getTileY() - 1)){
					if(currentView != Input.KEY_W)
						renderTurnAnimation(Input.KEY_W);
					return false;
				}
				renderMoveAnimation(Input.KEY_W);
				counter++;
				return false;
			} else {
				counter = 0;
				return true;
			}

			// Rechts laufen
		case 7:
			if (counter < duration) {
				if (Map.isBlocked(getTileX() + 1, getTileY())){
					if(currentView != Input.KEY_D)
						renderTurnAnimation(Input.KEY_D);
					return false;
				}
				renderMoveAnimation(Input.KEY_D);
				counter++;
				return false;
			} else {
				counter = 0;
				return true;
			}

			// Runter Laufen
		case 8:
			if (counter < duration) {
				if (Map.isBlocked(getTileX(), getTileY() + 1)){
					if(currentView != Input.KEY_S)
						renderTurnAnimation(Input.KEY_S);
					return false;
				}
				renderMoveAnimation(Input.KEY_S);
				counter++;
				return false;
			} else {
				counter = 0;
				return true;
			}
		}
		return true;
	}

	public void updateNpc(int delta) {
		if (step < route.length) {
			if (!super.isRunning) {
				if (moveNpc(route[step][0], route[step][1], delta))
					step++;
			} else {
				super.renderTick(0);
			}
		} else
			step = 0;
	}

	public void renderNpcHead() {
		renderEntityHead();
	}

	public void renderNpcBody() {
		renderEntityBody();
	}

}
