package com.blubblub.gravity;

import android.graphics.Point;

public class Circle extends Shape
{
	private int x, y, radius;
	Point center;
	public Circle(int x, int y, int radius)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
		center = new Point(x + radius, y + radius);
	}
	
	public boolean intersects(Circle c)
	{
		return distance(center.x, center.y, c.center.x, c.center.y) <= radius + c.radius;
	}
	
	public boolean intersects(Rectangle r)
	{
		if(r.contains(center.x, center.y))
			return true;
		int closestX = center.x;
		int closestY = center.y;
		
		if(center.x < r.upperLeft.x)
			closestX = r.upperLeft.x;
		else if(center.x > r.lowerRight.x)
			closestX = r.lowerRight.x;
		if(center.y < r.upperLeft.y)
			closestY = r.upperLeft.y;
		else if(center.y > r.lowerLeft.y)
			closestY = r.lowerLeft.y;
		return distance(center.x, center.y, closestX, closestY) < radius;
	}
	
	public boolean contains(int x, int y)
	{
		return distance(x, y, center.x, center.y) <= radius;
	}
	
	//Repeated from rectangle. maybe change?
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
		center = new Point(x + radius, y + radius);
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
		center = new Point(x + radius, y + radius);
	}
	
	public int getRadius() 
	{
		return radius;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
		center = new Point(x + radius, y + radius);
	}
}
