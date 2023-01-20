package game;

class SnowPea extends Plant {
	private static final long serialVersionUID = -923465118325001843L;

	SnowPea() {
		// FIXME get position of plant
		width = (MainGame.HIGHX - MainGame.LOWX) % 5;
		height = (MainGame.HIGHY - MainGame.LOWY) % 9;
		health = 10;
		img = MainGame.SNOWPEA;
		startTime = MainGame.t;
	}

	@Override
	void shoot(int row, int col) {
		new SnowPeaProjectile((col * MainGame.COLW + MainGame.LOWX) + MainGame.COLW,
				(row * MainGame.ROWH + MainGame.LOWY) + 15);
	}

	@Override
	void takeDamage(Zombie zomb) {
		this.health -= zomb.damage;
	}
}