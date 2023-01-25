package game;

class Wallnut extends Plant {
	private static final long serialVersionUID = -3255394878231110366L;

	Wallnut() {
		cost = 50;
		// FIXME get position of plant
		width = (MainGame.HIGHX - MainGame.LOWX) % 5;
		height = (MainGame.HIGHY - MainGame.LOWY) % 9;
		health = 40;
		startTime = MainGame.t;
		checkHealth();
	}

	// Changes images depending on health
	private void checkHealth() {
		if (this.health > 20) {
			img = MainGame.WALLNUT1;
		}
		if (this.health <= 20) {
			img = MainGame.WALLNUT2;
		}
		if (this.health <= 10) {
			img = MainGame.WALLNUT3;
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