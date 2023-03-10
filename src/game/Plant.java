package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Plant extends Rectangle {
	private static final long serialVersionUID = 3379616008960400833L;
	int cost;
	int health;
	int speed;
	int startTime;
	int chargeAmount;
	boolean charged;
	BufferedImage img;

	abstract void shoot(int row, int col);

	abstract void takeDamage(Zombie zomb);
}