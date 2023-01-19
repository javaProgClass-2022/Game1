package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class SnowPeaProjectile extends Rectangle {
	static int velocity = 3;
	static int damage = 5;
	BufferedImage img = MainGame.loadImage("Photos/Peas/snowy_pea.png");

	SnowPeaProjectile(int x, int y) {
		width = 25;
		height = 25;
		this.x = x;
		this.y = y;
		MainGame.snowPeaList.add(this);
	}
}