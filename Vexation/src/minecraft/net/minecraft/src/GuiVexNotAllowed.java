package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiVexNotAllowed extends GuiScreen
{
    public GuiVexNotAllowed()
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
        InitButtons();
    }

    public void InitButtons()
    {
        controlList.add(new GuiButton(0, width / 2 - 28, height - 28, 75, 20, "\2472EXIT"));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    public void actionPerformed(GuiButton guibutton)
    {
        if (!guibutton.enabled)
        {
            return;
        }

        if (guibutton.id == 0)
        {
            mc.shutdown();
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Sorry...", width / 2, 20, 52480);
        drawCenteredString(fontRenderer, "You are not allowed to use Vexation!", width / 2, 50, 0xbf00bf);
        drawCenteredString(fontRenderer, "This Client is not registered for your username!", width / 2, 60, 0xbf00bf);
        super.drawScreen(i, j, f);
    }
}
