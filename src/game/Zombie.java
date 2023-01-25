package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Zombie extends Rectangle {
	private static final long serialVersionUID = -9059053568528250575L;
	int health, damage, rowIsIn;
	double xx, speed;
	BufferedImage img;
	boolean isSlowed = false, isStuck = false;
}
