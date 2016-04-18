package net.mirchs.ld35.level.tile;

import net.mirchs.dm8.graphics.Sprite;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld35.TextureContainer;

public class FloorTile extends Tile {

	public FloorTile() {
		super(new Sprite(new Vector3f(0,0,0), new Vector2f(64, 64), TextureContainer.FLOOR_TEXTURE));
	}

}
