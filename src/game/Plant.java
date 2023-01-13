package game;

import java.awt.image.BufferedImage;

abstract class Plant {
	int cost, health, speed;
	BufferedImage img;

	abstract void shoot();

	abstract void takeDamage();

	abstract void die();
}
