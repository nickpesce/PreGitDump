package net.minecraft.src;

import java.io.PrintStream;

public class VexVersionCheck implements Runnable
{
    public VexVersionCheck()
    {
    }

    public void run()
    {
        try
        {
            Thread.sleep(0x493e0L);
            Variables.CheckVersionAgain = true;
            run();
        }
        catch (Exception exception)
        {
            System.out.println((new StringBuilder()).append("Vex Version thread error: ").append(exception).toString());
        }
    }
}
