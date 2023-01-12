package game;

import java.awt.image.BufferedImage;

abstract class Plant {
	// speed means how quickly they fire their respective projectile
	int cost, speed, health;
	BufferedImage img;

	abstract void shoot();

	abstract void takeDamage();
}
