package net.minecraft.src.MEDMEX.Modules.Combat;

import net.minecraft.src.Packet28EntityVelocity;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;

public class AntiKB extends Module {
   public static AntiKB instance;

   public AntiKB() {
      super("AntiKB", 0, Module.Category.COMBAT);
      instance = this;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet28EntityVelocity) {
         Packet28EntityVelocity packet = (Packet28EntityVelocity)e.getPacket();
         if (packet.entityId == this.mc.thePlayer.entityId) {
            e.setCancelled(true);
         }
      }

   }
}
