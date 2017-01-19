/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author Rohans
 */
public class Robotics {
  public static Color[][] pic;
	public static final boolean EDGES = true;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(WIDTH);
		BufferedImage b = (ImageIO.read(new File("testing.png")));
		boolean[][] w = EDGES?getEdges(b):getWhites(b);
		for (boolean[] b1 : w) {
			for (boolean bool : b1) {
				System.out.print(bool ? "H" : " ");
			}
			System.out.println();
		}
		GameFrame m = new GameFrame(w);
		for (;;) {
			m.repaint();
		}
	}
	static boolean[][] a;

	public static class GameFrame extends JFrame {

		public GameFrame(boolean[][] b) {
			super("Output");
			a = b;
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.add(new GamePanel());
			this.pack();
		}
	}

	public static class GamePanel extends JPanel {

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(Robotics.WIDTH, Robotics.HEIGHT);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.black);
			if(!EDGES){
			for (int i = 0; i < Robotics.WIDTH / META; i++) {
				for (int j = 0; j < Robotics.HEIGHT / META; j++) {
					if (a[i][j]) {

						g.fillRect(i * META, j * META, META, META);
					}
				}
			}
		}else{
						for (int i = 0; i < Robotics.WIDTH; i++) {
				for (int j = 0; j < Robotics.HEIGHT; j++) {
g.setColor(a[i][j]?Color.cyan:pic[i][j]);
					g.fillRect(i,j,1,1);
				}
			}	
			}
	}
	}

	/**
	 * The height of a meta pixel
	 */
	public static final int META = 8;

	/**
	 * Returns a boolean[][] of meta pixels where each square is true only if the
	 * square its on is "Mostly" white
	 *
	 * @param b
	 * @return the whites
	 */
	public static boolean[][] getWhites(BufferedImage b) {
		 pic = new Color[Robotics.WIDTH][Robotics.HEIGHT];

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				pic[i][j] = new Color(b.getRGB(i, j));
			}
		}
		boolean[][] whites = new boolean[WIDTH / META][HEIGHT / META];
		for (int i = 0; i < WIDTH / META; i++) {
			for (int j = 0; j < HEIGHT / META; j++) {
				int sum = 0;
				for (int k = 0; k < META; k++) {
					for (int l = 0; l < META; l++) {
						Color c = pic[i * META + k][j * META + l];
						sum += c.getBlue() + c.getGreen() + c.getRed();
					}
				}
				sum /= META * META * 3;
				//Hardcoded
				if (sum > 220) {
					whites[i][j] = true;
				}
			}
		}
		return whites;
	}

	public static boolean[][] getEdges(BufferedImage b) {
		pic = new Color[Robotics.WIDTH][Robotics.HEIGHT];

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				pic[i][j] = new Color(b.getRGB(i, j));
			}
		}
		boolean[][] edges = new boolean[WIDTH][HEIGHT];
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {

				Color o = pic[i][j];
				for (int k = -1; k < 2; k++) {
					for (int l = -1; l < 2; l++) {
						if (isInBounds(i + k, j + l)) {
							Color c = pic[i + k][j + l];
							if (Math.abs((c.getBlue() + c.getRed() + c.getGreen()) - (o.getBlue() + o.getRed() + o.getGreen())) > 100) {
edges[i][j]=true;
							}
						}
					}
				}
			}
		}
		return edges;
	}

	public static boolean isInBounds(int x, int y) {
		return x >= 0 && y >= 0 && y < HEIGHT && x < WIDTH;
	}
}

