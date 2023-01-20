package game;

public class FastZ extends Zombie {
	private static final long serialVersionUID = -5898861461361621463L;

	FastZ(int rowIsIn) {
		health = 20;
		speed = 2;
		damage = 1;
		height = 150;
		width = 100;
		if (this.isSlowed) {
			img = MainGame.FASTZSLOW;
		} else {
			img = MainGame.FASTZ;
		}
		this.rowIsIn = rowIsIn;
	}
}
