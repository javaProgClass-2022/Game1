package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Lawnmower extends Rectangle {

	int speed = 5;
	boolean triggered = false;
	BufferedImage img = MainGame.lawnMower;

	Lawnmower() {
		width = 90;
		height = 100;
	}

}
