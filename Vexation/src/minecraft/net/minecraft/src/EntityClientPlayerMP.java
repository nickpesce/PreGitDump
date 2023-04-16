package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP
{
	//VEX MADE STATIC
    public static NetClientHandler sendQueue;
    private double oldPosX;

    /** Old Minimum Y of the bounding box */
    private double oldMinY;
    private double oldPosY;
    private double oldPosZ;
    private float oldRotationYaw;
    private float oldRotationPitch;

    /** Check if was on ground last update */
    private boolean wasOnGround = false;

    /** should the player stop sneaking? */
    private boolean shouldStopSneaking = false;
    private boolean wasSneaking = false;
    private int field_71168_co = 0;

    /** has the client player's health been set? */
    private boolean hasSetHealth = false;
    
    //VEX---------------
    /**VEX player that has tripped the tripblock and should not be alerted for again **/
    private Object tbWait;
    
    static int GOTOZ;
    
    static int GOTOX;
    
    static boolean sentFlee = false;
    //END VEX-------------

    public EntityClientPlayerMP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler)
    {
        super(par1Minecraft, par2World, par3Session, 0);
        this.sendQueue = par4NetClientHandler;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        return false;
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(int par1) {}

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	
        if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)))
        {
            super.onUpdate();
            this.sendMotionUpdates();
        }
    }

    /**
     * Send updated motion and position information to the server and a lot of VEX stuff!!!!
     */
    public void sendMotionUpdates()
    {
    	if (!Variables.outDated && Variables.CheckVersionAgain)
        {
            Variables.CheckVersionAgain = false;

            try
            {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((new URL("http://dl.dropbox.com/u/63453681/VexVersion.txt")).openStream()));
                String s = bufferedreader.readLine();

                for (boolean flag2 = false; s != null && !s.isEmpty() && !flag2;)
                {
                    if (s.contains("VexationVersion"))
                    {
                        flag2 = true;
                        String as[] = s.split("~");

                        if (!as[1].equals(Variables.version))
                        {
                            Variables.outDated = true;
                            Variables.newVersion = as[1];
                            Variables.downloadURL = as[2];
                            Variables.Changelog = as[3];
                            Variables.MOTD = as[4];
                        }

                        System.out.println((new StringBuilder()).append("*************************Version: ").append(Variables.version).toString());
                    }
                    else
                    {
                        s = bufferedreader.readLine();
                    }
                }
            }
            catch (Exception exception)
            {
                System.out.println(exception);
            }
        }

        if (mc.thePlayer.isSwingInProgress && Variables.knockBack)
        {
            sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 4));
        }

        Variables.Translucent = (Variables.Xray || Variables.find || Variables.CaveFinder) && !Variables.clear;

        if (Variables.tripBlock)
        {
            for (int i = 0; i < mc.theWorld.loadedEntityList.size(); i++)
            {
                if (!(mc.theWorld.loadedEntityList.get(i) instanceof EntityPlayer))
                {
                    continue;
                }

                EntityPlayer entityplayer = (EntityPlayer)mc.theWorld.loadedEntityList.get(i);

                if (mc.theWorld.loadedEntityList.get(i) == mc.thePlayer || GuiIngame.FriendList.contains(entityplayer.username.toLowerCase()))
                {
                    continue;
                }

                if (Variables.tripBlockTwo)
                {
                    if ((int)entityplayer.posX >= Variables.TBX && (int)entityplayer.posX <= Variables.TBX2 && ((int)entityplayer.posY + 1 >= Variables.TBY && (int)entityplayer.posY + 1 <= Variables.TBY2 || (int)entityplayer.posY >= Variables.TBY && (int)entityplayer.posY <= Variables.TBY2) && (int)entityplayer.posZ >= Variables.TBZ && (int)entityplayer.posZ <= Variables.TBZ2)
                    {
                        if (entityplayer == tbWait)
                        {
                            continue;
                        }

                        addChatMessage((new StringBuilder()).append("\2474[URGENT] \2473").append(entityplayer.username).append(" \2471has tripped your Trip Block!!!").toString());
                        worldObj.playSoundAtEntity(this, "random.explode", getSoundVolume(), mc.thePlayer.getSoundPitch());

                        if (Variables.tripBlockCommand != null)
                        {
                            sendChatMessage(Variables.tripBlockCommand);
                        }

                        tbWait = entityplayer;
                        continue;
                    }

                    if (tbWait == entityplayer)
                    {
                        tbWait = null;
                    }

                    continue;
                }

                if (!Variables.tripBlock || Variables.tripBlockTwo)
                {
                    continue;
                }

                if ((int)entityplayer.posX == Variables.TBX && ((int)entityplayer.posY + 1 == Variables.TBY || (int)entityplayer.posY == Variables.TBY) && (int)entityplayer.posZ == Variables.TBZ)
                {
                    if (entityplayer == tbWait)
                    {
                        continue;
                    }

                    worldObj.playSoundAtEntity(this, "random.explode", getSoundVolume(), mc.thePlayer.getSoundPitch());
                    addChatMessage((new StringBuilder()).append("\2474[URGENT] \2473").append(entityplayer.username).append(" \2471has tripped your Trip Block!!!").toString());

                    if (Variables.tripBlockCommand != null)
                    {
                        sendChatMessage(Variables.tripBlockCommand);
                    }

                    tbWait = entityplayer;
                    continue;
                }

                if (tbWait == entityplayer)
                {
                    tbWait = null;
                }
            }
        }

        if (!Variables.alreadyCheckedForNoCheat)
        {
            addChatMessage("\247aChecking For NoCheat...");
            sendQueue.addToSendQueue(new Packet3Chat("/nocheat"));
            Variables.alreadyCheckedForNoCheat = true;
            Variables.checkingForPlugins = true;
        }

        if (Variables.flee)
        {
            if (mc.thePlayer.health <= 4 && mc.thePlayer.health > 0 && !sentFlee)
            {
                sentFlee = true;
                sendQueue.addToSendQueue(new Packet3Chat(Variables.hotKey1));
            }

            if (mc.thePlayer.health > 4 && mc.thePlayer.health > 0)
            {
                sentFlee = false;
            }
        }

        if (mc.thePlayer.isDead)
        {
            Variables.deathPosX = (int)Math.round(mc.thePlayer.posX);
            Variables.deathPosZ = (int)Math.round(mc.thePlayer.posZ);
            Variables.deathPosY = (int)Math.round(mc.thePlayer.posY);
        }

        if (Variables.freecam)
        {
            sendQueue.addToSendQueue(new Packet0KeepAlive());
        }else
        {

            Variables.posX = (int)Math.round(mc.thePlayer.posX);
            Variables.posZ = (int)Math.round(mc.thePlayer.posZ);
            Variables.posY = (int)Math.round(mc.thePlayer.posY);

            if (Variables.nuker || Variables.specnuke || Variables.sphere || Variables.carpet || Variables.irregcyl)
            {
                Thread thread = new Thread(new VexMultiPurposeThread());
                thread.start();
            }

            if (Variables.forcefield)
            {
                Thread thread1 = new Thread(new VexKillAura(mc));
                thread1.start();
            }

            if (Variables.aim)
            {
                for (int j = 0; mc.theWorld.loadedEntityList.size() > j; j++)
                {
                    Entity entity = (Entity)mc.theWorld.loadedEntityList.get(j);

                    if ((entity instanceof EntityPlayer) && getDistanceToEntity(entity) < 6F && entity != mc.thePlayer && !GuiIngame.FriendList.contains(((EntityPlayer)entity).username.toLowerCase()))
                    {
                        faceEntity(entity, 100F, 100F);
                    }
                }
            }

            try
            {
                if (Variables.specAim)
                {
                    faceEntity(mc.theWorld.getPlayerEntityByName(Variables.aimPlayer), 100F, 100F);
                }
            }
            catch (Exception exception1) { }

            if (Variables.nofall || Variables.fly || Variables.jump)
            {
                if (Variables.noCheat)
                {
                    mc.thePlayer.fall(1E+009F);
                    sendQueue.addToSendQueue(new Packet11PlayerPosition(motionX, -999D, -999D, motionZ, !onGround));
                }
                else
                {
                    //sendQueue.addToSendQueue(new Packet11PlayerPosition(motionX, -.1, this.posY, motionZ, !onGround));
                }
            }

            if (Variables.derp && !mc.isSingleplayer())
            {
                Random random = new Random();
                rotationYaw = random.nextInt(360) + 1;
                rotationPitch = random.nextInt(180) + 1;
                mc.getSendQueue().addToSendQueue(new Packet12PlayerLook(rotationYaw, rotationPitch, onGround));
                mc.getSendQueue().addToSendQueue(new Packet18Animation(mc.thePlayer, 1));
            }
        if (Variables.sneak)
        {
            sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
            wasSneaking = true;
        }
        else
        {
        //VEX End---------------------------------------------------------------------
        boolean var1 = this.isSprinting();

        if (var1 != this.wasSneaking)
        {
            	if (var1)
            	{
                	this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
            	}
            	else
            	{
            		this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
            	}

            	this.wasSneaking = var1;
        	}
        }

        boolean var2 = this.isSneaking();

        if (var2 != this.shouldStopSneaking)
        {
            if (var2)
            {
                this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
            }
            else
            {
                this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
            }

            this.shouldStopSneaking = var2;
        }

        double var3 = this.posX - this.oldPosX;
        double var5 = this.boundingBox.minY - this.oldMinY;
        double var7 = this.posZ - this.oldPosZ;
        double var9 = (double)(this.rotationYaw - this.oldRotationYaw);
        double var11 = (double)(this.rotationPitch - this.oldRotationPitch);
        boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.field_71168_co >= 20;
        boolean var14 = var9 != 0.0D || var11 != 0.0D;

        //VEX----------
        if (!Variables.freecam);
        
        if (this.ridingEntity != null)
        {
            this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0D, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
            var13 = false;
        }
        else if (var13 && var14)
        {
            this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
        }
        else if (var13)
        {
            this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
        }
        else if (var14)
        {
            this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
        }
        else
        {
            this.sendQueue.addToSendQueue(new Packet10Flying(this.onGround));
        }

        ++this.field_71168_co;
        this.wasOnGround = this.onGround;

        if (var13)
        {
            this.oldPosX = this.posX;
            this.oldMinY = this.boundingBox.minY;
            this.oldPosY = this.posY;
            this.oldPosZ = this.posZ;
            this.field_71168_co = 0;
        }

        if (var14)
        {
            this.oldRotationYaw = this.rotationYaw;
            this.oldRotationPitch = this.rotationPitch;
        }
    }
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem dropOneItem(boolean par1)
    {
        int var2 = par1 ? 3 : 4;
        this.sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
        return null;
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {}

    /**
     * VEX- TONS OF COMMANDSSends a chat message from the player. Args: chatMessage
     */
    public void sendChatMessage(String par1Str)
    {
        String s = par1Str;
        par1Str = par1Str.toLowerCase();

        if (par1Str.startsWith("."))
        {
            par1Str = par1Str.replaceFirst(".", "-");
        }

        if (par1Str.startsWith("-spam"))
        {
            try
            {
                String s1 = "TEAM VEX";
                String s12 = "50000";

                if (par1Str.contains(":"))
                {
                    byte byte0 = 1;

                    if (par1Str.indexOf(":", 6) >= 1)
                    {
                        byte0 = 2;
                    }
                    else
                    {
                        byte0 = 1;
                    }

                    String as5[] = new String[byte0];
                    as5 = par1Str.split(":");
                    s1 = as5[1];

                    if (byte0 == 2)
                    {
                        s12 = as5[2];
                    }

                    int l1 = Integer.parseInt(s12);

                    if (l1 > 0x186a0)
                    {
                        addChatMessage("\2474Please spam less than 100000 times!");
                    }
                    else
                    {
                        Thread thread1 = new Thread(new VexSpam(s1, l1));
                        thread1.start();
                    }
                }

                if (par1Str.equals("-spam"))
                {
                    Thread thread = new Thread(new VexSpam(s1, 50000));
                    thread.start();
                }
            }
            catch (Exception exception)
            {
                addChatMessage((new StringBuilder()).append("\2474Error! \247aCorrect usage = -spam:MESSAGE TO SPAM:NUMBER TO SPAM ").append(exception).toString());
            }
        }
        else if (par1Str.equals("-commands"))
        {
            addChatMessage("\247aVEXATION");
            addChatMessage("\2475--------");
            addChatMessage("\247aENABLED--\2475--DISABLED");
            addChatMessage("\247a(MANDATORY) [OPTIONAL] {INFO}");

            if (Variables.nofall)
            {
                addChatMessage("\247a-nofall");
            }
            else
            {
                addChatMessage("\2475-nofall");
            }

            addChatMessage("\2475-spam:message:#");

            if (Variables.find)
            {
                addChatMessage((new StringBuilder()).append("\247a-find (").append(Variables.findId).append(")").toString());
            }
            else
            {
                addChatMessage("\2475-find (ID)");
            }

            if (Variables.specnuke)
            {
                addChatMessage((new StringBuilder()).append("\247a-nuke: (").append(Variables.snID).append(")").toString());
            }
            else
            {
                addChatMessage("\2475-nuke (ID)");
            }

            if (Variables.noCheat)
            {
                addChatMessage("\247a-nc{NoCheat Protection}");
            }
            else
            {
                addChatMessage("\2475-nc{NoCheat Protection}");
            }

            addChatMessage("\2475-commands 2");
        }
        else if (par1Str.equals("-commands 2"))
        {
            if (Variables.jesus)
            {
                addChatMessage("\247a-jesus");
            }
            else
            {
                addChatMessage("\2475-jesus");
            }

            if (Variables.walk)
            {
                addChatMessage("\247a-walk");
            }
            else
            {
                addChatMessage("\2475-walk");
            }

            if (Variables.name)
            {
                addChatMessage("\247a-name");
            }
            else
            {
                addChatMessage("\2475-name");
            }

            if (Variables.irregcyl)
            {
                addChatMessage("\247a-irregcyl");
            }
            else
            {
                addChatMessage("\2475-irregcyl");
            }

            if (Variables.sphere)
            {
                addChatMessage("\247a-sphere");
            }
            else
            {
                addChatMessage("\2475-sphere");
            }

            if (Variables.fastplace)
            {
                addChatMessage("\247a-fp {Fastplace}");
            }
            else
            {
                addChatMessage("\2475-fp {Fastplace}");
            }

            if (Variables.cords)
            {
                addChatMessage("\247a-cords");
            }
            else
            {
                addChatMessage("\2475-cords");
            }

            if (Variables.locate)
            {
                addChatMessage("\247a-locate");
            }
            else
            {
                addChatMessage("\2475-locate");
            }

            if (Variables.chat)
            {
                addChatMessage("\247a-chat");
            }
            else
            {
                addChatMessage("\2475-chat");
            }

            addChatMessage("\2475-commands 3");
        }
        else if (par1Str.equals("-commands 3"))
        {
            if (Variables.fastmine)
            {
                addChatMessage("\247a-fm {fastmine}");
            }
            else
            {
                addChatMessage("\2475-fm {fastmine}");
            }

            addChatMessage("\2475-minespeed (speed)");

            if (Variables.CaveFinder)
            {
                addChatMessage("\247a-cave");
            }
            else
            {
                addChatMessage("\2475-cave");
            }

            addChatMessage("\2475-tp(Very short distances only!)");

            if (Variables.slow)
            {
                addChatMessage("\247a-slowfly");
            }
            else
            {
                addChatMessage("\2475-slowfly");
            }

            if (Variables.line)
            {
                addChatMessage("\247a-line");
            }
            else
            {
                addChatMessage("\2475-line");
            }

            addChatMessage("\2475-commands 4");
        }
        else if (par1Str.equals("-commands 4"))
        {
            if (Variables.aim)
            {
                addChatMessage((new StringBuilder()).append("\247a-aim [").append(Variables.aimPlayer).append("]").toString());
            }
            else
            {
                addChatMessage("\2475-aim [player]");
            }

            if (Variables.derp)
            {
                addChatMessage("\247a-derp");
            }
            else
            {
                addChatMessage("\2475-derp");
            }

            if (Variables.speedy)
            {
                addChatMessage((new StringBuilder()).append("\247a-speed (").append(Variables.speed).append(")").toString());
            }
            else
            {
                addChatMessage("\2475-speed (Speed #)");
            }

            if (Variables.knockBack)
            {
                addChatMessage("\247a-knockback");
            }
            else
            {
                addChatMessage("\247c-knockback");
            }

            addChatMessage("\247a-death {Death Position}");

            if (Variables.PanicCommand.isEmpty())
            {
                addChatMessage("\247a-setpanic (command)");
            }
            else
            {
                addChatMessage((new StringBuilder()).append("\247a-setpanic (").append(Variables.PanicCommand).append(")").toString());
            }

            if (Variables.mobFind)
            {
                addChatMessage("\247a-mobfind");
            }
            else
            {
                addChatMessage("\2475-mobfind");
            }

            if (Variables.fog)
            {
                addChatMessage("\247a-fog");
            }
            else
            {
                addChatMessage("\2475-fog");
            }

            if (Variables.autoPick)
            {
                addChatMessage("\247a-autopicker");
            }
            else
            {
                addChatMessage("\2475-autopicker");
            }

            addChatMessage("\2475-commands 5");
        }
        else if (par1Str.equals("-commands 5"))
        {
            if (Variables.run)
            {
                addChatMessage("\247a-run");
            }
            else
            {
                addChatMessage("\2475-run");
            }

            if (Variables.forcefield)
            {
                addChatMessage("\247a-kill");
            }
            else
            {
                addChatMessage("\2475-kill");
            }

            addChatMessage("\247a-say");

            if (Variables.flee)
            {
                addChatMessage("\247a-flee");
            }
            else
            {
                addChatMessage("\2475-flee");
            }

            if (Variables.chest)
            {
                addChatMessage("\247a-chest");
            }
            else
            {
                addChatMessage("\2475-chest");
            }

            if (Variables.dungeonChest)
            {
                addChatMessage("\247a-dungeonchest");
            }
            else
            {
                addChatMessage("\2475-dungeonchest");
            }

            if (Variables.chestLine)
            {
                addChatMessage("\247a-chestline");
            }
            else
            {
                addChatMessage("\2475-chestline");
            }

            if (Variables.drops)
            {
                addChatMessage("\247a-drops");
            }
            else
            {
                addChatMessage("\2475-drops");
            }

            addChatMessage("\247a-sexy");
            addChatMessage("\2475-commands 6");
        }
        else if (par1Str.equals("-commands 6"))
        {
            addChatMessage("\247a-colors");

            if (Variables.autoShoot)
            {
                addChatMessage("\247a-autoshoot");
            }
            else
            {
                addChatMessage("\2475-autoshoot");
            }

            if (Variables.Time)
            {
                addChatMessage("\247a-time");
            }
            else
            {
                addChatMessage("\2475-time");
            }

            if (Variables.tripBlock)
            {
                addChatMessage("\247a-tb{Trip Block}");
            }
            else
            {
                addChatMessage("\2475-tb{Trip Block}");
            }

            if (Variables.tripBlockTwo)
            {
                addChatMessage("\247a-tb 2{Trip Block Secondp Position}");
            }
            else
            {
                addChatMessage("\2475-tb 2{Trip Block Second Position}");
            }

            if (Variables.tripBlockCommand != null)
            {
                addChatMessage((new StringBuilder()).append("\247a-tbcommand = ").append(Variables.tripBlockCommand).toString());
            }
            else
            {
                addChatMessage("\2475-tbcommand(COMMAND){Trip Block Command}");
            }

            if (Variables.Console)
            {
                addChatMessage("\247a-console");
            }
            else
            {
                addChatMessage("\2475-console");
            }

            if (Variables.itemDamage)
            {
                addChatMessage("\247a-itemdamage");
            }
            else
            {
                addChatMessage("\2475-itemdamage");
            }

            if (Variables.nopush)
            {
                addChatMessage("\247a-nopush");
            }
            else
            {
                addChatMessage("\2475-nopush");
            }

            addChatMessage("\2475-commands 7");
        }
        else if (par1Str.equals("-commands 7"))
        {
            if (Variables.Zoom)
            {
                addChatMessage("\247a-zoom");
            }
            else
            {
                addChatMessage("\2475-zoom");
            }

            if (Variables.chatNotifier)
            {
                addChatMessage("\247a-notifier");
            }
            else
            {
                addChatMessage("\2475-notifier");
            }

            addChatMessage("\2475-addfriend NAME");
            addChatMessage("\2475-delfriend NAME");
            addChatMessage("\2475-purge friends");
            addChatMessage("\2475-friends");
            addChatMessage("\2475-addenemy NAME");
            addChatMessage("\2475-delenemy NAME");
            addChatMessage("\2475-purge enemies");
            addChatMessage("\2475-enemies");
        }
        else if (par1Str.startsWith("-derp"))
        {
            Variables.derp = !Variables.derp;

            if (Variables.derp)
            {
                addChatMessage("\247aDerp");
            }
            else
            {
                addChatMessage("\247cDerp");
            }
        }
        else if (par1Str.startsWith("-jesus"))
        {
            if (!Variables.noCheat)
            {
                Variables.jesus = !Variables.jesus;

                if (Variables.jesus)
                {
                    addChatMessage("\247ajesus");
                }
                else
                {
                    addChatMessage("\247cjesus");
                }
            }
            else
            {
                addChatMessage("\247cNoCheat does not allow jesus!");
            }
        }
        else if (par1Str.startsWith("-walk"))
        {
            Variables.walk = !Variables.walk;

            if (Variables.walk)
            {
                addChatMessage("\247aWalk");
            }
            else
            {
                addChatMessage("\247cWalk");
            }
        }
        else if (par1Str.startsWith("-nofall"))
        {
            Variables.nofall = !Variables.nofall;

            if (Variables.nofall)
            {
                addChatMessage("\247aNo Fall");
            }
            else
            {
                addChatMessage("\247cNo Fall");
            }
        }
        else if (par1Str.startsWith("-sphere"))
        {
            if (!Variables.noCheat)
            {
                Variables.sphere = !Variables.sphere;

                if (Variables.sphere)
                {
                    addChatMessage("\247aSphere");
                }
                else
                {
                    addChatMessage("\247cSphere");
                }
            }
            else
            {
                addChatMessage("\247cNoCheat does not allow Sphere");
            }
        }
        else if (par1Str.startsWith("-irregcyl"))
        {
            if (!Variables.noCheat)
            {
                Variables.irregcyl = !Variables.irregcyl;

                if (Variables.irregcyl)
                {
                    addChatMessage("\247aIrregularCylender");
                }
                else
                {
                    addChatMessage("\247cIrregularCylender");
                }
            }
            else
            {
                addChatMessage("\247aNoCheat does not allow IrregularCylender");
            }
        }
        else if (par1Str.startsWith("-carpet"))
        {
            if (!Variables.noCheat)
            {
                Variables.carpet = !Variables.carpet;

                if (Variables.carpet)
                {
                    addChatMessage("\247aCarpet");
                }
                else
                {
                    addChatMessage("\247cCarpet");
                }
            }
            else
            {
                addChatMessage("\247aNoCheat does not allow Carpet");
            }
        }
        else if (par1Str.startsWith("-name"))
        {
            Variables.name = !Variables.name;

            if (Variables.name)
            {
                addChatMessage("\247aname");
            }
            else
            {
                addChatMessage("\247cname");
            }
        }
        else if (par1Str.startsWith("-fp"))
        {
            Variables.fastplace = !Variables.fastplace;

            if (Variables.fastplace)
            {
                addChatMessage("\247afastplace");
            }
            else
            {
                addChatMessage("\247cfastplace");
            }
        }
        else if (par1Str.startsWith("-cords"))
        {
            Variables.cords = !Variables.cords;

            if (Variables.cords)
            {
                addChatMessage("\247acords");
            }
            else
            {
                addChatMessage("\247ccords");
            }
        }
        else if (par1Str.startsWith("-follow"))
        {
            Variables.follow = !Variables.follow;

            if (Variables.follow)
            {
                addChatMessage("\247afollow");
            }
            else
            {
                addChatMessage("\247cfollow");
            }
        }
        else if (par1Str.startsWith("-locate"))
        {
            Variables.locate = !Variables.locate;

            if (Variables.locate)
            {
                addChatMessage("\247alocate");
            }
            else
            {
                addChatMessage("\247clocate");
            }
        }
        else if (par1Str.startsWith("-slowfly"))
        {
            if (!Variables.noCheat)
            {
                if (Variables.slow)
                {
                    if (!Variables.AlreadyFlying)
                    {
                        Variables.fly = false;
                    }
                }
                else if (Variables.fly)
                {
                    Variables.AlreadyFlying = true;
                }
                else
                {
                    Variables.fly = true;
                }

                Variables.slow = !Variables.slow;

                if (Variables.slow)
                {
                    addChatMessage("\247aslowfly");
                }
                else
                {
                    addChatMessage("\247cslowfly");
                }
            }
            else
            {
                addChatMessage("\247cNoCheat does not allow slowfly");
            }
        }
        else if (par1Str.startsWith("-goto"))
        {
            if (!Variables.noCheat)
            {
                if (Variables.GOTO)
                {
                    Variables.GOTO = false;
                }
                else
                {
                    String s2 = par1Str.replaceAll("-goto ", "");
                    int l;
                    int k1;

                    if (s2.equals("death"))
                    {
                        l = (int)Variables.deathPosX;
                        k1 = (int)Variables.deathPosZ;
                    }
                    else
                    {
                        String as6[] = par1Str.split(" ");
                        String s18 = as6[1];
                        String s19 = as6[2];
                        l = Integer.parseInt(s18);
                        k1 = Integer.parseInt(s19);
                    }

                    GOTOZ = k1;
                    GOTOX = l;
                    Variables.GOTO = true;
                    Variables.fly = true;
                    addChatMessage((new StringBuilder()).append("\247aGoing to \2475X: ").append(l).append("\247a Z: ").append(k1).toString());
                }
            }
            else
            {
                addChatMessage("\247cNoCheat does not allow GoTO");
            }
        }
        else if (par1Str.startsWith("-test"))
        {
            Variables.test = !Variables.test;

            if (Variables.test)
            {
                addChatMessage("\247atest");
            }
            else
            {
                addChatMessage("\247ctest");
            }
        }
        else if (par1Str.startsWith("-chat"))
        {
            Variables.chat = !Variables.chat;

            if (Variables.chat)
            {
                addChatMessage("\247achat");
            }
            else
            {
                addChatMessage("\247cchat");
            }
        }
        else if (par1Str.startsWith("-fm"))
        {
            Variables.fastmine = !Variables.fastmine;

            if (Variables.fastmine)
            {
                if (Variables.minespeed <= 1.0F)
                {
                    Variables.minespeed = 1.3F;
                }

                addChatMessage("\247afastmine");
            }
            else
            {
                addChatMessage("\247cfastmine");
            }
        }
        else if (par1Str.startsWith("-minespeed"))
        {
            try
            {
                if (par1Str.contains(" "))
                {
                    Variables.fastmine = true;
                    String s3 = par1Str.replaceAll("-minespeed ", "");
                    System.out.println(par1Str);
                    float f = Float.parseFloat(s3);
                    Variables.minespeed = f;
                }
                else
                {
                    addChatMessage("\247cProper syntax = '-minespeed (#)'");
                }
            }
            catch (Exception exception1)
            {
                addChatMessage("\247cProper syntax = '-minespeed (#)'");
                System.out.println(exception1);
            }

            addChatMessage((new StringBuilder()).append("\247aMine speed set to: ").append(Variables.minespeed).toString());
        }
        else if (par1Str.startsWith("-tp"))
        {
            if (Variables.freecam)
            {
                Variables.freecam = false;
                Variables.fly = false;
                addChatMessage("\247aTeleporting");
            }
            else
            {
                addChatMessage("\247cYou must be in freecam!");
            }
        }
        else if (par1Str.startsWith("-cave"))
        {
            Variables.CaveFinder = !Variables.CaveFinder;
            mc.renderGlobal.loadRenderers();

            if (Variables.CaveFinder)
            {
                addChatMessage("\247aCave Finder");
            }
            else
            {
                addChatMessage("\247cCave Finder");
            }
        }
        else if (par1Str.startsWith("-addfriend "))
        {
            String s4 = par1Str.replaceAll("-addfriend ", "");
            GuiIngame.FriendList.add(s4.toLowerCase());
            Variables.saveFriendsList();
            addChatMessage((new StringBuilder()).append("\247a").append(s4).append(" added to friends!").toString());
            updateCloak();
        }
        else if (par1Str.startsWith("-delfriend "))
        {
            String s5 = par1Str.replaceAll("-delfriend ", "");

            if (GuiIngame.FriendList.contains(s5.toLowerCase()))
            {
                GuiIngame.FriendList.remove(s5);
                Variables.saveFriendsList();
                updateCloak();
                addChatMessage((new StringBuilder()).append("\247a").append(s5).append(" has been removed from friends!").toString());
            }
            else
            {
                addChatMessage((new StringBuilder()).append("\247cYou are not friends with ").append(s5).append("!").toString());
            }
        }
        else if (par1Str.startsWith("-friends"))
        {
            addChatMessage((new StringBuilder()).append("\2472Friends: \247f").append(GuiIngame.FriendList.toString()).toString());
        }
        else if (par1Str.startsWith("-addenemy "))
        {
            String s6 = par1Str.replaceAll("-addenemy ", "");
            GuiIngame.EnemyList.add(s6.toLowerCase());
            Variables.saveEnemyList();
            addChatMessage((new StringBuilder()).append("\247a").append(s6).append(" added to enemies!").toString());
            updateCloak();
        }
        else if (par1Str.startsWith("-delenemy "))
        {
            String s7 = par1Str.replaceAll("-delenemy ", "");

            if (GuiIngame.EnemyList.contains(s7.toLowerCase()))
            {
                GuiIngame.EnemyList.remove(s7);
                Variables.saveEnemyList();
                updateCloak();
                addChatMessage((new StringBuilder()).append("\247a").append(s7).append(" has been removed from enemies!").toString());
            }
            else
            {
                addChatMessage((new StringBuilder()).append("\247cYou are not enemies with ").append(s7).append("!").toString());
            }
        }
        else if (par1Str.startsWith("-enemies"))
        {
            addChatMessage((new StringBuilder()).append("\2475Enemies: \247f").append(GuiIngame.EnemyList.toString()).toString());
        }
        else if (par1Str.startsWith("-swastika"))
        {
            if (Variables.noCheat)
            {
                Variables.placeSwastika = !Variables.placeSwastika;

                if (Variables.placeSwastika)
                {
                    addChatMessage("\247aSwastika!");
                }
                else
                {
                    addChatMessage("\247cSwastika!");
                }
            }
            else
            {
                addChatMessage("\247cNoCheat does not allow Swastika!");
            }
        }
        else if (par1Str.startsWith("-flip"))
        {
            Variables.flip = !Variables.flip;
            addChatMessage("\247aFlipping auto-places");
        }
        else if (par1Str.startsWith("-line"))
        {
            if (!Variables.line)
            {
                if (!mc.gameSettings.viewBobbing)
                {
                    Variables.LineViewBobbing = false;
                }
                else
                {
                    mc.gameSettings.viewBobbing = false;
                    Variables.LineViewBobbing = true;
                }
            }
            else if (Variables.LineViewBobbing)
            {
                mc.gameSettings.viewBobbing = true;
            }
            else
            {
                mc.gameSettings.viewBobbing = false;
            }

            Variables.line = !Variables.line;

            if (Variables.line)
            {
                addChatMessage("\247aLine");
            }
            else
            {
                addChatMessage("\247cLine");
            }
        }
        else if (par1Str.startsWith("-aim"))
        {
            if (par1Str.contains(" "))
            {
                String s8 = par1Str.replaceAll("-aim ", "");
                Variables.aimPlayer = s8;
                Variables.specAim = true;
                Variables.aim = false;
                addChatMessage("\247aaim");
            }
            else if (Variables.specAim)
            {
                Variables.specAim = false;
                addChatMessage("\247caim");
            }
            else
            {
                Variables.aim = !Variables.aim;

                if (Variables.aim)
                {
                    addChatMessage("\247aaim");
                }
                else
                {
                    addChatMessage("\247caim");
                }
            }
        }
        else if (par1Str.startsWith("-deathcords"))
        {
            addChatMessage((new StringBuilder()).append("\247aYou Died at \2474 X: ").append(Variables.deathPosX).append("\2475 Y: ").append(Variables.deathPosY).append("\2472 Z: ").append(Variables.deathPosZ).toString());
        }
        else if (par1Str.startsWith("-xp"))
        {
            EntityXPOrb entityxporb = null;
            sendQueue.addToSendQueue(new Packet26EntityExpOrb(entityxporb));
        }
        else if (par1Str.startsWith("-speed"))
        {
            if (!Variables.noCheat)
            {
                try
                {
                    if (par1Str.contains(" "))
                    {
                        Variables.speedy = true;
                        String s9 = par1Str.replaceAll("-speed ", "");
                        int i1 = Integer.parseInt(s9);
                        if(i1 > 1)
                        	Variables.speed = i1;
                        else
                        {
                        	Variables.speed = 2;
                        	Variables.speedy = false;
                        }
                    }
                    else
                    {
                        addChatMessage("\247cProper syntax = '-speed (#)'");
                    }
                }
                catch (Exception exception2)
                {
                    addChatMessage("\247cProper syntax = '-speed (#)'");
                    System.out.println(exception2);
                }

                addChatMessage((new StringBuilder()).append("\247aSpeed set to: ").append(Variables.speed).toString());
            }
            else
            {
                addChatMessage("\247cNoCheat does not allow Speed Hack");
            }
        }
        else if (par1Str.startsWith("-setpanic "))
        {
            try
            {
                String s10 = par1Str.replaceAll("-setpanic ", "");
                System.out.println(s10);
                Variables.PanicCommand = s10;
                Variables.saveVexOptions();
                addChatMessage((new StringBuilder()).append("\247a").append(s10).append("\247a has been set to your panic command!").toString());
            }
            catch (Exception exception3)
            {
                addChatMessage("\247cError!");
            }
        }
        else if (par1Str.startsWith("-findmob"))
        {
            Variables.mobFind = !Variables.mobFind;
            mc.renderGlobal.loadRenderers();

            if (Variables.mobFind)
            {
                addChatMessage("\247aFinding Mobs");
            }
            else
            {
                addChatMessage("\247cFind Mob");
            }
        }
        else if (par1Str.startsWith("-fog"))
        {
            Variables.fog = !Variables.fog;

            if (Variables.fog)
            {
                addChatMessage("\247aFog");
            }
            else
            {
                addChatMessage("\247cFog");
            }
        }
        else if (par1Str.startsWith("-autopicker"))
        {
            Variables.autoPick = !Variables.autoPick;

            if (Variables.autoPick)
            {
                addChatMessage("\247aAuto Tool Picker");
            }
            else
            {
                addChatMessage("\247cAuto Tool Picker");
            }
        }
        else if (par1Str.startsWith("-run"))
        {
            Variables.run = !Variables.run;

            if (Variables.run)
            {
                addChatMessage("\247aRUN");
            }
            else
            {
                addChatMessage("\247cRUN");
            }
        }
        else if (par1Str.startsWith("-kill"))
        {
            if (!Variables.noCheat)
            {
                Variables.forcefield = !Variables.forcefield;

                if (Variables.forcefield)
                {
                    addChatMessage("\247aKill");
                }
                else
                {
                    addChatMessage("\247cKill");
                }
            }
            else
            {
                addChatMessage("\2474NoCheat does not allow Kill");
            }
        }
        else if (par1Str.startsWith("-say "))
        {
            s = par1Str.replace("-say ", "");
            sendQueue.addToSendQueue(new Packet3Chat(s));
        }
        else if (par1Str.startsWith("-flee"))
        {
            Variables.flee = !Variables.flee;

            if (Variables.flee)
            {
                addChatMessage("\247aHealth Flee (Sends Hot Key 1 if health is below 2 hearts)");
            }
            else
            {
                addChatMessage("\247cHealth Flee");
            }
        }
        else if (par1Str.equals("-chest"))
        {
            Variables.chest = !Variables.chest;

            if (Variables.chest)
            {
                addChatMessage("\247aChest");
            }
            else
            {
                addChatMessage("\247cChest");
            }
        }
        else if (par1Str.startsWith("-dungeonchest"))
        {
            Variables.dungeonChest = !Variables.dungeonChest;

            if (Variables.dungeonChest)
            {
            	Variables.chest = true;
                addChatMessage("\247aDungeon Chest");
            }
            else
            {
                addChatMessage("\247cDungeon Chest");
            }
        }
        else if (par1Str.startsWith("-drops"))
        {
            Variables.drops = !Variables.drops;

            if (Variables.drops)
            {
                addChatMessage("\247aDrops");
            }
            else
            {
                addChatMessage("\247cDrops");
            }
        }
        else if (par1Str.startsWith("-cn"))
        {
            try
            {
                par1Str = par1Str.replace("-cn ", "");
                String as[] = new String[2];
                as = par1Str.split(" ");
                String s13 = null;

                try
                {
                    URL url = new URL((new StringBuilder()).append("http://login.minecraft.net/?user=").append(as[0]).append("&password=").append(as[1]).append("&version=12").toString());
                    URLConnection urlconnection = url.openConnection();
                    urlconnection.setReadTimeout(5000);
                    urlconnection.setDoOutput(true);
                    BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
                    s13 = bufferedreader.readLine();
                    bufferedreader.close();
                }
                catch (Exception exception7)
                {
                    System.out.println(exception7);
                }

                mc.session.username = as[0];
                //TODO mc.session.mpPassParameter = as[1];
                String as4[] = new String[4];
                as4 = s13.split(":");
                mc.session.sessionId = as4[3];
            }
            catch (Exception exception4) { }
        }
        else if (par1Str.startsWith("-sexy"))
        {
            if (!Variables.noCheat)
            {
                Random random = new Random(0x7bf7d3L);
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2475\247k").append("XXXXXXXX".substring(0, random.nextInt(4) + 3)).toString()));
            }
            else
            {
                addChatMessage("\2474NoCheat does not allow SEXY");
            }
        }
        else if (par1Str.startsWith("-nc") || par1Str.startsWith("-nocheat"))
        {
            Variables.noCheat = !Variables.noCheat;

            if (Variables.noCheat)
            {
                addChatMessage("\247aNoCheat");
            }
            else
            {
                addChatMessage("\247cNoCheat");
            }
        }
        else if (par1Str.startsWith("-box"))
        {
            Variables.playerBox = !Variables.playerBox;

            if (Variables.playerBox)
            {
                addChatMessage("\247aBox");
            }
            else
            {
                addChatMessage("\247cBox");
            }
        }
        else if (par1Str.startsWith("-colors"))
        {
            addChatMessage("\2472Avalible Colors:");
            addChatMessage("\2470-black");
            addChatMessage("\2471-blue");
            addChatMessage("\2472-green");
            addChatMessage("\2473-cyan");
            addChatMessage("\2474-red");
            addChatMessage("\2475-purple");
            addChatMessage("\2476-gold");
            addChatMessage("\2477-gray");
            addChatMessage("\2478-darkgray");
            addChatMessage("\2479-lightblue");
            addChatMessage("\247a-lightgreen");
            addChatMessage("\247b-aqua");
            addChatMessage("\247c-lightred");
            addChatMessage("\247d-pink");
            addChatMessage("\247e-yellow");
            addChatMessage("\247f-white");
        }
        else if (par1Str.startsWith("-red"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-red ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2474").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -red!");
            }
        }
        else if (par1Str.startsWith("-blue"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-blue ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2471").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -blue!");
            }
        }
        else if (par1Str.startsWith("-green"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-green ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2472").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -green!");
            }
        }
        else if (par1Str.startsWith("-cyan"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-cyan ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2473").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -cyan!");
            }
        }
        else if (par1Str.startsWith("-purple"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-purple ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2475").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -purple!");
            }
        }
        else if (par1Str.startsWith("-gold"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-gold ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2476").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -gold!");
            }
        }
        else if (par1Str.startsWith("-gray"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-gray ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2477").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -gray!");
            }
        }
        else if (par1Str.startsWith("-darkgray"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-darkgray ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2478").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -darkgray!");
            }
        }
        else if (par1Str.startsWith("-lightblue"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-lightblue ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\2479").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -lightblue!");
            }
        }
        else if (par1Str.startsWith("-lightgreen"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-lightgreen ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\247a").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -lightgreen!");
            }
        }
        else if (par1Str.startsWith("-aqua"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-aqua ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\247b").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -aqua!");
            }
        }
        else if (par1Str.startsWith("-lightred"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-lightred ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\247c").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -lightred!");
            }
        }
        else if (par1Str.startsWith("-lightred"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-lightred ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\247c").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -lightred!");
            }
        }
        else if (par1Str.startsWith("-pink"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-pink ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\247d").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -pink!");
            }
        }
        else if (par1Str.startsWith("-yellow"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-yellow ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\247e").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -yellow!");
            }
        }
        else if (par1Str.startsWith("-white"))
        {
            if (par1Str.contains(" "))
            {
                s = s.replace("-white ", "");
                sendQueue.addToSendQueue(new Packet3Chat((new StringBuilder()).append("\247f").append(s).toString()));
            }
            else
            {
                addChatMessage("\2474A message must follow -white!");
            }
        }
        else if (par1Str.startsWith("-chestline"))
        {
            Variables.chestLine = !Variables.chestLine;

            if (Variables.chestLine)
            {
                addChatMessage("\247aChest Lines");
            }
            else
            {
                addChatMessage("\247cChest Lines");
            }
        }
        else if (par1Str.startsWith("-autoshoot"))
        {
            Variables.autoShoot = !Variables.autoShoot;

            if (Variables.autoShoot)
            {
                addChatMessage("\247aAuto Shoot");
            }
            else
            {
                addChatMessage("\247cAuto Shoot");
            }
        }
        else if (par1Str.startsWith("-time"))
        {
            Variables.Time = !Variables.Time;

            if (Variables.Time)
            {
                addChatMessage("\247aTime");
            }
            else
            {
                addChatMessage("\247cTime");
            }
        }
        else if (par1Str.equals("-tb"))
        {
            Variables.TBX = (int)mc.thePlayer.posX;
            Variables.TBY = (int)mc.thePlayer.posY;
            Variables.TBZ = (int)mc.thePlayer.posZ;
            Variables.tripBlock = !Variables.tripBlock;

            if (Variables.tripBlock)
            {
                Variables.saveTripBlockCords();
                addChatMessage((new StringBuilder()).append("\247aTrip Block set at X: ").append(Variables.TBX).append(" Y: ").append(Variables.TBY).append(" Z: ").append(Variables.TBZ).toString());
            }
            else
            {
                Variables.deleteTripBlock();
                addChatMessage("\247cTrip Block");
            }
        }
        else if (par1Str.startsWith("-tb 2"))
        {
            if (!Variables.tripBlock)
            {
                addChatMessage("\2474You first must select your first location with \2473\"-tb\"");
            }
            else
            {
                Variables.tripBlockTwo = true;
                Variables.TBX2 = (int)mc.thePlayer.posX;
                Variables.TBY2 = (int)mc.thePlayer.posY;
                Variables.TBZ2 = (int)mc.thePlayer.posZ;

                if (Variables.TBX > Variables.TBX2)
                {
                    int i = Variables.TBX;
                    Variables.TBX = Variables.TBX2;
                    Variables.TBX2 = i;
                }

                if (Variables.TBY > Variables.TBY2)
                {
                    int j = Variables.TBY;
                    Variables.TBY = Variables.TBY2;
                    Variables.TBY2 = j;
                }

                if (Variables.TBZ > Variables.TBZ2)
                {
                    int k = Variables.TBZ;
                    Variables.TBZ = Variables.TBZ2;
                    Variables.TBZ2 = k;
                }

                Variables.saveTripBlockCords();
                addChatMessage((new StringBuilder()).append("\247aSecond Trip Block location set at X: ").append(Variables.TBX2).append(" Y: ").append(Variables.TBY2).append(" Z: ").append(Variables.TBZ2).toString());
            }
        }
        else if (par1Str.startsWith("-tbcommand "))
        {
            String as1[] = par1Str.split(" ");
            Variables.tripBlockCommand = as1[1];
            addChatMessage((new StringBuilder()).append("\247aTrip Block Command set to \2473").append(Variables.tripBlockCommand).toString());
        }
        else if (par1Str.equals("-knockback"))
        {
            Variables.knockBack = !Variables.knockBack;

            if (Variables.knockBack)
            {
                addChatMessage("\247aKnock Back");
            }
            else
            {
                addChatMessage("\247cKnock Back");
            }
        }
        else if (par1Str.equals("-console"))
        {
            Variables.Console = !Variables.Console;

            if (Variables.Console)
            {
                addChatMessage("\247aConsole");
            }
            else
            {
                addChatMessage("\247cConsole");
            }
        }
        else if (par1Str.equals("-itemdamage"))
        {
            Variables.itemDamage = !Variables.itemDamage;

            if (Variables.itemDamage)
            {
                addChatMessage("\247aItem Damage");
            }
            else
            {
                addChatMessage("\247cItem Damage");
            }
        }
        else if (par1Str.equals("-nopush"))
        {
            if (!Variables.noCheat)
            {
                Variables.nopush = !Variables.nopush;

                if (Variables.nopush)
                {
                    addChatMessage("\247aNo Push");
                }
                else
                {
                    addChatMessage("\247cNo Push");
                }
            }
            else
            {
                addChatMessage("\2474NoCheat does not allow No Push");
            }
        }
        else if (par1Str.equals("-zoom"))
        {
            Variables.Zoom = !Variables.Zoom;

            if (Variables.Zoom)
            {
                addChatMessage("\247aZoom");
            }
            else
            {
                addChatMessage("\247cZoom");
            }
        }
        else if (par1Str.equals("-notifier"))
        {
            Variables.chatNotifier = !Variables.chatNotifier;

            if (Variables.chatNotifier)
            {
                addChatMessage("\247aChat Name Notifier");
            }
            else
            {
                addChatMessage("\247cChat Name Notifier");
            }
        }
        else if (par1Str.equals("-purge friends"))
        {
            GuiIngame.FriendList.clear();
            Variables.saveFriendsList();
            addChatMessage("\247aAll players removed from friends list");
        }
        else if (par1Str.equals("-purge enemies"))
        {
            GuiIngame.EnemyList.clear();
            Variables.saveEnemyList();
            addChatMessage("\247aAll players removed from enemy list");
        }
        else if (par1Str.startsWith("-find"))
        {
            try
            {
                if (par1Str.contains(" "))
                {
                    String s11 = par1Str.replaceAll("-find ", "");
                    s11 = s11.toLowerCase();
                    s11 = s11.replaceAll("s", "");
                    s11 = s11.replaceAll("diamond", "56");
                    s11 = s11.replaceAll("coal", "16");
                    s11 = s11.replaceAll("iron", "15");
                    s11 = s11.replaceAll("gold", "14");
                    s11 = s11.replaceAll("redtone", "73");
                    s11 = s11.replaceAll("mobpawner", "52");
                    s11 = s11.replaceAll("chet", "54");
                    s11 = s11.replaceAll("goldblock", "41");
                    s11 = s11.replaceAll("ironblock", "42");
                    s11 = s11.replaceAll("diamondblock", "57");
                    s11 = s11.replaceAll("furnace", "61");
                    s11 = s11.replaceAll("dungeon", "52, 48");
                    s11 = s11.replaceAll("dirt", "3");
                    s11 = s11.replaceAll("gra", "2");
                    s11 = s11.replaceAll("tone", "1");
                    s11 = s11.replaceAll("cobbletone", "4");
                    s11 = s11.replaceAll("wood", "5");
                    s11 = s11.replaceAll("log", "17");
                    s11 = s11.replaceAll("and", "12");
                    s11 = s11.replaceAll("gravel", "13");
                    s11 = s11.replaceAll("craftingtable", "58");
                    s11 = s11.replaceAll("workbench", "58");
                    s11 = s11.replaceAll("obidian", "49");
                    s11 = s11.replaceAll("torch", "50");
                    s11 = s11.replaceAll("redtonetorch", "76, 75");
                    s11 = s11.replaceAll("redtonedut", "55");
                    s11 = s11.replaceAll("redtonewire", "55");
                    s11 = s11.replaceAll("cactu", "81");
                    s11 = s11.replaceAll("ice", "79");
                    s11 = s11.replaceAll("nowblock", "80");
                    s11 = s11.replaceAll("now", "78");
                    s11 = s11.replaceAll("jukebox", "84");
                    s11 = s11.replaceAll("jukeboxe", "84");
                    s11 = s11.replaceAll("fence", "85");
                    s11 = s11.replaceAll("wheat", "59");
                    s11 = s11.replaceAll("crop", "59");
                    s11 = s11.replaceAll("reed", "83");
                    s11 = s11.replaceAll("ugarcane", "83");
                    s11 = s11.replaceAll("ugar", "83");

                    if (s11.contains(", "))
                    {
                        String as3[] = new String[2];
                        as3 = s11.split(", ");
                        String s15 = as3[0];
                        String s17 = as3[1];
                        int i2 = Integer.parseInt(s15);
                        int j2 = Integer.parseInt(s17);
                        Variables.findId = i2;
                        Variables.findId2 = j2;
                        Variables.find = true;
                        mc.renderGlobal.loadRenderers();
                        addChatMessage((new StringBuilder()).append("\247afinding ").append(i2).append("and").append(j2).toString());
                    }
                    else if (s11.startsWith("houe"))
                    {
                        Variables.Xray = true;
                        Variables.findHouse = true;
                        mc.renderGlobal.loadRenderers();
                        addChatMessage("\247afinding houses");
                    }
                    else
                    {
                        int j1 = Integer.parseInt(s11);
                        Variables.findId = j1;
                        mc.renderGlobal.loadRenderers();
                        Variables.find = true;
                        addChatMessage((new StringBuilder()).append("\247afinding ").append(j1).toString());
                    }
                }
                else if (par1Str.equals("-find"))
                {
                    Variables.Xray = false;
                    Variables.findId = 0;
                    Variables.findId2 = 0;
                    Variables.findHouse = false;
                    Variables.find = false;
                    mc.renderGlobal.loadRenderers();
                    addChatMessage("\247cfind");
                }
            }
            catch (Exception exception5)
            {
                System.out.println(exception5);
                addChatMessage("\2474Error! Item ID must follow -find!");
            }
        }
        else if (par1Str.startsWith("-nuke"))
        {
            if (!Variables.noCheat)
            {
                if (par1Str.contains(" "))
                {
                    try
                    {
                        Variables.specnuke = true;
                        String as2[] = par1Str.split(" ");
                        String s14 = as2[1];
                        as2[1] = as2[1].toLowerCase();
                        as2[1] = as2[1].replaceAll("diamond", "56");
                        as2[1] = as2[1].replaceAll("coal", "16");
                        as2[1] = as2[1].replaceAll("iron", "15");
                        as2[1] = as2[1].replaceAll("gold", "14");
                        as2[1] = as2[1].replaceAll("redstone", "73");
                        as2[1] = as2[1].replaceAll("mobspawner", "52");
                        as2[1] = as2[1].replaceAll("redstone", "73");
                        as2[1] = as2[1].replaceAll("chests", "54");
                        as2[1] = as2[1].replaceAll("goldblock", "41");
                        as2[1] = as2[1].replaceAll("ironblock", "42");
                        as2[1] = as2[1].replaceAll("diamondblock", "57");
                        as2[1] = as2[1].replaceAll("furnace", "61");
                        as2[1] = as2[1].replaceAll("dirt", "3");
                        as2[1] = as2[1].replaceAll("grass", "2");
                        as2[1] = as2[1].replaceAll("stone", "1");
                        as2[1] = as2[1].replaceAll("cobblestone", "4");
                        as2[1] = as2[1].replaceAll("wood", "5");
                        as2[1] = as2[1].replaceAll("logs", "17");
                        as2[1] = as2[1].replaceAll("sand", "12");
                        as2[1] = as2[1].replaceAll("gravel", "13");
                        as2[1] = as2[1].replaceAll("craftingtable", "58");
                        as2[1] = as2[1].replaceAll("workbench", "58");
                        as2[1] = as2[1].replaceAll("obsidian", "49");
                        as2[1] = as2[1].replaceAll("torch", "50");
                        as2[1] = as2[1].replaceAll("redstonetorch", "76,75");
                        as2[1] = as2[1].replaceAll("redstonedust", "55");
                        as2[1] = as2[1].replaceAll("redstonewire", "55");
                        as2[1] = as2[1].replaceAll("cactus", "81");
                        as2[1] = as2[1].replaceAll("ice", "79");
                        as2[1] = as2[1].replaceAll("snowblock", "80");
                        as2[1] = as2[1].replaceAll("snow", "78");
                        as2[1] = as2[1].replaceAll("jukeboxes", "84");
                        as2[1] = as2[1].replaceAll("fence", "85");
                        as2[1] = as2[1].replaceAll("wheat", "59");
                        as2[1] = as2[1].replaceAll("crop", "59");
                        as2[1] = as2[1].replaceAll("reed", "83");
                        as2[1] = as2[1].replaceAll("ugarcane", "83");
                        as2[1] = as2[1].replaceAll("ugar", "83");
                        as2[1] = as2[1].replaceAll("tallgra", "31");
                        String s16 = as2[1];
                        addChatMessage((new StringBuilder()).append("\247aNukeing ").append(s14).toString());
                        Variables.snID = Integer.parseInt(s16);
                    }
                    catch (Exception exception6)
                    {
                        addChatMessage((new StringBuilder()).append("\247cERROR!:").append(exception6).toString());
                    }
                }
                else
                {
                    Variables.specnuke = false;
                    addChatMessage("\247cNuker");
                }
            }
            else
            {
                addChatMessage("\247cNoCheat does not allow Nuker");
            }
        }
        else if (par1Str.startsWith("-"))
        {
            addChatMessage("\2474Type \"-commands\" for help!");
        }
        else
        {
            this.sendQueue.addToSendQueue(new Packet3Chat(s));
        }
    }

    /**
     * VEX- only sends packet if noswing is off. Swings the item the player is holding.
     */
    public void swingItem()
    {
        super.swingItem();
        //VEX
        if (!Variables.noswing)
        {
            this.sendQueue.addToSendQueue(new Packet18Animation(this, 1));
        }
    }

    public void respawnPlayer()
    {
        this.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource par1DamageSource, int par2)
    {
        if (!this.isEntityInvulnerable())
        {
            this.setEntityHealth(this.getHealth() - par2);
        }
    }

    /**
     * sets current screen to null (used on escape buttons of GUIs)
     */
    public void closeScreen()
    {
        this.sendQueue.addToSendQueue(new Packet101CloseWindow(this.openContainer.windowId));
        this.func_92015_f();
    }

    public void func_92015_f()
    {
        this.inventory.setItemStack((ItemStack)null);
        super.closeScreen();
    }

    /**
     * Updates health locally.
     */
    public void setHealth(int par1)
    {
        if (this.hasSetHealth)
        {
            super.setHealth(par1);
        }
        else
        {
            this.setEntityHealth(par1);
            this.hasSetHealth = true;
        }
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase != null)
        {
            if (par1StatBase.isIndependent)
            {
                super.addStat(par1StatBase, par2);
            }
        }
    }

    /**
     * Used by NetClientHandler.handleStatistic
     */
    public void incrementStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase != null)
        {
            if (!par1StatBase.isIndependent)
            {
                super.addStat(par1StatBase, par2);
            }
        }
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities()
    {
        this.sendQueue.addToSendQueue(new Packet202PlayerAbilities(this.capabilities));
    }

    public boolean func_71066_bF()
    {
        return true;
    }
}
