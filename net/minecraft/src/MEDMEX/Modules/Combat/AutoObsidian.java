package net.minecraft.src.MEDMEX.Modules.Combat;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.Timer;
import net.minecraft.src.de.Hero.settings.Setting;

public class AutoObsidian extends Module {
   public static Timer timer = new Timer();
   public static int X;
   public static int Z;
   public static int offsetX;
   public static int offsetZ;

   public AutoObsidian() {
      super("AutoObsidian", 0, Module.Category.COMBAT);
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Full Surround", this, false));
      Client.settingsmanager.rSetting(new Setting("Center Player", this, false));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate) {
         if (e.isPre()) {
            X = (int)this.mc.thePlayer.posX;
            Z = (int)this.mc.thePlayer.posZ;
            if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ < 0.0D) {
               offsetX = -1;
               offsetZ = -1;
            }

            if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ > 0.0D) {
               offsetX = 0;
               offsetZ = 0;
            }

            if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ < 0.0D) {
               offsetX = 0;
               offsetZ = -1;
            }

            if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ > 0.0D) {
               offsetX = -1;
               offsetZ = 0;
            }

            int bestSlot = true;

            for(int i = 0; i < 9; ++i) {
               int prevItem = this.mc.thePlayer.inventory.currentItem;
               ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
               if (stack != null && stack.itemID == 49 && timer.hasTimeElapsed(10L, false)) {
                  this.mc.thePlayer.inventory.currentItem = i;
                  int[] values;
                  if (this.mc.theWorld.isAirBlock(X + 1 + offsetX, (int)this.mc.thePlayer.posY - 1, Z + offsetZ)) {
                     values = this.getDir(new Vec3D((double)(X + 1 + offsetX), this.mc.thePlayer.posY - 1.0D, (double)(Z + offsetZ)));
                     this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                  }

                  if (this.mc.theWorld.isAirBlock(X - 1 + offsetX, (int)this.mc.thePlayer.posY - 1, Z + offsetZ)) {
                     values = this.getDir(new Vec3D((double)(X - 1 + offsetX), this.mc.thePlayer.posY - 1.0D, (double)(Z + offsetZ)));
                     this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                  }

                  if (this.mc.theWorld.isAirBlock(X + offsetX, (int)this.mc.thePlayer.posY - 1, Z + 1 + offsetZ)) {
                     values = this.getDir(new Vec3D((double)(X + offsetX), this.mc.thePlayer.posY - 1.0D, (double)(Z + 1 + offsetZ)));
                     this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                  }

                  if (this.mc.theWorld.isAirBlock(X + offsetX, (int)this.mc.thePlayer.posY - 1, Z - 1 + offsetZ)) {
                     values = this.getDir(new Vec3D((double)(X + offsetX), this.mc.thePlayer.posY - 1.0D, (double)(Z - 1 + offsetZ)));
                     this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                  }

                  if (this.mc.theWorld.isAirBlock(X + offsetX, (int)this.mc.thePlayer.posY - 2, Z + offsetZ)) {
                     values = this.getDir(new Vec3D((double)(X + offsetX), this.mc.thePlayer.posY - 2.0D, (double)(Z + offsetZ)));
                     this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                  }

                  if (Client.settingsmanager.getSettingByName("Full Surround").getValBoolean()) {
                     if (this.mc.theWorld.isAirBlock(X + 1 + offsetX, (int)this.mc.thePlayer.posY, Z + offsetZ)) {
                        values = this.getDir(new Vec3D((double)(X + 1 + offsetX), this.mc.thePlayer.posY, (double)(Z + offsetZ)));
                        this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                     }

                     if (this.mc.theWorld.isAirBlock(X - 1 + offsetX, (int)this.mc.thePlayer.posY, Z + offsetZ)) {
                        values = this.getDir(new Vec3D((double)(X - 1 + offsetX), this.mc.thePlayer.posY, (double)(Z + offsetZ)));
                        this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                     }

                     if (this.mc.theWorld.isAirBlock(X + offsetX, (int)this.mc.thePlayer.posY, Z + 1 + offsetZ)) {
                        values = this.getDir(new Vec3D((double)(X + offsetX), this.mc.thePlayer.posY, (double)(Z + 1 + offsetZ)));
                        this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                     }

                     if (this.mc.theWorld.isAirBlock(X + offsetX, (int)this.mc.thePlayer.posY, Z - 1 + offsetZ)) {
                        values = this.getDir(new Vec3D((double)(X + offsetX), this.mc.thePlayer.posY, (double)(Z - 1 + offsetZ)));
                        this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                     }
                  }

                  this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getStackInSlot(i));
                  this.mc.thePlayer.inventory.currentItem = prevItem;
                  timer.reset();
               }
            }
         }

         if (Client.settingsmanager.getSettingByName("Center Player").getValBoolean()) {
            Vec3D pos = GetPlayerPosHighFloored(this.mc.thePlayer);
            AxisAlignedBB bb = this.mc.thePlayer.getBoundingBox();
            Vec3D Center = new Vec3D(pos.xCoord + 0.5D, pos.yCoord, pos.zCoord + 0.5D);
            double l_XDiff = Math.abs(Center.xCoord - this.mc.thePlayer.posX);
            double l_ZDiff = Math.abs(Center.zCoord - this.mc.thePlayer.posZ);
            if (l_XDiff <= 0.1D && l_ZDiff <= 0.1D) {
               Center = Vec3D.ZERO;
            } else {
               double l_MotionX = Center.xCoord - this.mc.thePlayer.posX;
               double l_MotionZ = Center.zCoord - this.mc.thePlayer.posZ;
               this.mc.thePlayer.motionX = l_MotionX / 3.0D;
               this.mc.thePlayer.motionZ = l_MotionZ / 3.0D;
            }
         }
      }

   }

   public static Vec3D GetPlayerPosHighFloored(Entity p_Player) {
      return new Vec3D(Math.floor(p_Player.posX), Math.floor(p_Player.posY + 0.2D), Math.floor(p_Player.posZ));
   }

   public int[] getDir(Vec3D blockpos) {
      int[] values;
      if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord - 1, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord + -1, (int)blockpos.zCoord + 0, 1};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord + 1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 1, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 0, 4};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord - 1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord - 1, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 0, 5};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord + 1)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 1, 2};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord - 1)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord + 0, (int)blockpos.zCoord - 1, 3};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord - 1, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord - 1, (int)blockpos.zCoord, 0};
         return values;
      } else {
         values = new int[4];
         return values;
      }
   }
}
