package net.minecraft.src.MEDMEX.Modules.Movement;

import java.util.Iterator;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet8UpdateHealth;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class VelocityManip extends Module {
   public VelocityManip() {
      super("VelocityManip", 0, Module.Category.MOVEMENT);
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet8UpdateHealth) {
         Packet8UpdateHealth var2 = (Packet8UpdateHealth)e.getPacket();
      }

   }

   public void gettarget() {
      Iterator var2 = this.mc.theWorld.playerEntities.iterator();

      while(var2.hasNext()) {
         EntityPlayer p = (EntityPlayer)var2.next();
         if (this.mc.thePlayer.getDistanceToEntity(p) < 4.0F && p != this.mc.thePlayer) {
            double x = p.posX;
            double y = p.posY;
            double var7 = p.posZ;
         }
      }

   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate) {
         e.isPre();
      }

   }
}
