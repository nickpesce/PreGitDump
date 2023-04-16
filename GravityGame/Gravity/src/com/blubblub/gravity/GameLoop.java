package com.blubblub.gravity;


public class GameLoop
{
	
	Game game;
	public static final int UPDATES_PER_SECOND = 60;
	public static final int SKIP_TICKS = 1000 / UPDATES_PER_SECOND;
	private boolean runFlag = false;

	
	public GameLoop(Game game)
	{
		this.game = game;
	}

	public void start()
	{
		new Thread(new Runnable(){
			@Override
			public void run() {
				GameLoop.this.run(.05);
			}}).start();
	}
	/**
	 * Begin the game loop
	 * 
	 * @param delta
	 *            time between logic updates (in seconds)
	 */
	private void run(double delta) {
		runFlag = true;
		// startup();
		// convert the time to seconds
		double nextTime = (double) System.nanoTime() / 1000000000.0;
		double maxTimeDiff = 0.5;
		int skippedFrames = 1;
		int maxSkippedFrames = 5;
		while (runFlag) {
			// convert the time to seconds
			double currTime = (double) System.nanoTime() / 1000000000.0;
			if ((currTime - nextTime) > maxTimeDiff)
				nextTime = currTime;
			if (currTime >= nextTime)
			{
				// assign the time for the next update
				nextTime += delta;
				//System.out.println("Update " + 1000.0 * (nextTime - currTime) + " Extra");
				game.update();
				if ((currTime < nextTime) || (skippedFrames > maxSkippedFrames))
				{
					//System.out.println("draw");
					game.surface.draw();
					skippedFrames = 1;
				} else 
				{
					skippedFrames++;
				}
			    }else
			    {
				// calculate the time to sleep
				int sleepTime = (int) (1000.0 * (nextTime - currTime));
				// sanity check
				if (sleepTime > 0) 
				{
					// sleep until the next update
					try
					{
						Thread.sleep(sleepTime);
					} catch (InterruptedException e){}
				}
			}
		}
		//shutdown();
	}
		    public void stop()
		    {
		        runFlag = false;
		    }

}
