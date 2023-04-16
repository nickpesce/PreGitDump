import java.util.*;

public class Apple {

	int x;
	int y;
	Random r;
	Snake snake;

	public Apple(Snake snake) {
		this.snake = snake;
		r = new Random();
		newApple();
	}

	public void newApple() {
		x = r.nextInt(Settings.mapSize);
		y = r.nextInt(Settings.mapSize);
		
		for (int i = 0; i < snake.snakeLocation.length; i++) {
			while(true)
			{
				if(snake.snakeLocation[i].getX() == x && snake.snakeLocation[i].getY() == y)
				{
					x = r.nextInt(Settings.mapSize);
					y = r.nextInt(Settings.mapSize);
				}
			else
				break;
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
