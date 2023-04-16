import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
public class Tetris extends JFrame
{
	public Map map;
	public static void main(String[] args)
	{
		Tetris tetris = new Tetris();
		tetris.setUpFrame();
	}

	public void setUpFrame()
	{
		setTitle("Tetris");
		setLocationRelativeTo(null);
		getContentPane().setPreferredSize(new Dimension((Settings.tileSize * 10) + (Settings.tileSize*4) - 10, (Settings.tileSize * 20) - 10));
		pack();
		setVisible(true);
		setResizable(false);
		map = new Map(this);
		add(map);
		map.addOptions();
		map.requestFocusInWindow();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		repaint();
	}
}