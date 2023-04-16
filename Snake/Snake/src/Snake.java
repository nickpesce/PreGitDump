import java.awt.event.*;
import java.util.*;

public class Snake implements KeyListener {

	int size;
	int extra;
	Point[] snakeLocation;
	String direction;
	String nextDirection;
	Map map;

	public Snake(Map map) {
		this.map = map;
		init();
	}

	public void init() {
		size = Settings.defaultSnakeLegnth;
		extra = 0;
		nextDirection = "down";
		snakeLocation = new Point[size];
		for (int i = 0; i < size; i++) {
			snakeLocation[i] = new Point(Settings.mapSize / 2, i);
		}
		update();
	}

	public void update() {
		if (extra > 0) {
			extra--;
			Point[] newSnakeLocation = Arrays.copyOf(snakeLocation, size + 1);
			for (int i = 0; i < size; i++) {
				newSnakeLocation[i + 1] = snakeLocation[i];
			}
			snakeLocation = newSnakeLocation;
			snakeLocation[0] = new Point(snakeLocation[1].getX(),
					snakeLocation[1].getY());
			size++;

		}
		direction = nextDirection;
		moveForward();
	}

	public void moveUpOne() {
		snakeLocation[size - 1].setY(snakeLocation[size - 1].getY() - 1);
	}

	public void moveDownOne() {
		snakeLocation[size - 1].setY(snakeLocation[size - 1].getY() + 1);
	}

	public void moveLeftOne() {

		snakeLocation[size - 1].setX(snakeLocation[size - 1].getX() - 1);
	}

	public void moveRightOne() {

		snakeLocation[size - 1].setX(snakeLocation[size - 1].getX() + 1);
	}

	public void moveForward() {

		for (int i = 0; i < size; i++) {
			if (snakeLocation[i] == null) {
				System.out.println(i);
				snakeLocation[i].setPosition(snakeLocation[i + 1].getX(),
						snakeLocation[i + 1].getY());
			}
			if (i + 1 < size)
				snakeLocation[i].setPosition(snakeLocation[i + 1].getX(),
						snakeLocation[i + 1].getY());
			else {
				if (direction.equals("up"))
					snakeLocation[i].setPosition(snakeLocation[i].getX(),
							snakeLocation[i].getY() - 1);
				else if (direction.equals("down"))
					snakeLocation[i].setPosition(snakeLocation[i].getX(),
							snakeLocation[i].getY() + 1);
				else if (direction.equals("left"))
					snakeLocation[i].setPosition(snakeLocation[i].getX() - 1,
							snakeLocation[i].getY());
				else if (direction.equals("right"))
					snakeLocation[i].setPosition(snakeLocation[i].getX() + 1,
							snakeLocation[i].getY());

			}
		}
	}

	public boolean checkGameOver() {
		for (int i = 0; i < snakeLocation.length; i++) {
			if (snakeLocation[i].getY() < 0
					|| snakeLocation[i].getY() >= Settings.mapSize
					|| snakeLocation[i].getX() < 0
					|| snakeLocation[i].getX() >= Settings.mapSize)
				return true;
			// check if snake is on snake
			for (int j = 0; j < snakeLocation.length; j++) {
				if (i != j
						&& snakeLocation[i].getX() == snakeLocation[j].getX()
						&& snakeLocation[i].getY() == snakeLocation[j].getY())
					return true;
			}
		}
		return false;
	}

	@Deprecated
	public boolean canMoveForward() {
		if (direction.equals("up") && (snakeLocation[size - 1].getY() - 1) < 0) {
			return false;
		}

		if (direction.equals("down")
				&& (snakeLocation[size - 1].getY() + 1) > 50) {
			return false;
		}

		if (direction.equals("left")
				&& (snakeLocation[size - 1].getX() - 1) < 0) {
			return false;
		}

		if (direction.equals("right")
				&& (snakeLocation[size - 1].getX() + 1) > 50) {
			return false;
		}

		for (int i = 0; i < size; i++) {
			if (direction.equals("up")
					&& ((snakeLocation[size - 1].getY() - 1 == snakeLocation[i]
							.getY()))
					&& ((snakeLocation[size - 1].getX() == snakeLocation[i]
							.getX())))
				return false;
			if (direction.equals("down")
					&& ((snakeLocation[size - 1].getY() + 1 == snakeLocation[i]
							.getY()))
					&& ((snakeLocation[size - 1].getX() == snakeLocation[i]
							.getX())))
				return false;
			if (direction.equals("left")
					&& ((snakeLocation[size - 1].getY() == snakeLocation[i]
							.getY()))
					&& ((snakeLocation[size - 1].getX() - 1 == snakeLocation[i]
							.getX())))
				return false;
			if (direction.equals("right")
					&& ((snakeLocation[size - 1].getY() == snakeLocation[i]
							.getY()))
					&& ((snakeLocation[size - 1].getX() + 1 == snakeLocation[i]
							.getX())))
				return false;

		}
		return true;
	}

	public void grow(int length) {
		extra += length;
	}

	public void reset() {
		init();
	}

	// KEY LISTENERS
	@Override
	public void keyReleased(KeyEvent k) {
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

	@Override
	public void keyPressed(KeyEvent k) {
		switch (k.getKeyCode()) {
		case KeyEvent.VK_UP:
			if (!direction.equals("down")) {
				nextDirection = "up";
			}
			break;
		case KeyEvent.VK_DOWN:
			if (!direction.equals("up")) {
				nextDirection = "down";
			}
			break;
		case KeyEvent.VK_LEFT:
			if (!direction.equals("right")) {
				nextDirection = "left";
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (!direction.equals("left")) {
				nextDirection = "right";
			}
			break;
		case KeyEvent.VK_SPACE:
				map.newGame();
			break;
		case KeyEvent.VK_ENTER:
				map.newGame();
			break;
		case KeyEvent.VK_M:
			if(!map.gameOver)
				map.gameOver();
				map.openMainMenu();
		}
	}
}

class Point {
	int x = 0;
	int y = 0;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
