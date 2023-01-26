package game;

class BasicZ extends Zombie {
	private static final long serialVersionUID = -2031496935122581009L;

	BasicZ(int rowIsIn) {
		health = 30;
		speed = 0.5;
		damage = 1;
		height = 170;
		width = 100;
		if (this.isSlowed) {
			img = MainGame.BASICZSLOW;
		} else {
			img = MainGame.BASICZ;
		}
		this.rowIsIn = rowIsIn;
	}
}
