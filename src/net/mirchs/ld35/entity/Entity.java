package net.mirchs.ld35.entity;

import net.mirchs.dm8.graphics.Sprite;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld35.level.Level;

public abstract class Entity {
	
	public Sprite sprite;
	protected Level level;
	
	protected boolean removed = false;
	
	public abstract void update();
	
	public void init(Level level) {
		this.level = level;
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	
	public Vector3f getPosition() {
		return sprite.getPosition();
	}
	
	public Vector2f getSize() {
		return sprite.getSize();
	}
	
	public Sprite getSprite() {
		return sprite;
	}

}
