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
	final static BufferedImage peashooter = loadImage("Photos/peashooter.png");
	final static BufferedImage potatomine = loadImage("Photos/potato-mine.png");
	final static BufferedImage snowpea = loadImage("Photos/snow-pea.png");
	final static BufferedImage sunflower = loadImage("Photos/sunflower.png");
	final static BufferedImage wallnut = loadImage("Photos/wall-nut.png");
	final static BufferedImage sun = loadImage("Photos/sun.png");

	static Plant board[][] = new Plant[5][9];

	static int t = 0;
	static int level = 1;
	int zCount = level*10; //amount of zombies in each level
	ArrayList<Zombie> zList = new ArrayList<Zombie>();

	/***** instance variables (global) *****/
	DrawingPanel panel = new DrawingPanel();

	// constructor
	MainGame() {
		createAndShowGUI();
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
			// gets background image after running try catch
			g.drawImage(bkg1, 0, 150, getWidth(), 650, null);
			g.drawImage(peashooter, 250, 0, 150, 150, null);
			g.drawImage(snowpea, 425, 0, 190, 150, null);
			g.drawImage(sunflower, 640, 0, 150, 150, null);
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
		initializeZombies(t, zList, level, zCount);
		
		//when the amount of zombies are 0, it increases the level and reinstates the zombies
		//TODO this is placeholder code until we figure out what will happen when the level is completed
		if(zCount < 0) {
			level++;
			zCount = level*10;
		}
	}
	
	
	public void initializeZombies(int t, ArrayList<Zombie> zList, int level, int zCount) {
		
		//creates a zombie every 2 seconds
		if(t%2000 == 0 && zCount >= 0) {
			
			//random number to figure out which type based on the level
			int type = (int)(Math.random()*level + 1);
			
			//creates the zombies based on the type
			if(type == 1) {
				Zombie c = new BasicZ();
				c.x = PANW;
				
				//which row to put the zombie based on random number
				int row = (int)(Math.random()*5);
				
				//TODO this splits the screen into 5 rows, but I need to know the coordinates of the rows in the image
				c.y = PANH*row/5;
				zList.add(c);
				
			} if(type == 2) {
				Zombie c = new FastZ();
				c.x = PANW;
				int row = (int)(Math.random()*5);
				c.y = PANH*row/5;
				zList.add(c);
				
			} else {
				Zombie c = new BruteZ();
				c.x = PANW;
				int row = (int)(Math.random()*5);
				c.y = PANH*row/5;
				zList.add(c);
			}
			
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
