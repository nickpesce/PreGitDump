package net.minecraft.src;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class VexKillAura implements Runnable
{
    Minecraft mc;
    int i;
    double YAW;
    double NYAW;

    public VexKillAura(Minecraft minecraft)
    {
        mc = minecraft;
    }

    public void run()
    {
        Kill();
    }

    public void Kill()
    {
        try
        {
            if (Variables.forcefield)
            {
                for (int j = 0; j < mc.theWorld.loadedEntityList.size(); j++)
                {
                    if (mc.theWorld.loadedEntityList.get(j) instanceof EntityPlayer)
                    {
                        EntityPlayer entityplayer = (EntityPlayer)mc.theWorld.loadedEntityList.get(j);

                        if (!GuiIngame.FriendList.contains(entityplayer.username.toLowerCase()) && entityplayer != mc.thePlayer && mc.thePlayer.getDistanceToEntity((Entity)mc.theWorld.loadedEntityList.get(j)) <= 8F && mc.thePlayer.canEntityBeSeen((Entity)mc.theWorld.loadedEntityList.get(j)))
                        {
                            if (Variables.noCheat)
                            {
                                mc.thePlayer.faceEntity((Entity)mc.theWorld.loadedEntityList.get(j), 999F, 999F);
                                Thread.sleep(50L);
                                mc.thePlayer.swingItem();
                                mc.playerController.attackEntity(mc.thePlayer, (Entity)mc.theWorld.loadedEntityList.get(j));
                            }
                            else
                            {
                                if (mc.thePlayer.onGround)
                                {
                                    mc.thePlayer.jump();
                                }

                                EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 4));
                                mc.playerController.attackEntity(mc.thePlayer, (Entity)mc.theWorld.loadedEntityList.get(j));
                            }
                        }
                    }
                    else if (((mc.theWorld.loadedEntityList.get(j) instanceof EntityMob) || (mc.theWorld.loadedEntityList.get(j) instanceof EntityAnimal)) && mc.thePlayer.getDistanceToEntity((Entity)mc.theWorld.loadedEntityList.get(j)) <= 8F && mc.thePlayer.canEntityBeSeen((Entity)mc.theWorld.loadedEntityList.get(j)))
                    {
                        if (Variables.noCheat)
                        {
                            Thread.sleep(100L);
                            mc.thePlayer.faceEntity((Entity)mc.theWorld.loadedEntityList.get(j), 999F, 999F);
                            mc.thePlayer.swingItem();
                            mc.playerController.attackEntity(mc.thePlayer, (Entity)mc.theWorld.loadedEntityList.get(j));
                        }
                        else
                        {
                            if (mc.thePlayer.onGround)
                            {
                                mc.thePlayer.jump();
                            }

                            EntityClientPlayerMP _tmp1 = (EntityClientPlayerMP)mc.thePlayer;
                            EntityClientPlayerMP.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 4));
                            mc.playerController.attackEntity(mc.thePlayer, (Entity)mc.theWorld.loadedEntityList.get(j));
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
