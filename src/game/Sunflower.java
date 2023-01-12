package game;

class Sunflower extends Plant {
	
	static final int shootInterval = 540;
	
	Sunflower(){
		health = 10;
		img = MainGame.loadImage("Photos/sunflower.png");

		// TODO Find zombie that intersects
//		if (zombie.intersects this) {
//			this.takeDamage();
//		}
	}

	@Override
	void shoot() { // Creates sun instead of shooting anything
		new Sun();
	}

	@Override
	void takeDamage() {
		// FIXME Get zombie damage and reduce by this number, on intersects?
		// this.health - zombie.damage;
		if (this.health <= 0) {
			this.die();
		}
	}

	@Override
	void die() {
		// TODO
	}
}