package game;

public class FastZ extends Zombie {
	private static final long serialVersionUID = -5898861461361621463L;

	FastZ(int rowIsIn) {
		health = 20;
		speed = 3;
		damage = 1;
		height = 150;
		width = 100;
		img = MainGame.fastZ;
		this.rowIsIn = rowIsIn;
	}
}
