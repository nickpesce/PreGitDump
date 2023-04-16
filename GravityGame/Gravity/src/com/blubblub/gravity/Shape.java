package com.blubblub.gravity;

public class Shape 
{
	
	public boolean intersects(Shape s)
	{
		if(this instanceof Circle && s instanceof Rectangle)
			return ((Rectangle)s).intersects((Circle)this);
		else if(this instanceof Rectangle && s instanceof Circle)
			return ((Rectangle)this).intersects((Circle)s);
		else if(this instanceof Rectangle)
			return ((Rectangle)s).intersects((Rectangle)this);
		else if(this instanceof Circle)
			return ((Circle)s).intersects((Circle)this);
		else
		{
			System.out.println("ASDF ERROR!!! Shape " + s + " is not a circle or rectangle... WTF?!?!?");
		}
			return false;
	}
}
