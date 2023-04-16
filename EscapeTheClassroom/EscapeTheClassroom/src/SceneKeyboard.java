import java.awt.Color;
import java.awt.Graphics;


public class SceneKeyboard extends Scene {

	private boolean hasSKey;
	public static final int 
	S_KEY = 0;
	public SceneKeyboard(Game game)
	{
		super(game);
		setBackground("Keyboard.jpg");
		addClickBox(138, 244, 44, 38, S_KEY);
		setSurroundings(Game.SceneName.center, Game.SceneName.center, Game.SceneName.center, Game.SceneName.center);
	}

	@Override
	public void onClickBoxPress(int id)
	{		
		super.onClickBoxPress(id);
	}
	
	@Override
	public void itemDroppedInBox(Item item, int clickBoxId) 
	{
		super.itemDroppedInBox(item, clickBoxId);
		if(item.getId()==Item.S_KEY && clickBoxId == S_KEY)
		{
			getGame().getInventory().remove(item);
			hasSKey = true;
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		super.render(g);
		if(!hasSKey)
		{
			g.setColor(Color.BLACK);
			g.fillRect(138, 244, 44, 38);
		}
	}
	
	public boolean hasSKey()
	{
		return hasSKey;
	}

}
