package net.minecraft.src;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.Minecraft;

public class VexDownload implements Runnable
{
    boolean downloadJar;
    boolean downloadCL;
    GuiVexUpdate GuiScreen;

    VexDownload(boolean flag, boolean flag1, GuiVexUpdate guivexupdate)
    {
        downloadJar = false;
        downloadCL = false;
        GuiScreen = null;
        downloadJar = flag;
        downloadCL = flag1;
        GuiScreen = guivexupdate;
    }

    VexDownload(boolean flag, boolean flag1)
    {
        downloadJar = false;
        downloadCL = false;
        GuiScreen = null;
        downloadJar = flag;
        downloadCL = flag1;
    }

    public void run()
    {
        if (downloadJar)
        {
            try
            {
                File file = new File(Minecraft.theMinecraft.mcDataDir, "bin\\Vexation.jar");

                if (file.exists())
                {
                    file.delete();
                }

                long l = System.currentTimeMillis();
                URL url = new URL(Variables.downloadURL);
                URLConnection urlconnection = url.openConnection();

                if (GuiScreen != null)
                {
                    GuiScreen.jarSize = urlconnection.getContentLength();
                }

                InputStream inputstream = url.openStream();
                FileOutputStream fileoutputstream1 = new FileOutputStream(file);
                byte abyte1[] = new byte[0x25800];
                int i = 0;
                boolean flag = false;

                do
                {
                    int j;

                    if ((j = inputstream.read(abyte1)) < 0)
                    {
                        break;
                    }

                    fileoutputstream1.write(abyte1, 0, j);
                    i += j;

                    if (GuiScreen != null)
                    {
                        GuiScreen.jarProgress = i;
                    }
                }
                while (true);

                long l1 = System.currentTimeMillis();

                if (GuiScreen != null)
                {
                    GuiScreen.UpdatedStatus = (new StringBuilder()).append("Done. ").append((new Integer(i)).toString()).append(" bytes read (").append((new Long(l1 - l)).toString()).append(" millseconds).").toString();
                }

                fileoutputstream1.close();
                inputstream.close();

                if (GuiScreen != null)
                {
                    GuiScreen.Updating = false;
                }
            }
            catch (Exception exception)
            {
                if (GuiScreen != null)
                {
                    GuiScreen.UpdatedStatus = (new StringBuilder()).append("\2474Failed to update! Error: ").append(exception).toString();
                }

                System.out.println(exception);
            }
        }

        if (downloadCL)
        {
            try
            {
                File file1 = new File(Minecraft.theMinecraft.mcDataDir, "Vexation\\Changelog.txt");
                System.out.println(Variables.Changelog);
                BufferedInputStream bufferedinputstream = new BufferedInputStream((new URL(Variables.Changelog)).openStream());
                FileOutputStream fileoutputstream = new FileOutputStream(file1);
                BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream, 1024);

                for (byte abyte0[] = new byte[1024]; bufferedinputstream.read(abyte0, 0, 1024) >= 0; bufferedoutputstream.write(abyte0)) { }

                bufferedoutputstream.close();
                fileoutputstream.close();
                bufferedinputstream.close();
                Variables.readChangeLog(false);
            }
            catch (Exception exception1)
            {
                if (GuiScreen != null)
                {
                    GuiScreen.UpdatedStatus = (new StringBuilder()).append("\2474Failed to download Changelog! Error: ").append(exception1).toString();
                }

                System.out.println(exception1);
            }
        }
    }
}
