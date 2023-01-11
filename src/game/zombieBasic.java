package game;

public class zombieBasic {

	int health = 20;
	double speed = 1;
	int height;
	int width;
	int damage = 1;
	int x;
	int y;
	
	zombieBasic(int height, int width, int x, int y) {
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
	}
}
