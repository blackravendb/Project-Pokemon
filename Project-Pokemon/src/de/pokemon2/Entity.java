package de.pokemon2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Entity {
	/**entity Koordinaten*/
	protected double x, y;
	
	/**entity Breite und Höhe*/
	protected int width, height, dx, dy;
	
	/**entity tile position im Tileset*/
	protected int[]id;
	
	protected Image image;
	
	private Rectangle me;
	private Rectangle him = new Rectangle();
	
	/**entity Bewegungsgeschwindigkeit*/
	protected double moveSpeed;	
	
	public Entity (int[] id,double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		moveSpeed = 2;
		me = new Rectangle ((int)x, (int)y, width, height);
	}
	
	public void move (double delta) {
		x = x + moveSpeed * delta * dx;
		y = y + moveSpeed * delta * dy;
	}
	
	public void setImage(int[] i){
		image = Tile.characters.getSubimage(i[0] * Tile.size, i[1] * Tile.size, width, height);
	}
	
	public void render (Graphics g) {
		setImage(id);
		g.drawImage(image, (int)(x - Core.oX), (int)(y - Core.oY), null);
	}
	
	public boolean collidesWith(Entity entity) {
		him.setBounds((int)entity.x, (int)entity.y, entity.width, entity.height);
		
		return me.intersects(him);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public abstract void collidedWith (Entity entity);
}