package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Lawnmower extends Rectangle {
	private static final long serialVersionUID = -9216121392590284934L;
	
	int speed = 5;
	boolean triggered = false;
	BufferedImage img = MainGame.lawnMower;

	Lawnmower() {
		width = 90;
		height = 100;
	}

}
