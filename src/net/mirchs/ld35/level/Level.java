package net.mirchs.ld35.level;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import net.mirchs.dm8.graphics.Renderable2D;
import net.mirchs.dm8.graphics.Shader;
import net.mirchs.dm8.graphics.Sprite;
import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.layers.Layer2D;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.dm8.sound.Sound;
import net.mirchs.ld35.GameState;
import net.mirchs.ld35.Screen;
import net.mirchs.ld35.TextureContainer;
import net.mirchs.ld35.entity.Entity;
import net.mirchs.ld35.entity.mob.Chaser;
import net.mirchs.ld35.entity.mob.Mob;
import net.mirchs.ld35.entity.mob.Mob.Type;
import net.mirchs.ld35.entity.mob.player.Player;
import net.mirchs.ld35.level.tile.BackBrickTile;
import net.mirchs.ld35.level.tile.BrickTile;
import net.mirchs.ld35.level.tile.FloorTile;
import net.mirchs.ld35.level.tile.NullTile;
import net.mirchs.ld35.level.tile.Tile;

public class Level extends Layer2D {

	protected String path;
	protected int width;
	protected int height;
	protected int[] intTiles;
	protected Tile[] tiles;

	public int number;

	private int xScroll, yScroll;

	private boolean lost = false;
	private boolean won = false;

	public List<Mob> mobs = new ArrayList<Mob>();

	private Player player;

	public int time = 100;
	private int counter = 0;
	public Sprite timeDisplay = new Sprite(new Vector3f(time, 16, 0), new Vector2f(200, 32), TextureContainer.HEALTH_BAR);

	private Sound shapeshift = new Sound("res/audio/shapeshift.wav");

	public Level(String path, Shader shader, Matrix4f projection) {
		super(shader, projection);
		load(path);

	}

	private void load(String path) {
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			intTiles = new int[width * height];
			tiles = new Tile[width * height];
			image.getRGB(0, 0, width, height, intTiles, 0, width);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Tile aux = tiles[x + y * width];
					tiles[x + y * width] = tiles[x + (height - y - 1) * width];
					tiles[x + (height - y - 1) * width] = aux;
				}
			}

			for (int i = 0; i < intTiles.length; i++) {

				tiles[i] = new NullTile();

				if (intTiles[i] == 0xff180E20)
					tiles[i] = new BrickTile();
				else if (intTiles[i] == 0xff020102)
					tiles[i] = new BackBrickTile();
				else if (intTiles[i] == 0xff1A0E1B)
					tiles[i] = new FloorTile();

				tiles[i].sprite.position.x = (i % width) << 6;
				tiles[i].sprite.position.y = (i / width) << 6;
				renderables.add(tiles[i].sprite);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
		for (int i = 0; i < intTiles.length; i++) {
			tiles[i].sprite.position.x += xScroll;
			tiles[i].sprite.position.y += yScroll;

		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Player) {
			this.player = (Player) e;
		} else if (e instanceof Mob) {
			mobs.add((Mob) e);
		}
		renderables.add(e.getSprite());
	}

	public void remove() {

		for (int i = 0; i < mobs.size(); i++) {
			Mob e = mobs.get(i);
			if (e.isRemoved()) {
				mobs.remove(e);
			}
		}

	}

	public void update() {
		if (counter % 60 == 0) {
			time--;
			timeDisplay.size.x = time * 10;
		}
		counter++;
		player.update();
		if (player.getHealth() <= 0)
			lost = true;
		for (Mob e : mobs)
			e.update();

		if (mobs.isEmpty()) {
			if (number != 3)
				GameState.chageState(GameState.WON_LEVEL);
			else
				GameState.chageState(GameState.WON_GAME);
			GameState.LAST_WON_LEVEL = (byte) this.number;
			if (number == 3)
				Screen.WONG.play();
			else
				Screen.WONL.play();
		}

		if (timeDisplay.size.x <= 0)
			lose();
	}

	public void render() {
		shader.enable();
		renderer.begin();
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].sprite.submit(renderer);
		}
		if (player.type == Type.GREEN)
			player.sprite.texture = TextureContainer.GREEN_TEXTURE;
		else if (player.type == Type.RED)
			player.sprite.texture = TextureContainer.RED_TEXTURE;
		else if (player.type == Type.BLUE)
			player.sprite.texture = TextureContainer.BLUE_TEXTURE;

		player.sprite.submit(renderer);
		remove();
		for (Mob mob : mobs) {
			mob.sprite.submit(renderer);
		}
		renderer.pop();
		renderer.end();
		renderer.flush();
		shader.disable();
	}

	public void shapeShift(Type type) {
		Vector3f pos = player.getPosition();

		if (type == Type.GREEN && player.type != Type.GREEN) {
			player.sprite.texture = new Texture("res/player_sprites/green_stay.png");
			player.type = Type.GREEN;
		}

		else if (type == Type.RED && player.type != Type.RED) {
			player.sprite.texture = new Texture("res/player_sprites/red_stay.png");
			player.type = Type.RED;
		}

		else if (type == Type.BLUE && player.type != Type.BLUE) {
			player.sprite.texture = new Texture("res/player_sprites/blue_stay.png");
			player.type = Type.BLUE;
		}
		shapeshift.play();

	}

	public void setPlayer() {
		for (Mob mob : mobs) {
			if (mob instanceof Chaser)
				((Chaser) mob).player = player;
		}
	}

	public boolean tileCollision(Vector2f position, int size, int xOffSet, int yOffSet) {
		boolean solid = false;

		for (int c = 0; c < 4; c++) {
			int xt = (int) (position.x - c % 2 * size + xOffSet) >> 6;
			int yt = (int) (position.y - c / 2 * size + yOffSet) >> 6;
			if (getTile(xt, yt).isSolid()) solid = true;

		}
		return solid;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return new NullTile();
		if (intTiles[x + y * width] == 0xff180E20) return new BrickTile();
		if (intTiles[x + y * width] == 0xff020102) return new BackBrickTile();
		if (intTiles[x + y * width] == 0xff1A0E1B) return new FloorTile();

		return new NullTile();
	}

	public Player getPlayer() {
		return player;
	}

	public void lose() {
		lost = true;
		GameState.chageState(GameState.GAME_OVER);
		Screen.LOST.play();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
