import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SettingsMenu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	JButton bApply;
	JButton bCancel;
	MainMenu mainMenu;
	ArrayList<JTextField> tfArray = new ArrayList<JTextField>();

	public SettingsMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
		init();
	}

	public void init() {
		setPreferredSize(new Dimension(500, 500));
		setBackground(Color.GREEN);
		setLayout(null);

		for (int i = 0; i < Settings.optionString.length; i++) {
			JLabel label = new JLabel(Settings.optionString[i]);
			label.setVisible(true);
			label.setSize(300, 20);
			label.setLocation(5, (i * 30) + 50);
			add(label);

			JTextField tf = new JTextField();
			tf.setVisible(true);
			tf.setSize(100, 20);
			tf.setLocation(350, (i * 30) + 50);
			if(Settings.optionString[i].equals("Default Snake Length"))
				tf.setText(Integer.toString(Settings.defaultSnakeLegnth));
			if(Settings.optionString[i].equals("Map Size"))
				tf.setText(Integer.toString(Settings.mapSize));
			if(Settings.optionString[i].equals("Tile Size"))
				tf.setText(Integer.toString(Settings.tileSize));
			if(Settings.optionString[i].equals("Speed"))
				tf.setText(Integer.toString(Settings.speed));
			if(Settings.optionString[i].equals("Growth Per Apple"))
				tf.setText(Integer.toString(Settings.gpa));
			tfArray.add(tf);
			add(tf);
		}

		bApply = new JButton("Apply");
		bApply.setVisible(true);
		bApply.setLocation(350, 400);
		bApply.setSize(75, 30);
		bApply.addActionListener(this);
		add(bApply);

		bCancel = new JButton("Cancel");
		bCancel.setVisible(true);
		bCancel.setLocation(50, 400);
		bCancel.setSize(75, 30);
		bCancel.addActionListener(this);
		add(bCancel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bApply)) {
			this.setVisible(false);
			for (int i = 0; i < Settings.optionString.length; i++) {
				try {
					if (Settings.optionString[i].equals("Default Snake Length"))
						Settings.defaultSnakeLegnth = Integer.parseInt(tfArray.get(i).getText());
					else if (Settings.optionString[i].equals("Tile Size"))
						Settings.tileSize = Integer.parseInt(tfArray.get(i).getText());
					else if (Settings.optionString[i].equals("Map Size"))
						Settings.mapSize = Integer.parseInt(tfArray.get(i).getText());
					else if (Settings.optionString[i].equals("Speed"))
						Settings.speed = Integer.parseInt(tfArray.get(i).getText());
					else if (Settings.optionString[i].equals("Growth Per Apple"))
						Settings.gpa = Integer.parseInt(tfArray.get(i).getText());
				} catch (Exception E) {
					E.printStackTrace();
				}
			}
			mainMenu.setVisible(true);
			if((Settings.tileSize * Settings.mapSize) > 1000) {
				Settings.tileSize = 10;
				Settings.mapSize = 50;
				JOptionPane.showMessageDialog(null, "The product of the Map Size and Tile Size can not be greater than 1,000. They have been reset to default");
			}
		} else if (e.getSource().equals(bCancel)) {
			mainMenu.frame.remove(this);
			mainMenu.setVisible(true);
		}
	}
}
