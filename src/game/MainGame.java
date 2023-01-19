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
	final static BufferedImage SNOWPEA = loadImage("Photos/snow-pea.png");
	final static BufferedImage SUNFLOWER = loadImage("Photos/sunflower.png");
	final static BufferedImage WALLNUT = loadImage("Photos/wall-nut.png");

	// zombie photos
	final static BufferedImage basicZ = loadImage("Photos/basicZ.png");
	final static BufferedImage fastZ = loadImage("Photos/fastZ.png");
	final static BufferedImage bruteZ = loadImage("Photos/bruteZ.png");

	static boolean selPeashooter = false;
	static boolean selSnowpea = false;
	static boolean selSunflower = false;
	static boolean selWallnut = false;
	static boolean selPotatoMine = false;
	static Plant selectedPlant = null;

	static boolean playerStatus = true;
	static int sun = 100;
	static int t = 0;
	static int level = 1;
	static int zCount = level * 10; // amount of zombies in each level

	static ArrayList<Zombie> zList = new ArrayList<Zombie>();
	static Lawnmower mowList[] = new Lawnmower[5];
	static ArrayList<PeaProjectile> normalPeaList = new ArrayList<PeaProjectile>();
	static ArrayList<SnowPeaProjectile> snowPeaList = new ArrayList<SnowPeaProjectile>();

	static Plant board[][] = new Plant[5][9];
	// for the 2d array:
	// X RANGE: 260 - 1030, 9 columns each 86 wide
	// Y RANGE: 230 - 770, 5 rows each 108 tall
	// these values make sure that the images look okay in every space
	static final int lowX = 255;
	static final int highX = 1030;
	static final int lowY = 220;
	static final int highY = 770;
	static final int colW = 85;
	static final int rowH = 108;

	/***** instance variables (global) *****/
	DrawingPanel panel = new DrawingPanel();

	// constructor
	MainGame() {
		createAndShowGUI();
		lawnMowerCreation();
		startTimer();
	}

	void createAndShowGUI() {
		JFrame frame = new JFrame("Awesome game!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	void startTimer() {
		Timer timer = new Timer(1, this);
		timer.setInitialDelay(1000);
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
			g.drawImage(PEASHOOTER, 280, 0, 120, 120, null);
			g.setFont(new Font("Montferrato", Font.PLAIN, 18));
			g.drawString("100", 330, 142);
			g.drawString("175", 530, 142);
			g.drawImage(SNOWPEA, 480, 0, 120, 120, null);
			g.drawString("50", 725, 142);
			g.drawImage(SUNFLOWER, 675, 0, 120, 120, null);
			g.drawString("50", 920, 142);
			g.drawImage(WALLNUT, 875, 0, 105, 120, null);
			g.drawString("25", 1125, 142);
			g.drawImage(POTATOMINE, 1075, 0, 120, 120, null);
			g.drawImage(SUNIMG, 10, 0, 150, 150, null);
			g.setFont(new Font("Montferrato", Font.BOLD, 36));
			g.drawString((sun + ""), 170, 85);

			drawPlants(g);
			drawPeas(g);
			drawMovers(g);
			drawZombies(g);
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
			for (Zombie zomb : zList) {
				g.drawImage(zomb.img, zomb.x, zomb.y, zomb.width, zomb.height, null);
			}
		}

		void drawMovers(Graphics g) {
			for (Lawnmower mower : mowList) {
				if (mower != null) {
					if (mower.x >= PANW) {
						mower = null;
					}
					if (mower != null) {
						g.drawImage(mower.img, mower.x, mower.y, mower.width, mower.height, null);
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
						g.drawImage(board[y][x].img, lowX + (x * colW), lowY + (y * rowH), colW, rowH, null);
					}
				}
			}
			// show which plant has been selected
			if (selPeashooter) {
				g.drawRect(270, 2, 140, 146);
			}
			if (selSnowpea) {
				g.drawRect(470, 2, 140, 146);
			}
			if (selSunflower) {
				g.drawRect(665, 2, 140, 146);
			}
			if (selWallnut) {
				g.drawRect(865, 2, 125, 146);
			}
			if (selPotatoMine) {
				g.drawRect(1065, 2, 140, 146);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			if (!selPeashooter && !selSnowpea && !selSunflower && !selWallnut && !selPotatoMine) {
				if (x > 280 && x < 400 && y > 0 && y < 150) {
					selPeashooter = true;
				}
				if (x > 480 && x < 600 && y > 0 && y < 150) {
					selSnowpea = true;
				}
				if (x > 675 && x < 795 && y > 0 && y < 150) {
					selSunflower = true;
				}
				if (x > 875 && x < 980 && y > 0 && y < 150) {
					selWallnut = true;
				}
				if (x > 1075 && x < 1195 && y > 0 && y < 150) {
					selPotatoMine = true;
				}
			}
			// if any plant has been selected, then find the row & col of the next mouse
			// click. If valid, then place selected plant on clicked upon tile
			else {
				int row = (y - lowY) / rowH;
				int col = (x - lowX) / colW;
				if (row >= 0 && row <= 4 && col >= 0 && col <= 8) {
					if (selPeashooter) {
						board[row][col] = new Peashooter();
						selPeashooter = false;
					}
					if (selSnowpea) {
						board[row][col] = new SnowPea();
						selSnowpea = false;
					}
					if (selSunflower) {
						board[row][col] = new Sunflower();
						selSunflower = false;
					}
					if (selWallnut) {
						board[row][col] = new Wallnut();
						selWallnut = false;
					}
					if (selPotatoMine) {
						board[row][col] = new PotatoMine();
						selPotatoMine = false;
					}
				} else {
					selPeashooter = false;
					selSnowpea = false;
					selSunflower = false;
					selWallnut = false;
					selPotatoMine = false;
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
		createandmoveZombies();
		triggerMower();
		shootandmovePeas();
		plantZombieIntersect();
		// when the amount of zombies are 0, it increases the level and reinstates the
		// zombies
		if (zCount < 0 && playerStatus) {
			level++;
			zCount = level * 10;
		}
		panel.repaint();
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
		if (t % 220 == 0) {
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					if (board[y][x] != null) {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] != null) {
					if ((t - board[y][x].startTime) % 150 == 0) {
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
		// if zombie insersects plant, stops and plant takes damage
		double orignalSpeed = 0;
		System.out.println(board.length);
		if (t % 60 == 0) {
			for (int x = 0; x < board.length; x++) {
				for (int i = 0; i < board.length; i++) {
					if (board[x][i] == null) {
						continue;
					}
					Plant currentPlant = board[x][i];
					for (int j = 0; j < zList.size(); j++) {
						Zombie zomb = zList.get(j);
						if (currentPlant.intersects(zomb)) {
							orignalSpeed = zomb.speed;
							zomb.speed = 0;
							currentPlant.takeDamage(zomb);
							// Removes plant if it dies
							if (currentPlant.health <= 0) {
								board[x][i] = null;
							}
						} else {
							zomb.speed = orignalSpeed;
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
					int zombrow = (zomb.y - lowY - rowH + zomb.height) / rowH;
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
			m.y = lowY + i * rowH;
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
			c.y = lowY + row * rowH + rowH - c.height;
			zList.add(c);

			// decreases the zombie count when one is created
			zCount--;
		}

		// goes through each zombie and moves them
		for (int i = 0; i < zList.size(); i++) {
			Zombie z = zList.get(i);
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
				System.out.println("GAME ENDED.");
			}
		}
	}
}
