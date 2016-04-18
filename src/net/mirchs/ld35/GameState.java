package net.mirchs.ld35;

public class GameState {

	public static final byte START      = 0x1;
	public static final byte GAME       = 0x2;
	public static final byte WON_LEVEL  = 0x3;
	public static final byte GAME_OVER  = 0x4;
	public static final byte WON_GAME   = 0x5;
	public static final byte ABOUT      = 0x6;
	
	public static byte CURRENT_LEVEL = 0;
	public static byte LAST_WON_LEVEL = 0;
	
	public static byte gameState;
	
	public static void chageState(byte state) {
		gameState = state;
	}
	
}
