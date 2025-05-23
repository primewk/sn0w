package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;

public class AutoTool extends Module {
   public static AutoTool instance;

   public AutoTool() {
      super("AutoTool", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet14BlockDig) {
         Packet14BlockDig p = (Packet14BlockDig)e.getPacket();
         int blockid = this.mc.theWorld.getBlockId(p.xPosition, p.yPosition, p.zPosition);
         if (blockid != 0) {
            float s = 0.1F;
            int currentItem = this.mc.thePlayer.inventory.currentItem;

            for(int i = 0; i < 9; ++i) {
               ItemStack is = this.mc.thePlayer.inventory.getStackInSlot(i);
               if (is != null) {
                  float strength = is.getStrVsBlock(Block.blocksList[blockid]);
                  if (strength > s) {
                     s = strength;
                     this.mc.thePlayer.inventory.currentItem = i;
                  }
               }
            }
         }
      }

   }
}
