package userinterface;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
	
	public static final int SCREEN_WIDTH = 600;
	private GameScreen gameScreen;
	
	//constructor untuk memunculkan frame
	public GameWindow() {
		//inheritance
		super("Alien Escape 2022"); //fungsi super --> suatu objek yang mewakili class induk
		setSize(SCREEN_WIDTH, 175); //screen diatur menjadi 175
		setLocation(400, 200); //lokasi screen berasa
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); //agar frame tidak lepas/berubah
		
		gameScreen = new GameScreen();
		addKeyListener(gameScreen); //menambahkan keylistener
		add(gameScreen); //menambahkan screen
	}
	
	public void startGame() {
		setVisible(true); //agar bisa memulai kodingan/game
		gameScreen.startGame(); 
	}
	
	public static void main(String args[]) { //fungsi main
		(new GameWindow()).startGame();
	}
}
