import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
public class Tetrino
{
	//types
	public final static int
	I = 0,
	J = 1,
	L = 2,
	O = 3,
	S = 4,
	T = 5,
	Z = 6;

	//directions
	public final static int
	UP = 0,
	RIGHT = 1,
	DOWN = 2,
	LEFT = 3;

	int type;
	int direction;
	private int x, y = 0;
	//cords of squares
	java.util.List<Point> boxes = new ArrayList<Point>();
	Color color;
	Map map;

	public Tetrino(int type, int x, int y, Map map)
	{
		this.type = type;
		this.direction = UP;
		this.x = x;
		this.y = y;
		this.map = map;
		setUpShape();
		setColor();
	}

	public void rotate(boolean right)
	{
		if(right)
			this.direction++;
		else
			this.direction--;
		this.direction %= 4;
		updateRotation();
		/*
		java.util.List<Point> tempBoxes = new ArrayList<Point>();
		for(int i = 0; i < boxes.size(); i++)
		{
			if(type != O)
			{
				Point p = boxes.get(i);
				double[] pt = {p.getX(), p.getY()};
				AffineTransform.getRotateInstance(Math.toRadians(-90), boxes.get(1).getX(), boxes.get(1).getY()).transform(pt, 0, pt, 0, 1);
				int newX = (int)pt[0];
				int newY = (int) pt[1];
				tempBoxes.add(i, new Point(newX, newY));
			}
		}
		if(areBoxesValid(tempBoxes) && (type != O))
			boxes = tempBoxes;
			*/
	}

	public void updateRotation()
	{
		setUpShape();
		this.direction %= 4;
		java.util.List<Point> tempBoxes = new ArrayList<Point>();
		for(int i = 0; i < boxes.size(); i++)
		{
			if(type != O)
			{
				Point p = boxes.get(i);
				double[] pt = {p.getX(), p.getY()};
				AffineTransform.getRotateInstance(Math.toRadians(direction * -90), boxes.get(1).getX(), boxes.get(1).getY()).transform(pt, 0, pt, 0, 1);
				int newX = (int)pt[0];
				int newY = (int) pt[1];
				tempBoxes.add(i, new Point(newX, newY));
			}
		}
		if(areBoxesValid(tempBoxes) && (type != O))
	boxes = tempBoxes;
	}

	public boolean areBoxesValid(java.util.List<Point> boxes)
	{
		for(Point p : boxes)
		{
			if(p.getX() < 0 || p.getX() > 9 || p.getY() > 19)
				return false;
			for(Tetrino t : map.tetrinos)
			{
				for(Point p2 : t.boxes)
				{
					if(map.currentTetrino != t)
					{
						if((p.getX() - 1 == p2.getX() && p.getY() == p2.getY()) || (p.getX() + 1 == p2.getX() && p.getY() == p2.getY()) || (p.getY() + 1 == p2.getY() && (p.getX() == p2.getX())))
						{
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public void moveDown(int amount)
	{
		y += amount;
		for(int i = 0; i < boxes.size(); i++)
		{
			int pX = (int)boxes.get(i).getX();
			int pY = (int)boxes.get(i).getY();
			boxes.set(i, new Point(pX, pY+amount));
		}
	}

	public void moveLeft(int amount)
	{
		x -= amount;
		for(int i = 0; i < boxes.size(); i++)
		{
			int pX = (int)boxes.get(i).getX();
			int pY = (int)boxes.get(i).getY();
			boxes.set(i, new Point(pX-amount, pY));
		}
	}

	public void moveRight(int amount)
	{
		x += amount;
		for(int i = 0; i < boxes.size(); i++)
		{
			int pX = (int)boxes.get(i).getX();
			int pY = (int)boxes.get(i).getY();
			boxes.set(i, new Point(pX + amount, pY));
		}
	}

	public void setUpShape()
	{
		boxes.clear();
			switch(type)
			{
			case I:
				boxes.add(new Point(0 + x, 0 + y));
				boxes.add(new Point(0 + x, 1 + y));
				boxes.add(new Point(0 + x, 2 + y));
				boxes.add(new Point(0 + x, 3 + y));
				break;
			case J:
				boxes.add(new Point(1 + x, 0 + y));
				boxes.add(new Point(1 + x, 1 + y));
				boxes.add(new Point(1 + x, 2 + y));
				boxes.add(new Point(0 + x, 2 + y));
				break;
			case L:
				boxes.add(new Point(0 + x, 0 + y));
				boxes.add(new Point(0 + x, 1 + y));
				boxes.add(new Point(0 + x, 2 + y));
				boxes.add(new Point(1 + x, 2 + y));
				break;
			case O:
				boxes.add(new Point(0 + x, 0 + y));
				boxes.add(new Point(0 + x, 1 + y));
				boxes.add(new Point(1 + x, 0 + y));
				boxes.add(new Point(1 + x, 1 + y));
				break;
			case S:
				boxes.add(new Point(0 + x, 1 + y));
				boxes.add(new Point(1 + x, 0 + y));
				boxes.add(new Point(1 + x, 1 + y));
				boxes.add(new Point(2 + x, 0 + y));
				break;
			case T:
				boxes.add(new Point(0 + x, 0 + y));
				boxes.add(new Point(0 + x, 1 + y));
				boxes.add(new Point(0 + x, 2 + y));
				boxes.add(new Point(1 + x, 1 + y));
				break;
			case Z:
				boxes.add(new Point(0 + x, 0 + y));
				boxes.add(new Point(1 + x, 0 + y));
				boxes.add(new Point(1 + x, 1 + y));
				boxes.add(new Point(2 + x, 1 + y));
				break;
			}
		}
		public void setColor()
		{
			switch(type)
			{
			case I:
				color = Color.CYAN;
				break;
			case J:
				color = Color.BLUE;
				break;
			case L:
				color = new Color(255, 165, 0);
				break;
			case O:
				color = Color.YELLOW;
				break;
			case S:
				color = Color.GREEN;
				break;
			case T:
				color = Color.MAGENTA;
				break;
			case Z:
				color = Color.RED;
				break;
			}
		}
	public void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		updateRotation();
		//for(Point p : boxes)
		for(int i = 0; i < boxes.size(); i++)
		{
			Point p = boxes.get(i);
			int bX = (int) p.getX();
			int bY = (int) p.getY();
			Rectangle rect = new Rectangle((bX)*Settings.tileSize, (bY)*Settings.tileSize, Settings.tileSize, Settings.tileSize);
			g2d.setColor(color);
			g2d.fill(rect);
			g2d.setColor(Color.BLACK);
			g2d.draw(rect);
		}
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getWidth()
	{
		int maxX = 0;
		int minX = 0;

		for(Point p : boxes)
		{
			maxX = (int) Math.max(maxX, p.getX());
			minX = (int) Math.min(minX, p.getX());
		}
		return (maxX - minX) + 1;
	}

	public int getHeight()
	{
		int maxY = 0;
		int minY = 0;

		for(Point p : boxes)
		{
			maxY = (int) Math.max(maxY, p.getY());
			minY = (int) Math.min(minY, p.getY());
		}
		return (maxY - minY) + 1;
	}

	public int getDirection()
	{
		return direction;
	}

	public void setDirection(int newDirection)
	{
		direction = newDirection;
		updateRotation();
	}
	public void setLocation(int x, int y)
	{
		boxes.clear();
		this.x = x;
		this.y = y;
		setUpShape();
	}
}