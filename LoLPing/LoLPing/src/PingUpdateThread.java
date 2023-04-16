import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class PingUpdateThread implements Runnable
{

	LoLPing lp;
	Process pingProcess;
	BufferedReader bri;
	
	public PingUpdateThread(LoLPing lp)
	{
		this.lp = lp;
	}
	
	@Override
	public void run() 
	{
	    try {
	        String line;
	        pingProcess = Runtime.getRuntime().exec("ping " + lp.ip + " -t -w 5000");
	        bri = new BufferedReader(new InputStreamReader(pingProcess.getInputStream()));
	        while ((line = bri.readLine()) != null && lp.isRunning()) 
	        {
	        	if(line.startsWith("Reply"))
	        	{
	        		String ping = line.split(" ")[4].replace("time=", "").replace("ms", "");
	        		lp.setPing(ping);
	        	}
	        	else if(line.startsWith("Pinging"))
	        		lp.setPing("Connecting...");
	        	else
	        		lp.setPing("Lost Connection");
	        }
	        System.out.println("Thread stopped");
	      }
	      catch (Exception err) {
	        err.printStackTrace();
	      }
	}
	
	public void finalize()
	{
        try {
			bri.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        pingProcess.destroy();
        System.out.println("Closed");
	}
}
