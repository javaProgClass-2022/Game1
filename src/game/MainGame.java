package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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
	static int t = 0;
	static int level = 1;
	int zCount = level*10; //amount of zombies in each level
	ArrayList<Zombie> zList = new ArrayList<Zombie>();
	
	ArrayList<Lawnmower> mowList = new ArrayList<Lawnmower>();

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
		timer.setInitialDelay(5000);
		timer.start();
	}

	class DrawingPanel extends JPanel {
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
			g.drawString(sun, 170, 85);

			drawPlants(g);
		}

		void drawPlants(Graphics g) {
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					// if there is a plant on the tile, it'll display it
					if (board[y][x] != null) {
						g.drawImage(snowpea, lowX + (x * colW), lowY + (y * rowH), colW, rowH, null);
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
		// DEBUG
		// System.out.println(t + " " + level);
		t++;
		initializeZombies();
		triggerMower();
		//when the amount of zombies are 0, it increases the level and reinstates the zombies
		//TODO this is placeholder code until we figure out what will happen when the level is completed
		if(zCount < 0 && playerStatus) {
			level++;
			zCount = level*10;
		}
	}
	
	public void triggerMower() {
		for(int i = 0; i < mowList.size(); i++) {
			Lawnmower mower = mowList.get(i);
			for(int j = 0; j < zList.size(); j++) {
				Zombie zomb = zList.get(j);
				int lawnrow = i;
				//TODO change when we get the right height of the garden
				int zombrow = zomb.y*5/PANH;
				if(zombrow == lawnrow && zomb.x >= 60) {
					mower.triggered = true;
				}
			}
		}
		for(int i = 0; i < mowList.size(); i++) {
			Lawnmower mower = mowList.get(i);
			if(mower.triggered) {
				mower.x+=mower.speed;
				for(int j = 0; j < zList.size(); j++) {
					Zombie zomb = zList.get(j);
					//TODO, add a rect class to all other classes
//					if(mower.intersects(zomb)) zList.remove(zomb);
				}
				if(mower.x > PANW) {
					mowList.remove(mower);
				}
			}
		}
	}
	
	public void lawnMowerCreation() {
		
		for(int i = 0; i < 5; i++) {
			Lawnmower m = new Lawnmower();
			//TODO, change to the height of the garden instead of screen
			m.y = PANH*i/5;
			mowList.add(m);
			
			//TODO add image for the lawnmower
		}
	}
	
	public void initializeZombies() {
		
		//creates a zombie every 2 seconds
		if(t%500 == 0 && zCount >= 0) {
			
			//random number to figure out which type based on the level
			int type = (int)(Math.random()*level + 1);
			int row = (int)(Math.random()*5);
			Zombie c;
			
			//creates the zombies based on the type
			if(type == 1) {
				c = new BasicZ();
			} if(type == 2) {
				c = new FastZ();
				
			} else {
				c = new BruteZ();
			}
			c.x = PANW;
			c.y = PANH*row/5;
			zList.add(c);
			
			//decreases the zombie count when one is created
			zCount--;
		}
		
		//going through each zombie and moving them
		for(int i = 0; i < zList.size(); i++) {
			Zombie z = zList.get(i);
			z.x--;
			
			//if the zombie is dead, then it removes it from the list
			if(z.health <= 0) {
				zList.remove(z);
			}
		}
	}
}
