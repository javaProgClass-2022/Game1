package game;

class Sunflower extends Plant {

	// Generates sun every 5 seconds, can be reduced
	static final int shootInterval = 540;

	Sunflower() {
		health = 10;
		img = MainGame.loadImage("Photos/sunflower.png");

		// TODO Find zombie that intersects
//		if (zombie.intersects this) {
//			this.takeDamage();
//		}
	}

	@Override
	void takeDamage() {
		// FIXME Get zombie damage and reduce by this number, on intersects?
		// this.health - zombie.damage;
	}

	@Override
	void shoot(int row, int col) {
		// TODO make sun
		// new Sun();
	}
}