package game;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract class Zombie extends Rectangle {
	int health, x, y, damage, height, width;
	double speed;
	BufferedImage img;
}
