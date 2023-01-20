package game;

class SnowPea extends Plant {

	static final int shootInterval = 60;

	SnowPea() {
		health = 10;
		img = MainGame.loadImage("Photos/snow-pea.png");
		startTime = MainGame.t;
	}

	@Override
	void shoot(int row, int col) {
		new SnowPeaProjectile((col * MainGame.colW + MainGame.lowX) + MainGame.colW,
				(row * MainGame.rowH + MainGame.lowY) + 15);
	}

	@Override
	void takeDamage() {
		// FIXME Get zombie damage, on intersects?
		// this.health =- zombie.damage;
	}
}