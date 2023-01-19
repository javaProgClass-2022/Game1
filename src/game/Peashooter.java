package game;

class Peashooter extends Plant {
	private static final long serialVersionUID = 4371182833899103646L;
	
	static int side = 25;

	Peashooter() {
		health = 10;
		img = MainGame.loadImage("Photos/peashooter.png");
		startTime = MainGame.t;
	}

	@Override
	void shoot(int row, int col) {
		new PeaProjectile((col * MainGame.colW + MainGame.lowX) + MainGame.colW,
				(row * MainGame.rowH + MainGame.lowY) + 15);
	}

	@Override
	void takeDamage(Zombie zomb) {
		this.health -= zomb.damage;
	}
}