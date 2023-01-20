package game;

class Peashooter extends Plant {
	private static final long serialVersionUID = 4371182833899103646L;

	Peashooter() {
		width = (MainGame.HIGHX - MainGame.LOWX) % 5;
		height = (MainGame.HIGHY - MainGame.LOWY) % 9;
		health = 10;
		img = MainGame.PEASHOOTER;
		startTime = MainGame.t;
	}

	@Override
	void shoot(int row, int col) {
		new PeaProjectile((col * MainGame.COLW + MainGame.LOWX) + MainGame.COLW,
				(row * MainGame.ROWH + MainGame.LOWY) + 15);
	}

	@Override
	void takeDamage(Zombie zomb) {
		this.health -= zomb.damage;
	}
}