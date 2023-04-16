package net.minecraft.src;

public class GuiIngameMenu extends GuiScreen
{
    /** Also counts the number of updates, not certain as to why yet. */
    private int updateCounter2 = 0;

    /** Counts the number of screen updates. */
    private int updateCounter = 0;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        updateCounter2 = 0;
        controlList.clear();
        byte byte0 = -16;
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + byte0, StatCollector.translateToLocal("menu.returnToMenu")));

        ((GuiButton)controlList.get(0)).displayString = StatCollector.translateToLocal("menu.disconnect");

        controlList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + byte0, StatCollector.translateToLocal("menu.returnToGame")));
        controlList.add(new GuiButton(7, width / 2 - 100, height / 4 + 72 + byte0, StatCollector.translateToLocal("\2472VEXATION OPTIONS")));
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + byte0, StatCollector.translateToLocal("menu.options")));
        controlList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + byte0, 98, 20, StatCollector.translateToLocal("gui.achievements")));
        controlList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + byte0, 98, 20, StatCollector.translateToLocal("gui.stats")));
        controlList.add(new GuiButton(8, width / 2 - 100, height / 4 + 144 + byte0, "Reconnect"));
        controlList.add(new GuiButton(9, width / 2 - 100, height / 4 + 168 + byte0, "Texture Packs"));
        controlList.add(new GuiButtonSmallVex(10, width / 2 - 124, height / 4 + 72 + byte0, "/gui/AccountButton.png"));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 1:
                par1GuiButton.enabled = false;
                this.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);
                this.mc.displayGuiScreen(new GuiMainMenu());

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                this.mc.sndManager.resumeAllSounds();
                break;

            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
                break;

            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
                break;

            case 7:
            	mc.displayGuiScreen(new GuiVexOptions(this, mc.gameSettings));
            	break;
            case 8:
            	 mc.theWorld.sendQuittingDisconnectingPacket();
                 Variables.reconnect = true;
                 mc.displayGuiScreen(new GuiNameChanger(this));
                 break;
            case 9:
            	mc.displayGuiScreen(new GuiTexturePacks(this));
            	break;
            case 10:
            	mc.displayGuiScreen(new GuiVexAccounts(this));
            	break;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.updateCounter;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Game menu", this.width / 2, 40, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
