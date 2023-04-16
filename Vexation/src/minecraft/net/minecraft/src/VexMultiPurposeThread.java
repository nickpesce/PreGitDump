package net.minecraft.src;

import java.io.PrintStream;
import net.minecraft.client.Minecraft;

public class VexMultiPurposeThread implements Runnable
{
    Minecraft mc;

    public VexMultiPurposeThread()
    {
        mc = Minecraft.theMinecraft;
    }

    public void run()
    {
        try
        {
            Nuke();
            Thread.sleep(35L);
        }
        catch (Exception exception)
        {
            System.out.println((new StringBuilder()).append("NUKEING ERROR: ").append(exception).toString());
        }
    }

    public void Nuke()
    {
        if (Variables.specnuke)
        {
            byte byte0 = 5;

            for (int i = byte0; i > -byte0; i--)
            {
                for (int j1 = byte0; j1 > -byte0; j1--)
                {
                    for (int k2 = byte0; k2 > -byte0; k2--)
                    {
                        double d2 = mc.thePlayer.posX + (double)i;
                        double d7 = mc.thePlayer.posY + (double)j1;
                        double d12 = mc.thePlayer.posZ + (double)k2;
                        int l3 = (int)d2;
                        int i5 = (int)d7;
                        int j6 = (int)d12;
                        int i7 = mc.theWorld.getBlockId(l3, i5, j6);
                        Block block = Block.blocksList[i7];

                        if (block != null && i7 == Variables.snID)
                        {
                            EntityClientPlayerMP _tmp = (EntityClientPlayerMP)mc.thePlayer;
                            EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet14BlockDig(0, l3, i5, j6, 1));
                            EntityClientPlayerMP _tmp1 = (EntityClientPlayerMP)mc.thePlayer;
                            EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet14BlockDig(2, l3, i5, j6, 1));
                        }
                    }
                }
            }
        }

        if (Variables.nuker)
        {
            try
            {
                byte byte1 = 5;

                for (int j = byte1; j > -byte1; j--)
                {
                    for (int k1 = byte1; k1 > -byte1; k1--)
                    {
                        for (int l2 = byte1; l2 > -byte1; l2--)
                        {
                            double d3 = mc.thePlayer.posX + (double)j;
                            double d8 = mc.thePlayer.posY + (double)k1;
                            double d13 = mc.thePlayer.posZ + (double)l2;
                            int i4 = (int)d3;
                            int j5 = (int)d8;
                            int k6 = (int)d13;
                            int j7 = mc.theWorld.getBlockId(i4, j5, k6);
                            Block block1 = Block.blocksList[j7];

                            if (block1 != null)
                            {
                                EntityClientPlayerMP _tmp2 = (EntityClientPlayerMP)mc.thePlayer;
                                EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet14BlockDig(0, i4, j5, k6, 1));
                                EntityClientPlayerMP _tmp3 = (EntityClientPlayerMP)mc.thePlayer;
                                EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet14BlockDig(2, i4, j5, k6, 1));
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

        if (Variables.sphere)
        {
            byte byte2 = 5;

            for (int k = byte2; k > -byte2; k--)
            {
                for (int l1 = byte2; l1 > -byte2; l1--)
                {
                    for (int i3 = byte2; i3 > -byte2; i3--)
                    {
                        double d4 = mc.thePlayer.posX + (double)k;
                        double d9 = mc.thePlayer.posY + (double)l1;
                        double d14 = mc.thePlayer.posZ + (double)i3;
                        int j4 = (int)d4;
                        int k5 = (int)d9;
                        int l6 = (int)d14;
                        ItemStack itemstack2 = mc.thePlayer.getCurrentEquippedItem();

                        if (itemstack2 != null)
                        {
                            EntityClientPlayerMP _tmp4 = (EntityClientPlayerMP)mc.thePlayer;
                            EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet15Place(j4, k5, l6, 1, itemstack2, 0F, 0F, 0F));
                        }
                    }
                }
            }
        }

        if (Variables.irregcyl)
        {
            byte byte3 = 5;

            for (int l = byte3; l > -byte3; l--)
            {
                for (int i2 = byte3; i2 > -byte3; i2--)
                {
                    double d = mc.thePlayer.posX + (double)l;
                    double d5 = mc.thePlayer.posY - 1.0D;
                    double d10 = mc.thePlayer.posZ + (double)i2;
                    int j3 = (int)d;
                    int k4 = (int)d5;
                    int l5 = (int)d10;
                    ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();

                    if (itemstack != null)
                    {
                        EntityClientPlayerMP _tmp5 = (EntityClientPlayerMP)mc.thePlayer;
                        EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet15Place(j3, k4, l5, 1, itemstack, 0F, 0F, 0F));
                    }
                }
            }
        }

        if (Variables.carpet)
        {
            byte byte4 = 5;

            for (int i1 = byte4; i1 > -byte4; i1--)
            {
                for (int j2 = byte4; j2 > -byte4; j2--)
                {
                    double d1 = mc.thePlayer.posX + (double)i1;
                    double d6 = mc.thePlayer.posY - 3D;
                    double d11 = mc.thePlayer.posZ + (double)j2;
                    int k3 = (int)d1;
                    int l4 = (int)d6;
                    int i6 = (int)d11;
                    ItemStack itemstack1 = mc.thePlayer.getCurrentEquippedItem();

                    if (itemstack1 != null)
                    {
                        EntityClientPlayerMP _tmp6 = (EntityClientPlayerMP)mc.thePlayer;
                        EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet15Place(k3, l4, i6, 1, itemstack1, 0F, 0F, 0F));
                    }
                }
            }
        }
    }
}
