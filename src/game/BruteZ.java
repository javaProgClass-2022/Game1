package game;

public class BruteZ extends Zombie {
	BruteZ(int rowIsIn) {
		health = 35;
		speed = 0.5;
		damage = 2;
		height = 200;
		width = 200;
		img = MainGame.bruteZ;
		this.rowIsIn = rowIsIn;
	}
}
