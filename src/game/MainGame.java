package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	final static BufferedImage bkg1 = loadImage("Photos/BackGround1.jpg");
	final static BufferedImage sunIMG = loadImage("Photos/sun.png");
	final static BufferedImage lawnMower = loadImage("Photos/lawnmower.png");

	// plant photos
	final static BufferedImage peashooter = loadImage("Photos/peashooter.png");
	final static BufferedImage potatomine = loadImage("Photos/potato-mine.png");
	final static BufferedImage snowpea = loadImage("Photos/snow-pea.png");
	final static BufferedImage sunflower = loadImage("Photos/sunflower.png");
	final static BufferedImage wallnut = loadImage("Photos/wall-nut.png");

	// zombie photos
	final static BufferedImage basicZ = loadImage("Photos/basicZ.png");
	final static BufferedImage fastZ = loadImage("Photos/fastZ.png");
	final static BufferedImage bruteZ = loadImage("Photos/bruteZ.png");

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

	class DrawingPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		DrawingPanel() {
			this.setBackground(new Color(225, 198, 153));
			this.setPreferredSize(new Dimension(PANW, PANH));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// sets up base menu and game board
			g.drawImage(bkg1, 0, 150, getWidth(), 650, null);
			g.drawImage(peashooter, 280, 0, 120, 120, null);
			g.setFont(new Font("Montferrato", Font.PLAIN, 18));
			g.drawString("100", 330, 142);
			g.drawString("175", 530, 142);
			g.drawImage(snowpea, 480, 0, 120, 120, null);
			g.drawString("50", 725, 142);
			g.drawImage(sunflower, 675, 0, 120, 120, null);
			g.drawString("50", 920, 142);
			g.drawImage(wallnut, 875, 0, 105, 120, null);
			g.drawString("25", 1125, 142);
			g.drawImage(potatomine, 1075, 0, 120, 120, null);
			g.drawImage(sunIMG, 10, 0, 150, 150, null);
			g.setFont(new Font("Montferrato", Font.BOLD, 36));
			g.drawString((sun + ""), 170, 85);

			drawPlants(g);
			drawPeas(g);
			drawMovers(g);
			drawZombies(g);
		}

		void drawPeas(Graphics g) {
			for (int x = 0; x < normalPeaList.size(); x++) {
				g.drawImage(normalPeaList.get(x).img, normalPeaList.get(x).x, normalPeaList.get(x).y,
						PeaProjectile.side, PeaProjectile.side, null);
				normalPeaList.get(x).movePea();
			}
			for (int x = 0; x < snowPeaList.size(); x++) {
				g.drawImage(snowPeaList.get(x).img, snowPeaList.get(x).x, snowPeaList.get(x).y, SnowPeaProjectile.side,
						SnowPeaProjectile.side, null);
				snowPeaList.get(x).movePea();
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
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					// if there is a plant on the tile, it'll display it
					if (board[y][x] != null) {
						g.drawImage(board[y][x].img, lowX + (x * colW), lowY + (y * rowH), colW, rowH, null);
					}
				}
			}
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
		initializeZombies();
		triggerMower();
		// when the amount of zombies are 0, it increases the level and reinstates the
		// zombies
		// TODO this is placeholder code until we figure out what will happen when the
		// level is completed

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

		if (zCount < 0 && playerStatus) {
			level++;
			zCount = level * 10;
		}
		panel.repaint();
	}

	public void triggerMower() {
		for (int i = 0; i < mowList.length; i++) {
			if (mowList[i] != null) {
				Lawnmower mower = mowList[i];
				for (int j = 0; j < zList.size(); j++) {
					Zombie zomb = zList.get(j);
					int lawnrow = i;
					// zomb.y is the higher range of the space between zombie's y coordinate and the
					// rest is the lowest y coordinate a zombie can be at
					int zombrow = (zomb.y - lowY - rowH + zomb.height) / rowH;
					if (zombrow == lawnrow && zomb.x <= 170) {
						mower.triggered = true;
						zList.remove(j);
					}
				}
			}
		}
		for (int i = 0; i < mowList.length; i++) {
			if (mowList[i] != null) {
				Lawnmower mower = mowList[i];
				if (mower.triggered) {
					mower.x += mower.speed;
					for (int j = 0; j < zList.size(); j++) {
						Zombie zomb = zList.get(j);
						if (mower.intersects(zomb)) {
							System.out.println("INTERSECTS");
							zList.remove(zomb);
						}
					}
				}
			}
		}
	}

	public void lawnMowerCreation() {
		for (int i = 0; i < 5; i++) {
			Lawnmower m = new Lawnmower();
			m.x = 170 - i * 2;
			m.y = lowY + i * rowH;
			mowList[i] = m;
		}
	}

	public void initializeZombies() {

		// creates a zombie every 2 seconds
		if (t % 200 == 0 && zCount >= 0) {

			// random number to figure out which type based on the level
			int type = (int) (Math.random() * level + 1);
			int row = (int) (Math.random() * 5);
			Zombie c;

			// creates the zombies based on the type
			if (type == 1) {
				c = new BasicZ();
			} else if (type == 2) {
				c = new FastZ();
			} else {
				c = new BruteZ();
			}
			c.x = PANW;
			c.y = lowY + row * rowH + rowH - c.height;
			zList.add(c);

			// decreases the zombie count when one is created
			zCount--;
		}

		// going through each zombie and moving them
		for (int i = 0; i < zList.size(); i++) {
			Zombie z = zList.get(i);
			z.x -= z.speed;

			// if the zombie is dead, then it removes it from the list
			if (z.health <= 0) {
				zList.remove(z);
			}
		}
	}
}
