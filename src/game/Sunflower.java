package game;

class Sunflower extends Plant {
	private static final long serialVersionUID = -3049630824248063159L;
	
	// Generates sun every 5 seconds, can be reduced
	static final int shootInterval = 540;

	Sunflower() {
		// FIXME get position of plant
		width = (MainGame.HIGHX - MainGame.LOWX) % 5;
		height = (MainGame.HIGHY - MainGame.LOWY) % 9;
		health = 10;
		img = MainGame.SUNFLOWER;
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