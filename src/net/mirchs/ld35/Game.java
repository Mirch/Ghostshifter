package net.mirchs.ld35;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;

import net.mirchs.dm8.graphics.Shader;
import net.mirchs.dm8.graphics.Sprite;
import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.Window;
import net.mirchs.dm8.graphics.layers.Layer2D;
import net.mirchs.dm8.input.Keyboard;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.dm8.sound.Sound;
import net.mirchs.ld35.entity.mob.Chaser;
import net.mirchs.ld35.entity.mob.Mob.Type;
import net.mirchs.ld35.entity.mob.player.Player;
import net.mirchs.ld35.level.Level;

public class Game implements Runnable {

	public static Window window;
	private boolean running;
	private Thread thread;

	private Shader shader;

	private Screen menuLayer;
	private Screen wonLevelLayer;
	private Screen lostGameLayer;
	private Screen wonGameLayer;
	private Screen aboutLayer;

	private Layer2D guiLayer;

	private Level[] levels;

	public static Level level;
	private Player player;
	private Chaser chaser, chaser1, chaser2;


	private Matrix4f proj;

	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() {
		window = new Window("Ghostshifter", 1280, 720);
		GameState.chageState(GameState.START);
		glfwSwapInterval(0);
		glClearColor(0, 0, 0, 1);

		int tids[] = {
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		};

		shader = new Shader("shaders/shader.vert", "shaders/shader.frag");

		shader.enable();
		shader.setUniform1iv("textures", tids);
		shader.disable();

		proj = Matrix4f.ortho(0.0f, window.WIDTH, 0.0f, window.HEIGHT, -1.0f, 1.0f);

		guiLayer = new Layer2D(shader, proj);
		GameState.CURRENT_LEVEL = 0;
		initLevels();
		menuLayer = new Screen(shader, proj);
		aboutLayer = new Screen(shader, proj);
		wonGameLayer = new Screen(shader, proj);
		wonLevelLayer = new Screen(shader, proj);
		lostGameLayer = new Screen(shader, proj);

	}

	@Override
	public void run() {
		init();
		double lastTime = glfwGetTime();
		final double ns = 1 / 60.0f;
		double delta = 0;
		int frames = 0, updates = 0;
		double time = glfwGetTime();
		while (running) {
			double now = glfwGetTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			render();
			frames++;
			if (delta >= 1) {
				updates++;
				update();
				delta--;
			}

			if (glfwGetTime() - time >= 1) {
				// System.out.println(frames + " " + updates);
				time++;
				frames = 0;
				updates = 0;
			}

			if (Keyboard.isKeyDown(GLFW_KEY_ESCAPE)) {
				glfwSetWindowShouldClose(window.getID(), GL_TRUE);
			}
			if (glfwWindowShouldClose(window.getID()) == GL_TRUE) {
				running = false;
			}
		}

		System.exit(0);
	}

	private void initLevels() {
		// GameState.chageState(GameState.GAME);

		if (GameState.CURRENT_LEVEL == 0) {
			level = new Level("res/levels/level1.png", shader, proj);
			level.time = 45;
			level.number = 0;
			guiLayer = new Layer2D(shader, proj);
			player = new Player(new Vector2f(9 * 64, 6 * 64), new Sprite(new Vector3f(3 * 64, 3 * 64, 0), new Vector2f(64, 64), new Texture("res/player_sprites/green_stay.png")));
			guiLayer.add(level.timeDisplay);

			chaser = new Chaser(new Vector2f(5 * 64, 5 * 64), new Sprite(new Vector3f(9 * 64, 10 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			chaser1 = new Chaser(new Vector2f(12 * 64, 5 * 64), new Sprite(new Vector3f(18 * 64, 6 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			chaser2 = new Chaser(new Vector2f(9 * 64, 8 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser3 = new Chaser(new Vector2f(11 * 64, 1 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser4 = new Chaser(new Vector2f(2 * 64, 7 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser5 = new Chaser(new Vector2f(17 * 64, 7 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);

			level.add(player);

			level.add(chaser);
			level.add(chaser1);
			level.add(chaser2);
			level.add(chaser3);
			level.add(chaser4);
			level.add(chaser5);

		}

		else if (GameState.CURRENT_LEVEL == 1) {
			level = new Level("res/levels/level2.png", shader, proj);
			level.time = 67;
			level.number = 1;
			guiLayer = new Layer2D(shader, proj);
			player = new Player(new Vector2f(9 * 64, 6 * 64), new Sprite(new Vector3f(3 * 64, 3 * 64, 0), new Vector2f(64, 64), TextureContainer.GREEN_TEXTURE));
			guiLayer.add(level.timeDisplay);

			chaser = new Chaser(new Vector2f(18 * 64, 1 * 64), new Sprite(new Vector3f(9 * 64, 10 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			chaser1 = new Chaser(new Vector2f(1 * 64, 1 * 64), new Sprite(new Vector3f(18 * 64, 6 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			chaser2 = new Chaser(new Vector2f(2 * 64, 10 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser3 = new Chaser(new Vector2f(18 * 64, 10 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser4 = new Chaser(new Vector2f(3 * 64, 1 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser5 = new Chaser(new Vector2f(16 * 64, 1 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser6 = new Chaser(new Vector2f(9 * 64, 1 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser7 = new Chaser(new Vector2f(10 * 64, 1 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser8 = new Chaser(new Vector2f(9 * 64, 10 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser9 = new Chaser(new Vector2f(10 * 64, 10 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);

			level.add(player);

			level.add(chaser);
			level.add(chaser1);
			level.add(chaser2);
			level.add(chaser3);
			level.add(chaser4);
			level.add(chaser5);
			level.add(chaser6);
			level.add(chaser7);
			level.add(chaser8);
			level.add(chaser9);
		} else if (GameState.CURRENT_LEVEL == 2) {
			level = new Level("res/levels/level3.png", shader, proj);
			level.time = 70;
			level.number = 2;
			guiLayer = new Layer2D(shader, proj);
			player = new Player(new Vector2f(9 * 64, 6 * 64), new Sprite(new Vector3f(3 * 64, 3 * 64, 0), new Vector2f(64, 64), TextureContainer.GREEN_TEXTURE));
			guiLayer.add(level.timeDisplay);

			chaser = new Chaser(new Vector2f(10 * 64, 1 * 64), new Sprite(new Vector3f(9 * 64, 10 * 64, 0), new Vector2f(16, 16), TextureContainer.BLUE_MOB), Type.BLUE);
			chaser1 = new Chaser(new Vector2f(2 * 64, 2 * 64), new Sprite(new Vector3f(18 * 64, 6 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			chaser2 = new Chaser(new Vector2f(17 * 64, 1 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser3 = new Chaser(new Vector2f(18 * 64, 5 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser4 = new Chaser(new Vector2f(1 * 64, 5 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser5 = new Chaser(new Vector2f(2 * 64, 9 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser6 = new Chaser(new Vector2f(18 * 64, 9 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser7 = new Chaser(new Vector2f(9 * 64, 10 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser8 = new Chaser(new Vector2f(10 * 64, 10 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);

			level.add(player);

			level.add(chaser);
			level.add(chaser1);
			level.add(chaser2);
			level.add(chaser3);
			level.add(chaser4);
			level.add(chaser5);
			level.add(chaser6);
			level.add(chaser7);
			level.add(chaser8);
		} else if (GameState.CURRENT_LEVEL == 3) {
			level = new Level("res/levels/level4.png", shader, proj);
			level.time = 80;
			level.number = 3;
			guiLayer = new Layer2D(shader, proj);
			player = new Player(new Vector2f(9 * 64, 6 * 64), new Sprite(new Vector3f(3 * 64, 3 * 64, 0), new Vector2f(64, 64), TextureContainer.GREEN_TEXTURE));
			guiLayer.add(level.timeDisplay);

			chaser = new Chaser(new Vector2f(1 * 64, 1 * 64), new Sprite(new Vector3f(9 * 64, 10 * 64, 0), new Vector2f(16, 16), TextureContainer.BLUE_MOB), Type.BLUE);
			chaser1 = new Chaser(new Vector2f(1 * 64, 9 * 64), new Sprite(new Vector3f(18 * 64, 6 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			chaser2 = new Chaser(new Vector2f(18 * 64, 9 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser3 = new Chaser(new Vector2f(18 * 64, 1 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.BLUE_MOB), Type.BLUE);
			Chaser chaser4 = new Chaser(new Vector2f(7 * 64, 2 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser5 = new Chaser(new Vector2f(12 * 64, 2 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.GREEN_MOB), Type.GREEN);
			Chaser chaser6 = new Chaser(new Vector2f(7 * 64, 7 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser7 = new Chaser(new Vector2f(12 * 64, 7 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser8 = new Chaser(new Vector2f(5 * 64, 9 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.RED_MOB), Type.RED);
			Chaser chaser9 = new Chaser(new Vector2f(14 * 64, 9 * 64), new Sprite(new Vector3f(18 * 64, 9 * 64, 0), new Vector2f(16, 16), TextureContainer.BLUE_MOB), Type.BLUE);

			level.add(player);

			level.add(chaser);
			level.add(chaser1);
			level.add(chaser2);
			level.add(chaser3);
			level.add(chaser4);
			level.add(chaser5);
			level.add(chaser6);
			level.add(chaser7);
			level.add(chaser8);
			level.add(chaser9);
		}
	}

	private void update() {
		window.update();
		if (GameState.gameState == GameState.START)
			menuLayer.update();
		else if (GameState.gameState == GameState.GAME) {
			level.update();
		} else if (GameState.gameState == GameState.ABOUT)
			aboutLayer.update();
		else if (GameState.gameState == GameState.WON_LEVEL) {
			wonLevelLayer.update();
			initLevels();
		} else if (GameState.gameState == GameState.GAME_OVER) {
			lostGameLayer.update();
			initLevels();
		} else if (GameState.gameState == GameState.WON_GAME) {
			wonGameLayer.update();
			initLevels();
		}

	}

	private void render() {
		window.clear();
		if (GameState.gameState == GameState.START)
			menuLayer.render();
		else if (GameState.gameState == GameState.GAME) {
			level.render();
			guiLayer.render();
		} else if (GameState.gameState == GameState.ABOUT)
			aboutLayer.render();
		else if (GameState.gameState == GameState.WON_LEVEL) {
			wonLevelLayer.render();
		} else if (GameState.gameState == GameState.GAME_OVER) {
			lostGameLayer.render();
		} else if (GameState.gameState == GameState.WON_GAME) {
			wonGameLayer.render();
		}

	}

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
		new Game().start();
	}

}
