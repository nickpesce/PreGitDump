package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class VexNewAlts
{
    private Minecraft mc;

    VexNewAlts(Minecraft minecraft)
    {
        mc = Minecraft.theMinecraft;
        mc = minecraft;
    }

    public static String getImage()
    {
        String s = (new HtmlRequester("http://alts.min.vc/?testmode&getalt")).getHTML();
        return s;
    }

    public static String sendCaptcha(String s, String s1)
    {
        String s2 = (new HtmlRequester((new StringBuilder()).append("http://alts.min.vc/?testmode&getalt&captcha=").append(s).append("&client=").append(s1).toString())).getHTML();
        return s2;
    }
}
