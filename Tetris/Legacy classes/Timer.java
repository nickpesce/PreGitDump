
public class Timer {
	
	final Map map;
	ScoreBoard scoreBoard;
	public Timer(Map map, ScoreBoard sb)
	{
		this.map = map;
		this.scoreBoard = sb;
	}
	
	public void startTimer()
	{
		new Thread(new Runnable(){

			@Override
			public void run()
			{
				map.playing = true;
				while(map.playing)
				{
					try{
					Thread.sleep(Settings.speed);
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					map.update();
				}
			}
		
		}).start();
	}
}
