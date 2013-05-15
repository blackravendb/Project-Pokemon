package de.pokemon2;
import java.awt.Graphics;
import java.awt.Rectangle;



public class EntityPlayer extends Entity {
	
	public int moveSpeed = 1;
	
	private static int Rx, Ry; //Render x, Render y
	private static int tX = 5, tY = 5; //Tile x, Tile y (Rasterkoordinaten)
	/*
	//Spieler Grafik
	public static int[][] pImgUp= {{2, 1},{1, 1}, {0, 1},{1, 1}};
	public static int[][] pImgDown= {{2, 3}, {1, 3}, {0, 3}, {1, 3}};
	public static int[][] pImgLeft= {{3, 3}, {4, 3}, {5, 3}, {4, 3}};
	public static int[][] pImgRight= {{5, 1}, {4, 1}, {3, 1}, {4, 1}};
	public static int[][] pImgStand = {{1, 3}, {1, 1}, {4, 3}, {4, 1}};
	*/
	
	public static boolean isMoving = false;
	private static int moveDelta = 0;
	
	private Core Pokemon;
	
	public int aniFrame = 0;
	public int aniTime = 8;
	public int aniDelta = 0;
	
	public static int lastDir = 0; //letzte Bewegungsrichtung
	
	public EntityPlayer(Core Pokemon, double x, double y, int width, int height){
		
		super(new int[] {0,0}, x, y, width, height);
		
		Rx = (int)(x - Pokemon.oX);
		Ry = (int)(y - Pokemon.oY);
		
		moveSpeed = 1;
	}

	@Override
	public void move(double delta){
		
		aniDelta ++;
		
		if(aniDelta >= aniTime){
			aniFrame ++;
			aniDelta = 0;
			if(aniFrame > 3){
				aniFrame = 0;
			}
		}
		
		//hoch
		if(Pokemon.bW){
			System.out.println ("tick");
			if(canMove(tX, tY - 1) && !isMoving){
				isMoving = true;
				tY -= 1;
			}
		/*	else {
				Pokemon.bW = false;
			}
			if (!isMoving){
				Pokemon.bW = false;
			} */
			if(isMoving) { //(isMoving)
				Pokemon.oY -= moveSpeed;
				moveDelta += moveSpeed;
				System.out.println(moveDelta); //TODO
				
				if (moveDelta >= Tile.size){
					isMoving = false;
					moveDelta = 0;
					Pokemon.bW = false;
					
				}
			}
			lastDir = 1;
		}
		
		//runter
		else if (Pokemon.bS){
			if(canMove(tX, tY + 1) && !isMoving){
				isMoving = true;
				tY += 1;
			}
			
			if(isMoving) { //(isMoving)
				Pokemon.oY += moveSpeed;
				moveDelta += moveSpeed;
				
				if (moveDelta >= Tile.size){
					isMoving = false;
					moveDelta = 0;
					Pokemon.bS = false;
					
				}
			}
			lastDir = 0;
		}
		
		//links
		else if (Pokemon.bA){
			if(canMove(tX - 1, tY) && !isMoving){
				isMoving = true;
				tX -= 1;
			}

			if(isMoving) { //(isMoving)
				Pokemon.oX -= moveSpeed;
				moveDelta += moveSpeed;
				
				if (moveDelta >= Tile.size){
					isMoving = false;
					moveDelta = 0;
					Pokemon.bA = false;
					
				}
			}
			lastDir = 2;
		}
		
		//rechts
		else if (Pokemon.bD){
			if(canMove(tX + 1, tY) && !isMoving){
				isMoving = true;
				tX += 1;
			}
			if(isMoving) { //(isMoving)
				Pokemon.oX += moveSpeed;
				moveDelta += moveSpeed;
				
				if (moveDelta >= Tile.size){
					isMoving = false;
					moveDelta = 0;
					Pokemon.bD = false;
					
				}
			}
			lastDir = 3;
		}
	}
	
	public boolean canMove (int tX, int tY){
	/*	if(Core.core.level.solid[tX][tY].id[0] == Tile.blank[0] && Core.core.level.solid[tX][tY].id[1] == Tile.blank[1]){ //Pokemon.level.solid
			System.out.println("true");
			return true;
		}
		System.out.println("false");
	*/	return true;
	}
	
	@Override
	public void render (Graphics g){
		super.setImage(new int[]{0,0});
		g.drawImage(image, Rx, Ry, null);
		
		/*
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
		*/
	}

	@Override
	public void collidedWith(Entity entity) {
		// TODO Auto-generated method stub
		
	}
}
