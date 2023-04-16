package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiVexCredits extends GuiScreen
{
    private GuiScreen parentScreen;

    public GuiVexCredits(GuiScreen guiscreen)
    {
        parentScreen = guiscreen;
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
        controlList.add(new GuiButton(0, width / 2 - 28, height - 28, 75, 20, "\2472DONE"));
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
            mc.displayGuiScreen(parentScreen);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    public void keyTyped(char c, int i)
    {
        if (i == 1)
        {
            mc.displayGuiScreen(parentScreen);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "CREDITS", width / 2, 20, 52480);
        drawCenteredString(fontRenderer, "Main Coder: Diamondsplox", width / 2, 50, 0xbf00bf);
        drawCenteredString(fontRenderer, "Secondary Coder: Diggy2020", width / 2, 60, 0xbf00bf);
        drawCenteredString(fontRenderer, "Thanks to Eplision for a great reference!", width / 2, 70, 0xbf00bf);
        drawCenteredString(fontRenderer, "Thanks to Alias for a great reference for Eplision!", width / 2, 80, 0xbf00bf);
        super.drawScreen(i, j, f);
    }
}
