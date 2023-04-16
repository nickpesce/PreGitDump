package net.minecraft.src;

import java.io.PrintStream;
import net.minecraft.client.Minecraft;

public class VexBowThread implements Runnable
{
    static boolean run = true;

    public VexBowThread()
    {
    }

    public void run()
    {
        while (run)
        {
            try
            {
                Thread.sleep(720);
            }
            catch (Exception exception)
            {
                System.out.println(exception);
            }
            finally
            {
            	System.out.println("STOP");
                Minecraft.theMinecraft.playerController.onStoppedUsingItem(Minecraft.theMinecraft.thePlayer);
                Minecraft.theMinecraft.playerController.sendUseItem(Minecraft.theMinecraft.thePlayer, Minecraft.theMinecraft.theWorld, Minecraft.theMinecraft.thePlayer.inventory.getCurrentItem());
            }
        }
    }

    public void Stop()
    {
        run = false;
    }

    public void Start()
    {
        run = true;

    }
}
