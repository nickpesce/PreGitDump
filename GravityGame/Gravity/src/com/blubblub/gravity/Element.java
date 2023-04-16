package com.blubblub.gravity;

import java.util.Random;

public enum Element {
	WATER, FIRE, AIR, EARTH, PLAIN;
	
	public static Element rand()
	{
		switch(new Random().nextInt(4))
		{
		case 0: return WATER;
		case 1: return FIRE;
		case 2: return AIR;
		case 3: return EARTH;
		default: return null;
		}
	}
	
	public static Element rand(Element exclude)
	{
		Element result;
		while((result = rand()).equals(exclude)){System.out.println("chose: " + result + " but excluding because: " + exclude);}
		return result;
	}

	public static Element getOpposite(Element element)
	{
		switch(element)
		{
		case WATER:
			return FIRE;
		case FIRE:
			return WATER;
		case AIR:
			return EARTH;
		case EARTH:
			return AIR;
		case PLAIN:
			return null;
		}
		return null;
	}
}
