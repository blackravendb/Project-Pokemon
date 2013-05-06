package de.pokemon;
import java.awt.Graphics;
import java.awt.Rectangle;



public class Player extends Rectangle {
	
	public double moveSpeed = 0.8;
	
	//Spieler Grafik
	public static int[][] pImgUp= {{0, 1}, {2, 1}};
	public static int[][] pImgDown= {{0, 3}, {2, 3}};
	public static int[][] pImgLeft= {{3, 3}, {5, 3}};
	public static int[][] pImgRight= {{3, 1}, {5, 1}};
	public static int[][] pImgStand = {{1, 3}, {1, 1}, {4, 3}, {4, 1}};
	
	public static boolean isMoving = false;
	public static boolean up =false;
	public static boolean down =false;
	public static boolean left =false;
	public static boolean right =false;
	
	public int aniFrame = 0;
	public int aniTime = 20;
	public int aniDelta = 0;
	
	private int lastDir = 0; //letzte Bewegungsrichtung
	
	public Player(String name){
		width = 16;
		height = 32;
		setBounds((Core.pixel.width / 2) - (width / 2), (Core.pixel.height / 2) - (height / 2), width, height);
	}
	
	public void tick() {
		
		aniDelta ++;
		
		if(aniDelta >= aniTime){
			aniFrame ++;
			aniDelta = 0;
			if(aniFrame > 1){
				aniFrame = 0;
			}
		}
		
		if(up){
			Core.oY -= moveSpeed;
			lastDir = 1;
		}
		else if (down){
			Core.oY += moveSpeed;
			lastDir = 0;
		}
		else if (left){
			Core.oX -= moveSpeed;
			lastDir = 2;
		}
		else if (right){
			Core.oX += moveSpeed;
			lastDir = 3;
		}
	}
	
	public void render (Graphics g){
		
		
		if(down){
			g.drawImage(Tile.characters, this.x, this.y, x + width, y + height, pImgDown[aniFrame][0] * Tile.size, pImgDown[aniFrame][1] * Tile.size - Tile.size, pImgDown[aniFrame][0] * Tile.size + width, pImgDown[aniFrame][1] *Tile.size - Tile.size + height, null);
		}
		else if(up){
			g.drawImage(Tile.characters, this.x, this.y, x + width, y + height, pImgUp[aniFrame][0] * Tile.size, pImgUp[aniFrame][1] * Tile.size - Tile.size, pImgUp[aniFrame][0] * Tile.size + width, pImgUp[aniFrame][1] *Tile.size - Tile.size + height, null);
		}
		else if(left){
			g.drawImage(Tile.characters, this.x, this.y, x + width, y + height, pImgLeft[aniFrame][0] * Tile.size, pImgLeft[aniFrame][1] * Tile.size - Tile.size, pImgLeft[aniFrame][0] * Tile.size + width, pImgLeft[aniFrame][1] *Tile.size - Tile.size + height, null);
		}
		else if(right){
			g.drawImage(Tile.characters, this.x, this.y, x + width, y + height, pImgRight[aniFrame][0] * Tile.size, pImgRight[aniFrame][1] * Tile.size - Tile.size, pImgRight[aniFrame][0] * Tile.size + width, pImgRight[aniFrame][1] *Tile.size - Tile.size + height, null);
		}
		else {
			switch (lastDir){
				case 0:
					g.drawImage(Tile.characters, this.x, this.y, x + width, y + height, pImgStand[0][0] * Tile.size, pImgStand[0][1] * Tile.size - Tile.size, pImgStand[0][0] * Tile.size + width, pImgStand[0][1] * Tile.size - Tile.size + height, null);
					break;
					
				case 1:
					g.drawImage(Tile.characters, this.x, this.y, x + width, y + height, pImgStand[1][0] * Tile.size, pImgStand[1][1] * Tile.size - Tile.size, pImgStand[1][0] * Tile.size + width, pImgStand[1][1] *Tile.size - Tile.size + height, null);
					break;
					
				case 2:
					g.drawImage(Tile.characters, this.x, this.y, x + width, y + height, pImgStand[2][0] * Tile.size, pImgStand[2][1] * Tile.size - Tile.size, pImgStand[2][0] * Tile.size + width, pImgStand[2][1] *Tile.size - Tile.size + height, null);
					break;
					
				case 3:
					g.drawImage(Tile.characters, this.x, this.y, x + width, y + height, pImgStand[3][0] * Tile.size, pImgStand[3][1] * Tile.size - Tile.size, pImgStand[3][0] * Tile.size + width, pImgStand[3][1] *Tile.size - Tile.size + height, null);
					break;
					
			}
		}
	}
}
