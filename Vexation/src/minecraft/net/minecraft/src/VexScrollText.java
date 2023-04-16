package net.minecraft.src;

import java.io.InputStream;
import net.minecraft.client.Minecraft;

public class VexScrollText implements Runnable
{
    private String Words;
    private int Y;
    private int X;
    private int Color;
    private GuiMainMenu gui;
    InputStream fontIs;
    private VexFont newVexFont;

    public VexScrollText(String s, int i, int j, GuiMainMenu guimainmenu)
    {
        X = 1;
        fontIs = (net.minecraft.src.VexFont.class).getResourceAsStream("/font/VexFont.ttf");
        newVexFont = new VexFont(Minecraft.theMinecraft, fontIs, 12);
        Y = i;
        Words = s;
        Color = j;
        gui = guimainmenu;
    }

    public void run()
    {
        do
        {
            try
            {
                Thread.sleep(20L);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            if (X > gui.width)
            {
                X = -newVexFont.getStringWidth(Words);
            }
            else
            {
                X++;
            }

            gui.VexScrollText(Words, X, Y, Color);
        }
        while (true);
    }
}
