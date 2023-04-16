package com.blubblub.gravity;

import android.graphics.Point;

public class Rectangle extends Shape
{

	int x, y, width, height;
	Point lowerLeft, lowerRight, upperLeft, upperRight;

	public Rectangle(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		upperLeft = new Point(x, y);
		lowerLeft = new Point(x, y+height);
		upperRight = new Point(x+width, y);
		lowerRight = new Point(x+width, y+height);
	}
	
	public boolean intersects(Rectangle r)
	{
		return
				lowerLeft.x < r.lowerRight.x &&
				lowerRight.x > r.lowerLeft.x &&
				lowerLeft.y < r.upperLeft.y &&
				upperLeft.y > r.lowerLeft.y;
	}
	
	public boolean intersects(Circle c)
	{
		if(contains(c.center.x, c.center.y))
			return true;
		
		int closestX = c.center.x;
		int closestY = c.center.y;
		
		if(c.center.x < lowerLeft.x)
			closestX = lowerLeft.x;
		else if(c.center.x > lowerRight.x)
			closestX = lowerRight.x;
		if(c.center.y < upperLeft.y)
			closestY = upperLeft.y;
		else if(c.center.y > lowerLeft.y)
			closestY = lowerLeft.y;
		return distance(c.center.x, c.center.y, closestX, closestY) < c.getRadius();
		
	}
	
	public boolean contains(int x, int y)
	{
		return 
				lowerLeft.x <= x && lowerRight.x >= x &&
				lowerLeft.y >= y && upperLeft.y <= y;
	}
	
	public static double distance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt(Math.pow((x2 - x1),2) + Math.pow((y2 - y1), 2));
	}
	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
		upperLeft = new Point(x, y);
		lowerLeft = new Point(x, y+height);
		upperRight = new Point(x+width, y);
		lowerRight = new Point(x+width, y+height);
	}
	public int getY() {
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
		upperLeft = new Point(x, y);
		lowerLeft = new Point(x, y+height);
		upperRight = new Point(x+width, y);
		lowerRight = new Point(x+width, y+height);
	}
	public int getWidth()
	{
		return width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public int getHeight() 
	{
		return height;
	}
	public void setHeight(int height)
	{
		this.height = height;
	}
}
