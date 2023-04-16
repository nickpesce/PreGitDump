package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiVexHelp extends GuiScreen
{
    GuiScreen parentScreen;
    int startingLine;

    GuiVexHelp(GuiScreen guiscreen)
    {
        startingLine = 0;
        parentScreen = guiscreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.add(new GuiButton(1, width / 2 - 100, height - 40, stringtranslate.translateKey("gui.cancel")));
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        super.keyTyped(c, i);
    }

    /**
     * Called when the mouse is moved or a mouse button is released.  Signature: (mouseX, mouseY, which) which==-1 is
     * mouseMove, which==0 or which==1 is mouseUp
     */
    protected void mouseMovedOrUp(int i, int j, int k)
    {
        if (k == 0)
        {
            if (((Variables.consoleText.length - startingLine) + 3) * 9 > height - 50 && j >= height / 2)
            {
                startingLine += 2;
            }

            if (j <= height / 2)
            {
                startingLine -= 2;
            }

            if (startingLine <= -1)
            {
                startingLine = 0;
            }

            if (startingLine >= Variables.consoleText.length)
            {
                startingLine = Variables.consoleText.length - 1;
            }
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.id == 1)
        {
            mc.displayGuiScreen(parentScreen);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("HELP"), width / 2, 10, 0xffffff);
        drawCenteredString(fontRenderer, stringtranslate.translateKey("Click towards the top orbottom of the screen to scroll"), width / 2, height - 10, 191);
        drawGradientRect(2, 27, width - 2, height - 42, 0x66bf00bf, 0x6600bf00);
        int k = 0;

        for (int l = startingLine; l < Variables.consoleText.length; l++)
        {
            String as[] = Variables.consoleText[l].split(":");

            if (Variables.consoleText[l].startsWith("spam"))
            {
                as[0] = "spam:message:#";
                as[1] = " Spams the chat with (message) (#) times";
            }

            if ((k + 3) * 9 < height - 50)
            {
                drawString(fontRenderer, (new StringBuilder()).append("\247b").append(as[0]).append(" : \2477").append(as[1]).toString(), 5, (k + 3) * 9, 0xbfbfbf);
            }

            k++;
        }

        super.drawScreen(i, j, f);
    }
}
