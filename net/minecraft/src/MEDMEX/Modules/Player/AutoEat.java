package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.Timer;

public class AutoEat extends Module {
   public static AutoEat instance;
   public static double x;
   public static double y;
   public static double z;
   public static Timer timer = new Timer();

   public AutoEat() {
      super("AutoEat", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre() && this.mc.thePlayer.health < 20) {
         int bestSlot = true;

         for(int i = 0; i < 9; ++i) {
            int prevItem = this.mc.thePlayer.inventory.currentItem;
            ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemFood && timer.hasTimeElapsed(10L, false)) {
               this.mc.thePlayer.inventory.currentItem = i;
               this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getStackInSlot(i));
               this.mc.thePlayer.inventory.currentItem = prevItem;
               timer.reset();
            }
         }
      }

   }
}
