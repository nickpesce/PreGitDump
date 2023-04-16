import java.awt.Graphics;


public class SceneCloset extends Scene {

	private Item screwdriver;
	
	private static final int
	SCREWDRIVER = 0;
	
	public SceneCloset(Game game) 
	{
		super(game);
		setBackground("Closet.jpg");
		addClickBox(895, 440, 45, 23, SCREWDRIVER);
		setSurroundings(Game.SceneName.south, Game.SceneName.south, Game.SceneName.south, Game.SceneName.south);
		screwdriver = new Item(Item.SKREWDRIVER);
	}
	
	@Override
	public void onClickBoxPress(int id)
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case SCREWDRIVER:
			if(screwdriver != null)
			{
				getGame().getInventory().add(screwdriver);
				screwdriver = null;
			}
			break;
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		super.render(g);
		if(screwdriver != null)
			g.drawImage(screwdriver.getIcon(), 895, 440, 45, 23, null);
	}

}
