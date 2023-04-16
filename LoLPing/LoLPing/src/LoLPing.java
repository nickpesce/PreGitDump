import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoLPing extends JFrame implements ItemListener, WindowListener, ActionListener{

	private JLabel pingLabel;
	private JLabel logo;
	private JLabel ipLabel;
	private JCheckBox toggleTray;
	private JTextField tfIp;
	
	private String ping;
	private boolean minimizeToTray;
	private Thread updateThread;
	private boolean running;
	String ip;
	
	private TrayIcon trayIcon;
	private MenuItem miExit;
	private SystemTray tray;
	private boolean inTray;
	
	public static void main(String[] ags)
	{
		new LoLPing(); 
	}
	
	public LoLPing()
	{
		super("LoLPing");
		setSize(600, 400);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		try {
			setIconImage(ImageIO.read(this.getClass().getResource("lollogo.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(null);
		addWindowListener(this);
		
		minimizeToTray = true;
		running = true;
		ip = "64.7.194.1";
		
		pingLabel = new JLabel();
		pingLabel.setForeground(Color.WHITE);
		pingLabel.setFont(new Font("ARIAL", Font.PLAIN, 30));
		pingLabel.setBounds(10, 290, 300, 100);
		
		ipLabel = new JLabel();
		ipLabel.setForeground(Color.WHITE);
		//ipLabel.setBackground(Color.WHITE);
		pingLabel.setFont(new Font("ARIAL", Font.PLAIN, 30));
		ipLabel.setBounds(0, 0, 30, 20);
		
		tfIp = new JTextField(ip);
		tfIp.addActionListener(this);
		tfIp.setBackground(Color.BLACK);
		tfIp.setForeground(Color.WHITE);
		tfIp.setBounds(20, 0, 100, 20);
		
		logo = new JLabel();
		logo.setIcon(new ImageIcon(this.getClass().getResource("lollogo.jpg")));
		logo.setBounds(0, 0, 600, 300);
		
		toggleTray = new JCheckBox("keep in tray", true);
		toggleTray.setBackground(null);
		toggleTray.setForeground(Color.WHITE);
		toggleTray.addItemListener(this);
		toggleTray.setBounds(490, 290, 200, 100);
		
		
		getContentPane().add(ipLabel);
		getContentPane().add(logo);
		getContentPane().add(tfIp);
		getContentPane().add(toggleTray);
		getContentPane().add(pingLabel);

		startUpdateThread();
		repaint();
	}
	
	public void startUpdateThread()
	{
		running = true;
		final PingUpdateThread updater = new PingUpdateThread(this);
		updateThread = new Thread(updater);
		updateThread.start();
		Runtime.getRuntime().addShutdownHook(new Thread() 
		{  
			public void run() 
			{  
			   System.out.println("calling from hook");  
			   updater.finalize(); 
			}
		});  
	}
	
	public void setPing(String ping)
	{
		this.ping = ping;
		pingLabel.setText(ping);
		if(inTray)
			trayIcon.setImage(getTrayImage());
	}

	public Image getTrayImage()
	{
		int size = tray.getTrayIconSize().width;
		Image icon = createImage(tray.getTrayIconSize().width, tray.getTrayIconSize().height);
		Graphics g = icon.getGraphics();
		String text;
		g.setFont(new Font("ARIAL", Font.PLAIN, (int)(size / 2.0)));
		try
		{
			int iPing = Integer.parseInt(ping);
			int colorNum = (iPing - 200);
			if(colorNum >= 0)
				g.setColor(new Color(colorNum, 0, 0 ));
			else
				g.setColor(new Color(0, colorNum * -2, 0 ));
			text = ping;
		}catch(Exception e)
		{
			text = "D:";
			g.setColor(Color.RED);
		}
		g.fillRect(0, 0, size, size);
		g.setColor(new Color(255 - g.getColor().getRed(), 255 - g.getColor().getGreen(), 255 - g.getColor().getBlue()));
		g.drawString(text, 2, size - 4);
		return icon;
	}
	
	public void addTrayIcon()
	{
		if(!SystemTray.isSupported())
			return;
		tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(getTrayImage(), "LoL Ping");
		PopupMenu popup = new PopupMenu();
		miExit = new MenuItem("Exit");
		miExit.addActionListener(this);
		popup.add(miExit);
		
		trayIcon.setPopupMenu(popup);
		trayIcon.addActionListener(this);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void removeTrayIcon()
	{
		tray.remove(trayIcon);
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) 
	{
		if(arg0.getSource().equals(toggleTray))
		{
			minimizeToTray = arg0.getStateChange() == ItemEvent.SELECTED;
			if(minimizeToTray)
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			else
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0){}
	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		if(minimizeToTray)
		{
			setVisible(false);
			addTrayIcon();
			inTray = true;
		}
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource().equals(trayIcon))
		{
			setVisible(true);
			if(inTray)
			{
				setVisible(true);
				removeTrayIcon();
				inTray = false;
			}
		}
		else if(arg0.getSource().equals(miExit))
		{
			System.exit(0);
		}
		else if(arg0.getSource().equals(tfIp))
		{
			ip = tfIp.getText();
			updateThread.stop();
			startUpdateThread();
		}
		
	}
}
