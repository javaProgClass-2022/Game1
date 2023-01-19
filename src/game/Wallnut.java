package game;

class Wallnut extends Plant {

	Wallnut() {
		health = 42;
		startTime = MainGame.t;
		checkHealth();
	}

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
		// FIXME Get zombie damage and reduce by this number, on intersects?
		// this.health - zombie.damage;
	}

	@Override
	void shoot(int row, int col) {
		// TODO Auto-generated method stub

	}
}