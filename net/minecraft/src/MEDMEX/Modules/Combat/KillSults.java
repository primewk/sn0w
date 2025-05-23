package net.minecraft.src.MEDMEX.Modules.Combat;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MEDMEX.Modules.Module;

public class KillSults extends Module {
   public static KillSults instance;

   public KillSults() {
      super("KillSults", 0, Module.Category.COMBAT);
      instance = this;
   }

   public void PlayerUnload(EntityPlayer p) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.thePlayer.isEntityAlive() && p != this.mc.thePlayer && (double)this.mc.thePlayer.getDistanceToEntity(p) < 6.0D && p.username != this.mc.thePlayer.username) {
         this.mc.thePlayer.sendChatMessage(p.username + " got obliterated by Ye Loves You");
      }

   }
}
