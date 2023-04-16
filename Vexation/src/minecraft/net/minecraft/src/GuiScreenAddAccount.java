package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiScreenAddAccount extends GuiScreen
{
    private GuiScreen field_35362_a;
    private GuiVexTextFieldPrivate accountPass;
    private GuiTextField accountName;
    private AccountNBTStorage accountNBTStorage;

    public GuiScreenAddAccount(GuiScreen guiscreen, AccountNBTStorage accountnbtstorage)
    {
        field_35362_a = guiscreen;
        accountNBTStorage = accountnbtstorage;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        accountName.updateCursorCounter();
        accountPass.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("Done")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
        accountName = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
        accountName.setText(accountNBTStorage.name);
        accountName.isFocused = true;
        accountName.setMaxStringLength(32);
        accountPass = new GuiVexTextFieldPrivate(fontRenderer, width / 2 - 100, 116, 200, 20);
        accountPass.setText(accountNBTStorage.pass);
        accountPass.setMaxStringLength(128);
        ((GuiButton)controlList.get(0)).enabled = accountPass.getText().length() > 0 && accountName.getText().length() > 0;
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
            field_35362_a.deleteAccount(false, 0);
        }
        else if (guibutton.id == 0)
        {
            accountNBTStorage.name = accountName.getText();
            accountNBTStorage.pass = accountPass.getText();
            field_35362_a.deleteAccount(true, 0);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        accountName.textboxKeyTyped(c, i);
        accountPass.textboxKeyTyped(c, i);

        if (i == 1)
        {
            mc.displayGuiScreen(field_35362_a);
        }

        if (c == '\t')
        {
            if (accountName.isFocused)
            {
                accountName.isFocused = false;
                accountPass.isFocused = true;
            }
            else
            {
                accountName.isFocused = true;
                accountPass.isFocused = false;
            }
        }

        if (c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }

        ((GuiButton)controlList.get(0)).enabled = accountPass.getText().length() > 0 && accountPass.getText().length() > 0;

        if (((GuiButton)controlList.get(0)).enabled)
        {
            String s = accountPass.getText().trim();
            String as[] = s.split(":");

            if (as.length > 2)
            {
                ((GuiButton)controlList.get(0)).enabled = false;
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        accountPass.mouseClicked(i, j, k);
        accountName.mouseClicked(i, j, k);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("Edit Account"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("Account Name"), width / 2 - 100, 63, 0xa0a0a0);
        drawString(fontRenderer, stringtranslate.translateKey("Account Password"), width / 2 - 100, 104, 0xa0a0a0);
        accountName.drawTextBox();
        accountPass.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
