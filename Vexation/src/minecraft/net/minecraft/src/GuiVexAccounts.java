package net.minecraft.src;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiVexAccounts extends GuiScreen
{
    private static int threadsPending = 0;
    private static Object lock = new Object();
    private GuiScreen parentScreen;
    private GuiSlotAccount accountSlotContainer;
    private List accountList;
    private int selectedAccount;
    private GuiButton buttonEdit;
    private GuiButton buttonSelect;
    private GuiButton buttonDelete;
    private boolean deleteClicked;
    private boolean addClicked;
    private boolean editClicked;
    private boolean directClicked;
    private String field_35350_v;
    private AccountNBTStorage tempAccount;
    private String Error;

    public GuiVexAccounts(GuiScreen guiscreen)
    {
        Error = "";
        accountList = new ArrayList();
        selectedAccount = -1;
        deleteClicked = false;
        addClicked = false;
        editClicked = false;
        directClicked = false;
        field_35350_v = null;
        tempAccount = null;
        parentScreen = guiscreen;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        loadAccountList();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        accountSlotContainer = new GuiSlotAccount(this);
        initGuiControls();
    }

    private void loadAccountList()
    {
        try
        {
            NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(mc.mcDataDir, "Vexation\\accounts.dat"));
            NBTTagList nbttaglist = nbttagcompound.getTagList("accounts");
            accountList.clear();

            for (int i = 0; i < nbttaglist.tagCount(); i++)
            {
                accountList.add(AccountNBTStorage.createAccountNBTStorage((NBTTagCompound)nbttaglist.tagAt(i)));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void saveAccountList()
    {
        try
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < accountList.size(); i++)
            {
                nbttaglist.appendTag(((AccountNBTStorage)accountList.get(i)).getCompoundTag());
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("accounts", nbttaglist);
            CompressedStreamTools.safeWrite(nbttagcompound, new File(mc.mcDataDir, "Vexation\\accounts.dat"));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void initGuiControls()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.add(buttonEdit = new GuiButton(7, width / 2 - 100 - 4, height - 28, 70, 20, stringtranslate.translateKey("selectServer.edit")));
        controlList.add(buttonDelete = new GuiButton(2, width / 2 - 30, height - 28, 70, 20, stringtranslate.translateKey("selectServer.delete")));
        controlList.add(buttonSelect = new GuiButton(1, width / 2 - 100 - 4, height - 52, 105, 20, stringtranslate.translateKey("Log In")));
        controlList.add(new GuiButton(3, width / 2 + 0 + 14, height - 52, 105, 20, stringtranslate.translateKey("Add Account")));
        controlList.add(new GuiButton(0, width / 2 + 44, height - 28, 75, 20, stringtranslate.translateKey("gui.cancel")));
        boolean flag = selectedAccount >= 0 && selectedAccount < accountSlotContainer.getSize();
        buttonSelect.enabled = flag;
        buttonEdit.enabled = flag;
        buttonDelete.enabled = flag;
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

        if (guibutton.id == 2)
        {
            String s = ((AccountNBTStorage)accountList.get(selectedAccount)).name;

            if (s != null)
            {
                deleteClicked = true;
                StringTranslate stringtranslate = StringTranslate.getInstance();
                String s1 = stringtranslate.translateKey("Are you sure you want to delete this account?");
                String s2 = (new StringBuilder()).append("'").append(s).append("' ").append(stringtranslate.translateKey("selectServer.deleteWarning")).toString();
                String s3 = stringtranslate.translateKey("selectServer.deleteButton");
                String s4 = stringtranslate.translateKey("gui.cancel");
                GuiVexYesNo guivexyesno = new GuiVexYesNo(this, s1, s2, s3, s4, selectedAccount);
                mc.displayGuiScreen(guivexyesno);
            }
        }
        else if (guibutton.id == 1)
        {
            loginToAccount(selectedAccount);
        }
        else if (guibutton.id == 4)
        {
            directClicked = true;
            mc.displayGuiScreen(new GuiScreenAccountList(this, tempAccount = new AccountNBTStorage("", "")));
        }
        else if (guibutton.id == 3)
        {
            addClicked = true;
            mc.displayGuiScreen(new GuiScreenAddAccount(this, tempAccount = new AccountNBTStorage("", "")));
        }
        else if (guibutton.id == 7)
        {
            editClicked = true;
            AccountNBTStorage accountnbtstorage = (AccountNBTStorage)accountList.get(selectedAccount);
            mc.displayGuiScreen(new GuiScreenAddAccount(this, tempAccount = new AccountNBTStorage(accountnbtstorage.name, accountnbtstorage.pass)));
        }
        else if (guibutton.id == 0)
        {
            mc.displayGuiScreen(parentScreen);
        }
        else if (guibutton.id == 8)
        {
            mc.displayGuiScreen(new GuiMultiplayer(parentScreen));
        }
        else
        {
            accountSlotContainer.actionPerformed(guibutton);
        }
    }

    public void deleteAccount(boolean flag, int i)
    {
        if (deleteClicked)
        {
            deleteClicked = false;

            if (flag)
            {
                accountList.remove(i);
                saveAccountList();
            }

            mc.displayGuiScreen(this);
        }
        else if (addClicked)
        {
            addClicked = false;

            if (flag)
            {
                accountList.add(tempAccount);
                saveAccountList();
            }

            mc.displayGuiScreen(this);
        }
        else if (editClicked)
        {
            editClicked = false;

            if (flag)
            {
                AccountNBTStorage accountnbtstorage = (AccountNBTStorage)accountList.get(selectedAccount);
                accountnbtstorage.name = tempAccount.name;
                accountnbtstorage.pass = tempAccount.pass;
                saveAccountList();
            }

            mc.displayGuiScreen(this);
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
        if (i == 1)
        {
            mc.displayGuiScreen(parentScreen);
        }

        if (c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(2));
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        field_35350_v = null;
        StringTranslate stringtranslate = StringTranslate.getInstance();
        accountSlotContainer.drawScreen(i, j, f);
        drawCenteredString(fontRenderer, stringtranslate.translateKey("Accounts"), width / 2, 20, 0xffffff);

        if (Error.contains("503"))
        {
            drawCenteredString(fontRenderer, stringtranslate.translateKey((new StringBuilder()).append("Could not connect to Minecraft.net!: ").append(Error).toString()), width / 2, 10, 0xbf0000);
        }
        else if (!Error.isEmpty())
        {
            drawCenteredString(fontRenderer, stringtranslate.translateKey(Error), width / 2, 10, 0xbf0000);
        }

        super.drawScreen(i, j, f);

        if (field_35350_v != null)
        {
            func_35325_a(field_35350_v, i, j);
        }
    }

    private void loginToAccount(int i)
    {
        loginToAccount((AccountNBTStorage)accountList.get(i));
    }

    private void loginToAccount(AccountNBTStorage accountnbtstorage)
    {
        String s = accountnbtstorage.pass;
        String s1 = accountnbtstorage.name;
        String s2 = "";
        Error = "";

        //try
       // {
          //  if (!mc.isSingleplayer())
          //  {
           //     mc.theWorld.sendQuittingDisconnectingPacket();
           // }

            try
            {
                URL url = new URL((new StringBuilder()).append("http://login.minecraft.net/?user=").append(accountnbtstorage.name).append("&password=").append(accountnbtstorage.pass).append("&version=13").toString());
                URLConnection urlconnection = url.openConnection();
                urlconnection.setReadTimeout(5000);
                urlconnection.setDoOutput(true);
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
                s2 = bufferedreader.readLine();
                bufferedreader.close();
                String as[] = new String[4];
                as = s2.split(":");
                System.out.println(s2);
                if (as.length >= 3)
                {
                    mc.session.username = as[2];
                    //mc.session.mpPassParameter = accountnbtstorage.pass;
                    mc.session.sessionId = as[3];

                    if (!mc.isSingleplayer() && parentScreen instanceof GuiIngameMenu) 
                    {
                    	mc.theWorld.sendQuittingDisconnectingPacket();
                        Variables.reconnect = true;
                        mc.displayGuiScreen(new GuiNameChanger(this));
                    }
                    else
                    {
                        mc.displayGuiScreen(parentScreen);
                    }
                }
                else
                {
                    Error = "Invalid Username or Password";
                }
            }
            catch (Exception exception)
            {
                Error = exception.toString();
                System.out.println(exception);
            }
        }
        //catch (Exception exception1)
       // {
          //  if (Error.isEmpty())
          //  {
          //      Error = s2;
         //   }

         //   System.out.println((new StringBuilder()).append("ERROR! ").append(exception1).append(" ").append(s2).toString());
        //}
   // }

    protected void func_35325_a(String s, int i, int j)
    {
        if (s == null)
        {
            return;
        }
        else
        {
            int k = i + 12;
            int l = j - 12;
            int i1 = fontRenderer.getStringWidth(s);
            drawGradientRect(k - 3, l - 3, k + i1 + 3, l + 8 + 3, 0xc0000000, 0xc0000000);
            fontRenderer.drawStringWithShadow(s, k, l, -1);
            return;
        }
    }

    static List getAccountList(GuiVexAccounts guivexaccounts)
    {
        return guivexaccounts.accountList;
    }

    static int setSelectedAccount(GuiVexAccounts guivexaccounts, int i)
    {
        return guivexaccounts.selectedAccount = i;
    }

    static int getSelectedAccount(GuiVexAccounts guivexaccounts)
    {
        return guivexaccounts.selectedAccount;
    }

    static GuiButton getButtonSelect(GuiVexAccounts guivexaccounts)
    {
        return guivexaccounts.buttonSelect;
    }

    static GuiButton getButtonEdit(GuiVexAccounts guivexaccounts)
    {
        return guivexaccounts.buttonEdit;
    }

    static GuiButton getButtonDelete(GuiVexAccounts guivexaccounts)
    {
        return guivexaccounts.buttonDelete;
    }

    static void loginToAccount(GuiVexAccounts guivexaccounts, int i)
    {
        guivexaccounts.loginToAccount(i);
    }

    static Object getLock()
    {
        return lock;
    }

    static int getThreadsPending()
    {
        return threadsPending;
    }

    static int incrementThreadsPending()
    {
        return threadsPending++;
    }

    static int decrementThreadsPending()
    {
        return threadsPending--;
    }

    static String func_35327_a(GuiVexAccounts guivexaccounts, String s)
    {
        return guivexaccounts.field_35350_v = s;
    }
}
