import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Item {

	public static final int
	S_KEY = 0,
	SKREWDRIVER = 1,
	PAPER = 2,
	CLOSET_KEY = 3,
	DOOR_KEY = 4,
	WIRING_INSTRUCTIONS = 5,
	PHONE_KEY = 6;
	
	private int id;
	private BufferedImage icon;
	
	public Item(int id) 
	{
		this.id = id;
		try{
			switch(id)
			{
			case S_KEY:
				icon = ImageIO.read(getClass().getResourceAsStream("SKey.png"));
				break;
			case SKREWDRIVER:
				icon = ImageIO.read(getClass().getResourceAsStream("ItemScrewdriver.jpg"));
				break;
			case PAPER:
				icon = ImageIO.read(getClass().getResourceAsStream("ItemPaper.png"));
				break;
			case CLOSET_KEY:
				icon = ImageIO.read(getClass().getResourceAsStream("ItemDoorKey.png"));
				break;
			case DOOR_KEY:
				icon = ImageIO.read(getClass().getResourceAsStream("ItemDoorKey.png"));
				break;
			case WIRING_INSTRUCTIONS:
				icon = ImageIO.read(getClass().getResourceAsStream("ComputerManual.jpg"));
				break;
			case PHONE_KEY:
				icon = ImageIO.read(getClass().getResourceAsStream("ItemDoorKey.png"));
				break;
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the type of item.
	 * @return Integer referring to one of the Item constants
	 */
	public int getId() 
	{
		return id;
	}

	public BufferedImage getIcon()
	{
		return icon;
	}

	/**
	 * Draws the icon at a specific x,y with a defined width and height. 
	 * Synonomous to g.drawImage(icon, x, y, width, height, null); 
	 * except when in debug mode when it adds the id# and green circle.
	 * @param g Graphics object
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawIcon(Graphics g, int x, int y, int width, int height) 
	{
		if(Game.DEBUG)
		{
			g.setColor(Color.GREEN);
			g.fillOval(x, y, width, height);
			g.drawString(""+id, x, y);
		}
		g.drawImage(icon, x, y, width, height, null);
	}
}
