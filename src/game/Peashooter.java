package game;

class Peashooter extends Plant {

	static final int shootInterval = 60;
	static int side = 25;

	Peashooter() {
		health = 10;
		img = MainGame.loadImage("Photos/peashooter.png");

		// TODO Find zombie that intersects
//		if (zombie.intersects this) {
//			this.takeDamage();
//		}
	}

	@Override
	void shoot(int row, int col) {
		new PeaProjectile((col * MainGame.colW + MainGame.lowX) + MainGame.colW,
				(row * MainGame.rowH + MainGame.lowY) + 15);
	}

	@Override
	void takeDamage() {
		// TODO Get zombie damage
		// this.health =- zombie.damage;
	}
}