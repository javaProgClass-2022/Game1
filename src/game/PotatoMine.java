package game;

class PotatoMine extends Plant {
	private static final long serialVersionUID = -3369321461276148885L;

	static final int damage = 10000;

	PotatoMine() {
		health = 1;
		img = MainGame.loadImage("Photos/potato-mine.png");
		startTime = MainGame.t;
	}

	@Override
	void takeDamage(Zombie zomb) {
		zomb.health = 0;
		this.health -= 1;
	}

	@Override
	void shoot(int row, int col) { // Does nothing, potato mines do not shoot
	}
}