package game;

class PotatoMine extends Plant {
	private static final long serialVersionUID = -3369321461276148885L;

	static final int damage = 10000;
	int chargeAmount;

	PotatoMine() {
		cost = 25;
		health = 1;
		startTime = MainGame.t;
		this.chargeAmount = 0;
		if (!charged) {
			img = MainGame.POTATOMINECHARGING;
			width = 0;
			height = 0;
		} else {
			img = MainGame.POTATOMINE;
			width = MainGame.COLW;
			height = MainGame.ROWH;
		}
	}

	@Override
	void takeDamage(Zombie zomb) {
		if (charged) {
			this.health -= zomb.damage;
			zomb.health = 0;
		}
	}

	@Override
	void shoot(int row, int col) { // Does nothing, potato mines do not shoot
	}
}