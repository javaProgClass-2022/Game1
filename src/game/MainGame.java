package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MainGame implements ActionListener {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainGame();
			}
		});
	}

	/***** constants *****/
	final static int PANW = 1450;
	final static int PANH = 800;
	final static BufferedImage BKG1 = loadImage("Photos/BackGround1.jpg");
	final static BufferedImage SUNIMG = loadImage("Photos/sun.png");
	final static BufferedImage LAWNMOWER = loadImage("Photos/lawnmower.png");

	// plant photos
	final static BufferedImage PEASHOOTER = loadImage("Photos/peashooter.png");
	final static BufferedImage POTATOMINE = loadImage("Photos/potato-mine.png");
	final static BufferedImage POTATOMINECHARGING = loadImage("Photos/potato_mine_charging.png");
	final static BufferedImage SNOWPEA = loadImage("Photos/snow-pea.png");
	final static BufferedImage SUNFLOWER = loadImage("Photos/sunflower.png");
	final static BufferedImage WALLNUT1 = loadImage("Photos/wall-nut.png");
	final static BufferedImage WALLNUT2 = loadImage("Photos/WallnutExtra/WallnutDamage1.png");
	final static BufferedImage WALLNUT3 = loadImage("Photos/WallnutExtra/WallnutDamage2.png");

	static boolean selectedPlants[] = { false, false, false, false, false };
	static Plant selectedPlant = null;

	// pea photos
	final static BufferedImage PEAPROJECTILE = loadImage("Photos/Peas/pea.png");
	final static BufferedImage SNOWPEAPROJECTILE = loadImage("Photos/Peas/snowy_pea.png");

	// zombie photos
	final static BufferedImage BASICZ = loadImage("Photos/basicZ.png");
	final static BufferedImage BASICZSLOW = loadImage("Photos/SlowedZombies/basicZslow.png");
	final static BufferedImage FASTZ = loadImage("Photos/fastZ.png");
	final static BufferedImage FASTZSLOW = loadImage("Photos/SlowedZombies/fastZslow.png");
	final static BufferedImage BRUTEZ = loadImage("Photos/bruteZ.png");
	final static BufferedImage BRUTEZSLOW = loadImage("Photos//SlowedZombies/bruteZslow.png");

	//event photos
	final static BufferedImage STARTSCREEN = loadImage("Photos/StartScreen.png");
	final static BufferedImage DEATHSCREEN = loadImage("Photos/DeathScreen.png");
	final static BufferedImage NEXTLEVELSCREEN = loadImage("Photos/nextLevel.png");


	final static double sunIncriment = 15.0;
	static boolean playerStatus = true;
	static double sun = 100;
	static int t = 0;
	static int level = 1;
	static int zCount = level * 10; // amount of zombies in each level
	static boolean nextLevelScreen = false;
	static boolean start = true;
	static final int SCREENTIME = 500;



	static ArrayList<Zombie> zList = new ArrayList<Zombie>();
	static Lawnmower mowList[] = new Lawnmower[5];
	static ArrayList<PeaProjectile> normalPeaList = new ArrayList<PeaProjectile>();
	static ArrayList<SnowPeaProjectile> snowPeaList = new ArrayList<SnowPeaProjectile>();
	static ArrayList<Sun> sunList = new ArrayList<Sun>();

	static Plant board[][] = new Plant[5][9];
	// for the 2d array:
	// X RANGE: 260 - 1030, 9 columns each 86 wide
	// Y RANGE: 230 - 770, 5 rows each 108 tall
	// these values make sure that the images look okay in every space
	static final int LOWX = 255;
	static final int HIGHX = 1030;
	static final int LOWY = 220;
	static final int HIGHY = 770;
	static final int COLW = 85;
	static final int ROWH = 108;

	/***** instance variables (global) *****/
	DrawingPanel panelGame = new DrawingPanel();

	// constructor
	MainGame() {
		board[0][0] = new Peashooter();
		board[1][0] = new SnowPea();
		board[2][0] = new Wallnut();
		board[3][0] = new Sunflower();
		board[4][0] = new PotatoMine();
		board[0][3] = new Sunflower();
		createAndShowGUI();
		lawnMowerCreation();
		startTimer();
	}

	void openStartMenu() {
		JFrame frameStart = new JFrame("Welcome to Bootleg Plants vs Zombies");
		frameStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameStart.setResizable(false);
		JPanel panelStart = new JPanel();

		frameStart.add(panelStart);
		frameStart.pack();
		frameStart.setLocationRelativeTo(null);
		frameStart.setVisible(true);
	}

	void createAndShowGUI() {
		JFrame frameGame = new JFrame("Awesome game!");
		frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameGame.setResizable(false);

		frameGame.add(panelGame);
		frameGame.pack();
		frameGame.setLocationRelativeTo(null);
		frameGame.setVisible(true);
	}

	void startTimer() {
		Timer timer = new Timer(1, this);
		timer.setInitialDelay(0000);
		timer.start();
	}

	class DrawingPanel extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;

		DrawingPanel() {
			this.setBackground(new Color(225, 198, 153));
			this.setPreferredSize(new Dimension(PANW, PANH));
			this.addMouseListener(this);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// sets up base menu and game board
			g.drawImage(BKG1, 0, 150, getWidth(), 650, null);
			g.drawImage(PEASHOOTER, 275, 0, 120, 120, null);
			g.setFont(new Font("Montferrato", Font.PLAIN, 18));
			g.drawString("100", 330, 142);
			g.drawString("175", 530, 142);
			g.drawImage(SNOWPEA, 480, 0, 120, 120, null);
			g.drawString("50", 725, 142);
			g.drawImage(SUNFLOWER, 675, 0, 120, 120, null);
			g.drawString("50", 920, 142);
			g.drawImage(WALLNUT1, 875, 0, 105, 120, null);
			g.drawString("25", 1125, 142);
			g.drawImage(POTATOMINE, 1075, 0, 120, 120, null);
			g.drawImage(SUNIMG, 10, 0, 150, 150, null);
			g.setFont(new Font("Montferrato", Font.BOLD, 36));
			g.drawString(((int)sun + ""), 170, 85);

			drawPlants(g);
			drawPeas(g);
			drawMovers(g);
			drawZombies(g);
			drawSun(g);
			if(nextLevelScreen) nextLevel(g);
			if(start) startScreen(g);
			if(!playerStatus) deathScreen(g);
		}

		void nextLevel(Graphics g) {
			g.drawImage(NEXTLEVELSCREEN, 0, 0, PANW, PANH, null);
		}

		void deathScreen(Graphics g) {
			g.drawImage(DEATHSCREEN, 0, 0, PANW, PANH, null);
		}

		void startScreen(Graphics g) {
			g.drawImage(STARTSCREEN, 0, 0, PANW, PANH, null);
		}		

		void drawPeas(Graphics g) {
			for (PeaProjectile pea : normalPeaList) {
				g.drawImage(pea.img, pea.x, pea.y, pea.width, pea.height, null);
			}
			for (SnowPeaProjectile snowpea : snowPeaList) {
				g.drawImage(snowpea.img, snowpea.x, snowpea.y, snowpea.width, snowpea.height, null);
			}
		}

		void drawZombies(Graphics g) {
			for (int x = 0; x < zList.size(); x++) {
				g.drawImage(zList.get(x).img, zList.get(x).x, zList.get(x).y, zList.get(x).width, zList.get(x).height,
						null);
			}
		}

		void drawMovers(Graphics g) {
			for (int x = 0; x < mowList.length; x++) {
				if (mowList[x] != null) {
					if (mowList[x].x >= PANW) {
						mowList[x] = null;
					}
					if (mowList[x] != null) {
						g.drawImage(mowList[x].img, mowList[x].x, mowList[x].y, mowList[x].width, mowList[x].height,
								null);
					}
				}
			}
		}

		void drawPlants(Graphics g) {
			// draws every plant in board[][] array if not null
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					// if there is a plant on the tile, it'll display it
					if (board[y][x] != null) {
						g.drawImage(board[y][x].img, LOWX + (x * COLW), LOWY + (y * ROWH), COLW, ROWH, null);
					}
				}
			}
			// show which plant has been selected
			for (int i = 0; i < selectedPlants.length; i++) {
				if (selectedPlants[i]) {
					g.drawRect(i * 200 + 265, 2, 140, 146);
				}
			}
		}

		void drawSun(Graphics g) {
			for(int i = 0; i < sunList.size(); i++) {
				Sun sun = sunList.get(i);
				g.drawImage(sun.img, sun.x, sun.y, sun.width, sun.height, null);
			}
		}

		void drawSun(Graphics g) {
			for(int i = 0; i < sunList.size(); i++) {
				Sun sun = sunList.get(i);
				g.drawImage(sun.img, sun.x, sun.y, sun.width, sun.height, null);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			for(Sun sunny : sunList) {
				if(x >= sunny.x-sunny.width && x <= sunny.x+sunny.width && y >= sunny.y-sunny.height && y <= sunny.y+sunny.height) {
					sun+=sunIncriment;
					sunList.remove(sunny);
					break;
				}
			}

			if (!selectedPlants[0] && !selectedPlants[1] && !selectedPlants[2] && !selectedPlants[3]
					&& !selectedPlants[4]) {
				if (x > 280 && x < 400 && y > 0 && y < 150) {
					selectedPlants[0] = true;
				}
				if (x > 480 && x < 600 && y > 0 && y < 150) {
					selectedPlants[1] = true;
				}
				if (x > 675 && x < 795 && y > 0 && y < 150) {
					selectedPlants[2] = true;
				}
				if (x > 875 && x < 980 && y > 0 && y < 150) {
					selectedPlants[3] = true;
				}
				if (x > 1075 && x < 1195 && y > 0 && y < 150) {
					selectedPlants[4] = true;
				}
			}

			// if any plant has been selected, then find the row & col of the next mouse
			// click. If valid, then place selected plant on clicked upon tile
			else {
				int row = (y - LOWY) / ROWH;
				int col = (x - LOWX) / COLW;
				if (row >= 0 && row <= 4 && col >= 0 && col <= 8) {
					if (selectedPlants[0]) {
						board[row][col] = new Peashooter();
						selectedPlants[0] = false;
					}
					if (selectedPlants[1]) {
						board[row][col] = new SnowPea();
						selectedPlants[1] = false;
					}
					if (selectedPlants[2]) {
						board[row][col] = new Sunflower();
						selectedPlants[2] = false;
					}
					if (selectedPlants[3]) {
						board[row][col] = new Wallnut();
						selectedPlants[3] = false;
					}
					if (selectedPlants[4]) {
						board[row][col] = new PotatoMine();
						selectedPlants[4] = false;
					}
				} else {
					for (int j = 0; j < selectedPlants.length; j++) {
						selectedPlants[j] = false;
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	// if image not found (via try/catch), throw error message
	static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null, "An image failed to load:" + filename, "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		return img;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		t++;
		if (zCount < 0 && playerStatus) {
			level++;
			zCount = level * 10;
			nextLevelScreen = true;
			for(int i = 0; i < zList.size(); i++) {
				zList.remove(i);
			}
			t = 0;
		}
		if(t <= SCREENTIME) {
			if(t == SCREENTIME) {
				if(nextLevelScreen) nextLevelScreen = false;
				if(!playerStatus) {
					level = 0;
					playerStatus = true;
					lawnMowerCreation();
					for(int i = 0; i < zList.size(); i++) {
						zList.remove(i);
					}
					createandmoveZombies();
				} 
				else start = false;
			}
		}
		else {
			createandmoveZombies();
			triggerMower();
			plantZombieIntersect();
			checkPotatoCharge();
			// when the amount of zombies are 0, it increases the level and reinstates the
			// zombies
			shootandmovePeas();
			plantZombieIntersect();
			checkPotatoCharge();
			// every plant, if allowed, shoots
			if (t % 150 == 0) {
				for (int y = 0; y < board.length; y++) {
					for (int x = 0; x < board[y].length; x++) {
						if (board[y][x] != null) {
							board[y][x].shoot(y, x);
						}
					}
				}
			}
			sun+=0.0025;
		}

		panel.repaint();
	}

	private void checkPotatoCharge() {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] instanceof PotatoMine) {
					board[y][x].chargeAmount++;
					if (board[y][x].chargeAmount == 300) {
						board[y][x].charged = true;
					}
				}
			}
		}
	}

	public void checkSunOnScreen() {
		for(Sun sunny : sunList) {
			sunny.timeRemaining--;
			if(sunny.timeRemaining < 0) sunList.remove(sunny);
			break;
		}
	}

	public void sunFlowerCheck() {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if (board[i][j] instanceof Sunflower ) {
					board[i][j].startTime++;
					if(board[i][j].startTime%2000 == 0) {
						createSun();
					}
				}
			}
		}
	}

	//creates sun, generates a random sun on screen and adds it to the sun list
	public void createSun() {
		int x = (int) (Math.random()*PANW);
		int y = (int) (Math.random()*PANH-150);
		Sun sun = new Sun();
		sun.x = x;
		sun.y = y;
		sunList.add(sun);
	}

	void shootandmovePeas() {
		// moves peas
		for (PeaProjectile pea : normalPeaList) {
			pea.x += PeaProjectile.velocity;
		}
		for (SnowPeaProjectile snowpea : snowPeaList) {
			snowpea.x += SnowPeaProjectile.velocity;
		}
		// every plant, if allowed, shoots
		if (t % 200 == 0) {
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					if (board[y][x] != null) {
						board[y][x].shoot(y, x);
					}
				}
			}
		}
		// take the normal pea list and if any of it intersects with any zombie, make
		// the latter take damage
		for (int i = 0; i < normalPeaList.size(); i++) {
			PeaProjectile pea = normalPeaList.get(i);
			for (int j = 0; j < zList.size(); j++) {
				Zombie zomb = zList.get(j);
				if (pea.intersects(zomb)) {
					zomb.health -= PeaProjectile.damage;
					normalPeaList.remove(pea);
				}
			}
		}
		// take the snowpea list and if any of it intersects with any zombie, do a
		// smaller amount of damage, but lower velocity. This only happens once to a
		// zombie
		for (int i = 0; i < snowPeaList.size(); i++) {
			SnowPeaProjectile snowpea = snowPeaList.get(i);
			for (int j = 0; j < zList.size(); j++) {
				Zombie zomb = zList.get(j);
				if (snowpea.intersects(zomb)) {
					zomb.health -= SnowPeaProjectile.damage;
					if (zomb.isSlowed == false) {
						zomb.speed *= 0.66;
						zomb.isSlowed = true;
					}
					snowPeaList.remove(snowpea);
				}
			}
		}
	}

	void plantZombieIntersect() {
		// if zombie insersects plant, stops and plant takes damage every seconf
		for (int x = 0; x < board.length; x++) {
			for (int i = 0; i < board.length; i++) {
				if (board[x][i] == null) {
					continue;
				}
				Plant currentPlant = board[x][i];
				for (int j = 0; j < zList.size(); j++) {
					Zombie zomb = zList.get(j);
					if (!currentPlant.intersects(zomb)) {
						if (zomb instanceof BasicZ) {
							zomb.speed = 1;
						}
						if (zomb instanceof BruteZ) {
							zomb.speed = 0.5;
						}
						if (zomb instanceof FastZ) {
							zomb.speed = 2;
						}
						continue;
					}
					zomb.speed = 0;
					if (t % 60 == 0) {
						currentPlant.takeDamage(zomb);
						if (currentPlant.health <= 0) { // Removes plant if it dies
							board[x][i] = null;
						}
					}
				}
			}
		}
	}


	void triggerMower() {
		for (int i = 0; i < mowList.length; i++) {
			if (mowList[i] != null) {
				Lawnmower mower = mowList[i];
				for (int j = 0; j < zList.size(); j++) {
					Zombie zomb = zList.get(j);
					int lawnrow = i;
					// zomb.y is the higher range of the space between zombie's y coordinate and the
					// rest is the lowest y coordinate a zombie can be at
					int zombrow = (zomb.y - LOWY - ROWH + zomb.height) / ROWH;
					if (zombrow == lawnrow && zomb.x <= 175) {
						mower.triggered = true;
					}
				}
			}
		}
		// allows triggered motors to kill zombies in its row
		for (int i = 0; i < mowList.length; i++) {
			if (mowList[i] != null) {
				Lawnmower mower = mowList[i];
				if (mower.triggered) {
					mower.x += mower.speed;
					for (int j = 0; j < zList.size(); j++) {
						Zombie zomb = zList.get(j);
						if ((zomb.rowIsIn == i) && mower.intersects(zomb)) {
							zList.remove(zomb);
						}
					}
				}
			}
		}
	}

	void lawnMowerCreation() {
		for (int i = 0; i < 5; i++) {
			Lawnmower m = new Lawnmower();
			m.x = 170 - i * 2;
			m.y = LOWY + i * ROWH;
			mowList[i] = m;
		}
	}

	void createandmoveZombies() {
		// creates a zombie every 2 seconds
		if (t % 200 == 0 && zCount >= 0) {

			// random number to figure out which type based on the level
			int type = (int) (Math.random() * level + 1);
			int row = (int) (Math.random() * 5);
			Zombie c;

			// creates the zombies based on the type
			if (type == 1) {
				c = new BasicZ(row);
			} else if (type == 2) {
				c = new FastZ(row);
			} else {
				c = new BruteZ(row);
			}
			c.x = PANW;
			c.xx = c.x;
			c.y = LOWY + row * ROWH + ROWH - c.height;
			zList.add(c);

			// decreases the zombie count when one is created
			zCount--;
		}

		// goes through each zombie and moves them
		for (int i = 0; i < zList.size(); i++) {
			Zombie z = zList.get(i);
			// x is AN INT value and therefore, double speed change values are troublesome
			// as
			// they might just get rounded down (as happens upon casting) and not actually
			// change the speed
			// TODO maybe make a double xx value that compliments x to act as a middle
			// ground between moving it a double speed each second
			z.xx -= z.speed;
			z.x = (int) Math.round(z.xx);

			// if the zombie is dead, then it removes it from the list
			if (z.health <= 0) {
				zList.remove(z);
			}
			// if zombie hits left without a mower, game ends
			if (z.x <= 170) {
				zList.remove(z);
				// TODO make actual game end screen
				playerStatus = false;
				t = 0;
			}
		}
	}
}