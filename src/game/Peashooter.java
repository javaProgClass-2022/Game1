package game;

class Peashooter extends Plant{
	
	static final int shootInterval = 60;
	
	Peashooter(){
		int shootTime = 0;
		health = 10;
		img = MainGame.loadImage("Photos/peashooter.png");
		
		// Shoots every second, after which it resets interval between shots
		if (shootTime == shootInterval) {
			this.shoot();
			shootTime = 0;
		}
		
		// TODO Find zombie that intersects
//		if (zombie.intersects this) {
//			this.health-zombie.damage;
//		}
	}

	@Override
	void shoot() {
		new Pea();
	}

	@Override
	void takeDamage() {
		// FIXME Get zombie damage and reduce by this number, on intersects?
		// this.health - zombie.damage;
	}
}