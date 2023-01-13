package game;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Zombie extends Rectangle {
	int health, x, y, damage;
	static int height = 200, width = 100;
	double speed;
	BufferedImage img;
}
