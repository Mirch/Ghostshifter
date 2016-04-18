package net.mirchs.ld35.level.tile;

import net.mirchs.dm8.graphics.Sprite;

public class Tile {
	
	public Sprite sprite;
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public boolean isSolid() {
		return false;
	}
	

}
