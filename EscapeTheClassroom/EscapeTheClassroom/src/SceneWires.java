import java.awt.Color;
import java.awt.Graphics;



public class SceneWires extends Scene {

	private int red, yellow, blue, green, orange, cyan, magenta, pink;
	private int connected;
	private final int numWires = 8;
	
	public static final int
	TERMINAL_RED = 0,
	TERMINAL_YELLOW = 1,
	TERMINAL_BLUE = 2,
	TERMINAL_GREEN = 3,
	TERMINAL_ORANGE = 4,
	TERMINAL_CYAN = 5,
	TERMINAL_MAGENTA = 6,
	TERMINAL_PINK = 7;
	
	public SceneWires(Game game)
	{
		super(game);
		connected = 0;
		red = yellow = blue = green = orange = cyan = magenta = pink = -1;
		for(int i = 0;  i < numWires; i++)
			addClickBox((i*100) + 130, 400, 10, 10, i);
		setSurroundings(Game.SceneName.streamline, Game.SceneName.streamline, Game.SceneName.streamline, Game.SceneName.streamline);
	}
	
	@Override
	public void onClickBoxPress(int id) 
	{
		super.onClickBoxPress(id);
		switch(id)
		{		
		case TERMINAL_RED:
			if(connected > numWires)
				break;
			if(red == -1)
			{
				red = connected;
				connected++;
				checkFinished();
			}
			else if(red == connected - 1)
			{
				connected --;
				red = -1;
			}
			break;	
			
		case TERMINAL_YELLOW:
			if(connected > numWires)
				break;
			if(yellow == -1)
			{
				yellow = connected;
				connected++;
				checkFinished();
			}
			else if(yellow == connected -1)
			{
				connected --;
				yellow = -1;
			}
			break;
			
		case TERMINAL_BLUE:
			if(connected > numWires)
				break;

			if(blue == -1)
			{
				blue = connected;
				connected++;
				checkFinished();
			}
			else if(blue == connected -1)
			{
				connected --;
				blue = -1;
			}
			break;
		case TERMINAL_GREEN:
			if(connected > numWires)
				break;

			if(green == -1)
			{
				green = connected;
				connected++;
				checkFinished();
			}
			else if(green == connected -1)
			{
				connected --;
				green = -1;
			}
			break;
		case TERMINAL_ORANGE:
			if(connected > numWires)
				break;
			if(orange == -1)
			{
				orange = connected;
				connected++;
				checkFinished();
			}
			else if(orange == connected -1)
			{
				connected --;
				orange = -1;
			}
			break;
		case TERMINAL_CYAN:
			if(connected > numWires)
				break;

			if(cyan == -1)
			{
				cyan = connected;
				connected++;
				checkFinished();
			}
			else if(cyan == connected -1)
			{
				connected --;
				cyan = -1;
			}
			break;
		case TERMINAL_MAGENTA:
			if(connected > numWires)
				break;

			if(magenta == -1)
			{
				magenta = connected;
				connected++;
				checkFinished();
			}
			else if(magenta == connected -1)
			{
				connected --;
				magenta = -1;
			}
			break;
		case TERMINAL_PINK:
			if(connected > numWires)
				break;

			if(pink == -1)
			{
				pink = connected;
				connected++;
				checkFinished();
			}
			else if(pink == connected -1)
			{
				connected --;
				pink = -1;
			}
			break;
		}
	}
	
	/**
	 * Checks if the order is correct, and if it is, turns computer on
	 */
	private void checkFinished()
	{
		if(isCorrect())
		{
			getGame().message("The computer is booting up!");
			((SceneStreamline)(getGame().getScenes().get(Game.SceneName.streamline))).setPowered(true);
		}
	}
	
	/**
	 * Checks if the wires are connected in the right order.
	 * @return Returns true if order is correct.
	 */
	private boolean isCorrect()
	{
		return red == 0 && green == 1 && yellow == 2 && pink == 3 && blue == 4&& magenta == 5 && orange == 6 && cyan == 7;
	}
	
	@Override
	public void onEnter()
	{
		super.onEnter();
		getGame().message("It seems as though someone tore out all the wires.");
	}
	
	@Override
	public void render(Graphics g) 
	{
		super.render(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.setColor(Color.GRAY);
		for(int i = 0; i < numWires; i++)
		{
			g.drawRect((i*100)+130, 200, 10, 10);
		}
		g.setColor(Color.WHITE);
		for(int i = 0; i < connected; i++)
		{
			g.fillRect((i*100)+130, 200, 10, 10);
		}
		g.setColor(Color.RED);
		g.drawOval(130, 400, 10, 10);
		if(red != -1)
		{
			g.fillOval(130, 400, 10, 10);
			drawWire(g, 135, 405, (red*100) + 135, 205);
		}
		
		g.setColor(Color.YELLOW);
		g.drawOval(230, 400, 10, 10);
		if(yellow != -1)
		{
			g.fillOval(230, 400, 10, 10);
			drawWire(g, 235, 405, (yellow*100) + 135, 205);
		}
		
		g.setColor(Color.BLUE);
		g.drawOval(330, 400, 10, 10);
		if(blue != -1)
		{
			g.fillOval(330, 400, 10, 10);
			drawWire(g, 335, 405, (blue*100) + 135, 205);
		}
		
		g.setColor(Color.GREEN);
		g.drawOval(430, 400, 10, 10);
		if(green != -1)
		{
			g.fillOval(430, 400, 10, 10);
			drawWire(g, 435, 405, (green*100) + 135, 205);
		}
		
		g.setColor(Color.ORANGE);
		g.drawOval(530, 400, 10, 10);
		if(orange != -1)
		{
			g.fillOval(530, 400, 10, 10);
			drawWire(g, 535, 405, (orange*100) + 135, 205);
		}
		
		g.setColor(Color.CYAN);
		g.drawOval(630, 400, 10, 10);
		if(cyan != -1)
		{
			g.fillOval(630, 400, 10, 10);
			drawWire(g, 635, 405, (cyan*100) + 135, 205);
		}
		
		g.setColor(Color.MAGENTA);
		g.drawOval(730, 400, 10, 10);
		if(magenta != -1)
		{
			g.fillOval(730, 400, 10, 10);
			drawWire(g, 735, 405, (magenta*100) + 135, 205);
		}
		
		g.setColor(Color.PINK);
		g.drawOval(830, 400, 10, 10);
		if(pink != -1)
		{
			g.fillOval(830, 400, 10, 10);
			drawWire(g, 835, 405, (pink*100) + 135, 205);
		}
	}
	
	/**
	 * Draws a thicker line(3 wide)
	 * @param g
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	private void drawWire(Graphics g, int x1, int y1, int x2, int y2)
	{
		g.drawLine(x1-1, y1, x2-1, y2);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x1+1, y1, x2+1, y2);
	}
}
