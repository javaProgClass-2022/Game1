package game;

public class BruteZ extends Zombie {
	private static final long serialVersionUID = 7206111899573913653L;

	BruteZ(int rowIsIn) {
		health = 50;
		speed = 0.5;
		damage = 2;
		height = 190;
		width = 200;
		img = MainGame.bruteZ;
		this.rowIsIn = rowIsIn;
	}
}
