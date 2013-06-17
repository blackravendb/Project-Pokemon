package de.pokemon;

import org.newdawn.slick.Input;

public class Npc extends Entity {
	private int[][] route;
	private String name;

	private int counter = 0;

	private int step = 0;

	private int random;
	private int random2;
	private int randomCounter = 0;
	
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
	Npc(int posX, int posY, String name, Event event) {
		super(posX, posY, Core.tileSize, Core.tileSize * 2, ResourceManager
				.getNpcImagePath(name), event);
		route = ResourceManager.getNpcRoute(name);
		this.name = name;
		
		lastTileX = getTileX();
		lastTileY = getTileY();
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
				if (Map.isBlocked(getTileX() - 1, getTileY())) {
					if (currentView != Input.KEY_A)
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
				if (Map.isBlocked(getTileX(), getTileY() - 1)) {
					if (currentView != Input.KEY_W)
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
				if (Map.isBlocked(getTileX() + 1, getTileY())) {
					if (currentView != Input.KEY_D)
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
				if (Map.isBlocked(getTileX(), getTileY() + 1)) {
					if (currentView != Input.KEY_S)
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
		if (route != null) {
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
		// Zufallsmuster
		else {
			if (!isRunning || randomCounter > 3000) {
				if (isRunning) {
					super.isRunning = false;
					counter = 0;
					Map.setBlocked(lastTileX, lastTileY, false);
				}
				if (counter == 0) {
					random = (int) (Math.random() * (9 + 6) - 6);
					if (random <= 0) {
						random = 0;
						random2 = (int) (Math.random() * (3000 - 500) + 500);
					} else {
						random2 = (int) (Math.random() * (4 - 1) + 1);

					}
					System.out.println("random: " + random + " random2: "
							+ random2);
				}
				moveNpc(random, random2, delta);
			} else {
				renderTick(0);
				randomCounter += delta;
				lastTileX = getTileX();
				lastTileY = getTileY();
			}
		}
	}

	public void renderNpcHead() {
		renderEntityHead();
	}

	public void renderNpcBody() {
		renderEntityBody();
	}

}
