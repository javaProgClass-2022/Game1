package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Plant extends Rectangle {
	private static final long serialVersionUID = 3379616008960400833L;
	int cost, health, speed, startTime;
	int chargeAmount;
	boolean charged;
	BufferedImage img;
	int side = 25;

	abstract void shoot(int row, int col);
	abstract void takeDamage(Zombie zomb);
}