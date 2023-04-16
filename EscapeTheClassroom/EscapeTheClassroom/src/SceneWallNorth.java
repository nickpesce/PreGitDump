import java.io.IOException;

import javax.imageio.ImageIO;


public class SceneWallNorth extends Scene {

	public static final int
	DOOR = 0,
	PHONE = 1,
	SHELF = 2;
	
	private boolean doorLocked;
	public SceneWallNorth(Game game)
	{
		super(game);
		try {
			setBackground(ImageIO.read(getClass().getResourceAsStream("WallNorth.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		doorLocked = true;
		addClickBox(843, 144, 95, 333, DOOR);
		addClickBox(730, 273, 37, 48, PHONE);
		addClickBox(20, 20, 735, 120, SHELF);
		setSurroundings(Game.SceneName.ceiling, Game.SceneName.east, Game.SceneName.center, Game.SceneName.west);
	}

	
	@Override
	public void itemDroppedInBox(Item item, int clickBoxId)
	{
		super.itemDroppedInBox(item, clickBoxId);
		switch(clickBoxId)
		{
		case DOOR:
			if(item.getId() == Item.DOOR_KEY)
			{
				doorLocked = false;
				getGame().getInventory().remove(item);
				getGame().message("The key worked!\nThe door is open!");
			}
			break;
		}
	}
	
	@Override
	public void onClickBoxPress(int id) 
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case DOOR:
			if(doorLocked)
				getGame().message("The door is locked.");
			else
				getGame().escape();
			break;
		case PHONE:
			getGame().switchScene(Game.SceneName.phone);
			break;
		case SHELF:
			getGame().switchScene(Game.SceneName.shelf);
			break;
		}
		
	}
}
