import java.awt.*;
import javax.swing.*;

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;
	Snake snake;
	int tileSize = Settings.tileSize;
	int mapSize = Settings.mapSize;
	int[][] map = new int[mapSize][mapSize];
	int score = 0;
	MainMenu menu;
	boolean updating;
	boolean gameOver;
	Apple apple;

	public Map(MainMenu menu) {
		this.menu = menu;
		snake = new Snake(this);
		init();
	}

	public void init() {
		apple = new Apple(snake);
		updating = true;
		gameOver = false;
		//Restart and menu are in snake
		addKeyListener(snake);
		startUpdateThread();
	}

	public void startUpdateThread() {
		updating = true;
		Thread update = new Thread(new Runnable() {
			public void run() {
				while (updating) {
					try {
						Thread.sleep(Settings.speed);
					} catch (Exception e) {
						e.printStackTrace();
					}
					update();
				}
			}
		});
		update.start();
	}

	public void update() {
		snake.update();
		if(snake.checkGameOver())
			gameOver();
		// check for apple eaten
		if (snake.snakeLocation[snake.size - 1].getX() == apple.getX()
				&& snake.snakeLocation[snake.size - 1].getY() == apple.getY()) {
			apple.newApple();
			snake.grow(Settings.gpa);
			score += 500;
		}
		//score += 25;
		repaint();
	}

	public void gameOver() {
		updating = false;
		gameOver = true;
		setBackground(Color.BLACK);
		if(score >= Settings.highScoreValues[4])
		{
			new GuiHighScore(this, menu.frame);
		}
	}
	
	public void newGame()
	{
		gameOver = false;
		if(!updating)
		startUpdateThread();
		snake.reset();
		score = 0;
		apple.newApple();
		repaint();
	}

	public void openMainMenu() {
		//This can't be the best way to do this
		menu.frame.remove(this);
		menu.frame.add(menu);
		menu.repaint();
		//menu.frame.pack();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Font font = new Font("ARIAL", Font.PLAIN, 25);
		g.setFont(font);
		if (!gameOver) {
			for (int y = 0; y <= mapSize; y++) {
				for (int x = 0; x <= mapSize; x++) {
					for (int s = 0; s < snake.size; s++) {
						if (snake.snakeLocation[s].getX() == x
								&& snake.snakeLocation[s].getY() == y) {
							g.setColor(Color.BLUE);
							g.fillRect(x * tileSize, y * tileSize, tileSize,
									tileSize);
							break;
						} else {
							if (apple.getX() == x && apple.getY() == y)
								g.setColor(Color.RED);
							else
								g.setColor(Color.PINK);
							g.fillRect(x * tileSize, y * tileSize, tileSize,
									tileSize);
						}
					}
					g.setColor(Color.BLACK);
					//g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
				}
			}
			g.drawString("SCORE: " + score, 0, 25);
		} else {
			String go = "Game Over!";
			FontMetrics fm = g.getFontMetrics(font);
			g.setColor(Color.RED);
			g.drawString(go, (getWidth() / 2)
					- ((int) fm.getStringBounds(go, g).getWidth() / 2),
					getHeight() / 2);
			String scoreString = "Score: " + score;
			g.drawString(scoreString, (getWidth() / 2)
					- ((int) fm.getStringBounds(scoreString, g).getWidth() / 2),
					(getHeight() / 2)+ 50);
		}
	}
}