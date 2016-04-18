package net.mirchs.ld35.entity.mob.player;

import static org.lwjgl.glfw.GLFW.*;

import net.mirchs.dm8.graphics.Sprite;
import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.Window;
import net.mirchs.dm8.input.Keyboard;
import net.mirchs.dm8.input.Mouse;
import net.mirchs.dm8.input.MousePosition;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld35.TextureContainer;
import net.mirchs.ld35.entity.mob.Mob;

public class Player extends Mob {

	private int counter = 0, timer = 0;

	public Sprite healthSprite, progressSprite;

	public int progress = 0;

	private Sprite sprite1;

	private boolean moving = false;

	public Player(Vector2f spawnPoint, Sprite sprite) {
		super(spawnPoint, sprite);
		this.type = Type.GREEN;

	}

	@Override
	public void update() {
		counter++;
		int xa = 0, ya = 0;

		moving = false;

		if (Keyboard.isKeyDown(GLFW_KEY_W) || Keyboard.isKeyDown(GLFW_KEY_UP) ) {
			ya += 2;
			moving = true;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_S)|| Keyboard.isKeyDown(GLFW_KEY_DOWN)) {
			ya -= 2;
			moving = true;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_A)|| Keyboard.isKeyDown(GLFW_KEY_LEFT)) {
			xa -= 2;
			moving = true;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_D)|| Keyboard.isKeyDown(GLFW_KEY_RIGHT)) {
			xa += 2;
			moving = true;
		}

		if (health <= 0)
			level.lose();

		move(xa, ya);

	}

	public void hit(int force) {
		super.hit(force);

	}

}
