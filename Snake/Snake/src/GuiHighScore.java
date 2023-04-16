import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GuiHighScore extends JDialog {

	private static final long serialVersionUID = 1L;
	Map map;
	JTextField tfName;
	JLabel lText;
	JButton bSubmit;
	Font font;
	boolean keyTyped = false;

	public GuiHighScore(Map map, JApplet frame) {
		super();
		this.map = map;
		init();
	}

	public void init() {
		toFront();
		setTitle("NEW HIGH SCORE!");
		setBackground(Color.RED);
		setVisible(true);

		font = new Font("ARIAL", Font.PLAIN, 20);

		lText = new JLabel("You placed " + getPlace(map.score)
				+ "\n with a score of " + (/*
											 * im not sure why i have to do
											 * this, but the score displayed
											 * here is always too low
											 */map.score + 25));
		lText.setFont(font);
		lText.setForeground(Color.RED);
		lText.setVisible(true);

		tfName = new JTextField("Name");
		tfName.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (!keyTyped) {
					keyTyped = true;
					tfName.setText("");
				}
			}
		});
		
		bSubmit = new JButton("Submit");
		bSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.newHighScore(tfName.getText(), map.score);
				dispose();
				map.menu.repaint();
			}
		});

		add(tfName, BorderLayout.CENTER);
		tfName.requestFocus();
		add(bSubmit, BorderLayout.SOUTH);
		add(lText, BorderLayout.NORTH);
		pack();
		setLocation((map.getWidth() / 2) - (getWidth() / 2), map.getHeight()
				/ 2 - (getHeight() / 2));
	}

	public int getPlace(int score) {
		for (int i = 0; i < Settings.highScoreValues.length; i++) {
			if (score >= Settings.highScoreValues[i]) {
				return i + 1;
			}
		}
		return 0;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
}
