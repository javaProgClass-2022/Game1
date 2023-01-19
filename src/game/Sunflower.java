package game;

class Sunflower extends Plant {
	private static final long serialVersionUID = -3049630824248063159L;
	
	// Generates sun every 5 seconds, can be reduced
	static final int shootInterval = 540;

	Sunflower() {
		health = 10;
		img = MainGame.loadImage("Photos/sunflower.png");
		startTime = MainGame.t;
	}

	@Override
	void takeDamage(Zombie zomb) {
		this.health -= zomb.damage;
	}

	@Override
	void shoot(int row, int col) {
		// TODO make sun
		// new Sun();
	}
}