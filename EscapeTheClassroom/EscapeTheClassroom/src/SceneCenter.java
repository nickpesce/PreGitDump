
public class SceneCenter extends Scene {

	public static final int
	MONITOR = 0,
	KEYBOARD = 1;
	public SceneCenter(Game game) 
	{
		super(game);
		addClickBox(515, 100, 170, 127, MONITOR);
		addClickBox(515, 227, 170, 50, KEYBOARD);
		setBackground("Center.jpg");
		setSurroundings(Game.SceneName.ceiling, Game.SceneName.west, Game.SceneName.north, Game.SceneName.east);
	}

	@Override
	public void onClickBoxPress(int id) 
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case MONITOR:
			getGame().switchScene(Game.SceneName.monitor);
			break;
		case KEYBOARD:
			getGame().switchScene(Game.SceneName.keyboard);
			break;
		}
	}

}
