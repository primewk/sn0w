package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.Packet17Sleep;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;

public class SleepWalk extends Module {
   public static SleepWalk instance;

   public SleepWalk() {
      super("SleepWalk", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet17Sleep) {
         e.setCancelled(true);
      }

   }
}
