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
import javax.swing.event.MouseInputListener;

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

	static boolean selectedPlants[] = { false, false, false, false, false };
	static Plant selectedPlant = null;

	// zombie photos
	final static BufferedImage basicZ = loadImage("Photos/basicZ.png");
	final static BufferedImage fastZ = loadImage("Photos/fastZ.png");
	final static BufferedImage bruteZ = loadImage("Photos/bruteZ.png");

	
	final static double sunIncriment = 15.0;
	static boolean playerStatus = true;
	static double sun = 100;
	static int t = 0;
	static int level = 1;
	static int zCount = level * 10; // amount of zombies in each level



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
		board[0][3] = new Sunflower();
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
			g.drawString(((int)sun + ""), 170, 85);

			drawPlants(g);
			drawPeas(g);
			drawMovers(g);
			drawZombies(g);
			drawSun(g);
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
				int row = (y - lowY) / rowH;
				int col = (x - lowX) / colW;
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
		initializeZombies();
		triggerMower();
		sunFlowerCheck();
		checkSunOnScreen();
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
		sun+=0.0025;
		panel.repaint();
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

	public void createSun() {
		int x = (int) (Math.random()*PANW);
		int y = (int) (Math.random()*PANH-150);
		Sun sun = new Sun();
		sun.x = x;
		sun.y = y;
		sunList.add(sun);
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
						if ((zomb.rowIsIn == i) && mower.intersects(zomb)) {
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
				c = new BasicZ(row);
			} else if (type == 2) {
				c = new FastZ(row);
			} else {
				c = new BruteZ(row);
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
