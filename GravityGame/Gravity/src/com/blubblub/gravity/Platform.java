package com.blubblub.gravity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Platform implements Drawable, Updatable, Collidable{

	Element type;
	int x, y, width, height;
	Bitmap image;
	Game game;
	boolean visible = false;
	Rectangle AABB;
	
	public Platform(Game game, int x, int y, int width, Element type)
	{
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = 20;
		this.type = type;
		image = scaleBitmap(getBitmapByType(type));
		AABB = new Rectangle(x, y, width, height);
	}

	public void add()
	{
		game.addDrawable(this);
		game.addUpdatable(this);
		game.addCollidable(this);
	}
	
	public void remove()
	{
		game.removeDrawable(this);
		game.removeUpdatable(this);
		game.removeCollidable(this);
	}
	
	@Override
	public Bitmap getBitmap()
	{
		return image;
	}

	@Override
	public int getX()
	{
		return x;
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
	
	public Bitmap getBitmapByType(Element element)
	{
		switch(element)
		{
		case WATER:
			return BitmapFactory.decodeResource(game.getResources(), R.drawable.plain0);
		case AIR:
			return BitmapFactory.decodeResource(game.getResources(), R.drawable.plain2);
		case FIRE: 
			return BitmapFactory.decodeResource(game.getResources(), R.drawable.plain1);
		case EARTH:
			return BitmapFactory.decodeResource(game.getResources(), R.drawable.plain3);

			default:
				return null;
		}
	}

	private Bitmap scaleBitmap(Bitmap bm)
	{
		Matrix matrix = new Matrix();
		matrix.postScale((float)width/bm.getWidth(),(float)height/ bm.getHeight());
		return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),  matrix, false);
	}
	@Override
	public synchronized void update()
	{
		//TODO wtf did i do here? no collision checking while iterating... FIX
		/*
		if(game.speed >= game.ball.diameter)
		{
			int heightIterations = game.speed/(game.ball.diameter-1);
			for(int i = 0; i < heightIterations; i++)
			{
				y -= (game.ball.diameter-1);
			}
			for(int i = 0; i < game.speed%(game.ball.diameter-1); i++)
			{
				y--;
			}
		}else
		*/
		y-=game.speed;
		AABB.setY(y);
		if(!visible && y <= height)
		{
			visible = true;
		}
		if(y < -height)
		{
			remove();
		}
	}

	@Override
	public void onHit(Collidable c)
	{
		if(c instanceof Ball)
		{
			if(this.type.equals(Element.getOpposite(((Ball)c).getElement())))
			{
				this.remove();
			}else
			{
				//game.gameOver();
			}

		}
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
}
