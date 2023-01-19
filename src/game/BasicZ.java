package game;

class BasicZ extends Zombie {
	private static final long serialVersionUID = -2031496935122581009L;

	BasicZ(int rowIsIn) {
		health = 30;
		speed = 1;
		damage = 1;
		height = 170;
		width = 100;
		img = MainGame.basicZ;
		this.rowIsIn = rowIsIn;
	}
}
