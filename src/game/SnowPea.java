package game;

class SnowPea extends Plant {

	static final int shootInterval = 60;

	SnowPea() {
		health = 10;
		img = MainGame.loadImage("Photos/snow-pea.png");

		// TODO Find zombie that intersects
//		if (zombie.intersects this) {
//			this.takeDamage();
//		}
	}

	@Override
	void shoot(int row, int col) {
		new SnowPeaProjectile((col * MainGame.colW + MainGame.lowX) + MainGame.colW,
				(row * MainGame.rowH + MainGame.lowY) + 15);
	}

	@Override
	void takeDamage() {
		// TODO Get zombie damage
		// this.health =- zombie.damage;
	}
}