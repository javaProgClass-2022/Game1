package game;

class Wallnut extends Plant {
	
	Wallnut(){
		health = 40;
		img = MainGame.loadImage("Photos/wall-nut.png");

		// TODO Find zombie that intersects
//		if (zombie.intersects this) {
//			this.takeDamage();
//		}
	}

	@Override
	void shoot() { // Do nothing, wallnuts do not shoot
	}

	@Override
	void takeDamage() {
		// FIXME Get zombie damage and reduce by this number, on intersects?
		// this.health - zombie.damage;
	}
}