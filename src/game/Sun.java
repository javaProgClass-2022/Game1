package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sun extends Rectangle {
	private static final long serialVersionUID = 5694368912891992696L;
	int timeRemaining;
	BufferedImage img = MainGame.loadImage("Photos/Misc/sun.png");
	
	Sun() {
		timeRemaining = 2000;
		width = 100;
		height = 100;
	}
}