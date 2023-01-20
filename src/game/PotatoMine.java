package game;

class PotatoMine extends Plant {

	static final int chargeTime = 2000;
	static final int damage = 10000;

	PotatoMine() {
		int currentCharge = 0;
		health = 1;
		img = MainGame.loadImage("Photos/potato-mine.png");
		startTime = MainGame.t;

		if (currentCharge >= chargeTime) {
// 		TODO Find zombie that intersects
//			if (zombie.intersects this) {
//				takeDamage();
//			}
		}
	}

	@Override
	void takeDamage() {
		// TODO get zombie
		// zombie.health - damage();
	}

	@Override
	void shoot(int row, int col) {
		// TODO Auto-generated method stub
	}
}