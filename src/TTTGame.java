/* Name: Blessing Babajide
 * NetID: bbabajid
 * Assignment: Project 3
 * Lab Section: MW 10:25 am
 * I did not collaborate with anyone on this assignment.
 */
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

public class TTTGame extends JFrame implements ActionListener {
	protected JLabel info = new JLabel("Player 1's Turn");
	protected JButton button1 = new JButton("New Game");
	protected JLabel p1wins = new JLabel("P1 Wins: ");
	protected JLabel p2wins = new JLabel("P2 Wins: ");
	protected JLabel draw = new JLabel("Draws   : ");
	protected int winner = 0;
	protected int p1c, p2c, drawc;
	protected int x, y,xp,yp;
	protected static int array[][] = new int[3][3];
	protected boolean player1 = true;

	public TTTGame() {
		super();
		setTitle("TicTacToe :)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480, 580);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		// Top panel:
		JPanel topPanel = new JPanel();
		add(topPanel);
		// New Game button: 
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New Game Initiated");
				reset();
				repaint();
			}});
		topPanel.add(button1);
		info.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		topPanel.add(info);
		// Statistics panel
		JPanel stats = new JPanel();
		stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
		stats.add(p1wins);
		stats.add(p2wins);
		stats.add(draw);
		topPanel.add(stats);
		// Bottom Panel for Grid
		DrawCanvas TTTcanvas = new DrawCanvas();
		add(TTTcanvas);
		repaint();
		pack();
	}
	//Class for Grid and Game play
	protected class DrawCanvas extends JPanel implements MouseListener {
		public DrawCanvas() {
			setPreferredSize(new Dimension(480,480));
			addMouseListener(this);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//Grid drawing
			g.drawLine(90, 390, 90, 90);
			g.drawLine(390,390,390,90);
			g.drawLine(90, 90, 390, 90);
			g.drawLine(90, 390, 390, 390);
			g.drawLine(190, 90, 190, 390);
			g.drawLine(290, 90, 290, 390);
			g.drawLine(90, 190, 390, 190);
			g.drawLine(90, 290, 390, 290);
			
			Font font = new Font("SanSerif",Font.ITALIC,25);
			g.setFont(font);
			//Drawing X and O's on the grid
			for(int x=0;x<3;x++){
				for(int y=0;y<3;y++){
					if(array[x][y]==1)  // draw an x
						g.drawString("X",90+50+90*x,150+90*y);
					else if(array[x][y]==2)  // "2" represents o 
						g.drawString("O", 90+50+90*x, 150+90*y);
			}}
		}
		@Override //mouse events
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mouseClicked(MouseEvent e) {
			//determining game play according to clicks
			if (checkWinner() > 0) {}
			else {
				xp = e.getX();
				yp = e.getY();
				System.out.println("Mouse clicked at " + xp + "," + yp);

				if (((xp < 90) || (yp < 90)) || ((xp > 390) || (yp >390 ))) { // out of bounds
					System.out.println("out");
					player1 = !player1;
					repaint();}

				else {
					if (xp > 90 && xp < 190)
						x = 0;
					else if (xp > 200 && xp < 290)
						x = 1;
					else if (xp > 300 && xp < 390) 
						x = 2;

					if (yp > 90 && yp < 190)
						y = 0;
					else if (yp > 200 && yp < 290)
						y = 1;
					else if (yp > 300 && yp < 390) 
						y = 2;

					if (array[x][y] == 1 || array[x][y] == 2) { //box already filled
						player1 = !(player1);
						repaint();
					} else {
						if (player1 == true) { // x played
							array[x][y] = 1;
						}
						if (player1 == false) { // o played
							array[x][y] = 2;
						}}
				}	//checks for wins and draws
				if (checkWinner() == 1) {	
					p1c++;
					p1wins.setText("P1 Wins: "+ Integer.toString(p1c));
					info.setText("P1 wins!");
				} else if (checkWinner() == 2) {
					p2c++;
					p2wins.setText("P2 Wins: "+ Integer.toString(p2c));
					info.setText("P2 wins!");
				} else if (checkDraw() == true) {
					info.setText("It's a draw!");
					drawc++;
					draw.setText("Draws   : "+drawc);
				} else {							//switching players
					if (player1 == true) {
						info.setText("Player 2's turn");
						player1 = !player1;
					}else{
						info.setText("Player 1's turn");
						player1 = !player1;
					}
				}
				repaint();
			}}}

	public  boolean checkDraw() {		//check if no win and all boxes are filled
		boolean completed = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (array[i][j] == 0)
					completed = false;}
		}
		
		return completed;
	}

	public int checkWinner() {			//compare rows/columns for win

		//horizontal
		for(int i=0;i<3;i++) {
			int a = array[i][0];
			int b = array[i][1];
			int c = array[i][2];

			if((a == b) && (b == c)&& a!=0) {
				winner = a;
			}}
		//vertical
		for(int i=0;i<3;i++) {
			int a = array[0][i];
			int b = array[1][i];
			int c = array[2][i];

			if((a == b) && (b == c) && a!=0) {
				winner = a;
			}}
		//diagonal 1
		int a = array[0][0];
		int b = array[1][1];
		int c = array[2][2];
		if((a == b) && (b == c) && a!=0) 
			winner = a;
		//diagonal 2
		a = array[0][2];
		b = array[1][1];
		c = array[2][0];
		if((a == b) && (b == c) && a!=0)
			winner = a;

		return winner;
	}

	public void reset() {				//reset 2d array,button
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				array[i][j] = 0;
		}
		winner =-1;
		player1 = true;
		info.setText("Player 1's turn");
	}

	public static void main(String[] args) {		//main method to set frame visible
		new TTTGame().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {       //just here because it eclipse was saying i have an error
	}}