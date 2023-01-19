package game;

class Wallnut extends Plant {

	Wallnut() {
		health = 42;
		checkHealth();

		// TODO Find zombie that intersects
//		if (zombie.intersects this) {
//			this.takeDamage();
//		}
	}

	// Changes images depending on health
	private void checkHealth() {
		if (this.health > 20) {
			img = MainGame.loadImage("Photos/wall-nut.png");
		}
		if (this.health <= 20) {
			img = MainGame.loadImage("Photos/WallnutExtra/WallnutDamage1.png");
		}
		if (this.health <= 10) {
			img = MainGame.loadImage("Photos/WallnutExtra/WallnutDamage2.png");
		}
	}

	@Override
	void takeDamage() {
		// TODO Get zombie damage and reduce by this number
		// this.health - zombie.damage;
	}

	@Override
	void shoot(int row, int col) { // Do nothing, wall-nuts do not shoot
	}
}