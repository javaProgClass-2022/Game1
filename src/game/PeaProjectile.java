package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class PeaProjectile extends Rectangle {
	static int velocity = 1;
	static int damage = 5;
	BufferedImage img = MainGame.loadImage("Photos/Peas/pea.png");

	PeaProjectile(int x, int y) {
		width = 25;
		height = 25;
		this.x = x;
		this.y = y;
		MainGame.normalPeaList.add(this);
	}
}