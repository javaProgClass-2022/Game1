package game;

import java.awt.image.BufferedImage;

class SnowPeaProjectile {
	static int velocity = 10;
	static int damage = 5;
	int x, y;
	BufferedImage img = MainGame.loadImage("Photos/Peas/snowy_pea.png");

	// TODO Attach timer to this somehow
	void movePea() {
		this.x = +velocity;
		this.y = +velocity;
	}
}