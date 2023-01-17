package game;

import java.awt.image.BufferedImage;

class PeaProjectile {
	static int velocity = 10;
	static int damage = 5;
	int x, y;
	BufferedImage img = MainGame.loadImage("Photos/Peas/pea.png");
	
	// TODO Attach timer to this somehow
	void movePea() {
		this.x =+ velocity;
		this.y =+ velocity;
	}
}