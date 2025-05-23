package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.Packet10Flying;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;

public class NoFall extends Module {
   public NoFall() {
      super("NoFall", 0, Module.Category.MOVEMENT);
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet10Flying) {
         Packet10Flying p = (Packet10Flying)e.getPacket();
         if (this.mc.thePlayer.fallDistance >= 2.0F) {
            p.onGround = true;
         }
      }

   }
}
