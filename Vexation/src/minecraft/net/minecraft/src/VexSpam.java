package net.minecraft.src;

import java.io.PrintStream;
import net.minecraft.client.Minecraft;

public class VexSpam implements Runnable
{
    String Message;
    int amount;

    VexSpam(String s, int i)
    {
        Message = null;
        amount = 0;
        Message = s;
        amount = i;
    }

    public void run()
    {
        for (int i = 0; amount > i; i++)
        {
            try
            {
                Thread.sleep(100L);
                System.out.println("spam");
                Minecraft.theMinecraft.thePlayer.sendChatMessage(Message);
            }
            catch (Exception exception)
            {
                System.out.println(exception);
            }
        }
    }
}
