package gameobject;

import java.awt.Graphics;
import java.awt.Rectangle;

//enemy ini hanya untuk mendeklarasikan bahwa objek tersebut abstrak
public abstract class Enemy {
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract Rectangle getBound();
	public abstract boolean isOutOfScreen();
}
