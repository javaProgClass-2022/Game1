package game;

import java.awt.image.BufferedImage;

class PeaProjectile {
	static int velocity = 1;
	static int damage = 5;
	static int side = 25;
	int x, y;
	BufferedImage img = MainGame.loadImage("Photos/Peas/pea.png");

	PeaProjectile(int x, int y) {
		this.x = x;
		this.y = y;
		MainGame.normalPeaList.add(this);
	}
	
	void movePea() {
		this.x += velocity;
	}
}