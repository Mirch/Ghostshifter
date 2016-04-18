package net.mirchs.ld35;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import net.mirchs.dm8.graphics.Shader;
import net.mirchs.dm8.graphics.Sprite;
import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.layers.Layer2D;
import net.mirchs.dm8.input.Keyboard;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.dm8.sound.Sound;

public class Screen extends Layer2D {

	public static final Sprite START_GAME = new Sprite(new Vector3f(0, 0, 0), new Vector2f(1280, 720), new Texture("res/screens/startGame.jpg"));
	public static final Sprite ABOUT = new Sprite(new Vector3f(0, 0, 0), new Vector2f(1280, 720), new Texture("res/screens/about.jpg"));
	public static final Sprite EXIT = new Sprite(new Vector3f(0, 0, 0), new Vector2f(1280, 720), new Texture("res/screens/exit.jpg"));

	public static final Sprite ABOUT_PAGE = new Sprite(new Vector3f(0, 0, 0), new Vector2f(1280, 720), new Texture("res/screens/about_page.jpg"));

	public static final Sprite LEVEL_COMPLETED = new Sprite(new Vector3f(0, 0, 0), new Vector2f(1280, 720), new Texture("res/screens/won_level.jpg"));
	public static final Sprite GAME_OVER = new Sprite(new Vector3f(0, 0, 0), new Vector2f(1280, 720), new Texture("res/screens/game_over.jpg"));
	public static final Sprite WIN_GAME = new Sprite(new Vector3f(0, 0, 0), new Vector2f(1280, 720), new Texture("res/screens/win_game.jpg"));

	private Sprite sprite;
	
	public static final Sound LOST = new Sound("res/audio/lost.wav");
	public static final Sound WONL = new Sound("res/audio/winlev.wav");
	public static final Sound WONG = new Sound("res/audio/wongame.wav");
	
	public static int counter = 0;
	
	

	public Screen(Shader shader, Matrix4f projection) {
		super(shader, projection);
		this.sprite = START_GAME;
		renderables.add(START_GAME);
	}

	public void update() {
		counter++;
		if (GameState.gameState == GameState.GAME)
			renderables.clear();

		if (GameState.gameState == GameState.ABOUT) {
			this.sprite = ABOUT_PAGE;
			renderables.clear();
			renderables.add(ABOUT_PAGE);

			if (Keyboard.isKeyDown(GLFW_KEY_BACKSPACE))
				GameState.chageState(GameState.START);
		}

		else if (GameState.gameState == GameState.WON_LEVEL) {
			this.sprite = LEVEL_COMPLETED;
			renderables.clear();
			renderables.add(LEVEL_COMPLETED);
		}

		else if (GameState.gameState == GameState.WON_GAME) {
			this.sprite = WIN_GAME;
			renderables.clear();
			renderables.add(WIN_GAME);
		}

		else if (GameState.gameState == GameState.GAME_OVER) {
			this.sprite = GAME_OVER;
			renderables.clear();
			renderables.add(GAME_OVER);
		}

		if (GameState.gameState == GameState.GAME_OVER || GameState.gameState == GameState.WON_GAME) {
			GameState.CURRENT_LEVEL = 0;
			if (Keyboard.isKeyDown(GLFW_KEY_ENTER)) {
				GameState.chageState(GameState.START);
			}
		}

		if (GameState.gameState == GameState.WON_LEVEL) {
			if(GameState.CURRENT_LEVEL <= GameState.LAST_WON_LEVEL)
			GameState.CURRENT_LEVEL = (byte) (GameState.LAST_WON_LEVEL + 1);
			if (Keyboard.isKeyDown(GLFW_KEY_ENTER)) {
				GameState.chageState(GameState.GAME);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (this.sprite.equals(START_GAME)) {
			if (Keyboard.isKeyDown(GLFW_KEY_UP) || Keyboard.isKeyDown(GLFW_KEY_W)) {
				this.sprite = EXIT;
				renderables.remove(START_GAME);
				renderables.add(EXIT);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else if (Keyboard.isKeyDown(GLFW_KEY_DOWN) || Keyboard.isKeyDown(GLFW_KEY_S)) {
				this.sprite = ABOUT;
				renderables.remove(START_GAME);
				renderables.add(ABOUT);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (Keyboard.isKeyDown(GLFW_KEY_ENTER))
				GameState.chageState(GameState.GAME);

		} else if (this.sprite.equals(ABOUT)) {
			if (Keyboard.isKeyDown(GLFW_KEY_UP) || Keyboard.isKeyDown(GLFW_KEY_W)) {
				this.sprite = START_GAME;
				renderables.remove(ABOUT);
				renderables.add(START_GAME);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else if (Keyboard.isKeyDown(GLFW_KEY_DOWN) || Keyboard.isKeyDown(GLFW_KEY_S)) {
				this.sprite = EXIT;
				renderables.remove(ABOUT);
				renderables.add(EXIT);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (Keyboard.isKeyDown(GLFW_KEY_ENTER))
				GameState.chageState(GameState.ABOUT);

		} else if (this.sprite.equals(EXIT)) {
			if (Keyboard.isKeyDown(GLFW_KEY_UP) || Keyboard.isKeyDown(GLFW_KEY_W)) {
				this.sprite = ABOUT;
				renderables.remove(EXIT);
				renderables.add(ABOUT);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else if (Keyboard.isKeyDown(GLFW_KEY_DOWN) || Keyboard.isKeyDown(GLFW_KEY_S)) {
				this.sprite = START_GAME;
				renderables.remove(EXIT);
				renderables.add(START_GAME);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (Keyboard.isKeyDown(GLFW_KEY_ENTER)) {
				glfwSetWindowShouldClose(Game.window.getID(), GL_TRUE);
			}

		}
	}

}
