import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class MainMenu extends JPanel {

	// Mainly to get rid of warning. Unique ID for class i think...
	private static final long serialVersionUID = 1L;
	JApplet frame;
	JButton bStart;
	JButton bSettings;
	BufferedImage pic;

	public MainMenu(JApplet frame) {
		this.frame = frame;
		init();
	}

	public void init() {
		setBackground(Color.GREEN);
		this.setLayout(null);
		setPreferredSize(new Dimension(500, 500));
		setVisible(true);
		try {
			pic = ImageIO.read(getClass().getResource("snake.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		bStart = new JButton("Start");
		bStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		bStart.setVisible(true);
		bStart.setSize(100, 30);
		bStart.setLocation(50, 100);
		add(bStart);

		bSettings = new JButton("Settings");
		bSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSettings();
			}
		});
		bSettings.setVisible(true);
		bSettings.setSize(100, 30);
		System.out.println(getWidth());
		bSettings.setLocation(getWidth()-150, 100);
		add(bSettings);
		repaint();
	}

	public void startGame() {
		frame.remove(this);
		Map map = new Map(this);
		map.setPreferredSize(new Dimension(
				Settings.tileSize * Settings.mapSize, Settings.tileSize
						* Settings.mapSize));
		frame.add(map);
		map.requestFocus();
		frame.validate();
		//frame.pack();
	}

	public void openSettings() {
		this.setVisible(false);
		SettingsMenu settings = new SettingsMenu(this);
		frame.add(settings);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//refresh frame width and buton position each repaint
		bSettings.setLocation(getWidth()-150, 100);
		Font font = new Font("ARIAL", Font.PLAIN, 60);
		String s = "SNAKE";
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics(font);
		g.setColor(Color.RED);
		g.drawString(s, (getWidth() / 2)
				- ((int) fm.getStringBounds(s, g).getWidth() / 2), 50);
		font = font.deriveFont(30f);
		g.setFont(font);
		fm = g.getFontMetrics(font);
		g.drawString("HIGH SCORES", (getWidth() / 2)
				- ((int) fm.getStringBounds("HIGH SCORES", g).getWidth() / 2),
				200);
		for (int i = 0; i < Settings.highScoreNames.length; i++) {
			if (Settings.highScoreNames[i] != null) {
				String score = i + 1 + ". " + Settings.highScoreNames[i] + "-"
						+ Settings.highScoreValues[i];
				g.drawString(score, (getWidth() / 2)
						- ((int) fm.getStringBounds(score, g).getWidth() / 2),
						i * 40 + 240);
			}
		}
		g.drawImage(pic, (getWidth()/2) - 200, 400, 333, 82, this);
	}
}
