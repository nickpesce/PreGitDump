package net.minecraft.src;

import java.io.PrintStream;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiVexAlt extends GuiScreen
{
    private GuiScreen parentScreen;
    private String statusString;
    public static GuiTextField CaptchaBox;
    private int texture;
    public String previousNames[];
    public String previousIds[];
    private String lastName;
    private String lastID;
    private int lastNum;

    public GuiVexAlt(GuiScreen guiscreen)
    {
        lastNum = 0;
        parentScreen = guiscreen;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        CaptchaBox.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        texture = Minecraft.theMinecraft.renderEngine.getTexture("http://alts.min.vc/?testmode&getalt");
        controlList.clear();
        CaptchaBox = new GuiTextField(fontRenderer, width / 2 - 50, height / 2 + 50, 148, 20);
        CaptchaBox.isFocused = true;
        CaptchaBox.setMaxStringLength(128);
        InitButtons();
    }

    public void InitButtons()
    {
        controlList.add(new GuiButton(0, width / 2 - 37, height - 28, 75, 20, "\2472DONE"));
        controlList.add(new GuiButton(1, width - 80, height - 28, 75, 20, "\2472Get New Alt!"));
        controlList.add(new GuiButton(2, 5, height - 28, 75, 20, "\2472 Use Last Alt!"));
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        CaptchaBox.mouseClicked(i, j, k);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
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

        if (Character.isLetter(c) || Character.isDigit(c) || c == '\r' || c == '\b' || c == ' ' || c == '/' || c == '-')
        {
            CaptchaBox.textboxKeyTyped(c, i);

            if (c <= 0);
        }
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

        if (guibutton.id == 1)
        {
            lastNum = 0;
            String s = VexNewAlts.sendCaptcha(CaptchaBox.getText(), "Vexation");
            System.out.println(s);

            if (s.contains(":"))
            {
                previousNames[previousNames.length] = mc.session.username;
                previousIds[previousIds.length] = mc.session.sessionId;
                lastName = mc.session.username;
                lastID = mc.session.sessionId;
                String as[] = s.split(":");
                mc.session.sessionId = as[1];
                mc.session.username = as[0];
                statusString = (new StringBuilder()).append("\247aNew Alt: ").append(mc.session.username).toString();
            }
            else if (s.contains("too many alts"))
            {
                statusString = "\247fERROR: \2474You have used too many alts from online DB";
            }
            else if (s.startsWith("8"))
            {
                statusString = "\247fERROR: \2474Please wait 2 minutes";
            }
            else if (s.startsWith("10"))
            {
                statusString = "\247fERROR: \2474Invalid captcha, try again";
            }
            else
            {
                statusString = "\247fERROR: \2474Something went wrong, try again";
            }
        }

        if (guibutton.id == 2 && previousNames != null && previousNames.length > lastNum)
        {
            statusString = (new StringBuilder()).append("\247aNew Alt: ").append(mc.session.username).toString();
            mc.session.username = previousNames[lastNum];
            mc.session.sessionId = previousIds[lastNum];
            lastNum++;
            mc.session.sessionId = lastID;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        drawTexturedModalRect(width / 2 - 100, height / 2 - 100, 0, 45, 240, 180);
        CaptchaBox.drawTextBox();
        drawCenteredString(fontRenderer, "ALTS", width / 2, 10, 52480);
        drawCenteredString(fontRenderer, statusString, width / 2, 40, 52480);
        super.drawScreen(i, j, f);
    }
}
