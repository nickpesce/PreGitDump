
public class GameLoop {

	private Game game;
	private boolean running;
	
	public GameLoop(Game game) 
	{
		this.game = game;
	}

	/**
	 * Starts the gameloop. Will run at 60tps.
	 */
	public void start()
	{
		running = true;
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				while(running)
				{
					long s = System.currentTimeMillis();
					game.repaint();
					if(game.getCurrentScene() != null)
						game.getScenes().get(game.getCurrentScene()).update();
					try {
						Thread.sleep(Math.max(16 - (s - System.currentTimeMillis()), 0));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void stop()
	{
		running = false;
	}
}
