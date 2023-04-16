import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


public class ScoreBoard extends JPanel{

	/*public int score = 0;
	Map map;
	Font font;
	public ScoreBoard(Map map)
	{
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.setSize(Settings.tileSize * 4, Settings.tileSize * 20);
		this.setLocation(Settings.tileSize * 10, 0);
		this.map = map;
	}
	
	public void update()
	{
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Settings.tileSize*4, Settings.tileSize*4);
		if(font == null)
			font = new Font("ARIAL", Font.PLAIN, Settings.tileSize / 3);
		g.setFont(font);
		g.drawString("SCORE: " + score, 0, Settings.tileSize * 6);
		map.nextTetrino.draw(g);
	}
	*/
}
