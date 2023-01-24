package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sun extends Rectangle {
	int timeRemaining;
	BufferedImage img = MainGame.loadImage("Photos/sun.png");
	
	Sun() {
		timeRemaining = 2000;
		width = 100;
		height = 100;
	}
}