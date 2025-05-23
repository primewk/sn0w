package net.minecraft.src.MEDMEX.Modules.World;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class BucketFix extends Module {
   public static BucketFix instance;

   public BucketFix() {
      super("BucketFix", 0, Module.Category.WORLD);
      instance = this;
   }

   public void onEnable() {
      System.out.println(this.mc.thePlayer.inventory.getStackInSlot(0));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem().equals(Item.bucketEmpty)) {
            this.mc.thePlayer.dropCurrentItem();
         }

         if (this.mc.thePlayer.inventory.getCurrentItem() == null) {
            int bestSlot = true;

            for(int i = 0; i < 9; ++i) {
               int prevItem = this.mc.thePlayer.inventory.currentItem;
               ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
               if (stack != null && stack.getItem() == Item.bucketLava && stack.stackSize != 1 && stack.stackSize != -128 && this.mc.thePlayer.inventory.getStackInSlot(i + 1) == null) {
                  this.mc.playerController.handleMouseClick(0, i + 36, 0, false, this.mc.thePlayer);
                  this.mc.playerController.handleMouseClick(0, i + 37, 1, false, this.mc.thePlayer);
                  this.mc.playerController.handleMouseClick(0, i + 36, 0, false, this.mc.thePlayer);
               }
            }
         }
      }

   }
}
