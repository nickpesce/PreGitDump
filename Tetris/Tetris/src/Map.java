import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class Map extends JPanel implements ActionListener
{
	java.util.List<Tetrino> tetrinos = new ArrayList<Tetrino>();
	Tetris frame;
	LossScreen ls;
	public Tetrino nextTetrino;
	public Tetrino currentTetrino;
	Tetrino heldTetrino;
	Ghost ghost;
	boolean playing = true;
	int clearsInARow = 0;
	public int score = 0;
	javax.swing.Timer timer;
	JComboBox difficultySelector;
	JTextField tileSizeBox;
	Font font;

	public Map(Tetris frame)
	{
		this.frame = frame;
		this.setVisible(true);
		addTetrino();
		addKeyListener(new MyKeyListener(this));
		timer = new javax.swing.Timer(Settings.speed, this);
		timer.start();
		setLayout(null);
	}

	public void addOptions()
	{
		String[] difficulties = {"Easy" ,"Medium" , "Hard"};
		difficultySelector = new JComboBox(difficulties);
		difficultySelector.addActionListener(this);
		difficultySelector.setVisible(true);
		difficultySelector.setLocation(Settings.tileSize * 10, Settings.tileSize * 7);
		difficultySelector.setSize(100, 30);

		tileSizeBox = new JTextField();
		tileSizeBox.setSize(100, 30);
		tileSizeBox.setLocation(Settings.tileSize * 10, Settings.tileSize * 9);
		tileSizeBox.setVisible(true);
		tileSizeBox.addActionListener(this);
		add(difficultySelector);
		add(tileSizeBox);
	}

	public void addTetrino()
	{
		Random r = new Random();
		if(nextTetrino == null)
		{
			nextTetrino = new Tetrino(r.nextInt(7), 0, 0, this);
			nextTetrino.setLocation((3 - nextTetrino.getWidth()) + 10, 0);
		}
		nextTetrino.setLocation(4, 0);
		currentTetrino = nextTetrino;
		for(Point p1 : currentTetrino.boxes)
		{
			for(Tetrino t : tetrinos)
			{
				for(Point p2 : t.boxes)
				{
					if(p1.getX() == p2.getX() && (p1.getY() == p2.getY()))
					{
						displayLossScreen();
						return;
					}
				}
			}
		}
		tetrinos.add(nextTetrino);
		nextTetrino = new Tetrino(r.nextInt(7), 0, 0, this);
		//nextTetrino = new Tetrino(Tetrino.O, 0, 0, this);
		nextTetrino.setLocation(3 - nextTetrino.getWidth() + 10, 0);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//Draw Grid
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				g.setColor(Color.GRAY);
				g.fillRect(i*Settings.tileSize, j*Settings.tileSize, Settings.tileSize, Settings.tileSize);
				g.setColor(Color.BLACK);
				g.drawRect(i*Settings.tileSize, j*Settings.tileSize, Settings.tileSize, Settings.tileSize);
			}
		}

		//draw ghost
		if(ghost != null)
			ghost.draw(g);

		//Draw Tetrinos
		for(Tetrino tetrino : tetrinos)
		{
			tetrino.draw(g);
		}

		//SCOREBOARD
		g.setColor(Color.BLACK);
		g.fillRect(Settings.tileSize * 10, 0, Settings.tileSize * 4, Settings.tileSize * 20);
		g.setColor(Color.WHITE);
		g.fillRect(Settings.tileSize * 10, 0, Settings.tileSize*4, Settings.tileSize*4);
		if(font == null)
			font = new Font("ARIAL", Font.PLAIN, Settings.tileSize / 3);
		g.setFont(font);
		g.drawString("SCORE: " + score, Settings.tileSize * 10, Settings.tileSize * 6);
		nextTetrino.draw(g);
		if(heldTetrino != null)
			heldTetrino.draw(g);
	}

	public void update()
	{
		if(checkForBottomCollision(currentTetrino))
		{
			score += 20;
			if(checkAndClearRows())
				score += 100 * clearsInARow;
			addTetrino();
		}
		else
			currentTetrino.moveDown(1);
		ghost = new Ghost(currentTetrino);
		repaint();
	}

	public boolean isCollidedOnLeft()
	{
		for(Point p1 : currentTetrino.boxes)
		{
			if(p1.getX() == 0)
			{
				return true;
			}
			for(Tetrino t : tetrinos)
			{
				for(Point p2 : t.boxes)
				{
					if(currentTetrino != t)
					{
						if(p1.getX() - 1 == p2.getX() && p1.getY() == p2.getY())
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isCollidedOnRight()
	{
		for(Point p1 : currentTetrino.boxes)
		{
			if (p1.getX() == 9)
			{
				return true;
			}
			for(Tetrino t : tetrinos)
			{
				for(Point p2 : t.boxes)
				{
					if(currentTetrino != t)
					{
						if(p1.getX() + 1 == p2.getX() && p1.getY() == p2.getY())
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean checkForBottomCollision(Tetrino tetrino)
	{
		for(Point p1 : tetrino.boxes)
		{
			//compare to floor
			if(p1.getY() == 19)
			{
				return true;
			}

			//compare to other tetrinos
			for(Tetrino t : tetrinos)
			{
				for(Point p2 : t.boxes)
				{
					if(currentTetrino != t && p1.getY() + 1 == p2.getY() && (p1.getX() == p2.getX() || p1.getX() == p2.getX()))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean checkAndClearRows()
	{
		boolean rowFilled = false;
		//<Row #, # of boxes>
		HashMap<Integer, Integer> boxesPerRow = new HashMap<Integer, Integer>();
		for(Tetrino t : tetrinos)
		{
				for(Point p1 : t.boxes)
				{
					int currentNumOfBoxes = 0;
					if(boxesPerRow.get((int)p1.getY()) != null)
						currentNumOfBoxes = boxesPerRow.get((int)p1.getY());
					boxesPerRow.put((int) p1.getY(), currentNumOfBoxes + 1);
				}
		}

		for(int i = 0; i < 20; i++)
		{
			if(boxesPerRow.get(i) != null && boxesPerRow.get(i) == 10)
			{
				clearRow(i);
				shiftAllDownAbove(i);
				rowFilled = true;
			}
		}
		if(rowFilled)
			clearsInARow += 1;
		else
			clearsInARow = 0;
		return rowFilled;

	}

	private void clearRow(int row)
	{
		for(Tetrino t : tetrinos)
		{
				java.util.List<Integer> boxesToRemove = new ArrayList<Integer>();
				for(int i = 0; i < t.boxes.size(); i++)
				{
					Point p = t.boxes.get(i);
					if(p.getY() == row)
					{
						boxesToRemove.add(i);
					}
				}

				int offset = 0;
				for(int i = 0; i < boxesToRemove.size(); i++)
				{
					t.boxes.remove((int)boxesToRemove.get(i)-offset);
					offset++;
				}
		}
	}

	private void shiftAllDownAbove(int row)
	{
		for (Tetrino t : tetrinos)
		{
			for (Point p : t.boxes)
			{
				if ((int) p.getY() < row)
				{
					p.translate(0, 1);
				}
			}
		}
	}

	public void holdCurrent()
	{
		if(heldTetrino == null)
		{
			heldTetrino = currentTetrino;
			addTetrino();
		}
		else
		{
			Tetrino temp = currentTetrino;
			currentTetrino = heldTetrino;
			tetrinos.add(currentTetrino);
			heldTetrino = temp;
			currentTetrino.setLocation(4, 0);
		}
		heldTetrino.setLocation(10, 10);
		tetrinos.remove(heldTetrino);
		repaint();
	}

	public void displayLossScreen()
	{
		ls = new LossScreen(frame);
		timer.stop();
		frame.add(ls);
		frame.remove(this);
		frame.repaint();
	}

	public void replay()
	{
		tetrinos.clear();
		currentTetrino = null;
		nextTetrino = null;
		addTetrino();
		frame.remove(ls);
		frame.add(this);
		score = 0;
		timer.start();
		frame.repaint();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(timer))
		{
			update();
		}
		else if(e.getSource().equals(difficultySelector))
		{
			if(difficultySelector.getSelectedIndex() == 0)
			{
				System.out.println("Easy");
				timer.setDelay(500);
				Settings.speed = 1000;
			}
			else if(difficultySelector.getSelectedIndex() == 1)
			{
				System.out.println("Medium");
				timer.setDelay(250);
				Settings.speed = 500;
			}
			else if(difficultySelector.getSelectedIndex() == 2)
			{
				System.out.println("Hard");
				timer.setDelay(100);
				Settings.speed = 250;
			}
			displayLossScreen();
			replay();
			requestFocusInWindow();
		}
		else if(e.getSource().equals(tileSizeBox))
		{
			Settings.tileSize = Integer.parseInt(tileSizeBox.getText());
			difficultySelector.setLocation(Settings.tileSize * 10, Settings.tileSize * 7);
			tileSizeBox.setLocation(Settings.tileSize * 10, Settings.tileSize * 9);
			revalidate();
			repaint();
			frame.getContentPane().setPreferredSize(new Dimension((Settings.tileSize * 10) + (Settings.tileSize*4) - 10, (Settings.tileSize * 20) - 10));
			frame.pack();
			requestFocusInWindow();
		}
	}
}