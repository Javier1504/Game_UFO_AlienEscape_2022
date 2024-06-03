package gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Batu extends Enemy {
	
	public static final int Y_LAND = 125; //posisi y gambar batu
	
	//deklarasi class
	private int posX;
	private int width;
	private int height;
	private BufferedImage image;
	private MainCharacter mainCharacter;
	private Rectangle rectBound;
	
	//mendapatkan posisi dari objek batu
	public Batu(MainCharacter mainCharacter, int posX, int width, int height, BufferedImage image) {
		this.posX = posX;
		this.width = width;
		this.height = height;
		this.image = image;
		this.mainCharacter = mainCharacter;
		rectBound = new Rectangle();
	}
	
	public void update() {
		posX -= mainCharacter.getSpeedX(); //kecepatan dari objek batu update
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, posX, Y_LAND - image.getHeight(), null); //untuk mendapatkan posisi batu
		g.setColor(Color.white); //untuk mengetahui seberapa besar frame yang harus dilewati main char
//		Rectangle bound = getBound();
//		g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}
	
	public Rectangle getBound() { //sata main melompat
		rectBound = new Rectangle();
		//untuk mengatur frame lonncatan, semakin besar semakin tinggi framenya
		rectBound.x = (int) posX + (image.getWidth() - width)/2;
		rectBound.y = Y_LAND - image.getHeight() + (image.getHeight() - height)/2;
		rectBound.width = width;
		rectBound.height = height;
		return rectBound;
	}

	@Override
	public boolean isOutOfScreen() {
		if(posX < -image.getWidth()) {
			return true;
		}
		return false;
	}
	
}
