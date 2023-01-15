package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Lawnmower extends Rectangle {

	static int width = 90, height = 100;
	int speed = 1;
	int x, y;
	boolean triggered = false;
	BufferedImage img = MainGame.lawnMower;

}
