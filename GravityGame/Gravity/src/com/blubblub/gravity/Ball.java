package com.blubblub.gravity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Ball implements Drawable, Collidable, Updatable{

	boolean visible = true;
	Bitmap[] images;
	int currentFrame;
	Game game;
	Element element;
	private int x, y, vx, vy, radius, diameter;
	Circle AABB;
	
	public Ball(Game game, int x, int y, int diameter) 
	{
		this.x = x;
		this.y = y;
		this.game = game;
		this.radius = diameter/2;
		this.diameter = diameter;
		element = Element.PLAIN;
		
		
		currentFrame = 0;
		images = new Bitmap[4];
		images[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(game.getResources(), R.drawable.plain0), diameter, diameter, false);
		images[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(game.getResources(), R.drawable.plain1), diameter, diameter, false);
		images[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(game.getResources(), R.drawable.plain2), diameter, diameter, false);
		images[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(game.getResources(), R.drawable.plain3), diameter, diameter, false);

		AABB = new Circle(x, y, radius);
	}

	public void add()
	{
		game.addCollidable(this);
		game.addDrawable(this);
		game.addUpdatable(this);
	}
	
	public void remove()
	{
		game.removeDrawable(this);
		game.removeUpdatable(this);
		game.removeCollidable(this);
	}
	@Override
	public void update()
	{
		setX(x + vx);
		setY(y+vy);
		if(element.equals(Element.WATER))
			currentFrame = 0;
		else if(element.equals(Element.FIRE))
			currentFrame = 1;
		else if(element.equals(Element.AIR))
			currentFrame = 2;
		else if(element.equals(Element.EARTH))
			currentFrame = 3;
		if(element.equals(Element.PLAIN))
		{
			if(currentFrame < 3)
				currentFrame++;
			else
				currentFrame = 0;
		}
	}

	@Override
	public void onHit(Collidable c) 
	{
	}

	@Override
	public Shape getAABB()
	{
		return AABB;
	}
	@Override
	public boolean collided(Collidable c) 
	{
		return AABB.intersects(c.getAABB());
	}

	@Override
	public Bitmap getBitmap() 
	{
		return images[currentFrame];
	}

	public void setElement(Element e)
	{
		this.element = e;
	}
	
	public Element getElement()
	{
		return element;
	}
	
	@Override
	public int getX() 
	{
		return x;
	}
	
	public void setX(int x)
	{
		if(x < 0)
			this.x = 0;
		else if(x+radius*2 > game.width)
			this.x = game.width - radius*2;
		else
			this.x = x;
		AABB.setX(this.x);
	}

	public void setY(int y)
	{
		this.y = y;
		AABB.setY(this.y);
	}
	
	@Override
	public int getY() 
	{
		return y;
	}

	@Override
	public boolean isVisible()
	{
		return visible;
	}

}
