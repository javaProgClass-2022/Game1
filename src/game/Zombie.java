package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Zombie extends Rectangle {
	int health, damage, rowIsIn;
	double xx, speed;
	BufferedImage img;
	boolean isSlowed = false, isStuck = false;
}
