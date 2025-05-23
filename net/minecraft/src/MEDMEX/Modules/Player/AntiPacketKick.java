package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.Packet10Flying;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class AntiPacketKick extends Module {
   public static AntiPacketKick instance;

   public AntiPacketKick() {
      super("AntiPacketKick", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Max Backlog", this, 80.0D, 0.0D, 200.0D, true));
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && (double)this.mc.getSendQueue().netManager.sendQueueByteLength >= this.getSet("Max Backlog").getValDouble() && !(e.getPacket() instanceof Packet10Flying)) {
         e.setCancelled(true);
      }

   }
}
