package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiVexOptions extends GuiScreen
{
    private GuiScreen parentScreen;
    protected String screenTitle;
    private GameSettings options;
    public static GuiTextField HotKey1;
    public static GuiTextField HotKey2;
    public static GuiTextField HotKey3;
    public static GuiTextField HotKey4;

    public GuiVexOptions(GuiScreen guiscreen, GameSettings gamesettings)
    {
        screenTitle = "VexOptions";
        parentScreen = guiscreen;
        options = gamesettings;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        HotKey1.updateCursorCounter();
        HotKey2.updateCursorCounter();
        HotKey3.updateCursorCounter();
        HotKey4.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        screenTitle = stringtranslate.translateKey("\2475VEXATION OPTIONS");
        HotKey1 = new GuiTextField(fontRenderer, width / 2 - 155, height / 6 + 48, 148, 20);
        HotKey1.setText(Variables.hotKey1);
        HotKey1.setMaxStringLength(128);
        HotKey2 = new GuiTextField(fontRenderer, (width / 2 - 155) + 160, height / 6 + 48, 148, 20);
        HotKey1.setText(Variables.hotKey2);
        HotKey2.setMaxStringLength(128);
        HotKey3 = new GuiTextField(fontRenderer, (width / 2 - 155) + 0, height / 6 + 72, 148, 20);
        HotKey1.setText(Variables.hotKey3);
        HotKey3.setMaxStringLength(128);
        HotKey4 = new GuiTextField(fontRenderer, (width / 2 - 155) + 160, height / 6 + 72, 148, 20);
        HotKey1.setText(Variables.hotKey4);
        HotKey4.setMaxStringLength(128);
        controlList.add(new GuiButton(69, width - 105, 10, 100, 20, stringtranslate.translateKey("\247cRESET ALL OPTIONS")));
        controlList.add(new GuiButton(9, width - 105, height / 6 + 172, 100, 20, stringtranslate.translateKey("\247fHelp")));
        controlList.add(new GuiButton(70, 5, 10, 100, 20, Variables.noCheat ? "\247cNoCheat: On" : "\247aNoCheat: Off"));
        controlList.add(new GuiButton(2, width / 2 - 100, (height / 6 + 148) - 6, stringtranslate.translateKey("\2472Vexation Conrtols")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 6 + 172, stringtranslate.translateKey("gui.done")));
        controlList.add(new GuiButton(3, width / 2 - 100, (height / 6 + 122) - 6, stringtranslate.translateKey("\2472Credits")));

        if (!Variables.noCheat)
        {
            controlList.add(new GuiSlider(5, (width / 2 - 155) + 0, height / 6 + 0, EnumOptions.SPEED, (new StringBuilder()).append("Speed: ").append(Variables.speed).toString(), (float)Variables.speed / 10F));
            controlList.add(new GuiSlider(6, (width / 2 - 155) + 160, height / 6 + 0, EnumOptions.MINESPEED, (new StringBuilder()).append("Mine Speed: ").append(Variables.minespeed).toString(), (Variables.minespeed - 1.0F) / 4F));
            controlList.add(new GuiSlider(7, (width / 2 - 155) + 0, height / 6 + 24, EnumOptions.JUMPHEIGHT, (new StringBuilder()).append("Jump Height: ").append(Variables.jumpHeight).toString(), (float)((Variables.jumpHeight - 1.0D) / 9D)));
            controlList.add(new GuiSlider(8, (width / 2 - 155) + 160, height / 6 + 24, EnumOptions.OPACITY, (new StringBuilder()).append("Opacity: ").append((int)((((float)Variables.opacity - 25F) / 200F) * 100F)).append("%").toString(), ((float)Variables.opacity - 25F) / 200F));
        }
        else
        {
            controlList.add(new GuiSlider(6, (width / 2 - 155) + 0, height / 6 + 0, EnumOptions.MINESPEED, (new StringBuilder()).append("Mine Speed: ").append(Variables.minespeed).toString(), (Variables.minespeed - 1.0F) / 4F));
            controlList.add(new GuiSlider(8, (width / 2 - 155) + 160, height / 6 + 0, EnumOptions.OPACITY, (new StringBuilder()).append("Opacity: ").append((int)((((float)Variables.opacity - 25F) / 200F) * 100F)).append("%").toString(), ((float)Variables.opacity - 25F) / 200F));
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
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(parentScreen);
        }

        if (guibutton.id == 2)
        {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(new GuiVexControls(this, options));
        }

        if (guibutton.id == 3)
        {
            mc.displayGuiScreen(new GuiVexCredits(this));
        }

        if (guibutton.id == 69)
        {
            Variables.VEXGUI = true;
            Variables.kaistep = false;
            Variables.speedy = false;
            Variables.jump = false;
            Variables.fly = false;
            Variables.sneak = false;
            Variables.fullbright = false;
            Variables.nofall = true;
            Variables.forcefield = false;
            Variables.noswing = false;
            Variables.derp = false;
            Variables.spam = false;
            Variables.Xray = false;
            Variables.find = false;
            Variables.climb = false;
            Variables.nuker = false;
            Variables.specnuke = false;
            Variables.noweather = false;
            Variables.jesus = false;
            Variables.sphere = false;
            Variables.irregcyl = false;
            Variables.carpet = false;
            Variables.name = true;
            Variables.fastplace = false;
            Variables.cords = true;
            Variables.follow = false;
            Variables.nopush = true;
            Variables.reach = false;
            Variables.radar = false;
            Variables.locate = false;
            Variables.slow = false;
            Variables.GOTO = false;
            Variables.freecam = false;
            Variables.test = false;
            Variables.chat = false;
            Variables.AlreadyFlying = false;
            Variables.pause = false;
            Variables.fastmine = false;
            Variables.minespeed = 1.0F;
            Variables.line = false;
            Variables.posX = 0.0D;
            Variables.posZ = 0.0D;
            Variables.posY = 0.0D;
            Variables.fcX = 0.0D;
            Variables.fcZ = 0.0D;
            Variables.fcY = 0.0D;
            Variables.CaveFinder = false;
            Variables.give = false;
            Variables.username = "";
            Variables.nopaidcheck = false;
            Variables.reconnect = false;
            Variables.placeSwastika = false;
            Variables.aim = false;
            Variables.aimPlayer = null;
            Variables.LineViewBobbing = false;
            Variables.specAim = false;
            Variables.flip = true;
            Variables.speed = 1;
            Variables.mobFind = false;
            Variables.fog = true;
            Variables.autoPick = true;
            Variables.findHouse = false;
            Variables.run = false;
            Variables.jumpHeight = 1.0D;
            Variables.Translucent = true;
            Variables.opacity = 90;
            Variables.clear = false;
            Variables.hotKey1 = "";
            Variables.hotKey2 = "";
            Variables.hotKey3 = "";
            Variables.hotKey4 = "";
            Variables.Panic = false;
            Variables.speedMode1 = false;
            Variables.eat = true;
            Variables.flee = false;
            Variables.chest = false;
            Variables.noCheat = false;
            Variables.dungeonChest = false;
            Variables.checkingForPlugins = false;
            Variables.alreadyCheckedForNoCheat = false;
            Variables.drops = false;
            Variables.walk = true;
            Variables.chestLine = false;
            Variables.autoShoot = false;
            Variables.Time = true;
            Variables.tripBlock = false;
            Variables.tripBlockTwo = false;
            Variables.Compass = true;
            Variables.knockBack = false;
            Variables.Console = true;
            Variables.Day = false;
            Variables.itemDamage = true;
            Variables.playerBox = false;
            Variables.nopush = true;
            Variables.zoomAmount = 0.5F;
            Variables.Zoom = false;
            Variables.chatNotifier = true;
            mc.renderGlobal.loadRenderers();
            initGui();
        }

        if (guibutton.id == 70)
        {
            Variables.noCheat = !Variables.noCheat;

            if (Variables.noCheat)
            {
                Variables.turnOffNocheatHacks();
            }

            initGui();
        }

        if (guibutton.id == 9)
        {
            mc.displayGuiScreen(new GuiVexHelp(this));
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        HotKey1.mouseClicked(i, j, k);
        HotKey2.mouseClicked(i, j, k);
        HotKey3.mouseClicked(i, j, k);
        HotKey4.mouseClicked(i, j, k);
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
            HotKey1.textboxKeyTyped(c, i);
            HotKey2.textboxKeyTyped(c, i);
            HotKey3.textboxKeyTyped(c, i);
            HotKey4.textboxKeyTyped(c, i);

            if (c > 0)
            {
                Variables.hotKey1 = HotKey1.getText();
                Variables.hotKey2 = HotKey2.getText();
                Variables.hotKey3 = HotKey3.getText();
                Variables.hotKey4 = HotKey4.getText();
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
        drawCenteredString(fontRenderer, "\2475Hot Key 1: ", width / 2 - 185, height / 6 + 55, 0xffffff);
        drawCenteredString(fontRenderer, "\2475 :Hot Key 2", width / 2 + 185, height / 6 + 55, 0xffffff);
        drawCenteredString(fontRenderer, "\2475Hot Key 3: ", width / 2 - 185, height / 6 + 79, 0xffffff);
        drawCenteredString(fontRenderer, "\2475 :Hot Key 4", width / 2 + 185, height / 6 + 79, 0xffffff);
        HotKey1.drawTextBox();
        HotKey2.drawTextBox();
        HotKey3.drawTextBox();
        HotKey4.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
