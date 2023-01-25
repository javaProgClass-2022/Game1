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
	static final int SCREENTIME = 300;
	static Timer timer;
	final static BufferedImage BKG1 = loadImage("Photos/Misc/BackGround1.jpg");
	final static BufferedImage SUNIMG = loadImage("Photos/Misc/sun.png");
	final static BufferedImage LAWNMOWER = loadImage("Photos/Misc/lawnmower.png");
	final static BufferedImage SHOVEL = loadImage("Photos/Misc/shovel.png");
	final static BufferedImage SELECTOR = loadImage("Photos/Misc/selectorbackground.png");

	// plant photos
	final static BufferedImage PEASHOOTER = loadImage("Photos/Plants/peashooter.png");
	final static BufferedImage POTATOMINE = loadImage("Photos/Plants/potatomine/potatomine1.png");
	final static BufferedImage POTATOMINECHARGING = loadImage("Photos/Plants/potatomine/potatomine2.png");
	final static BufferedImage SNOWPEA = loadImage("Photos/Plants/snowpea.png");
	final static BufferedImage SUNFLOWER = loadImage("Photos/Plants/sunflower.png");
	final static BufferedImage WALLNUT1 = loadImage("Photos/Plants/wallnut/wallnut1.png");
	final static BufferedImage WALLNUT2 = loadImage("Photos/Plants/wallnut/wallnut2.png");
	final static BufferedImage WALLNUT3 = loadImage("Photos/Plants/wallnut/wallnut3.png");

	// pea photos
	final static BufferedImage PEAPROJECTILE = loadImage("Photos/Peas/peaprojectile.png");
	final static BufferedImage SNOWPEAPROJECTILE = loadImage("Photos/Peas/snowpeaprojectile.png");

	// zombie photos
	final static BufferedImage BASICZ = loadImage("Photos/Zombies/basicZ.png");
	final static BufferedImage BASICZSLOW = loadImage("Photos/Zombies/SlowedZombies/basicZslow.png");
	final static BufferedImage FASTZ = loadImage("Photos/Zombies/fastZ.png");
	final static BufferedImage FASTZSLOW = loadImage("Photos/Zombies/SlowedZombies/fastZslow.png");
	final static BufferedImage BRUTEZ = loadImage("Photos/Zombies/bruteZ.png");
	final static BufferedImage BRUTEZSLOW = loadImage("Photos/Zombies/SlowedZombies/bruteZslow.png");

	// event photos
	final static BufferedImage STARTSCREEN = loadImage("Photos/Misc/StartScreen.jpg");
	final static BufferedImage DEATHSCREEN = loadImage("Photos/Misc/DeathScreen.jpg");
	final static BufferedImage NEXTLEVELSCREEN = loadImage("Photos/Misc/nextLevel.png");

	static boolean selectedPlants[] = { false, false, false, false, false, false };
	static Plant selectedPlant = null;

	final static int sunIncrement = 25;
	static boolean playerStatus = true;
	static int sun = 300;
	static int t = 0;
	static int level = 1;
	static int zCount = level * 10; // amount of zombies in each level
	static boolean nextLevelScreen = false;
	static boolean start = true;
	static double zombieInterval = 600;

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
	DrawingPanel panel = new DrawingPanel();

	// constructor
	MainGame() {
		// FIXME DEBUG
		board[0][0] = new Peashooter();
		board[1][0] = new SnowPea();
		board[2][0] = new Wallnut();
		board[3][0] = new Sunflower();
		board[4][0] = new PotatoMine();

		createAndShowGUI();
		lawnMowerCreation();
		startTimer();
	}

	void createAndShowGUI() {
		JFrame frame = new JFrame("Bootleg Plants vs. Zombies");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	void startTimer() {
		timer = new Timer(1, this);
		timer.setInitialDelay(1000);
		timer.start();
	}

	class DrawingPanel extends JPanel implements MouseListener {
		private static final long serialVersionUID = -8900499542696685692L;

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
			g.setColor(Color.white);
			g.drawImage(BKG1, 0, 150, getWidth(), 650, null);
			g.drawImage(SELECTOR, 0, 0, getWidth(), 150, null);
			g.setFont(new Font("Consolas", Font.PLAIN, 18));
			g.drawImage(PEASHOOTER, 300, 15, 90, 100, null);
			g.drawString("100", 330, 130);
			g.drawImage(SNOWPEA, 500, 15, 90, 100, null);
			g.drawString("175", 530, 130);
			g.drawImage(SUNFLOWER, 690, 15, 90, 100, null);
			g.drawString("50", 725, 130);
			g.drawImage(WALLNUT1, 880, 15, 90, 100, null);
			g.drawString("50", 920, 130);
			g.drawImage(POTATOMINE, 1090, 15, 90, 100, null);
			g.drawString("25", 1125, 130);
			g.drawImage(SUNIMG, 25, 15, 120, 120, null);
			g.setFont(new Font("Consolas", Font.BOLD, 36));
			g.drawString(((int) sun + ""), 140, 85);
			g.drawString("Level " + Integer.toString(level), (PANW - 200), 50);
			g.drawImage(SHOVEL, 20, (PANH - 120), 80, 80, null);

			drawPlants(g);
			drawPeas(g);
			drawMovers(g);
			drawZombies(g);
			drawSun(g);

			if (nextLevelScreen)
				nextLevel(g);
			if (start)
				startScreen(g);
			if (!playerStatus)
				deathScreen(g);
		}

		void nextLevel(Graphics g) {
			g.drawImage(NEXTLEVELSCREEN, 0, 0, PANW, PANH, null);
		}

		void deathScreen(Graphics g) {
			g.drawImage(DEATHSCREEN, 0, 0, PANW, PANH, null);
			timer.stop();
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
			g.setColor(new Color(150, 150, 150));
			if (selectedPlants[0]) {
				g.drawRect(270, 2, 140, 146);
			}
			if (selectedPlants[1]) {
				g.drawRect(470, 2, 140, 146);
			}
			if (selectedPlants[2]) {
				g.drawRect(665, 2, 140, 146);
			}
			if (selectedPlants[3]) {
				g.drawRect(865, 2, 125, 146);
			}
			if (selectedPlants[4]) {
				g.drawRect(1065, 2, 140, 146);
			}
			if (selectedPlants[5]) {
				g.drawRect(20, (PANH - 120), 80, 80);
			}
		}

		void drawSun(Graphics g) {
			for (int i = 0; i < sunList.size(); i++) {
				Sun sun = sunList.get(i);
				g.drawImage(sun.img, sun.x, sun.y, sun.width, sun.height, null);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			for (Sun sunny : sunList) { // FIXME, I don't work properly
				if (x >= sunny.x - sunny.width && x <= sunny.x + sunny.width && y >= sunny.y - sunny.height
						&& y <= sunny.y + sunny.height) {
					sun += sunIncrement;
					sunList.remove(sunny);
					break;
				}
			}

			if (!selectedPlants[0] && !selectedPlants[1] && !selectedPlants[2] && !selectedPlants[3]
					&& !selectedPlants[4] && !selectedPlants[5]) {
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
				if (x > 20 && x < 100 && y > (PANH - 120) && y < (PANH - 40)) {
					selectedPlants[5] = true;
				}
			}

			// if any plant has been selected, then find the row & col of the next mouse
			// click. If valid, then place selected plant on clicked upon tile
			else {
				int row = (y - LOWY) / ROWH;
				int col = (x - LOWX) / COLW;
				if (row >= 0 && row < 5 && col >= 0 && col < 9) {
					if (selectedPlants[0] && board[row][col] == null && sun >= 100) {
						board[row][col] = new Peashooter();
						board[row][col].x = col * COLW + LOWX;
						board[row][col].y = row * ROWH + LOWY;
						sun -= 100;
						selectedPlants[0] = false;
					}
					if (selectedPlants[1] && board[row][col] == null && sun >= 175) {
						board[row][col] = new SnowPea();
						board[row][col].x = col * COLW + LOWX;
						board[row][col].y = row * ROWH + LOWY;
						sun -= 175;
						selectedPlants[1] = false;
					}
					if (selectedPlants[2] && board[row][col] == null && sun >= 50) {
						board[row][col] = new Sunflower();
						board[row][col].x = col * COLW + LOWX;
						board[row][col].y = row * ROWH + LOWY;
						sun -= 50;
						selectedPlants[2] = false;
					}
					if (selectedPlants[3] && board[row][col] == null && sun >= 50) {
						board[row][col] = new Wallnut();
						board[row][col].x = col * COLW + LOWX;
						board[row][col].y = row * ROWH + LOWY;
						sun -= 50;
						selectedPlants[3] = false;
					}
					if (selectedPlants[4] && board[row][col] == null && sun >= 25) {
						board[row][col] = new PotatoMine();
						board[row][col].x = col * COLW + LOWX;
						board[row][col].y = row * ROWH + LOWY;
						sun -= 25;
						selectedPlants[4] = false;
					}
					if (selectedPlants[5]) {
						sun += board[row][col].cost;
						board[row][col] = null;
						selectedPlants[5] = false;
					}
				} else {
					for (int j = 0; j < 6; j++) {
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

	// if image not found (via try/catch), displays error message
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
		// when the amount of zombies are 0, it increases the level
		// and reinstates the zombies
		if (zCount < 0 && playerStatus) {
			level++;
			zCount = level * 10;
			nextLevelScreen = true;
			for (int i = 0; i < zList.size(); i++) {
				zList.remove(i);
			}
			t = 0;
		}
		if (t <= SCREENTIME) {
			if (t == SCREENTIME) {
				if (nextLevelScreen)
					nextLevelScreen = false;
				if (!playerStatus) {
					level = 0;
					playerStatus = true;
					lawnMowerCreation();
					for (int i = 0; i < zList.size(); i++) {
						zList.remove(i);
					}
					createandmoveZombies();
				} else
					start = false;
			}
		} else {
			createandmoveZombies();
			triggerMower();
			plantZombieIntersect();
			checkPotatoCharge();
			// when the amount of zombies are 0, it increases the level and reinstates the
			// zombies
			shootandmovePeas();
		}

		// Increases sun every 6 seconds
		if (t % 360 == 0)
			sun += 25;

		panel.repaint();
	}

	void checkPotatoCharge() {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] instanceof PotatoMine) {
					board[y][x].chargeAmount++;
					if (board[y][x].chargeAmount == 900) {
						board[y][x].charged = true;
						board[y][x].img = POTATOMINE;
					}
				}
			}
		}
	}

	void checkSunOnScreen() {
		for (Sun sunny : sunList) {
			sunny.timeRemaining--;
			if (sunny.timeRemaining < 0)
				sunList.remove(sunny);
			break;
		}
	}

	void sunFlowerCheck() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] instanceof Sunflower) {
					board[i][j].startTime++;
					if (board[i][j].startTime % 2000 == 0) {
						createSun();
					}
				}
			}
		}
	}

	// creates sun, generates a random sun on screen and adds it to the sun list
	void createSun() {
		int x = (int) (Math.random() * PANW);
		int y = (int) (Math.random() * PANH - 150);
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
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == null)
					continue;
				if ((t - board[y][x].startTime) % 180 == 0)
					board[y][x].shoot(y, x);
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
		// Take the snowpea list and if any of it intersects with any zombie, lower
		// velocity.
		for (int i = 0; i < snowPeaList.size(); i++) {
			SnowPeaProjectile snowpea = snowPeaList.get(i);
			for (int j = 0; j < zList.size(); j++) {
				Zombie zomb = zList.get(j);
				if (snowpea.intersects(zomb)) {
					zomb.health -= SnowPeaProjectile.damage;
					zomb.isSlowed = true;
					// Changes image if zombie is slowed
					zomb.img = slowZombieImage(zomb.img);
					snowPeaList.remove(snowpea);
				}
			}
		}
	}

	private BufferedImage slowZombieImage(BufferedImage img) {
		if (img == BASICZ) {
			return BASICZSLOW;
		}
		if (img == FASTZ) {
			return FASTZSLOW;
		}
		if (img == BRUTEZ) {
			return BRUTEZSLOW;
		}
		return img;
	}

	void plantZombieIntersect() {
		// if zombie intersects plant, stops and plant takes damage every second
		for (int x = 0; x < board.length; x++) {
			for (int i = 0; i < board[x].length; i++) {
				if (board[x][i] == null) {
					continue;
				}
				Plant currentPlant = board[x][i];
				for (int j = 0; j < zList.size(); j++) {
					Zombie zomb = zList.get(j);
					if (zomb.rowIsIn != x)
						continue;
					if (!currentPlant.intersects(zomb)) {
						if (zomb instanceof BasicZ) {
							zomb.speed = 0.66;
						}
						if (zomb instanceof BruteZ) {
							zomb.speed = 0.25;
						}
						if (zomb instanceof FastZ) {
							zomb.speed = 1.75;
						}
						continue;
					}
					zomb.speed = 0;
					if (t % 60 == 0) {
						currentPlant.takeDamage(zomb);
						if (currentPlant instanceof Wallnut) {
							if (currentPlant.health <= 20) {
								currentPlant.img = MainGame.WALLNUT2;
							}
							if (currentPlant.health <= 10) {
								currentPlant.img = MainGame.WALLNUT3;
							}
						}
						if (currentPlant.health <= 0) { // Removes plant if it dies
							board[x][i] = null;
						}
					}
				}
			}
		}
	}

	// triggers lawnmower
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
		if (t % (int) zombieInterval == 0 && zCount >= 0) {

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
			zombieInterval *= 0.999;
		}

		// goes through each zombie and moves them
		for (int i = 0; i < zList.size(); i++) {
			Zombie z = zList.get(i);
			// x is AN INT value and therefore, double speed change values are troublesome
			// as they might just get rounded down (as happens upon casting) and not
			// actually change the speed
			if (z.isSlowed) {
				z.speed *= 0.5;
			}
			z.xx -= z.speed;
			z.x = (int) Math.round(z.xx);

			// if the zombie is dead, then it removes it from the list
			if (z.health <= 0) {
				zList.remove(z);
			}
			// if zombie hits left without a mower, game ends
			if (z.x <= 170) {
				zList.remove(z);
				playerStatus = false;
				t = 0;
			}
		}
	}
}