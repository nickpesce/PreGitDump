import javax.swing.*;

public class Game extends JApplet{

	static JFrame frame;
	public void init()
	{
		Settings.populateHighScores();
		MainMenu menu = new MainMenu(this);
		this.add(menu);
	}
	/*
	public static void main(String[] args)
	{

		Settings.populateHighScores();
		frame = new JFrame("SNAKE");
		MainMenu menu = new MainMenu(frame);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(false);
		frame.setResizable(false);
		frame.add(menu);
		frame.pack();
	}
	*/
	
}
