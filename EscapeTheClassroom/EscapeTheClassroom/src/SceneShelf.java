
public class SceneShelf extends Scene {

	private final static int
	PAPER = 0;
	public SceneShelf(Game game) 
	{
		super(game);
		setBackground("Shelf.jpg");
		addClickBox(535, 396, 260, 40, PAPER);
		setSurroundings(Game.SceneName.north, Game.SceneName.north, Game.SceneName.north, Game.SceneName.north);
	}

	public void onClickBoxPress(int id)
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case PAPER:
			if(!getGame().inventoryContains(Item.PAPER))
				getGame().getInventory().add(new Item(Item.PAPER));
			break;
		}
	}
}
