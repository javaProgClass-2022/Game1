package game;

class PotatoMine extends Plant {

	static final int chargeTime = 2000;
	static final int damage = 10000;

	PotatoMine() {
		int currentCharge = 0;
		health = 1;
		img = MainGame.loadImage("Photos/potato-mine.png");

		if (currentCharge >= chargeTime) {
// 		TODO Find zombie that intersects
//			if (zombie.intersects this) {
//				takeDamage();
//			}
		}
	}

	@Override
	void shoot() { // Do nothing,
	}

	@Override
	void takeDamage() {
		// TODO get zombie
		// zombie.health - damage();
		if (this.health <= 0) {
			this.die();
		}
	}

	@Override
	void die() {
	}
}