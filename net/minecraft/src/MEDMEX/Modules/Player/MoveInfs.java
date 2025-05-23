package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;

public class MoveInfs extends Module {
   public static MoveInfs instance;

   public MoveInfs() {
      super("MoveInfs", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet102WindowClick) {
         Packet102WindowClick p = (Packet102WindowClick)e.getPacket();
         if (p.inventorySlot != -999 && p.inventorySlot != -1) {
            ItemStack toMove = this.mc.thePlayer.craftingInventory.getSlot(p.inventorySlot).getStack();
            int lowestplayerslot = this.mc.thePlayer.craftingInventory.slots.size() - 36;
            if (toMove != null && toMove.stackSize < 1 && p.field_27050_f) {
               e.setCancelled(true);
               int openslot;
               if (p.inventorySlot >= lowestplayerslot) {
                  openslot = this.getopenslotinchest(lowestplayerslot);
                  if (openslot != -1) {
                     Client.sendPacket(new Packet102WindowClick(this.mc.thePlayer.craftingInventory.windowId, p.inventorySlot, 0, false, toMove, this.mc.thePlayer.craftingInventory.getNextTransactionID(this.mc.thePlayer.inventory)));
                     Client.sendPacket(new Packet102WindowClick(this.mc.thePlayer.craftingInventory.windowId, openslot, 0, false, (ItemStack)null, this.mc.thePlayer.craftingInventory.getNextTransactionID(this.mc.thePlayer.inventory)));
                     Client.sendPacket(new Packet102WindowClick(this.mc.thePlayer.craftingInventory.windowId, p.inventorySlot, 0, false, toMove, this.mc.thePlayer.craftingInventory.getNextTransactionID(this.mc.thePlayer.inventory)));
                  }
               }

               if (p.inventorySlot <= lowestplayerslot) {
                  openslot = this.getopenslotininventory(lowestplayerslot);
                  if (openslot != -1) {
                     Client.sendPacket(new Packet102WindowClick(this.mc.thePlayer.craftingInventory.windowId, p.inventorySlot, 0, false, toMove, this.mc.thePlayer.craftingInventory.getNextTransactionID(this.mc.thePlayer.inventory)));
                     Client.sendPacket(new Packet102WindowClick(this.mc.thePlayer.craftingInventory.windowId, openslot, 0, false, (ItemStack)null, this.mc.thePlayer.craftingInventory.getNextTransactionID(this.mc.thePlayer.inventory)));
                     Client.sendPacket(new Packet102WindowClick(this.mc.thePlayer.craftingInventory.windowId, p.inventorySlot, 0, false, toMove, this.mc.thePlayer.craftingInventory.getNextTransactionID(this.mc.thePlayer.inventory)));
                  }
               }
            }
         }
      }

   }

   public int getopenslotininventory(int lowestplayerslot) {
      int openslot = -1;

      for(int i = this.mc.thePlayer.craftingInventory.slots.size() - 1; i >= lowestplayerslot; --i) {
         if (this.mc.thePlayer.craftingInventory.getSlot(i).getStack() == null) {
            return i;
         }
      }

      return openslot;
   }

   public int getopenslotinchest(int lowestplayerslot) {
      int openslot = -1;

      for(int i = 0; i < lowestplayerslot; ++i) {
         if (this.mc.thePlayer.craftingInventory.getSlot(i).getStack() == null) {
            return i;
         }
      }

      return openslot;
   }
}
