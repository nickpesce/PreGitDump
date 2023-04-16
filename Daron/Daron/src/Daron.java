
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
public class Daron extends JFrame implements ActionListener
{
	JFrame frame;
	boolean raveing = false;
	Image cat;
	JLabel catLabel;
	ImageIcon catIcon;
	JLabel taMeow;
	JButton bGo;
	JButton bStop;
	public static void main(String args[])
	{
		Daron daron = new Daron();
	}

	public Daron()
	{
		setUpGui();
	}

	public void setUpGui()
	{
		frame = new JFrame("Daron");
		frame.setVisible(true);
		frame.setSize(700, 700);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		catIcon = new ImageIcon("cat.jpg");
		catLabel = new JLabel(catIcon);
		catLabel.setVisible(true);
		catLabel.setLocation((700/2) - (350/2), (700/2) - (350/2));
		catLabel.setSize(350, 350);
		bGo = new JButton("GO");
		bGo.setBounds(100, 600, 100, 20);
		bGo.addActionListener(this);
		bStop = new JButton("STOP");
		bStop.setBounds(500, 600, 100,20);
		bStop.addActionListener(this);
		taMeow = new JLabel("MEOW");
		taMeow.setVisible(true);
		taMeow.setSize(300, 70);
		frame.add(bGo);
		frame.add(bStop);
		frame.getContentPane().add(taMeow);
		frame.getContentPane().add(catLabel);

		startRave();
 	}

 	public void startRave()
 	{
		raveing = true;
		Thread rave = new Thread(new Runnable(){
			public void run()
			{
				final Random r = new Random();
				while(raveing)
				{
					try
					{
						Thread.sleep(350);
					}
					catch(Exception e){}

					SwingUtilities.invokeLater(new Runnable()
					{
					public void run()
					{
						Font font = new Font("ARIAL", 0, r.nextInt(60)+1);
						int red = r.nextInt(256);
						int green = r.nextInt(256);
						int blue = r.nextInt(256);
						Color color = new Color(red, green, blue);
						Color oppColor = new Color(255 - red, 255 - green, 255 - blue);
						taMeow.setLocation(r.nextInt(700 - (font.getSize() * 4)), r.nextInt(550));
						taMeow.setBackground(null);
						taMeow.setForeground(oppColor);
						taMeow.setFont(font);
						frame.getContentPane().setBackground(color);
					}
					});
				}
			}
			});
			rave.start();
	}
 	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(bGo))
		{
			if(!raveing)
			startRave();
		}
		if(e.getSource().equals(bStop))
		{
			raveing = false;
		}
 	}
}