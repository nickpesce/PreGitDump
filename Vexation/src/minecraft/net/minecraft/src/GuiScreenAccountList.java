package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiScreenAccountList extends GuiScreen
{
    private GuiScreen guiScreen;
    private GuiTextField accountTextField;
    private AccountNBTStorage field_35318_c;

    public GuiScreenAccountList(GuiScreen guiscreen, AccountNBTStorage accountnbtstorage)
    {
        guiScreen = guiscreen;
        field_35318_c = accountnbtstorage;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        accountTextField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("selectAccount.select")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
        accountTextField = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
        accountTextField.setText(field_35318_c.pass);
        accountTextField.setMaxStringLength(128);
        ((GuiButton)controlList.get(0)).enabled = accountTextField.getText().length() > 0;
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
        	this.guiScreen.confirmClicked(false, 0);
        }
        else if (guibutton.id == 0)
        {
            field_35318_c.pass = accountTextField.getText();
            guiScreen.confirmClicked(true, 0);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        accountTextField.textboxKeyTyped(c, i);

        if (i == 1)
        {
            mc.displayGuiScreen(guiScreen);
        }

        if (c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }

        ((GuiButton)controlList.get(0)).enabled = accountTextField.getText().length() > 0;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        accountTextField.mouseClicked(i, j, k);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawString(fontRenderer, "Enter Password", width / 2 - 100, 100, 0xa0a0a0);
        accountTextField.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
