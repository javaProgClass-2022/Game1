package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Zombie extends Rectangle {
	int health, damage;
	double speed;
	BufferedImage img;
}
