import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

public class Game implements MouseListener, KeyListener, MouseMotionListener, ComponentListener{

	public static final int WIDTH = 960, HEIGHT = 540;
	public int mouseX, mouseY;
	private JFrame frame;
	private GameCanvas canvas;
	private HashMap<SceneName, Scene> scenes = new HashMap<SceneName, Scene>();
	private GuiOverlay gui;
	private GameLoop gameLoop;
	private String loadingScene, timeString;
	private long startTime;
	public static final boolean DEBUG = false;
	
	public enum SceneName
	{
		north, east, south, west, ceiling, monitor, center, phone, keyboard, printer, streamline, wires, scanner, closet, shelf
	}
	
	private SceneName currentScene;
	private ArrayList<Item> inventory;
	public Item draggedItem, displayedItem;
	private ArrayList<String> messages;
	public SceneName sidePreview;
	
	public Game()
	{
		frame = new JFrame("Escape the Classroom");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new GameCanvas(this);
		canvas.setSize(WIDTH, HEIGHT);
		canvas.addMouseListener(this);
		canvas.addKeyListener(this);
		canvas.addMouseMotionListener(this);
		frame.add(canvas);
		frame.addComponentListener(this);
		frame.pack();		
		
		setFullScreen(true);
		
		inventory = new ArrayList<Item>();
		messages = new ArrayList<String>();
		gui = new GuiOverlay(this);
		gameLoop = new GameLoop(this);
		gameLoop.start();
		
		canvas.updateScale(frame);
		loadingScene = "North Wall";
		scenes.put(SceneName.north, new SceneWallNorth(this));
		loadingScene = "West Wall";
		scenes.put(SceneName.west, new SceneWallWest(this));
		loadingScene = "East Wall";
		scenes.put(SceneName.east, new SceneWallEast(this));
		loadingScene = "South Wall";
		scenes.put(SceneName.south, new SceneWallSouth(this));
		loadingScene = "Center Island";
		scenes.put(SceneName.center, new SceneCenter(this));
		loadingScene = "Ceiling";
		scenes.put(SceneName.ceiling, new SceneCeiling(this));
		loadingScene = "Computer Monitor";
		scenes.put(SceneName.monitor, new SceneMonitor(this));
		loadingScene = "Phone";
		scenes.put(SceneName.phone, new ScenePhone(this));
		loadingScene = "Keyboard";
		scenes.put(SceneName.keyboard, new SceneKeyboard(this));
		loadingScene = "Jerry";
		scenes.put(SceneName.streamline, new SceneStreamline(this));
		loadingScene = "Electronics";
		scenes.put(SceneName.wires, new SceneWires(this));
		loadingScene = "Scanner";
		scenes.put(SceneName.scanner, new SceneScanner(this));
		loadingScene = "Printer";
		scenes.put(SceneName.printer, new ScenePrinter(this));
		loadingScene = "Closet";
		scenes.put(SceneName.closet, new SceneCloset(this));
		loadingScene = "Shelf";
		scenes.put(SceneName.shelf, new SceneShelf(this));
		loadingScene = null;
		startTime = System.currentTimeMillis();
	}
	
	public void setFullScreen(boolean f)
	{
		if(frame.isDisplayable())
			frame.dispose();
		GraphicsDevice vc;
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = env.getDefaultScreenDevice();
		frame.setVisible(false);
		if(f)
		{
			frame.setUndecorated(true);//Title bars/scroll bars etc
			frame.setResizable(false);
			vc.setFullScreenWindow(frame);
		}else
		{
			frame.setUndecorated(false);
			frame.setVisible(true);
			frame.setResizable(true);
			vc.setFullScreenWindow(null);
		}
		canvas.requestFocusInWindow();

	}
	
	public void start()
	{
		switchScene(SceneName.north);
		addToMessageQueue("Welcome to \"Escape the Classroom\"\nClick this box to continue.");
		addToMessageQueue("The objective of the game is to get out of the room using \nwhatever you can find within it.");
		addToMessageQueue("Click on any of the edges of the screen to move in that\ndirection.");
		addToMessageQueue("Click on items to pick them up. Click on the item in your\ninventory to look at it closer");
		addToMessageQueue("Drag items out of your inventory to interact with parts of the\nroom. That's all. GLHF :D");
	}
	
	public void escape()
	{
		currentScene = null;
		long time = System.currentTimeMillis() - startTime;
		int minutes = (int) (time / 60000);
		time %= 60000;
		int seconds = (int) (time / 1000);
		time %= 1000;
		int ms = (int)time;
		
		timeString = minutes + " minutes " + seconds + " seconds " + ms + " ms";
	}
	
	public void switchScene(SceneName name)
	{
		if(currentScene != null)
			scenes.get(currentScene).onLeave();
		currentScene = name;
		scenes.get(name).onEnter();
		scenes.get(name).mouseMoved(mouseX, mouseY);
	}
	

	public boolean inventoryContains(int item)
	{
		for(Item i : inventory)
			if(i.getId() == item)
				return true;
		return false;
	}
	
	public void repaint()
	{

		if(scenes.get(currentScene) != null)
			canvas.render(scenes.get(currentScene), gui);
		else if(loadingScene != null)
		{
			canvas.render(GameCanvas.LOADING, loadingScene);
		}else if(timeString != null)
		{
			canvas.render(GameCanvas.VICTORY, timeString);
		}
	}
	
	public void clearMessages()
	{
		messages.clear();
	}
	
	public void message(String message)
	{
		clearMessages();
		addToMessageQueue(message);
	}
	
	public void addToMessageQueue(String message)
	{
		messages.add(message);
	}
	
	public void removeMessage()
	{
		if(messages.size() > 0)
			messages.remove(0);
	}
	
	public String getMessage()
	{
		if(messages.isEmpty())
			return null;
		return messages.get(0);
	}
	
	public ArrayList<Item> getInventory() 
	{
		return inventory;
	}
	
	public SceneName getCurrentScene() 
	{
		return currentScene;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		mouseX = (int) (e.getX()/canvas.getScaleX());
		mouseY = (int)(e.getY()/canvas.getScaleY());
		displayedItem = null;
		for(int i = 0; i < inventory.size(); i++)
		{
			if(new Rectangle(230+(i*50), Game.HEIGHT - 80, 50, 50).contains(mouseX, mouseY))
			{
				displayedItem = inventory.get(i);
				switch(displayedItem.getId())
				{
				case Item.PHONE_KEY:
				case Item.CLOSET_KEY:
					message("It is a key, but it's not for the exit");
					break;
				case Item.S_KEY:
					message("It is a \"S\" key removed from a keyboard");
					break;
				case Item.WIRING_INSTRUCTIONS:
					message("It seems to be a manual for a computer");
					break;
				case Item.SKREWDRIVER:
					message("It is your average regular skrewdriver");
					break;
				case Item.PAPER:
					message("It is just some plain computer paper");
					break;
				case Item.DOOR_KEY:
					message("It is the key to get out!\n:D");
					break;
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e)
	{		
		
	}

	public void mouseExited(MouseEvent e)
	{		
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		mouseX = (int) (e.getX()/canvas.getScaleX());
		mouseY = (int)(e.getY()/canvas.getScaleY());
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		mouseX = (int) (e.getX()/canvas.getScaleX());
		mouseY = (int)(e.getY()/canvas.getScaleY());
		if(Game.DEBUG)
			System.out.println(mouseX + " " + mouseY);
		if(currentScene != null)
		{
			scenes.get(currentScene).mouseMoved(mouseX, mouseY);
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		mouseX = (int) (e.getX()/canvas.getScaleX());
		mouseY = (int)(e.getY()/canvas.getScaleY());
		if(currentScene == null)
			return;
		scenes.get(currentScene).mousePressed(mouseX, mouseY);
		for(int i = 0; i < inventory.size(); i++)
		{
			if(new Rectangle(230+(i*50), Game.HEIGHT - 80, 50, 50).contains(mouseX, mouseY) && displayedItem == null)
			{

				draggedItem = inventory.get(i);
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		if(draggedItem != null && currentScene != null)
		{
			scenes.get(currentScene).itemDropped(mouseX, mouseY, draggedItem);
			draggedItem = null;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			if(displayedItem == null && draggedItem == null)
				System.exit(0);
			else
			{
				displayedItem = null;
				draggedItem = null;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_BACK_QUOTE)
		{
			setFullScreen(!frame.isUndecorated());
		}
		if(currentScene != null)
		{
			scenes.get(currentScene).keyPressed(e.getKeyCode(), e.getKeyChar());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public GameCanvas getCanvas()
	{
		return canvas;
	}

	public static void main(String[] args) 
	{
		Game g = new Game();
		g.start();
	}
	
	public HashMap<SceneName, Scene> getScenes()
	{
		return scenes;
	}

	public void setScenePreview(SceneName preview)
	{
		this.sidePreview = preview;
	}

	public JFrame getFrame() 
	{
		return frame;
	}

	@Override
	public void componentHidden(ComponentEvent e) {		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) 
	{
		canvas.updateScale(frame);
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

}
