import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LossScreen extends JPanel implements ActionListener{
	Tetris frame;
	JButton replay;
	public LossScreen(Tetris frame)
	{
		setLayout(new GridBagLayout());
		this.frame = frame;
		setVisible(true);
		setSize(frame.getWidth(), frame.getHeight());
		replay = new JButton("Play Again");
		replay.setSize(200, 20);
		replay.setLocation((frame.getWidth()/2) - 100, 100);
		replay.setVisible(true);
		replay.addActionListener(this);
		add(replay);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		Font font = new Font("ARIAL", Font.PLAIN, 20);
		g.setFont(font);
		FontMetrics fm = this.getFontMetrics(font);
		g.drawString("GAME OVER", (int) ((getWidth()/2) - (fm.getStringBounds("GAME OVER", g).getWidth())/2), 20);
		g.drawString("You scored " + frame.map.score, (int) ((getWidth()/2) - (fm.getStringBounds("You scored " + frame.map.score, g).getWidth()/2)), 50);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(replay))
		{
			frame.map.replay();
			setVisible(false);
			frame.map.requestFocusInWindow();
		}
	}
}
