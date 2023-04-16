import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SceneMonitor extends Scene {

	private String pass;
	private String message;
	private boolean loggedIn, printed, errorPrinting;
	
	private static final int
	PRINT = 0;
	
	public SceneMonitor(Game game)
	{
		super(game);
		addClickBox(Game.WIDTH/2 - 50, Game.HEIGHT/2-25, 100, 50, PRINT);
		setSurroundings(Game.SceneName.center, Game.SceneName.center, Game.SceneName.keyboard, Game.SceneName.center);
		pass = "";
		message = "Enter Password";
	}

	@Override
	public void onClickBoxPress(int id)
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case LEFT:
		case RIGHT:
		case UP:
		case DOWN:
			pass = "";
			break;
		case PRINT:
			if(((ScenePrinter)getGame().getScenes().get(Game.SceneName.printer)).printManual())
			{
				errorPrinting = false;
				printed = true;
			}
			else
			{
				errorPrinting = true;
			}
			
			break;
		}
	}
	
	/**
	 * Defined characters are typed into the password field. 
	 * If the S key is not present, it will display a message instead. 
	 * Enter checks password and logs in.
	 */
	@Override
	public void keyPressed(int keyCode, char keyChar)
	{
		if(keyCode == KeyEvent.VK_ENTER)
		{
			if(pass.equals("password"))
			{
				loggedIn = true;
			}
			else
				message = "Incorrect Password. Please Try Again.";
			pass = "";
		}
		else if(keyCode == KeyEvent.VK_S && !((SceneKeyboard)(getGame().getScenes().get(Game.SceneName.keyboard))).hasSKey())
		{
			getGame().message("The keyboard is missing the \'S\' key!");
			return;
		}
		else if(keyCode == KeyEvent.VK_BACK_SPACE)
		{
			if(pass.length() > 0)
				pass = pass.substring(0, pass.length()-1);
		}
		else if(!(keyCode == KeyEvent.CHAR_UNDEFINED))
			pass += keyChar;
	}

	@Override
	public void render(Graphics g)
	{
		super.render(g);
		if(loggedIn)
		{
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("Mrs. Donlon's Computer", (Game.WIDTH/2)-(g.getFontMetrics().stringWidth("Mrs. Donlon's Computer")/2), 30);
			if(errorPrinting)
			{
				g.setColor(Color.RED);
				g.drawString("Error Printing!", (Game.WIDTH/2)-(g.getFontMetrics().stringWidth("Error Printing!")/2), 60);
			}else if(printed)
			{
				g.setColor(Color.GREEN);
				g.drawString("Print Successful!", (Game.WIDTH/2)-(g.getFontMetrics().stringWidth("Print Successful!")/2), 60);
			}
			g.setColor(Color.GREEN);
			g.fillRect(Game.WIDTH/2 - 50, Game.HEIGHT/2-25, 100, 50);
			g.setColor(Color.BLACK);
			g.drawString("PRINT", Game.WIDTH/2- (g.getFontMetrics().stringWidth("PRINT")/2), Game.HEIGHT / 2);
		}
		else
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("Mrs. Donlon's Computer", (Game.WIDTH/2)-(g.getFontMetrics().stringWidth("Mrs. Donlon's Computer")/2), 30);
			g.drawString(message, (Game.WIDTH/2)-(g.getFontMetrics().stringWidth(message)/2), 100);
			g.drawString("PASSWORD: __________", (Game.WIDTH/2)-(g.getFontMetrics().stringWidth("PASSWORD: __________")/2), 200);
			for(int i = 0; i < pass.length(); i++)
			{
				g.drawString("*", ((Game.WIDTH/2)-(g.getFontMetrics().stringWidth("__________") - g.getFontMetrics().stringWidth("PASSWORD: __________")/2))+i*8, 200);
			}
		}
	}
}
