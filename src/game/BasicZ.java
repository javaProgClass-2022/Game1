package game;

class BasicZ extends Zombie {

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
