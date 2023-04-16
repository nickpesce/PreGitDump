package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class VexNukeThread implements Runnable
{
    Minecraft mc;

    public VexNukeThread()
    {
        mc = Minecraft.theMinecraft;
    }

    public void run()
    {
        if (Variables.specnuke)
        {
            byte byte0 = 5;

            for (int i = byte0; i > -byte0; i--)
            {
                for (int k = byte0; k > -byte0; k--)
                {
                    for (int i1 = byte0; i1 > -byte0; i1--)
                    {
                        double d = mc.thePlayer.posX + (double)i;
                        double d2 = mc.thePlayer.posY + (double)k;
                        double d4 = mc.thePlayer.posZ + (double)i1;
                        int k1 = (int)d;
                        int i2 = (int)d2;
                        int k2 = (int)d4;
                        int i3 = mc.theWorld.getBlockId(k1, i2, k2);
                        Block block = Block.blocksList[i3];

                        if (block != null && i3 == Variables.snID)
                        {
                            EntityClientPlayerMP _tmp = (EntityClientPlayerMP)mc.thePlayer;
                            EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet14BlockDig(0, k1, i2, k2, 1));
                            EntityClientPlayerMP _tmp1 = (EntityClientPlayerMP)mc.thePlayer;
                            EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet14BlockDig(2, k1, i2, k2, 1));
                        }
                    }
                }
            }
        }
        else
        {
            try
            {
                byte byte1 = 5;

                for (int j = byte1; j > -byte1; j--)
                {
                    for (int l = byte1; l > -byte1; l--)
                    {
                        for (int j1 = byte1; j1 > -byte1; j1--)
                        {
                            double d1 = mc.thePlayer.posX + (double)j;
                            double d3 = mc.thePlayer.posY + (double)l;
                            double d5 = mc.thePlayer.posZ + (double)j1;
                            int l1 = (int)d1;
                            int j2 = (int)d3;
                            int l2 = (int)d5;
                            int j3 = mc.theWorld.getBlockId(l1, j2, l2);
                            Block block1 = Block.blocksList[j3];

                            if (block1 != null)
                            {
                                EntityClientPlayerMP _tmp2 = (EntityClientPlayerMP)mc.thePlayer;
                                EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet14BlockDig(0, l1, j2, l2, 1));
                                EntityClientPlayerMP _tmp3 = (EntityClientPlayerMP)mc.thePlayer;
                                EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet14BlockDig(2, l1, j2, l2, 1));
                            }
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                mc.thePlayer.addChatMessage((new StringBuilder()).append("\247cError!").append(exception).toString());
            }
        }
    }
}
