package game;

import java.awt.image.BufferedImage;

class SnowPeaProjectile {
	static int velocity = 1;
	static int damage = 5;
	static int side = 25;
	int x, y;
	BufferedImage img = MainGame.loadImage("Photos/Peas/snowy_pea.png");

	SnowPeaProjectile(int x, int y) {
		this.x = x;
		this.y = y;
		MainGame.snowPeaList.add(this);
	}

	void movePea() {
		this.x += velocity;
	}
}