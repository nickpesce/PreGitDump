package net.minecraft.src;

import java.io.File;
import java.util.List;
import javax.swing.JProgressBar;
import net.minecraft.client.Minecraft;

public class GuiVexUpdate extends GuiScreen
{
    boolean Updating;
    boolean failedToClose;
    String UpdatedStatus;
    JProgressBar progressBar;
    public double jarSize;
    public double jarProgress;

    public GuiVexUpdate()
    {
        Updating = false;
        failedToClose = false;
        UpdatedStatus = "";
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
        controlList.add(new GuiButton(0, width / 2 + 5, height / 2, 100, 20, "\247fDo not update"));
        controlList.add(new GuiButton(1, width / 2 - 105, height / 2, 100, 20, "\247fUpdate"));
        controlList.add(new GuiButton(2, width / 2 - 50, height / 2 - 25, 100, 20, "\247fChangelog"));
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
            mc.displayGuiScreen(new GuiMainMenu());
        }

        if (guibutton.id == 1)
        {
            Updating = true;
            Thread thread = new Thread(new VexDownload(true, true, this));
            thread.start();
            openCL();
            openBinAndExit();
        }

        if (guibutton.id == 2)
        {
            Thread thread1 = new Thread(new VexDownload(false, true, this));
            thread1.start();
            openCL();
        }
    }

    public void openBinAndExit()
    {
        try
        {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            File file = new File(mc.mcDataDir, "bin");
            desktop.open(file);
            mc.shutdown();
        }
        catch (Exception exception)
        {
            failedToClose = true;
        }
    }

    public void openCL()
    {
        try
        {
            File file = new File(mc.mcDataDir, "Vexation\\Changelog.txt");
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            desktop.open(file);
        }
        catch (Exception exception) { }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    public void keyTyped(char c, int i)
    {
        if (i != 1);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Update Avalible!", width / 2, 20, 52480);
        drawCenteredString(fontRenderer, (new StringBuilder()).append("New Version ").append(Variables.newVersion).append(" Is Now Avaliable!").toString(), width / 2, 50, 0xffffff);

        if (Updating)
        {
            int k = 0;

            if (jarSize >= 1.0D)
            {
                k = (int)Math.round((jarProgress / jarSize) * 100D);
            }

            drawCenteredString(fontRenderer, jarSize <= 1.0D ? "Starting up" : (new StringBuilder()).append("Updating! ").append(k).append("%").toString(), width / 2, 70, 48896);
        }

        drawCenteredString(fontRenderer, "After Pressing Update Rename The \247b\"Vexation\" \247ffile to \247b\"minecraft\"", width / 2, 60, 0xffffff);
        drawCenteredString(fontRenderer, UpdatedStatus, width / 2, height / 2 + 50, 48896);

        if (failedToClose)
        {
            drawCenteredString(fontRenderer, "Failed to open bin! Go to \247a\"C:\\Users\\(YOUR USER NAME)\\AppData\\Roaming\\.minecraft\\bin\"", width / 2, height / 2 + 70, 0xbf0000);
            drawCenteredString(fontRenderer, "and change \247b\"Vexation.jar\" \2474to \247b\"minecraft.jar\"", width / 2, height / 2 + 80, 0xbf0000);
        }

        super.drawScreen(i, j, f);
    }
}
