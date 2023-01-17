package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Plant extends Rectangle {
	int cost, health, speed, x, y;
	BufferedImage img;

	abstract void shoot(int row, int col);

	abstract void takeDamage();
}