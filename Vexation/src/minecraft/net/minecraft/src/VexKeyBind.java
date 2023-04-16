package net.minecraft.src;

import java.util.*;

public class VexKeyBind
{
    public static List VexkeybindArray = new ArrayList();
    public static IntHashMap Vexhash = new IntHashMap();
    public String VexkeyDescription;
    public int VexkeyCode;
    public boolean Vexpressed;
    public int VexpressTime;

    public static void onTick(int i)
    {
        VexKeyBind vexkeybind = (VexKeyBind)Vexhash.lookup(i);

        if (vexkeybind != null)
        {
            vexkeybind.VexpressTime++;
        }
    }

    public static void setVexKeyBindState(int i, boolean flag)
    {
        VexKeyBind vexkeybind = (VexKeyBind)Vexhash.lookup(i);

        if (vexkeybind != null)
        {
            vexkeybind.Vexpressed = flag;
        }
    }

    public static void unPressAllKeys()
    {
        VexKeyBind vexkeybind;

        for (Iterator iterator = VexkeybindArray.iterator(); iterator.hasNext(); vexkeybind.unpressKey())
        {
            vexkeybind = (VexKeyBind)iterator.next();
        }
    }

    public static void resetKeyBindingArrayAndHash()
    {
        Vexhash.clearMap();
        VexKeyBind vexkeybind;

        for (Iterator iterator = VexkeybindArray.iterator(); iterator.hasNext(); Vexhash.addKey(vexkeybind.VexkeyCode, vexkeybind))
        {
            vexkeybind = (VexKeyBind)iterator.next();
        }
    }

    public VexKeyBind(String s, int i)
    {
        VexpressTime = 0;
        VexkeyDescription = s;
        VexkeyCode = i;
        VexkeybindArray.add(this);
        Vexhash.addKey(i, this);
    }

    public boolean isPressed()
    {
        if (VexpressTime == 0)
        {
            return false;
        }
        else
        {
            VexpressTime--;
            return true;
        }
    }

    private void unpressKey()
    {
        VexpressTime = 0;
        Vexpressed = false;
    }
}
