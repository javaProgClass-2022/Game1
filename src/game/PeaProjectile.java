package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class PeaProjectile extends Rectangle {
	private static final long serialVersionUID = 3467147150772030912L;
	
	static int velocity = 3;
	static int damage = 10;
	BufferedImage img = MainGame.loadImage("Photos/Peas/pea.png");

	PeaProjectile(int x, int y) {
		width = 25;
		height = 25;
		this.x = x;
		this.y = y;
		MainGame.normalPeaList.add(this);
	}
}