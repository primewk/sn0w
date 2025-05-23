package net.minecraft.src.MEDMEX.Modules.World;

import java.awt.Color;
import net.minecraft.src.Item;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;
import net.minecraft.src.MEDMEX.Utils.Timer;

public class InstaMine extends Module {
   public static Timer timer = new Timer();
   public static Timer timer2 = new Timer();
   public static boolean runningloop = false;
   int prevItem = -1;
   boolean ESP = true;
   boolean swing = false;
   boolean ifBlock = true;
   boolean onDone = false;
   boolean onPick = false;
   boolean sameItem = true;
   int delay = 0;
   public static InstaMine instance;
   boolean hasStarted = false;
   public Vec3D pos;
   int facing;
   float progress = 0.0F;
   int cooldown = 0;
   Item usedToBreak = null;

   public InstaMine() {
      super("InstaMine", 0, Module.Category.WORLD);
      instance = this;
   }

   public boolean cancelMiningAbortPacket() {
      return this.isEnabled();
   }

   public void onRender() {
      if (this.isEnabled() && this.pos != null) {
         RenderUtils.blockESPBox(this.pos, new Color(200, 200, 200, 120));
         int b = this.mc.theWorld.getBlockId((int)this.pos.xCoord, (int)this.pos.yCoord, (int)this.pos.zCoord);
         if (b != 0) {
            float i = Math.min(1.0F, this.progress);
            RenderUtils.blockESPBoxFilled(this.pos, new Color((int)(40.0F + 100.0F * i), (int)(160.0F - 140.0F * i), 40, 130));
         }
      }

   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (this.cooldown > 0) {
            --this.cooldown;
         } else if (this.pos != null) {
            int b = this.mc.theWorld.getBlockId((int)this.pos.xCoord, (int)this.pos.yCoord, (int)this.pos.zCoord);
            if (this.hasStarted && (b != 0 || !this.ifBlock)) {
               if (!runningloop) {
                  this.prevItem = this.mc.thePlayer.inventory.currentItem;
               }

               if (this.swing) {
                  this.mc.thePlayer.swingItem();
               }

               this.progress = 1.0F;
               if (this.onDone && this.progress < 1.0F) {
                  return;
               }

               if (this.cooldown > 0) {
                  return;
               }

               int bestSlot = true;

               for(int i = 0; i < 9; ++i) {
                  ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
                  if (stack != null && stack.getItem() instanceof ItemPickaxe) {
                     this.mc.thePlayer.inventory.currentItem = i;
                     if (timer.hasTimeElapsed(50L, true)) {
                        runningloop = true;
                     }
                  }
               }
            }

            if (runningloop && timer.hasTimeElapsed(55L, true)) {
               this.mc.thePlayer.inventory.currentItem = this.prevItem;
               runningloop = false;
            }

            if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.sameItem && this.usedToBreak != this.mc.thePlayer.inventory.getCurrentItem().getItem()) {
               return;
            }

            this.mc.getSendQueue().addToSendQueue(new Packet14BlockDig(2, (int)this.pos.xCoord, (int)this.pos.yCoord, (int)this.pos.zCoord, this.facing));
            this.cooldown = this.delay;
            if (this.mc.thePlayer.inventory.getCurrentItem() != null) {
               this.usedToBreak = this.mc.thePlayer.inventory.getCurrentItem().getItem();
            }
         }
      }

   }

   public boolean onBreak(Vec3D posBlock, int directionFacing) {
      if (!this.isEnabled()) {
         return false;
      } else if (this.onPick && Item.pickaxeDiamond != this.mc.thePlayer.inventory.getCurrentItem().getItem()) {
         return true;
      } else {
         if (this.pos == null || !this.pos.equals(posBlock)) {
            this.progress = 0.0F;
            this.hasStarted = false;
            this.usedToBreak = null;
         }

         this.pos = posBlock;
         this.facing = directionFacing;
         if (!this.hasStarted) {
            this.mc.getSendQueue().addToSendQueue(new Packet14BlockDig(0, (int)this.pos.xCoord, (int)this.pos.yCoord, (int)this.pos.zCoord, this.facing));
            this.mc.getSendQueue().addToSendQueue(new Packet14BlockDig(0, (int)this.pos.xCoord, (int)this.pos.yCoord, (int)this.pos.zCoord, this.facing));
            if (this.mc.thePlayer.inventory.getCurrentItem() != null) {
               this.usedToBreak = this.mc.thePlayer.inventory.getCurrentItem().getItem();
            }

            this.hasStarted = true;
         }

         return false;
      }
   }
}
