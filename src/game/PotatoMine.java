package game;

class PotatoMine extends Plant {
	private static final long serialVersionUID = -3369321461276148885L;

	static final int damage = 10000;
	int chargeAmount;
	boolean charged;

	PotatoMine() {
		health = 1;
		this.chargeAmount = 0;
		if (!charged) {
			img = MainGame.POTATOMINECHARGING;
			width = 0;
			height = 0;
		} else {
			img = MainGame.POTATOMINE;
			// FIXME get position of plant
			width = (MainGame.HIGHX - MainGame.LOWX) % 5;
			height = (MainGame.HIGHY - MainGame.LOWY) % 9;
		}
	}

	@Override
	void takeDamage(Zombie zomb) {
		if (!charged) {
			return;
		}
		zomb.health = 0;
		this.health -= 1;
	}

	@Override
	void shoot(int row, int col) { // Does nothing, potato mines do not shoot
	}
}