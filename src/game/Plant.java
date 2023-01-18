package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Plant {
	int cost, health, speed;
	BufferedImage img;

	abstract void shoot(int row, int col);

	abstract void takeDamage();
}