package game;

class Peashooter extends Plant{
	Peashooter(){
		cost = 3;
		speed = 1;
		health = 5;
		img = MainGame.loadImage("Photos/peashooter.png");
	}

	@Override
	void die() {
		// TODO
	}

}