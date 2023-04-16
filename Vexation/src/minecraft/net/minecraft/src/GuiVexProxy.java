package net.minecraft.src;

import java.net.URI;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiVexProxy extends GuiScreen
{
    private GuiScreen parentScreen;
    protected String screenTitle;
    public static GuiTextField hostBox;
    public static GuiTextField portBox;
    private static String proxyHost = "";
    private static String proxyPort = "";
    String Error;

    public GuiVexProxy(GuiScreen guiscreen)
    {
        Error = "";
        parentScreen = guiscreen;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        hostBox.updateCursorCounter();
        portBox.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        screenTitle = stringtranslate.translateKey("\2475PROXY");
        hostBox = new GuiTextField(fontRenderer, width / 2 - 155, height / 6 + 48, 148, 20);
        hostBox.setText(proxyHost);
        hostBox.isFocused = true;
        hostBox.setMaxStringLength(128);
        portBox = new GuiTextField(fontRenderer, (width / 2 - 155) + 160, height / 6 + 48, 148, 20);
        portBox.setText(proxyPort);
        portBox.isFocused = false;
        portBox.setMaxStringLength(128);
        controlList.add(new GuiButton(1, width / 2 - 100, height / 6 + 172, stringtranslate.translateKey("gui.done")));
        controlList.add(new GuiButton(2, width / 2 - 100, height / 6 + 150, stringtranslate.translateKey("gui.cancel")));
        controlList.add(new GuiButton(3, width / 2 - 100, height / 6 + 128, stringtranslate.translateKey("Find Proxies")));
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (!guibutton.enabled)
        {
            return;
        }

        if (guibutton.id == 1)
        {
            proxyHost = hostBox.getText();
            proxyPort = portBox.getText();
            System.setProperty("socksProxyHost", proxyHost);
            System.setProperty("socksProxyPort", proxyPort);
            mc.displayGuiScreen(parentScreen);
        }

        if (guibutton.id == 2)
        {
            mc.displayGuiScreen(parentScreen);
        }

        if (guibutton.id == 3)
        {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

            try
            {
                URI uri = new URI("http://sockslist.net/");
                desktop.browse(uri);
            }
            catch (Exception exception)
            {
                Error = exception.toString();
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        hostBox.mouseClicked(i, j, k);
        portBox.mouseClicked(i, j, k);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        if (i == 1)
        {
            mc.displayGuiScreen(parentScreen);
        }

        if (c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }

        hostBox.textboxKeyTyped(c, i);
        portBox.textboxKeyTyped(c, i);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
        drawCenteredString(fontRenderer, Error, width / 2, 30, 0xff0000);
        drawString(fontRenderer, ":", (width / 2 - 163) + 160, height / 6 + 53, 52480);
        hostBox.drawTextBox();
        portBox.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
