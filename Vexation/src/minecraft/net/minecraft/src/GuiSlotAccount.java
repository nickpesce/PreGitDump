package net.minecraft.src;

import java.util.List;
import org.lwjgl.opengl.GL11;

class GuiSlotAccount extends GuiSlot
{
    final GuiVexAccounts parentGui;

    public GuiSlotAccount(GuiVexAccounts guivexaccounts)
    {
        super(guivexaccounts.mc, guivexaccounts.width, guivexaccounts.height, 32, guivexaccounts.height - 64, 36);
        this.parentGui = guivexaccounts;
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return GuiVexAccounts.getAccountList(parentGui).size();
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int i, boolean flag)
    {
        GuiVexAccounts.setSelectedAccount(parentGui, i);
        boolean flag1 = GuiVexAccounts.getSelectedAccount(parentGui) >= 0 && GuiVexAccounts.getSelectedAccount(parentGui) < getSize();
        GuiVexAccounts.getButtonSelect(parentGui).enabled = flag1;
        GuiVexAccounts.getButtonEdit(parentGui).enabled = flag1;
        GuiVexAccounts.getButtonDelete(parentGui).enabled = flag1;

        if (flag && flag1)
        {
            GuiVexAccounts.loginToAccount(parentGui, i);
        }
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int i)
    {
        return i == GuiVexAccounts.getSelectedAccount(parentGui);
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return GuiVexAccounts.getAccountList(parentGui).size() * 36;
    }

    protected void drawBackground()
    {
        parentGui.drawDefaultBackground();
    }

    protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator)
    {
        AccountNBTStorage accountnbtstorage = (AccountNBTStorage)GuiVexAccounts.getAccountList(parentGui).get(i);
        parentGui.drawString(parentGui.fontRenderer, (new StringBuilder()).append("\2475").append(accountnbtstorage.name).toString(), j + 2, k + 1, 0xffffff);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        byte byte0 = 4;

        if (mouseX >= (j + 205) - byte0 && mouseY >= k - byte0 && mouseX <= j + 205 + 10 + byte0 && mouseY <= k + 8 + byte0)
        {
            GuiVexAccounts.func_35327_a(parentGui, "");
        }
    }
}
