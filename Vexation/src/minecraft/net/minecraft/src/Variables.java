package net.minecraft.src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class Variables
{
    public static String version = "0.0.7";
    private static File VexOptions;
    private static File TripBlockCords;
    private static File TBServer;
    public static String PanicCommand = "";
    public static ArrayList ChangelogArray = new ArrayList();
    public static ArrayList bannedPlayers = new ArrayList()
    {
    }
    ;
    public static ArrayList allowedPlayers = new ArrayList()
    {
    }
    ;
    public static String consoleText[] =
    {
        "commands [Page #]: Lists all avaliable commands", "nofall: Removes all fall damage", "spam:message:#: Spams the chat with (message) (#) times", "find [Item ID]: X-ray for a specific block", "nuke [Item ID]: Nukes a specific block", "nc: Disables all hacks not allowed by nocheat", "jesus: Allows you to walk and place on water and lava(not suggested)", "walk: Removes movement limitations such as web, ice, soulsand, and water", "name: Large nametags over player's heads", "irregcyl: Places blocks ontop of any blocks that are sticking up near you",
        "sphere: Creates a sphere around you", "carpet: creates a carpet of blocks under your feet", "fp: (Fast Place) place blocks faster", "cords: Shows your current coordinates on the top of the screen", "time: Toggle time display on screen", "locate: shows close players and their distances", "chat: toggles the chat box", "fm: (Fast Mine) Mine faster", "minespeed [Speed]: Change the speed at which you mine", "cave: Find caves",
        "tp: Teleports to your current freecam position(Very short distances only)", "slowfly: Flt, but slower!", "line: draw lines to nearby players and/or chests and/or drops", "box: Toggles player's outline box", "chest: Displays chests with a colored box and on the radar(does not include dungeon chests)", "dungeonchest: Displays dungeon chests with a black box and on the radar", "chestline: Draws lines to nearby chests if line is on", "drops: Draws lines to nearby drops if line is on", "aim [player]: Aims at any nearby player, or specified player", "derp: Herp",
        "speed [Speed]: Change your speed", "death: returns your last death position", "setpanic [command]: Sets the command that will be sent if you hit\"panic\"", "findmob: Shows mobs through walls", "fog: toggles void fog", "autopicker: Automaticly switches your tool to the most efficient tool", "run: Sprint... but faster...", "kill: Kill any mobs that come close to you", "say [message]: Allows you to chat a message that begins with \"-\" or \".\"", "flee: Automaticly sends hotkey 1 if your health drops below 2 hearts",
        "sexy: Adds a sexy message to the chat", "colors: Lists all avaliable colors", "autoshoot: Automaticly shoots the bow quickly", "tb: Sets your first Trip Block location(Cuboid)", "tb 2: Sets your second Trip Block location(Cuboid)", "tbcommand [Command]: Sets a command to be sent when your Trip Block is tripped", "knockback: Punch mobs farther away", "console: Toggle console predictions", "itemdamage: Toggles item damage display", "addenemy [Enemy's Username]: Adds a player to your enemy list",
        "delenemy [Enemy's Username]: Deletes a player to your enemy list", "addfriend [Friend's Username]: Adds a player to your friendlist", "delfriend [Friend's Username]: Deletes a friend from your friendlist", "purge friends: Deletes all players from your friends list", "purge enemies: Deletes all players from your enemies list", "enemies: Displays your enemy list", "friends: Lists all of your friends in your friendlist", "nopush: Other players can not push you", "zoom: Zoom in and out with the scroll wheel", "notifier: Notifys you if your name is said in chat"
    };
    static boolean VEXGUI = true;
    static boolean kaistep = false;
    static boolean speedy = false;
    static boolean jump = false;
    static boolean fly = false;
    static boolean sneak = false;
    static boolean fullbright = false;
    static boolean nofall = true;
    static boolean forcefield = false;
    static boolean noswing = false;
    static boolean derp = false;
    static boolean spam = false;
    static boolean Xray;
    static boolean find;
    static int findId;
    static int findId2;
    static boolean climb = false;
    static boolean nuker = false;
    static boolean specnuke = false;
    static boolean noweather = false;
    static boolean jesus = false;
    static boolean sphere = false;
    static boolean irregcyl = false;
    static boolean carpet = false;
    static boolean name = true;
    public static boolean fastplace = false;
    static boolean cords = true;
    static boolean follow = false;
    static boolean reach = false;
    static boolean radar = false;
    public static int radarX = 200;
    public static int radarY = 75;
    static boolean locate = false;
    static boolean slow = false;
    static boolean GOTO = false;
    static boolean freecam = false;
    static boolean test = false;
    static boolean chat = false;
    static boolean AlreadyFlying = false;
    static boolean pause = false;
    static boolean fastmine = false;
    static float minespeed = 1.0F;
    static boolean line = false;
    static double posX = 0.0D;
    static double posZ = 0.0D;
    static double posY = 0.0D;
    static double fcX = 0.0D;
    static double fcZ = 0.0D;
    static double fcY = 0.0D;
    static boolean CaveFinder;
    static boolean give = false;
    public static String username = "";
    public static boolean nopaidcheck = false;
    public static boolean reconnect = false;
    public static boolean placeSwastika = false;
    public static boolean aim = false;
    public static String aimPlayer;
    public static boolean LineViewBobbing = false;
    public static boolean specAim = false;
    public static boolean flip = true;
    public static String originalName;
    public static int speed = 1;
    public static double deathPosX;
    public static double deathPosY;
    public static double deathPosZ;
    public static boolean mobFind = false;
    public static boolean fog = false;
    public static boolean autoPick = true;
    public static boolean findHouse = false;
    public static boolean run = false;
    public static double jumpHeight = 1.0D;
    static boolean Translucent;
    public static int opacity = 90;
    public static boolean clear;
    public static String hotKey1 = "";
    public static String hotKey2 = "";
    public static String hotKey3 = "";
    public static String hotKey4 = "";
    public static boolean Panic = false;
    static boolean speedMode1;
    static boolean speedMode2;
    public static boolean eat = true;
    public static boolean flee = false;
    public static boolean chest = false;
    public static boolean noCheat = false;
    public static boolean dungeonChest = false;
    public static boolean checkingForPlugins = false;
    public static boolean alreadyCheckedForNoCheat = false;
    public static boolean drops = false;
    public static boolean walk = true;
    public static boolean chestLine = false;
    public static boolean autoShoot = false;
    public static boolean outDated = false;
    public static String newVersion;
    public static String downloadURL;
    public static String Changelog;
    public static boolean CheckVersionAgain;
    public static String MOTD = "Thank you for using Vexation";
    public static boolean Time = true;
    public static boolean tripBlock = false;
    public static boolean tripBlockTwo = false;
    public static int TBX;
    public static int TBY;
    public static int TBZ;
    public static int TBX2;
    public static int TBY2;
    public static int TBZ2;
    public static String tripBlockCommand;
    public static boolean Compass = true;
    public static boolean knockBack = false;
    public static int snID;
    public static boolean Console = true;
    public static boolean Day = false;
    public static boolean itemDamage = true;
    public static boolean playerBox = false;
    static boolean nopush = true;
    public static float zoomAmount = 0.5F;
    public static boolean Zoom = false;
    public static boolean chatNotifier = true;

    public Variables()
    {
        LoadVexOptions();
        loadFriendList();
    }

    public static void LoadVexOptions()
    {
    	System.out.println("LOADING VEX OPTIONS");
        try
        {
            File file = new File(Minecraft.theMinecraft.mcDataDir, "Vexation");

            if (!file.exists())
            {
                boolean flag = file.mkdirs();

                if (flag)
                {
                    System.out.println("VexDir created");
                }
                else
                {
                    System.out.println("VexDir creation failed!");
                }
            }

            GameSettings _tmp = Minecraft.theMinecraft.gameSettings;
            TBServer = new File(Minecraft.theMinecraft.mcDataDir, (new StringBuilder()).append("Vexation\\Trip Block Saves\\").append(GameSettings.lastServer).append(".txt").toString());
            VexOptions = new File(Minecraft.theMinecraft.mcDataDir, "Vexation\\VexOptions.txt");
            TripBlockCords = new File(Minecraft.theMinecraft.mcDataDir, "Vexation\\TripBlockCords.txt");

            if (!VexOptions.exists())
            {
                VexOptions.createNewFile();
            }

            BufferedReader bufferedreader = new BufferedReader(new FileReader(VexOptions));

            for (String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                try
                {
                    String as[] = s.split(":");

                    if (as[0].equals("Panic Command"))
                    {
                        PanicCommand = as[1];
                    }

                    if (as[0].equals("Hot Key 1"))
                    {
                        hotKey1 = as[1];
                    }

                    if (as[0].equals("Hot Key 2"))
                    {
                        hotKey2 = as[1];
                    }

                    if (as[0].equals("Hot Key 3"))
                    {
                        hotKey3 = as[1];
                    }

                    if (as[0].equals("Hot Key 4"))
                    {
                        hotKey4 = as[1];
                    }

                    if (as[0].equals("VEXGUI"))
                    {
                        VEXGUI = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Step"))
                    {
                        kaistep = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Speed Hack"))
                    {
                        speedy = Boolean.parseBoolean(as[1]);
                    }
                    
                    if (as[0].equals("Speed"))
                    {
                        speed = Integer.parseInt(as[1]);
                    }

                    if (as[0].equals("High Jump"))
                    {
                        VEXGUI = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Fly"))
                    {
                        fly = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Sneak"))
                    {
                        sneak = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Full Bright"))
                    {
                        fullbright = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("No Fall"))
                    {
                        nofall = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Kill"))
                    {
                        forcefield = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("No Swing"))
                    {
                        noswing = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Derp"))
                    {
                        derp = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Xray"))
                    {
                        Xray = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Find"))
                    {
                        find = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Climb"))
                    {
                        climb = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Nuke"))
                    {
                        nuker = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("No Weather"))
                    {
                        noweather = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Jesus"))
                    {
                        jesus = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Walk"))
                    {
                        walk = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Sphere"))
                    {
                        sphere = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Irregular Cylender"))
                    {
                        irregcyl = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Carpet"))
                    {
                        carpet = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Name"))
                    {
                        name = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Fast Place"))
                    {
                        fastplace = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Cords"))
                    {
                        cords = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Follow"))
                    {
                        follow = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Radar"))
                    {
                        radar = Boolean.parseBoolean(as[1]);
                    }
                    
                    if (as[0].equals("RadarX"))
                    {
                        radarX = Integer.parseInt(as[1]);
                    }
                    
                    if (as[0].equals("RadarY"))
                    {
                        radarY = Integer.parseInt(as[1]);
                    }

                    if (as[0].equals("Locate"))
                    {
                        locate = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Slow"))
                    {
                        slow = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Freecam"))
                    {
                        freecam = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Chat"))
                    {
                        chat = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Already Flying"))
                    {
                        AlreadyFlying = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Pause"))
                    {
                        pause = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Fast Mine"))
                    {
                        fastmine = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Mine Speed"))
                    {
                        minespeed = Float.parseFloat(as[1]);
                    }

                    if (as[0].equals("Line"))
                    {
                        line = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Freecam X"))
                    {
                        fcX = Double.parseDouble(as[1]);
                    }

                    if (as[0].equals("Freecam Y"))
                    {
                        fcY = Double.parseDouble(as[1]);
                    }

                    if (as[0].equals("Freecam Z"))
                    {
                        fcZ = Double.parseDouble(as[1]);
                    }

                    if (as[0].equals("Cave Finder"))
                    {
                        CaveFinder = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Place Swastika"))
                    {
                        placeSwastika = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Aim"))
                    {
                        aim = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Aim Player"))
                    {
                        aimPlayer = as[1];
                    }

                    if (as[0].equals("Line View Bobbing"))
                    {
                        LineViewBobbing = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Spec Aim"))
                    {
                        specAim = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Flip"))
                    {
                        flip = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Death PosX"))
                    {
                        deathPosX = Double.parseDouble(as[1]);
                    }

                    if (as[0].equals("Death PosY"))
                    {
                        deathPosY = Double.parseDouble(as[1]);
                    }

                    if (as[0].equals("Death PosZ"))
                    {
                        deathPosZ = Double.parseDouble(as[1]);
                    }

                    if (as[0].equals("Mob Find"))
                    {
                        mobFind = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Fog"))
                    {
                        fog = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Auto Picker"))
                    {
                        autoPick = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("House Finder"))
                    {
                        findHouse = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Run"))
                    {
                        run = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Opacity"))
                    {
                        opacity = Integer.parseInt(as[1]);
                    }

                    if (as[0].equals("Clear"))
                    {
                        clear = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Translucent"))
                    {
                        Translucent = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Flee"))
                    {
                        flee = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Chest"))
                    {
                        chest = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Dungeon Chest"))
                    {
                        dungeonChest = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Drops"))
                    {
                        drops = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Auto Shoot"))
                    {
                        autoShoot = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Chest Lines"))
                    {
                        chestLine = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Compass"))
                    {
                        Compass = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Time"))
                    {
                        Time = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Knockback"))
                    {
                        knockBack = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Console"))
                    {
                        Console = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Item Damage"))
                    {
                        itemDamage = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Player Box"))
                    {
                        playerBox = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("No Push"))
                    {
                        nopush = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Zoom"))
                    {
                        Zoom = Boolean.parseBoolean(as[1]);
                    }

                    if (as[0].equals("Zoom Amount"))
                    {
                        zoomAmount = Float.parseFloat(as[1]);
                    }

                    if (as[0].equals("Notifier"))
                    {
                        chatNotifier = Boolean.parseBoolean(as[1]);
                    }

                    int i = 0;

                    while (i < GameSettings.vexkeyBinds.length)
                    {
                        if (as[0].equals((new StringBuilder()).append("VexKey_").append(GameSettings.vexkeyBinds[i].VexkeyDescription).toString()))
                        {
                            GameSettings.vexkeyBinds[i].VexkeyCode = Integer.parseInt(as[1]);
                            VexKeyBind _tmp1 = GameSettings.vexkeyBinds[i];
                            VexKeyBind.resetKeyBindingArrayAndHash();
                        }

                        i++;
                    }
                }
                catch (Exception exception1)
                {
                    System.out.println(exception1);
                    System.out.println((new StringBuilder()).append("Skipping bad option: ").append(s).toString());
                }
            }
        }
        catch (Exception exception)
        {
            System.out.println((new StringBuilder()).append("Failed to load Vex Options:").append(exception).toString());
        }
    }

    public static void saveVexOptions()
    {
    	System.out.println("SAVING VEX OPTIONS");
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(VexOptions));
            printwriter.println("#Vexation Options");
            printwriter.println((new StringBuilder()).append("Version:").append(version).toString());
            printwriter.println((new StringBuilder()).append("Panic Command:").append(PanicCommand).toString());
            printwriter.println((new StringBuilder()).append("Hot Key 1:").append(hotKey1).toString());
            printwriter.println((new StringBuilder()).append("Hot Key 2:").append(hotKey2).toString());
            printwriter.println((new StringBuilder()).append("Hot Key 3:").append(hotKey3).toString());
            printwriter.println((new StringBuilder()).append("Hot Key 4:").append(hotKey4).toString());
            printwriter.println((new StringBuilder()).append("VEXGUI:").append(VEXGUI).toString());
            printwriter.println((new StringBuilder()).append("Step:").append(kaistep).toString());
            printwriter.println((new StringBuilder()).append("Speed Hack:").append(speedy).toString());
            printwriter.println((new StringBuilder()).append("Speed:").append(speed).toString());
            printwriter.println((new StringBuilder()).append("High Jump:").append(jump).toString());
            printwriter.println((new StringBuilder()).append("Fly:").append(fly).toString());
            printwriter.println((new StringBuilder()).append("Sneak:").append(sneak).toString());
            printwriter.println((new StringBuilder()).append("Full Bright:").append(fullbright).toString());
            printwriter.println((new StringBuilder()).append("No Fall:").append(nofall).toString());
            printwriter.println((new StringBuilder()).append("Kill:").append(forcefield).toString());
            printwriter.println((new StringBuilder()).append("No Swing:").append(noswing).toString());
            printwriter.println((new StringBuilder()).append("Derp:").append(derp).toString());
            printwriter.println((new StringBuilder()).append("Xray:").append(Xray).toString());
            printwriter.println((new StringBuilder()).append("Find:").append(find).toString());
            printwriter.println((new StringBuilder()).append("Climb:").append(climb).toString());
            printwriter.println((new StringBuilder()).append("Nuke:").append(nuker).toString());
            printwriter.println((new StringBuilder()).append("No Weather:").append(noweather).toString());
            printwriter.println((new StringBuilder()).append("Jesus:").append(jesus).toString());
            printwriter.println((new StringBuilder()).append("Walk:").append(walk).toString());
            printwriter.println((new StringBuilder()).append("Sphere:").append(sphere).toString());
            printwriter.println((new StringBuilder()).append("Irregular Cylender:").append(irregcyl).toString());
            printwriter.println((new StringBuilder()).append("Carpet:").append(carpet).toString());
            printwriter.println((new StringBuilder()).append("Name:").append(name).toString());
            printwriter.println((new StringBuilder()).append("Fast Place:").append(fastplace).toString());
            printwriter.println((new StringBuilder()).append("Cords:").append(cords).toString());
            printwriter.println((new StringBuilder()).append("Follow:").append(follow).toString());
            printwriter.println((new StringBuilder()).append("Radar:").append(radar).toString());
            printwriter.println((new StringBuilder()).append("RadarX:").append(radarX).toString());
            printwriter.println((new StringBuilder()).append("RadarY:").append(radarY).toString());
            printwriter.println((new StringBuilder()).append("Locate:").append(locate).toString());
            printwriter.println((new StringBuilder()).append("Slow:").append(slow).toString());
            printwriter.println((new StringBuilder()).append("Freecam:").append(freecam).toString());
            printwriter.println((new StringBuilder()).append("Chat:").append(chat).toString());
            printwriter.println((new StringBuilder()).append("Already Flying:").append(AlreadyFlying).toString());
            printwriter.println((new StringBuilder()).append("Pause:").append(pause).toString());
            printwriter.println((new StringBuilder()).append("Fast Mine:").append(fastmine).toString());
            printwriter.println((new StringBuilder()).append("Mine Speed:").append(minespeed).toString());
            printwriter.println((new StringBuilder()).append("Line:").append(line).toString());
            printwriter.println((new StringBuilder()).append("Freecam X:").append(fcX).toString());
            printwriter.println((new StringBuilder()).append("Freecam Y:").append(fcY).toString());
            printwriter.println((new StringBuilder()).append("Freecam Z:").append(fcZ).toString());
            printwriter.println((new StringBuilder()).append("Cave Finder:").append(CaveFinder).toString());
            printwriter.println((new StringBuilder()).append("Place Swastika:").append(placeSwastika).toString());
            printwriter.println((new StringBuilder()).append("Aim:").append(aim).toString());
            printwriter.println((new StringBuilder()).append("Aim Player:").append(aimPlayer).toString());
            printwriter.println((new StringBuilder()).append("Line View Bobbing:").append(LineViewBobbing).toString());
            printwriter.println((new StringBuilder()).append("Spec Aim:").append(specAim).toString());
            printwriter.println((new StringBuilder()).append("Flip:").append(flip).toString());
            printwriter.println((new StringBuilder()).append("Death PosX:").append(deathPosX).toString());
            printwriter.println((new StringBuilder()).append("Death PosY:").append(deathPosY).toString());
            printwriter.println((new StringBuilder()).append("Death PosZ:").append(deathPosZ).toString());
            printwriter.println((new StringBuilder()).append("Mob Find:").append(mobFind).toString());
            printwriter.println((new StringBuilder()).append("Fog:").append(fog).toString());
            printwriter.println((new StringBuilder()).append("Auto Picker:").append(autoPick).toString());
            printwriter.println((new StringBuilder()).append("House Finder:").append(findHouse).toString());
            printwriter.println((new StringBuilder()).append("Run:").append(run).toString());
            printwriter.println((new StringBuilder()).append("Opacity:").append(opacity).toString());
            printwriter.println((new StringBuilder()).append("Clear:").append(clear).toString());
            printwriter.println((new StringBuilder()).append("Translucent:").append(Translucent).toString());
            printwriter.println((new StringBuilder()).append("Flee:").append(flee).toString());
            printwriter.println((new StringBuilder()).append("Chest:").append(chest).toString());
            printwriter.println((new StringBuilder()).append("Dungeon Chest:").append(dungeonChest).toString());
            printwriter.println((new StringBuilder()).append("Drops:").append(drops).toString());
            printwriter.println((new StringBuilder()).append("Chest Lines:").append(chestLine).toString());
            printwriter.println((new StringBuilder()).append("Auto Shoot:").append(autoShoot).toString());
            printwriter.println((new StringBuilder()).append("Time:").append(Time).toString());
            printwriter.println((new StringBuilder()).append("Compass:").append(Compass).toString());
            printwriter.println((new StringBuilder()).append("Knockback:").append(knockBack).toString());
            printwriter.println((new StringBuilder()).append("Console:").append(Console).toString());
            printwriter.println((new StringBuilder()).append("Item Damage:").append(itemDamage).toString());
            printwriter.println((new StringBuilder()).append("Player Box:").append(playerBox).toString());
            printwriter.println((new StringBuilder()).append("No Push:").append(nopush).toString());
            printwriter.println((new StringBuilder()).append("Zoom:").append(Zoom).toString());
            printwriter.println((new StringBuilder()).append("Zoom Amount:").append(zoomAmount).toString());
            printwriter.println((new StringBuilder()).append("Notifier:").append(chatNotifier).toString());

            for (int i = 0; i < GameSettings.vexkeyBinds.length; i++)
            {
                printwriter.println((new StringBuilder()).append("VexKey_").append(GameSettings.vexkeyBinds[i].VexkeyDescription).append(":").append(GameSettings.vexkeyBinds[i].VexkeyCode).toString());
            }

            printwriter.close();
        }
        catch (Exception exception)
        {
            System.out.println((new StringBuilder()).append("Failed to save vex options:").append(exception).toString());
            exception.printStackTrace();
        }
    }

    public static void saveTripBlockCords()
    {
        try
        {
            File file = new File(Minecraft.theMinecraft.mcDataDir, "Vexation");
            File file1 = new File(file, "Trip Block Saves");

            if (!file1.exists())
            {
                boolean flag = file1.mkdir();

                if (flag)
                {
                    System.out.println("TBDir created");
                }
                else
                {
                    System.out.println("TBDir creation failed!");
                }
            }

            PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(TBServer)));

            if (tripBlock)
            {
                if (tripBlockTwo)
                {
                    printwriter.println((new StringBuilder()).append(TBX).append(":").append(TBY).append(":").append(TBZ).append(":").append(TBX2).append(":").append(TBY2).append(":").append(TBZ2).toString());
                }
                else
                {
                    printwriter.println((new StringBuilder()).append(TBX).append(":").append(TBY).append(":").append(TBZ).toString());
                }
            }

            printwriter.close();
        }
        catch (Exception exception)
        {
            System.out.println((new StringBuilder()).append("Failed to save Trip Block Cords: ").append(exception).toString());
        }
    }

    public static void loadTripBlockCords()
    {
        try
        {
            if (!TripBlockCords.exists())
            {
                TripBlockCords.createNewFile();
            }

            BufferedReader bufferedreader = new BufferedReader(new FileReader(TBServer));

            if (TBServer.exists())
            {
                for (String s = ""; (s = bufferedreader.readLine()) != null;)
                {
                    String as[] = s.split(":");
                    tripBlock = true;

                    if (as[5] != null)
                    {
                        TBX = Integer.parseInt(as[0]);
                        TBY = Integer.parseInt(as[1]);
                        TBZ = Integer.parseInt(as[2]);
                        TBX2 = Integer.parseInt(as[3]);
                        TBY2 = Integer.parseInt(as[4]);
                        TBZ2 = Integer.parseInt(as[5]);
                        tripBlockTwo = true;
                    }
                    else
                    {
                        TBX = Integer.parseInt(as[0]);
                        TBY = Integer.parseInt(as[2]);
                        TBZ = Integer.parseInt(as[3]);
                        tripBlockTwo = false;
                    }
                }
            }
        }
        catch (Exception exception) { }
    }

    public static void deleteTripBlock()
    {
        if (TBServer.exists())
        {
            boolean flag = TBServer.delete();
            System.out.println((new StringBuilder()).append(TBServer).append(" has been deleted = ").append(flag).toString());
        }
    }

    public static void loadFriendList()
    {
        try
        {
            File file = new File(Minecraft.theMinecraft.mcDataDir, "Vexation\\Friends.txt");

            if (file.exists())
            {
                System.out.println("reading friends list");
                BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
                String s;

                for (int i = 0; (s = bufferedreader.readLine()) != null; i++)
                {
                    if (!GuiIngame.FriendList.contains(s))
                    {
                        GuiIngame.FriendList.add(s);
                    }
                }
            }
            else
            {
                System.out.println("Creating friends list");
                saveFriendsList();
                return;
            }
        }
        catch (Exception exception)
        {
            System.err.print(exception.toString());
        }
    }

    public static void saveFriendsList()
    {
        try
        {
            File file = new File(Minecraft.theMinecraft.mcDataDir, "Vexation");

            if (!file.exists())
            {
                boolean flag = file.mkdir();

                if (flag)
                {
                    System.out.println("VexDir created");
                }
                else
                {
                    System.out.println("VexDir creation failed!");
                }
            }

            File file1 = new File(Minecraft.theMinecraft.mcDataDir, "Vexation\\Friends.txt");
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file1));

            for (int i = 0; i < GuiIngame.FriendList.size(); i++)
            {
                bufferedwriter.write((new StringBuilder()).append((String)GuiIngame.FriendList.get(i)).append("\r\n").toString());
            }

            bufferedwriter.close();
        }
        catch (Exception exception)
        {
            System.err.print(exception.toString());
        }
    }

    public static void saveEnemyList()
    {
        try
        {
            File file = new File(Minecraft.theMinecraft.mcDataDir, "Vexation");

            if (!file.exists())
            {
                boolean flag = file.mkdir();

                if (flag)
                {
                    System.out.println("VexDir created");
                }
                else
                {
                    System.out.println("VexDir creation failed!");
                }
            }

            File file1 = new File(Minecraft.theMinecraft.mcDataDir, "Vexation\\Enemies.txt");
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file1));

            for (int i = 0; i < GuiIngame.EnemyList.size(); i++)
            {
                bufferedwriter.write((new StringBuilder()).append((String)GuiIngame.EnemyList.get(i)).append("\r\n").toString());
            }

            bufferedwriter.close();
        }
        catch (Exception exception)
        {
            System.err.print(exception.toString());
        }
    }

    public static void loadEnemyList()
    {
        try
        {
            File file = new File(Minecraft.theMinecraft.mcDataDir, "Vexation\\Enemies.txt");

            if (file.exists())
            {
                System.out.println("reading enemies list");
                BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
                String s;

                for (int i = 0; (s = bufferedreader.readLine()) != null; i++)
                {
                    if (!GuiIngame.EnemyList.contains(s))
                    {
                        GuiIngame.EnemyList.add(s);
                    }
                }
            }
            else
            {
                System.out.println("Creating enemies list");
                saveEnemyList();
                return;
            }
        }
        catch (Exception exception)
        {
            System.err.print(exception.toString());
        }
    }

    public static void readChangeLog(boolean flag)
    {
        boolean flag1 = false;

        try
        {
            File file = new File(Minecraft.theMinecraft.mcDataDir, "Vexation\\Changelog.txt");

            if (file.exists())
            {
                System.out.println("reading changelog");
                BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
                String s;

                for (int i = 0; (s = bufferedreader.readLine()) != null; i++)
                {
                    if (s.contains(version) && flag)
                    {
                        flag1 = true;
                    }

                    ChangelogArray.add(s);
                }

                if (!flag1 && flag)
                {
                    System.out.println("Updating Changelog");
                    Thread thread1 = new Thread(new VexDownload(false, true));
                    thread1.start();
                }
            }
            else
            {
                Thread thread = new Thread(new VexDownload(false, true));
                thread.start();
            }
        }
        catch (Exception exception)
        {
            System.err.print(exception.toString());
        }
    }
    
    public static void updateChangelogLines(GuiScreen gui)
    {
    	ArrayList tempClArray = new ArrayList();
    	for(int i = 0; i < ChangelogArray.toArray().length; i++)
    	{
    		String s = (String)ChangelogArray.toArray()[i];
    		if (gui.fontRenderer.getStringWidth(s) > gui.width - 254)
    		{
    			List lineList = gui.fontRenderer.listFormattedStringToWidth(s, gui.width - 254);
    			for(int j = 0; j < lineList.toArray().length; j++)
    			{
    				ChangelogArray.add(lineList.get(j));
    			}
    		}else
    		{
    			tempClArray.add(ChangelogArray.get(i));
    		}
    	}
    	ChangelogArray = tempClArray;
    }

    public static void turnOffNocheatHacks()
    {
        speedy = false;
        jump = false;
        fly = false;
        sneak = false;
        nofall = false;
        noswing = false;
        climb = false;
        nuker = false;
        specnuke = false;
        jesus = false;
        sphere = false;
        irregcyl = false;
        carpet = false;
        follow = false;
        slow = false;
        GOTO = false;
        AlreadyFlying = false;
        pause = false;
        placeSwastika = false;
        speed = 1;
        jumpHeight = 1.0D;
        walk = false;
        nopush = false;
        Minecraft.theMinecraft.renderGlobal.loadRenderers();
    }

    static
    {
        Xray = false;
        find = false;
        CaveFinder = false;
        Translucent = (Xray || find || CaveFinder) && !clear;
        clear = false;
        speedMode1 = false;
        speedMode2 = !speedMode1;
    }
}
