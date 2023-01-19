package game;

class Wallnut extends Plant {
	private static final long serialVersionUID = -3255394878231110366L;

	Wallnut() {
		health = 42;
		startTime = MainGame.t;
		checkHealth();
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
	void takeDamage(Zombie zomb) {
		this.health -= zomb.damage;
	}

	@Override
	void shoot(int row, int col) { // Do nothing, wall-nuts do not shoot
	}
}