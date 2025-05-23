package net.minecraft.src.MEDMEX.Modules.Movement;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.GuiDownloadTerrain;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class Blink extends Module {
   public static Blink instance;
   public boolean shouldCancel = true;
   public CopyOnWriteArrayList<Packet10Flying> backedPackets = new CopyOnWriteArrayList();

   public Blink() {
      super("Blink", 0, Module.Category.MOVEMENT);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Blink Delay", this, 1.0D, 20.0D, 100.0D, true));
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && !(this.mc.currentScreen instanceof GuiDownloadTerrain) && this.shouldCancel && e.getPacket() instanceof Packet10Flying) {
         Packet10Flying p = (Packet10Flying)e.getPacket();
         this.backedPackets.add(p);
         e.setCancelled(true);
      }

   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (this.mc.thePlayer.ticksExisted % 15 == 0) {
            Client.sendPacket(new Packet0KeepAlive());
         }

         if (!this.backedPackets.isEmpty()) {
            this.attribute = " §7[§f" + this.backedPackets.size() + "§7]";
         }

         if ((double)this.backedPackets.size() > Client.settingsmanager.getSettingByName("Blink Delay").getValDouble()) {
            this.shouldCancel = false;
            Iterator var3 = this.backedPackets.iterator();

            while(var3.hasNext()) {
               Packet10Flying p = (Packet10Flying)var3.next();
               Client.sendPacket(p);
            }

            this.backedPackets.clear();
            this.shouldCancel = true;
         }
      }

   }

   public void onEnable() {
      this.shouldCancel = true;
   }

   public void onDisable() {
      this.shouldCancel = false;
   }
}
