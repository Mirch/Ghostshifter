package net.mirchs.ld35.entity.mob;

import java.util.Random;

import net.mirchs.dm8.graphics.Sprite;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.dm8.sound.Sound;
import net.mirchs.ld35.entity.mob.player.Player;
import net.mirchs.ld35.level.Level;

public class Chaser extends Mob {

	public Player player;
	private final Random random = new Random();
	private int genx, geny;

	private Sound hit = new Sound("res/audio/hit.wav");
	
	private int counter = 0;

	public Chaser(Vector2f spawnPoint, Sprite sprite, Type type) {
		super(spawnPoint, sprite);
		this.type = type;
	}

	public void init(Level level) {
		super.init(level);
		player = level.getPlayer();
	}

	@Override
	public void update() {
		float px = player.getPosition().x;
		float py = player.getPosition().y;

		int xa = 0, ya = 0;

		if (counter % 120 == 0) {
			genx = random.nextInt(2) - random.nextInt(2);
			geny = random.nextInt(2) - random.nextInt(2);
		}
		counter++;

		if (getDistance(player) < 16 && type.equals(player.type)) {
			player.hit(100);
			hit.play();
		}
		else if (getDistance(player) < 64 && !type.equals(player.type)) {
			level.shapeShift(type);
			remove();
		}

		if (getDistance(player) < 64 * 4) {

			if (this.type.equals(player.type)) {
				if (px > this.getPosition().x)
					xa++;
				else
					xa--;

				if (py > this.getPosition().y)
					ya++;
				else
					ya--;
			} else {
				if (px > this.getPosition().x)
					xa--;
				else
					xa++;

				if (py > this.getPosition().y)
					ya--;
				else
					ya++;
			}

		} else {
			xa = genx;
			ya = geny;
		}
		move(xa, ya);
	}

}
