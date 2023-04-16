import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SceneWallEast extends Scene {

	public static final int 
	AVIV = 0,
	SCANNER = 1;
	private boolean avivMode;
	private BufferedImage aviv, normalBackground;
	public SceneWallEast(Game game) 
	{
		super(game);
		try{
			normalBackground = ImageIO.read(getClass().getResourceAsStream("WallEast.jpg"));
			aviv = ImageIO.read(getClass().getResourceAsStream("Aviv.jpg"));

		}catch(IOException e)
		{
			e.printStackTrace();
		}
		setBackground(normalBackground);
		setSurroundings(Game.SceneName.ceiling, Game.SceneName.south, Game.SceneName.center, Game.SceneName.north);
		addClickBox(444, 213, 100, 100, AVIV);
		addClickBox(21, 293, 75, 40, SCANNER);
	}
	
	@Override
	public
	void onClickBoxPress(int id)
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case AVIV:
			avivMode = !avivMode;
			if(avivMode)
			{
				setBackground(aviv);
				getGame().message("Wild AVIV appeared!");
			}
			else
				setBackground(normalBackground);
			break;
		case SCANNER:
			getGame().switchScene(Game.SceneName.scanner);
			break;
		}
	}
}
