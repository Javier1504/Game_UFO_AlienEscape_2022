package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Resource;

public class EnemiesManager {
	
	private BufferedImage batu_kecil;
	private BufferedImage batu_besar;
	private Random rand;
	
	private List<Enemy> enemies;
	private MainCharacter mainCharacter;
	
	public EnemiesManager(MainCharacter mainCharacter) {
		rand = new Random();
		batu_kecil = Resource.getResouceImage("data/batu_kecil.png"); //menerima input gambar
		batu_besar = Resource.getResouceImage("data/batu_besar.png"); //menerima input gambar
		enemies = new ArrayList<Enemy>();
		this.mainCharacter = mainCharacter;
		enemies.add(createEnemy());
	}
	
	public void update() {
		for(Enemy e : enemies) {
			e.update();
		}
		Enemy enemy = enemies.get(0);
		if(enemy.isOutOfScreen()) {
			mainCharacter.upScore(); //score akan diup setiap melewati screen batu
			enemies.clear(); //hapus batu yang berhasil dilewati
			enemies.add(createEnemy()); //menambhakn batu lagi
		}
	}
	
	public void draw(Graphics g) {
		for(Enemy e : enemies) {
			e.draw(g);
		}
	}
	
	private Enemy createEnemy() {
		// if (enemyType = getRandom)
		int type = rand.nextInt(2);
		if(type == 0) {
			return new Batu(mainCharacter, 800, batu_kecil.getWidth() - 10, batu_kecil.getHeight() - 10, batu_kecil);
		} else {
			return new Batu(mainCharacter, 800, batu_besar.getWidth() - 10, batu_besar.getHeight() - 10, batu_besar);
		}
	}
	
	public boolean isCollision() {
		for(Enemy e : enemies) {
			if (mainCharacter.getBound().intersects(e.getBound())) {
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		enemies.clear();
		enemies.add(createEnemy());
	}
	
}
