package game;

class SnowPea extends Plant {
	private static final long serialVersionUID = -923465118325001843L;
	static final int shootInterval = 60;
	SnowPea() {
		width = MainGame.COLW;
		height = MainGame.ROWH;
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