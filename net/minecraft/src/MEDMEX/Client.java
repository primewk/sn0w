package net.minecraft.src.MEDMEX;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Packet;
import net.minecraft.src.MEDMEX.Commands.CommandManager;
import net.minecraft.src.MEDMEX.Config.Config;
import net.minecraft.src.MEDMEX.Config.ConfigAlts;
import net.minecraft.src.MEDMEX.Config.ConfigAuthme;
import net.minecraft.src.MEDMEX.Config.ConfigFriends;
import net.minecraft.src.MEDMEX.Config.ConfigServer;
import net.minecraft.src.MEDMEX.Config.ConfigWaypoints;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Event.listeners.EventChat;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Modules.Client.$nowmatica;
import net.minecraft.src.MEDMEX.Modules.Client.AutoAuthme;
import net.minecraft.src.MEDMEX.Modules.Client.AutoReconnect;
import net.minecraft.src.MEDMEX.Modules.Client.ChatTime;
import net.minecraft.src.MEDMEX.Modules.Client.ClickGUI;
import net.minecraft.src.MEDMEX.Modules.Client.MCF;
import net.minecraft.src.MEDMEX.Modules.Combat.AntiKB;
import net.minecraft.src.MEDMEX.Modules.Combat.AutoObsidian;
import net.minecraft.src.MEDMEX.Modules.Combat.BedAura;
import net.minecraft.src.MEDMEX.Modules.Combat.CombatLog;
import net.minecraft.src.MEDMEX.Modules.Combat.ForceField;
import net.minecraft.src.MEDMEX.Modules.Combat.KillSults;
import net.minecraft.src.MEDMEX.Modules.Movement.AutoWalk;
import net.minecraft.src.MEDMEX.Modules.Movement.Blink;
import net.minecraft.src.MEDMEX.Modules.Movement.Fat;
import net.minecraft.src.MEDMEX.Modules.Movement.Fly;
import net.minecraft.src.MEDMEX.Modules.Movement.InventoryMove;
import net.minecraft.src.MEDMEX.Modules.Movement.Jesus;
import net.minecraft.src.MEDMEX.Modules.Movement.NoClip;
import net.minecraft.src.MEDMEX.Modules.Movement.NoFall;
import net.minecraft.src.MEDMEX.Modules.Movement.Phase;
import net.minecraft.src.MEDMEX.Modules.Movement.Speed;
import net.minecraft.src.MEDMEX.Modules.Movement.Spider;
import net.minecraft.src.MEDMEX.Modules.Movement.Step;
import net.minecraft.src.MEDMEX.Modules.Movement.TargetStrafe;
import net.minecraft.src.MEDMEX.Modules.Movement.Timer;
import net.minecraft.src.MEDMEX.Modules.Movement.VelocityManip;
import net.minecraft.src.MEDMEX.Modules.Player.AntiDesync;
import net.minecraft.src.MEDMEX.Modules.Player.AntiPacketKick;
import net.minecraft.src.MEDMEX.Modules.Player.AutoEat;
import net.minecraft.src.MEDMEX.Modules.Player.AutoTool;
import net.minecraft.src.MEDMEX.Modules.Player.Backfill;
import net.minecraft.src.MEDMEX.Modules.Player.FastPlace;
import net.minecraft.src.MEDMEX.Modules.Player.FastPortal;
import net.minecraft.src.MEDMEX.Modules.Player.Freecam;
import net.minecraft.src.MEDMEX.Modules.Player.MoveInfs;
import net.minecraft.src.MEDMEX.Modules.Player.NoCollide;
import net.minecraft.src.MEDMEX.Modules.Player.SecretChat;
import net.minecraft.src.MEDMEX.Modules.Player.SleepWalk;
import net.minecraft.src.MEDMEX.Modules.Player.Yaw;
import net.minecraft.src.MEDMEX.Modules.Render.ArmorStatus;
import net.minecraft.src.MEDMEX.Modules.Render.BreakProgress;
import net.minecraft.src.MEDMEX.Modules.Render.Chams;
import net.minecraft.src.MEDMEX.Modules.Render.ChestESP;
import net.minecraft.src.MEDMEX.Modules.Render.Coords;
import net.minecraft.src.MEDMEX.Modules.Render.ESP;
import net.minecraft.src.MEDMEX.Modules.Render.Fullbright;
import net.minecraft.src.MEDMEX.Modules.Render.Nametags;
import net.minecraft.src.MEDMEX.Modules.Render.NewChunks;
import net.minecraft.src.MEDMEX.Modules.Render.NoHurtCam;
import net.minecraft.src.MEDMEX.Modules.Render.NoRender;
import net.minecraft.src.MEDMEX.Modules.Render.Radar;
import net.minecraft.src.MEDMEX.Modules.Render.Search;
import net.minecraft.src.MEDMEX.Modules.Render.Shader;
import net.minecraft.src.MEDMEX.Modules.Render.TabList;
import net.minecraft.src.MEDMEX.Modules.Render.Tracers;
import net.minecraft.src.MEDMEX.Modules.Render.ViewClip;
import net.minecraft.src.MEDMEX.Modules.Render.Waypoints;
import net.minecraft.src.MEDMEX.Modules.Render.Xray;
import net.minecraft.src.MEDMEX.Modules.World.AutoHighway;
import net.minecraft.src.MEDMEX.Modules.World.AutoTNT;
import net.minecraft.src.MEDMEX.Modules.World.AutoTunnel;
import net.minecraft.src.MEDMEX.Modules.World.BucketFix;
import net.minecraft.src.MEDMEX.Modules.World.ColoredSigns;
import net.minecraft.src.MEDMEX.Modules.World.FastDrop;
import net.minecraft.src.MEDMEX.Modules.World.InstaMine;
import net.minecraft.src.MEDMEX.Modules.World.ItemDeleter;
import net.minecraft.src.MEDMEX.Modules.World.LiquidInteract;
import net.minecraft.src.MEDMEX.Modules.World.NoWeather;
import net.minecraft.src.MEDMEX.Modules.World.Nuker;
import net.minecraft.src.MEDMEX.Modules.World.PacketCancel;
import net.minecraft.src.MEDMEX.Modules.World.PacketLogger;
import net.minecraft.src.MEDMEX.Modules.World.Packetmine;
import net.minecraft.src.MEDMEX.Modules.World.Scaffold;
import net.minecraft.src.MEDMEX.Modules.World.Tower;
import net.minecraft.src.MEDMEX.Modules.World.VisualRange;
import net.minecraft.src.MEDMEX.UI.HUD;
import net.minecraft.src.MEDMEX.Utils.StorageUtils;
import net.minecraft.src.MEDMEX.altman.AltManager;
import net.minecraft.src.MEDMEX.serverman.ServerManager;
import net.minecraft.src.de.Hero.settings.SettingsManager;

public class Client {
   public static int protocolver = 14;
   public static String name = "$now";
   public static String version = "1";
   public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList();
   public static CopyOnWriteArrayList<String> friends = new CopyOnWriteArrayList();
   public static CopyOnWriteArrayList<String> authme = new CopyOnWriteArrayList();
   public static CopyOnWriteArrayList<Module> drawn = new CopyOnWriteArrayList();
   public static HUD hud = new HUD();
   public static CommandManager commandManager = new CommandManager();
   public static SettingsManager settingsmanager;
   public static AltManager altManager;
   public static ServerManager serverManager;

   public static void startup() {
      settingsmanager = new SettingsManager();
      modules.add(new Fly());
      modules.add(new Fullbright());
      modules.add(new Packetmine());
      modules.add(new Freecam());
      modules.add(new Coords());
      modules.add(new Xray());
      modules.add(new ColoredSigns());
      modules.add(new Jesus());
      modules.add(new Scaffold());
      modules.add(new NoFall());
      modules.add(new ClickGUI());
      modules.add(new ArmorStatus());
      modules.add(new Waypoints());
      modules.add(new AutoEat());
      modules.add(new ForceField());
      modules.add(new NoClip());
      modules.add(new ViewClip());
      modules.add(new ChestESP());
      modules.add(new VisualRange());
      modules.add(new Nametags());
      modules.add(new NewChunks());
      modules.add(new ESP());
      modules.add(new InventoryMove());
      modules.add(new AntiKB());
      modules.add(new FastPlace());
      modules.add(new NoWeather());
      modules.add(new Step());
      modules.add(new FastDrop());
      modules.add(new NoRender());
      modules.add(new Timer());
      modules.add(new VelocityManip());
      modules.add(new Tracers());
      modules.add(new Search());
      modules.add(new SecretChat());
      modules.add(new TabList());
      modules.add(new ItemDeleter());
      modules.add(new InstaMine());
      modules.add(new Speed());
      modules.add(new MCF());
      modules.add(new NoCollide());
      modules.add(new BedAura());
      modules.add(new AutoObsidian());
      modules.add(new KillSults());
      modules.add(new Nuker());
      modules.add(new LiquidInteract());
      modules.add(new TargetStrafe());
      modules.add(new Chams());
      modules.add(new Tower());
      modules.add(new AutoTunnel());
      modules.add(new AutoWalk());
      modules.add(new Yaw());
      modules.add(new AutoAuthme());
      modules.add(new AutoReconnect());
      modules.add(new CombatLog());
      modules.add(new BreakProgress());
      modules.add(new AntiDesync());
      modules.add(new NoHurtCam());
      modules.add(new ChatTime());
      modules.add(new Backfill());
      modules.add(new PacketCancel());
      modules.add(new PacketLogger());
      modules.add(new BucketFix());
      modules.add(new AutoHighway());
      modules.add(new AutoTNT());
      modules.add(new AutoTool());
      modules.add(new Fat());
      modules.add(new Blink());
      modules.add(new FastPortal());
      modules.add(new Radar());
      modules.add(new SleepWalk());
      modules.add(new Spider());
      modules.add(new MoveInfs());
      modules.add(new Shader());
      modules.add(new Phase());
      modules.add(new AntiPacketKick());
      modules.add(new $nowmatica());
      StorageUtils.loadConfig();
      Config.loadConfig();
      ConfigAlts.load();
      ConfigWaypoints.load();
      ConfigFriends.load();
      ConfigAuthme.load();
      ConfigServer.load();
      System.out.println("Loading " + name + " " + version);
   }

   public static void onEvent(Event e) {
      if (e instanceof EventChat) {
         commandManager.handleChat((EventChat)e);
      }

      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (m.toggled) {
            m.onEvent(e);
         }
      }

   }

   public static void sendPacket(Packet p) {
      Minecraft.theMinecraft.getSendQueue().addToSendQueue(p);
   }

   public static void keyPress(int key) {
      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (key == m.getKey()) {
            m.toggle();
         }
      }

   }

   public static String onMessage(String s) {
      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         m.onMessage(s);
      }

      return s;
   }

   public static void onRenderGUI() {
      Iterator var1 = modules.iterator();

      while(var1.hasNext()) {
         Module m = (Module)var1.next();
         if (m.toggled) {
            m.onRenderGUI();
         }
      }

   }

   public static void onRenderEntities() {
      Iterator var1 = modules.iterator();

      while(var1.hasNext()) {
         Module m = (Module)var1.next();
         if (m.toggled) {
            m.onRender();
         }
      }

   }

   public static void onLog(String bound, String p) {
      Iterator var3 = modules.iterator();

      while(var3.hasNext()) {
         Module m = (Module)var3.next();
         if (m.toggled) {
            m.onLog(bound, p);
         }
      }

   }

   public static void getPacket(EventPacket e) {
      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (m.toggled) {
            m.getPacket(e);
         }
      }

   }

   public static List<Module> getModuleByCategory(Module.Category c) {
      List<Module> modules = new ArrayList();
      Iterator var3 = Client.modules.iterator();

      while(var3.hasNext()) {
         Module m = (Module)var3.next();
         if (m.category == c) {
            modules.add(m);
         }
      }

      return modules;
   }

   public static List<Module> getModules() {
      List<Module> modules = new ArrayList();
      Iterator var2 = Client.modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         modules.add(m);
      }

      return modules;
   }

   public static Module getModuleByName(String s) {
      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (m.name.equalsIgnoreCase(s)) {
            return m;
         }
      }

      return null;
   }

   public static void addChatMessage(String message) {
      message = "§b[§6Y§b]§r " + message;
      Minecraft.theMinecraft.thePlayer.addChatMessage(new String(message));
   }
}
