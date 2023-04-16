package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiVexControls extends GuiScreen
{
    private GuiScreen parentScreen;
    protected String screenTitle;
    private GameSettings options;
    private int buttonId;

    public GuiVexControls(GuiScreen guiscreen, GameSettings gamesettings)
    {
        screenTitle = "VexControls";
        buttonId = -69;
        parentScreen = guiscreen;
        options = gamesettings;
    }

    private int func_20080_j()
    {
        return width / 2 - 155;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate;
        label0:
        {
            stringtranslate = StringTranslate.getInstance();
            int i = func_20080_j();
            int j = 0;

            do
            {
                GameSettings _tmp = options;

                if (j >= GameSettings.vexkeyBinds.length)
                {
                    break label0;
                }

                controlList.add(new GuiSmallButton(j, i + (j % 2) * 160, height / 6 + 24 * (j >> 1), 70, 20, options.getVexOptionDisplayString(j)));
                j++;
            }
            while (true);
        }
        controlList.add(new GuiButton(200, width / 2 - 155, 15, 20, 20, stringtranslate.translateKey("\2472<--")));
        screenTitle = stringtranslate.translateKey("Vexation Controls");
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        label0:
        {
            int i = 0;

            do
            {
                GameSettings _tmp = options;

                if (i >= GameSettings.vexkeyBinds.length)
                {
                    break label0;
                }

                ((GuiButton)controlList.get(i)).displayString = options.getVexOptionDisplayString(i);
                i++;
            }
            while (true);
        }

        if (guibutton.id == 200)
        {
            mc.displayGuiScreen(parentScreen);
        }
        else
        {
            buttonId = guibutton.id;
            guibutton.displayString = (new StringBuilder()).append("\2475> ").append(options.getVexOptionDisplayString(guibutton.id)).append(" \2475<").toString();
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        if (buttonId >= 0)
        {
            options.setVexKeyBind(buttonId, -100 + k);
            ((GuiButton)controlList.get(buttonId)).displayString = options.getVexOptionDisplayString(buttonId);
            buttonId = -1;
            VexKeyBind.resetKeyBindingArrayAndHash();
        }
        else
        {
            super.mouseClicked(i, j, k);
        }
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
        else if (buttonId >= 0)
        {
            options.setVexKeyBind(buttonId, i);
            ((GuiButton)controlList.get(buttonId)).displayString = options.getVexOptionDisplayString(buttonId);
            buttonId = -1;
            VexKeyBind.resetKeyBindingArrayAndHash();
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        label0:
        {
            drawDefaultBackground();
            drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xbf00bf);
            int k = func_20080_j();
            int l = 0;

            do
            {
                boolean flag;
                label1:
                {
                    GameSettings _tmp = options;

                    if (l >= GameSettings.vexkeyBinds.length)
                    {
                        break label0;
                    }

                    flag = false;
                    int i1 = 0;

                    do
                    {
                        GameSettings _tmp1 = options;

                        if (i1 >= GameSettings.vexkeyBinds.length)
                        {
                            break label1;
                        }

                        for (int k1 = 0; k1 < options.keyBindings.length; k1++)
                        {
                            label2:
                            {
                                if (i1 != l)
                                {
                                    GameSettings _tmp2 = options;
                                    GameSettings _tmp3 = options;

                                    if (GameSettings.vexkeyBinds[l].VexkeyCode == GameSettings.vexkeyBinds[i1].VexkeyCode)
                                    {
                                        break label2;
                                    }
                                }

                                GameSettings _tmp4 = options;

                                if (GameSettings.vexkeyBinds[l].VexkeyCode != options.keyBindings[k1].keyCode)
                                {
                                    continue;
                                }
                            }
                            flag = true;
                        }

                        i1++;
                    }
                    while (true);
                }
                int j1 = l;

                if (buttonId == l)
                {
                    ((GuiButton)controlList.get(j1)).displayString = "\2475> \2472??? \2475<";
                }
                else if (flag)
                {
                    ((GuiButton)controlList.get(j1)).displayString = (new StringBuilder()).append("\2475").append(options.getVexOptionDisplayString(j1)).toString();
                }
                else
                {
                    ((GuiButton)controlList.get(j1)).displayString = (new StringBuilder()).append("\2472").append(options.getVexOptionDisplayString(j1)).toString();
                }

                drawString(fontRenderer, options.getVexKeyBindingDescription(l), k + (l % 2) * 160 + 70 + 6, height / 6 + 24 * (l >> 1) + 7, -1);
                l++;
            }
            while (true);
        }
        super.drawScreen(i, j, f);
    }
}
