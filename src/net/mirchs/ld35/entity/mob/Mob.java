package net.mirchs.ld35.entity.mob;

import net.mirchs.dm8.graphics.Sprite;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.ld35.entity.Entity;

public abstract class Mob extends Entity {

	protected Vector2f move;

	public static enum Type {
		GREEN, BLUE, RED
	}

	public Type type;

	protected float health = 100;

	public Mob(Vector2f spawnPoint, Sprite sprite) {
		this.sprite = sprite;
		this.sprite.position.x = spawnPoint.x;
		this.sprite.position.y = spawnPoint.y;
	}

	public void move(float xa, float ya) {
		if (xa != 0 && ya != 0) {
			float res = 2;
			
			float xres = (float)Math.sqrt(2), yres = (float)Math.sqrt(2);
			if (xa < 0)
				xres = -res;
			if (ya < 0)
				yres = -res;
			move(xres, 0);
			move(0, yres);
			return;
		}

		if (!collision(xa, ya)) {
			sprite.position.x += xa;
			sprite.position.y += ya;
		}

	}

	public float getDistance(Mob mob) {
		float xres = getPosition().x - mob.getPosition().x;
		float yres = getPosition().y - mob.getPosition().y;

		return (float) Math.sqrt(xres * xres + yres * yres);
	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (((int) sprite.position.x + (int) xa) + c % 2 * (int) (getSize().x * 3 / 4) + 2) >> 6;
			int yt = (((int) sprite.position.y + (int) ya) + c / 2 * (int) (getSize().y * 3 / 4) + 2) >> 6;
			if (level.getTile(xt, yt).isSolid()) solid = true;
		}
		return solid;
	}

	public float getHealth() {
		return health;
	}

	public void hit(int force) {

		health -= force;
	}

}
