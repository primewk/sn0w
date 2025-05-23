package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.MathHelper;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Backfill extends Module {
   public static Backfill instance;
   int X;
   int Y;
   int Z;
   int nZ;
   int nX;

   public Backfill() {
      super("Backfill", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void directions() {
      int direction = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      this.X = (int)this.mc.thePlayer.posX;
      this.Y = (int)this.mc.thePlayer.posY;
      this.Z = (int)this.mc.thePlayer.posZ;
      if (direction == 0) {
         this.nZ = -1;
         this.nX = 0;
      }

      if (direction == 1) {
         this.nX = 1;
         this.nZ = 0;
      }

      if (direction == 2) {
         this.nZ = 1;
         this.nX = 0;
      }

      if (direction == 3) {
         this.nX = -1;
         this.nZ = 0;
      }

   }

   public boolean canPlaceBlock(int x, int y, int z) {
      int id = this.mc.theWorld.getBlockId(x, y, z);
      return id == 0 || id == 10 || id == 11 || id == 8 || id == 9;
   }

   public void placeBlock(int x, int y, int z) {
      if (!this.canPlaceBlock(x - 1, y, z)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x - 1, y, z, 5);
      } else if (!this.canPlaceBlock(x + 1, y, z)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x + 1, y, z, 4);
      } else if (!this.canPlaceBlock(x, y, z - 1)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x, y, z - 1, 3);
      } else if (!this.canPlaceBlock(x, y, z + 1)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x, y, z + 1, 2);
      } else if (!this.canPlaceBlock(x, y - 1, z)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x, y - 1, z, 1);
      } else if (!this.canPlaceBlock(x, y + 1, z)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x, y - 1, z, 0);
      }
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.directions();

         for(int i = -3; i < 3; ++i) {
            for(int j = -3; j < 3; ++j) {
               if (this.mc.thePlayer.ticksExisted % 6 == 0) {
                  this.placeBlock(this.X + this.nX + i * this.nZ, this.Y + j, this.Z + this.nZ + i * this.nX);
               }
            }
         }
      }

   }
}
