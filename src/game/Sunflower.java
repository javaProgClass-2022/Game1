package game;

class Sunflower extends Plant {
	private static final long serialVersionUID = -3049630824248063159L;
	
	// Generates sun every 5 seconds, can be reduced
	static final int shootInterval = 540;

	Sunflower() {
		width = MainGame.COLW;
		height = MainGame.ROWH;
		health = 10;
		img = MainGame.SUNFLOWER;
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