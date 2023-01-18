package game;

public class FastZ extends Zombie {

	FastZ(int rowIsIn) {
		health = 15;
		speed = 1.5;
		damage = 1;
		height = 150;
		width = 100;
		img = MainGame.fastZ;
		this.rowIsIn = rowIsIn;
	}
}
