package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class SnowPeaProjectile extends Rectangle {
	private static final long serialVersionUID = -3762075800163819606L;

	static int velocity = 3;
	static int damage = 5;
	BufferedImage img = MainGame.SNOWPEAPROJECTILE;

	SnowPeaProjectile(int x, int y) {
		width = 25;
		height = 25;
		this.x = x;
		this.y = y;
		MainGame.snowPeaList.add(this);
	}
}