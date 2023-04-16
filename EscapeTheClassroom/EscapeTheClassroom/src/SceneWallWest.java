import java.io.IOException;

import javax.imageio.ImageIO;


public class SceneWallWest extends Scene{

	public static final int
	JERRY = 0,
	PRINTER = 1;
	public SceneWallWest(Game game) 
	{
		super(game);
		try {
			setBackground(ImageIO.read(getClass().getResourceAsStream("WallWest.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSurroundings(Game.SceneName.ceiling, Game.SceneName.north, Game.SceneName.center, Game.SceneName.south);
		addClickBox(351, 311,  80,  100, JERRY);
		addClickBox(51, 325,  80,  87, PRINTER);
	}

	@Override
	public
	void onClickBoxPress(int id) 
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case JERRY:
			getGame().switchScene(Game.SceneName.streamline);
			break;
		case PRINTER:
			getGame().switchScene(Game.SceneName.printer);
			break;
		}
	}

}
