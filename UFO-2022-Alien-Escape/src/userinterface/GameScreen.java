package userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameobject.Clouds;
import gameobject.EnemiesManager;
import gameobject.Land;
import gameobject.MainCharacter;
import util.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener {

	private static final int START_GAME_STATE = 0;
	private static final int GAME_PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;
	
	private Land land;
	private MainCharacter mainCharacter;
	private EnemiesManager enemiesManager;
	private Clouds clouds;
	private Thread thread;

	private boolean isKeyPressed;

	private int gameState = START_GAME_STATE;

	private BufferedImage replayButtonImage;//untuk mengambil gambar replay
	private BufferedImage gameOverButtonImage; 
	public GameScreen() {
		mainCharacter = new MainCharacter(); //membuat main character
		land = new Land(GameWindow.SCREEN_WIDTH, mainCharacter); //membuat land yang seukuran dengan frame
		mainCharacter.setSpeedX(4);//mengatur kecepatan dari main character
		replayButtonImage = Resource.getResouceImage("data/replay_button.png"); //mengambil input gambar replay
		gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png"); //mengambil gambar gameover
		enemiesManager = new EnemiesManager(mainCharacter); //membuat enemies 
		clouds = new Clouds(GameWindow.SCREEN_WIDTH, mainCharacter); //membuat planet yang seukuran dengan frame
	}

	public void startGame() { //saat game di start
		thread = new Thread(this); //mengatur alur jalannya program
		thread.start();
	}

	public void gameUpdate() { //setiap karakter jalan maka gambar akan seolah2 bergerak mengikuti
		if (gameState == GAME_PLAYING_STATE) {
			clouds.update();
			land.update();
			mainCharacter.update();
			enemiesManager.update();
			if (enemiesManager.isCollision()) { //saat objek tabrakan
				mainCharacter.playDeadSound();
				gameState = GAME_OVER_STATE; //akan memanggil game over
				mainCharacter.dead(true); //karakter bisa tamat
			}
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK); //mengatur latar belakang
		g.fillRect(0, 0, getWidth(), getHeight()); //untuk ,e,beri warna dengan ukuran frame
		
		//percabangan kode program switch case membandingkan isi sebuah variabel dengan beberapa nilai. 
		//Jika proses perbandingan tersebut menghasilkan true, maka block kode program akan di proses
		switch (gameState) {
		case START_GAME_STATE: //saat game di start
			mainCharacter.draw(g);
			break;
		case GAME_PLAYING_STATE:
		case GAME_OVER_STATE: //saat gameover
			clouds.draw(g);
			land.draw(g);
			enemiesManager.draw(g);
			mainCharacter.draw(g);
			g.setColor(Color.YELLOW); //untuk warna skor
			g.drawString("HI " + mainCharacter.score, 500, 20);
			if (gameState == GAME_OVER_STATE) {
				g.drawImage(gameOverButtonImage, 200, 30, null); //ukuran image gameover
				g.drawImage(replayButtonImage, 283, 50, null); //ukuran image replay
				
			}
			break;
		}
	}

	@Override
	public void run() {

		int fps = 60;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = 0;
		long elapsed;
		
		int msSleep;
		int nanoSleep;

		long endProcessGame;
		long lag = 0;

		while (true) {
			gameUpdate();
			repaint();
			endProcessGame = System.nanoTime();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int) (elapsed / 1000000);
			nanoSleep = (int) (elapsed % 1000000);
			if (msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			try {
				Thread.sleep(msSleep, nanoSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastTime = System.nanoTime();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyPressed) {
			isKeyPressed = true;
			switch (gameState) {
			case START_GAME_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = GAME_PLAYING_STATE;
				}
				break;
			case GAME_PLAYING_STATE:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					mainCharacter.jump();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					mainCharacter.down(true);
				}
				break;
			case GAME_OVER_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = GAME_PLAYING_STATE;
					resetGame();
				}
				break;

			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
		if (gameState == GAME_PLAYING_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				mainCharacter.down(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void resetGame() {
		enemiesManager.reset();
		mainCharacter.dead(false);
		mainCharacter.reset();
	}

}
