package net.minecraft.src;

import java.io.PrintStream;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiNameChanger extends GuiScreen
{
    private GuiScreen parentScreen;
    public static GuiTextField serverTextField;
    private GuiButton create;
    private GuiButton setdefault;
    private GuiTextField namechange;
    public static String cname;

    public GuiNameChanger(GuiScreen guiscreen)
    {
        parentScreen = guiscreen;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        serverTextField.updateCursorCounter();
        namechange.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(1, width / 2 - 100, height / 2 + 82, stringtranslate.translateKey("gui.cancel")));
        controlList.add(new GuiButton(4, width / 2 - 100, height / 2 + 38, stringtranslate.translateKey("Play")));
        controlList.add(new GuiButton(5, width / 2 - 100, height / 2 + 16, stringtranslate.translateKey("Quick Connect")));
        controlList.add(new GuiButton(6, width / 2 - 100, height / 2 + 60, stringtranslate.translateKey("ALTS!")));
        GameSettings _tmp = mc.gameSettings;
        String s = GameSettings.lastServer.replaceAll("_", ":");
        ((GuiButton)controlList.get(1)).enabled = s.length() > 0;
        ((GuiButton)controlList.get(2)).enabled = s.length() > 0;
        serverTextField = new GuiTextField(fontRenderer, width / 2 - 100, (height / 4 - 10) + 50 + 18, 200, 20);
        serverTextField.setText(s);
        serverTextField.isFocused = true;
        serverTextField.setMaxStringLength(128);
        String s1 = mc.session.username;

        if (cname != null)
        {
            s1 = cname;
        }

        namechange = new GuiTextField(fontRenderer, width / 2 - 100, height / 2 - 25, 200, 20);
        namechange.setText(s1);
        namechange.setMaxStringLength(16);

        if (namechange.getText().equals(mc.session.username))
        {
            ((GuiButton)controlList.get(1)).enabled = false;
            ((GuiButton)controlList.get(2)).enabled = false;
        }

        if (Variables.reconnect)
        {
            GameSettings _tmp1 = mc.gameSettings;
            GameSettings.lastServer = s.replaceAll(":", "_");
            mc.gameSettings.saveOptions();
            String as[] = s.split(":");

            if (s.startsWith("["))
            {
                int i = s.indexOf("]");

                if (i > 0)
                {
                    String s2 = s.substring(1, i);
                    String s3 = s.substring(i + 1).trim();

                    if (s3.startsWith(":") && s3.length() > 0)
                    {
                        s3 = s3.substring(1);
                        as = new String[2];
                        as[0] = s2;
                        as[1] = s3;
                    }
                    else
                    {
                        as = new String[1];
                        as[0] = s2;
                    }
                }
            }

            if (as.length > 2)
            {
                as = new String[1];
                as[0] = s;
            }

            Variables.reconnect = false;
            mc.displayGuiScreen(new GuiConnecting(mc, as[0], as.length <= 1 ? 25565 : Integer.parseInt(as[1])));
        }
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
            mc.displayGuiScreen(parentScreen);
        }
        else if (guibutton.id == 0)
        {
            String s = namechange.getText().trim();
            mc.session.username = s;

            if (mc.session.username == Variables.username)
            {
                Variables.nopaidcheck = false;
            }
            else
            {
                Variables.nopaidcheck = true;
            }

            String s3 = serverTextField.getText().trim();
            GameSettings _tmp = mc.gameSettings;
            GameSettings.lastServer = s3.replaceAll(":", "_");
            mc.gameSettings.saveOptions();
            String as[] = s3.split(":");

            if (s3.startsWith("["))
            {
                int i = s3.indexOf("]");

                if (i > 0)
                {
                    String s4 = s3.substring(1, i);
                    String s5 = s3.substring(i + 1).trim();

                    if (s5.startsWith(":") && s5.length() > 0)
                    {
                        s5 = s5.substring(1);
                        as = new String[2];
                        as[0] = s4;
                        as[1] = s5;
                    }
                    else
                    {
                        as = new String[1];
                        as[0] = s4;
                    }
                }
            }

            if (as.length > 2)
            {
                as = new String[1];
                System.out.println(as);
                as[0] = s3;
            }

            mc.displayGuiScreen(new GuiConnecting(mc, as[0], as.length <= 1 ? 25565 : Integer.parseInt(as[1])));
        }

        if (guibutton.id != 3);

        if (guibutton.id == 4)
        {
            String s1 = namechange.getText().trim();
            mc.session.username = s1;

            if (mc.session.username == Variables.username)
            {
                Variables.nopaidcheck = false;
            }
            else
            {
                Variables.nopaidcheck = true;
            }

            mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (guibutton.id == 5)
        {
            String s2 = namechange.getText().trim();
            mc.session.username = s2;

            if (mc.session.username == Variables.username)
            {
                Variables.nopaidcheck = false;
            }
            else
            {
                Variables.nopaidcheck = true;
            }

            Variables.reconnect = true;
            initGui();
        }

        if (guibutton.id == 6)
        {
            mc.displayGuiScreen(new GuiVexAlt(this));
        }
    }

    private int parseIntWithDefault(String s, int i)
    {
        try
        {
            return Integer.parseInt(s.trim());
        }
        catch (Exception exception)
        {
            return i;
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        serverTextField.textboxKeyTyped(c, i);

        if (i == 1)
        {
            mc.displayGuiScreen(parentScreen);
        }
        else if (c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(1));
        }

        ((GuiButton)controlList.get(1)).enabled = serverTextField.getText().length() > 0;
        ((GuiButton)controlList.get(2)).enabled = serverTextField.getText().length() > 0;

        if (Character.isLetter(c) || Character.isDigit(c) || c == '\r' || c == '\b' || c == '_')
        {
            namechange.textboxKeyTyped(c, i);

            if (c == '\r')
            {
                actionPerformed((GuiButton)controlList.get(1));
            }

            if (namechange.getText().equals(mc.session.username))
            {
                ((GuiButton)controlList.get(1)).enabled = false;
                ((GuiButton)controlList.get(2)).enabled = false;
            }
            else
            {
                ((GuiButton)controlList.get(1)).enabled = namechange.getText().length() > 0;
                ((GuiButton)controlList.get(2)).enabled = namechange.getText().length() > 0;
            }

            cname = namechange.getText();
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        serverTextField.mouseClicked(i, j, k);
        namechange.mouseClicked(i, j, k);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("NAME CHANGER"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawCenteredString(fontRenderer, stringtranslate.translateKey((new StringBuilder()).append("\2475Your current name is \2472[\2474").append(mc.session.username).append("\2472]").toString()), width / 2, ((height / 4 - 60) + 60) - 18, 0xa0a0a0);
        drawCenteredString(fontRenderer, stringtranslate.translateKey((new StringBuilder()).append("\2472Your original name is \2475[\2474").append(Variables.originalName).append("\2475]").toString()), width / 2, ((height / 4 - 60) + 60) - 9, 0xa0a0a0);
        drawCenteredString(fontRenderer, stringtranslate.translateKey("\2475Enter a new name!"), width / 2, (height / 4 - 60) + 60 + 0, 0xa0a0a0);
        drawCenteredString(fontRenderer, stringtranslate.translateKey("\2472Only works on Cracked/offline servers."), width / 2, (height / 4 - 60) + 60 + 9, 0xa0a0a0);
        namechange.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
