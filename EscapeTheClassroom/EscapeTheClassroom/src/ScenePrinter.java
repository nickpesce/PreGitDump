import java.awt.Color;
import java.awt.Graphics;


public class ScenePrinter extends Scene {

	private boolean paperLoaded, manualPrinted;
	private Item manual;
	public static final int
	PAPER_TRAY = 0,
	OUTPUT_TRAY = 1;
	
	public ScenePrinter(Game game) 
	{
		super(game);
		addClickBox(95, 200, 653, 318, PAPER_TRAY);
		addClickBox(375, 30, 125, 150, OUTPUT_TRAY);
		manual = new Item(Item.WIRING_INSTRUCTIONS);
		setSurroundings(Game.SceneName.west, Game.SceneName.west, Game.SceneName.west, Game.SceneName.west);
		setBackground("Printer.jpg");
	}

	@Override
	public void onClickBoxPress(int id)
	{
		super.onClickBoxPress(id);
		switch(id)
		{
		case OUTPUT_TRAY:
			if(manualPrinted && manual != null)
			{
				getGame().getInventory().add(manual);
				manual = null;
			}
			break;
		}
	}
	
	@Override
	public void itemDroppedInBox(Item item, int clickBoxId) 
	{
		super.itemDroppedInBox(item, clickBoxId);
		if(item.getId() == Item.PAPER && clickBoxId == PAPER_TRAY)
		{
			getGame().getInventory().remove(item);
			paperLoaded = true;
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		super.render(g);
		if(paperLoaded)
		{
			g.setColor(Color.GREEN);
		}else
		{
			g.setColor(Color.RED);
		}

		g.fillPolygon(new int[]{338, 420, 416, 330}, new int[]{226, 229, 279, 278}, 4);
		g.setColor(Color.BLACK);
		if(paperLoaded)
			g.drawString("Ready to Print", 337, 240);
		else
			g.drawString("Out of Paper", 337, 240);
		if(manualPrinted && manual != null)
		{
			g.drawImage(manual.getIcon(), 375, 30, 125, 150, null);
		}
	}
	
	public void loadPaper()
	{
		paperLoaded = true;
	}
	
	/**
	 * Attempts to print the computer manual.
	 * @return Boolean of success of print. Will fail if printer has no paper.
	 */
	public boolean printManual()
	{
		if(!paperLoaded)
			return false;
		paperLoaded = false;
		manualPrinted = true;
		return true;
	}
}
