import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SceneScanner extends Scene{

	public final static int
	PHONE_KEY = 0,
	OPEN = 1;
	
	private Item phoneKey;
	private BufferedImage openedImage;
	private boolean opened;
	
	public SceneScanner(Game game) 
	{
		super(game);
		addClickBox(275, 134, 75, 400, OPEN);
		addClickBox(400, 300, 50, 50, PHONE_KEY);
		setBackground("ScannerClosed.jpg");
		try 
		{
			openedImage = ImageIO.read(getClass().getResourceAsStream("ScannerOpen.jpg"));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		phoneKey = new Item(Item.PHONE_KEY);
		setSurroundings(Game.SceneName.east, Game.SceneName.east, Game.SceneName.east, Game.SceneName.east);
	}
	
	public void onClickBoxPress(int id)
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case PHONE_KEY:
			if(phoneKey != null)
			{
				getGame().getInventory().add(phoneKey);
				phoneKey = null;
			}
			break;
		case OPEN:
			if(!opened)
			{
				opened = true;
				setBackground(openedImage);
			}
			break;

		}
	}

	@Override
	public void render(Graphics g) 
	{
		super.render(g);
		if(phoneKey != null && opened)
			g.drawImage(phoneKey.getIcon(), 400, 300, 50, 50, null);
	}
}
