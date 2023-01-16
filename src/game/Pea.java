package game;

import java.awt.image.BufferedImage;

class Pea {
	static int velocity = 10;
	static int damage = 5;
	int x, y;
	
	// Peashooter loads normal pea. snowpea loads icy pea
	BufferedImage img;
	
	// TODO Attach timer to this somehow
	void movePea() {
		this.x =+ velocity;
		this.y =+ velocity;
	}
}