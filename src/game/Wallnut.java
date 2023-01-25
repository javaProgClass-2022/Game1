package game;

class Wallnut extends Plant {
	private static final long serialVersionUID = -3255394878231110366L;

	Wallnut() {
		cost = 50;
		width = MainGame.COLW;
		height = MainGame.ROWH;
		health = 40;
		startTime = MainGame.t;
		img = MainGame.WALLNUT1;
	}

	// Changes images depending on health

	@Override
	void takeDamage(Zombie zomb) {
		this.health -= zomb.damage;
	}

	@Override
	void shoot(int row, int col) { // Do nothing, wall-nuts do not shoot
	}
}