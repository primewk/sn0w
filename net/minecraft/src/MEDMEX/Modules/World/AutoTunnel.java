package net.minecraft.src.MEDMEX.Modules.World;

import java.awt.Color;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemTool;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.InventoryUtils;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;
import net.minecraft.src.de.Hero.settings.Setting;

public class AutoTunnel extends Module {
   public static AutoTunnel instance;
   Color breakColor = new Color(255, 0, 0);
   Color placeColor = new Color(0, 255, 0);
   Color lavaColor = new Color(0, 0, 255);

   public AutoTunnel() {
      super("AutoTunnel", 0, Module.Category.WORLD);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Surround", this, false));
      Client.settingsmanager.rSetting(new Setting("Distance", this, 4.0D, 1.0D, 6.0D, true));
      Client.settingsmanager.rSetting(new Setting("Backfill", this, false));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         int[] offsets = this.offsets();
         int playerX = (int)this.mc.thePlayer.posX;
         int playerY = (int)this.mc.thePlayer.posY;
         int playerZ = (int)this.mc.thePlayer.posZ;
         int preX = playerX + offsets[0];
         int preZ = playerZ + offsets[1];
         CopyOnWriteArrayList<Vec3D> breakables = this.findBreak(preX, playerY, preZ);
         CopyOnWriteArrayList<Vec3D> placeables = this.findPlace(preX, playerY, preZ);
         CopyOnWriteArrayList<Vec3D> lava = this.findLava(preX, playerY, preZ);
         if (this.getSet("Backfill").getValBoolean()) {
            this.silentplace(preX, playerY, preZ);
         }

         if (this.mc.theWorld.getBlockId(preX, playerY - 2, preZ) == 39 || this.mc.theWorld.getBlockId(preX, playerY - 2, preZ) == 40) {
            Client.sendPacket(new Packet14BlockDig(0, preX, playerY - 2, preZ, 1));
            Client.sendPacket(new Packet14BlockDig(2, preX, playerY - 2, preZ, 1));
         }

         int blockSlot;
         Vec3D v;
         Iterator var13;
         if (!breakables.isEmpty()) {
            blockSlot = InventoryUtils.getHotbarslotPickaxe();
            if (blockSlot != -1) {
               this.mc.thePlayer.inventory.currentItem = blockSlot;
            }

            if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemTool) {
               var13 = breakables.iterator();

               while(var13.hasNext()) {
                  v = (Vec3D)var13.next();
                  Client.sendPacket(new Packet14BlockDig(0, (int)v.xCoord, (int)v.yCoord, (int)v.zCoord, 1, this.mc.thePlayer.inventory.getCurrentItem()));
                  Client.sendPacket(new Packet14BlockDig(2, (int)v.xCoord, (int)v.yCoord, (int)v.zCoord, 1, this.mc.thePlayer.inventory.getCurrentItem()));
               }
            }
         }

         int[] values;
         if (!lava.isEmpty()) {
            blockSlot = InventoryUtils.getHotbarslotBlocks();
            if (blockSlot != -1) {
               this.mc.thePlayer.inventory.currentItem = blockSlot;
            }

            if (this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
               var13 = lava.iterator();

               while(var13.hasNext()) {
                  v = (Vec3D)var13.next();
                  values = this.getDir(v);
                  this.mc.thePlayer.swingItem();
                  this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
               }
            }
         }

         if (!placeables.isEmpty()) {
            blockSlot = InventoryUtils.getHotbarslotBlocks();
            if (blockSlot != -1) {
               this.mc.thePlayer.inventory.currentItem = blockSlot;
            }

            if (this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
               var13 = placeables.iterator();

               while(var13.hasNext()) {
                  v = (Vec3D)var13.next();
                  values = this.getDir(v);
                  this.mc.thePlayer.swingItem();
                  this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
               }
            }
         }
      }

   }

   public void onRender() {
      if (this.isEnabled()) {
         try {
            int[] offsets = this.offsets();
            int playerX = (int)this.mc.thePlayer.posX;
            int playerY = (int)this.mc.thePlayer.posY;
            int playerZ = (int)this.mc.thePlayer.posZ;
            int preX = playerX + offsets[0];
            int preZ = playerZ + offsets[1];
            CopyOnWriteArrayList<Vec3D> breakables = this.findBreak(preX, playerY, preZ);
            CopyOnWriteArrayList<Vec3D> placeables = this.findPlace(preX, playerY, preZ);
            CopyOnWriteArrayList<Vec3D> lava = this.findLava(preX, playerY, preZ);
            Iterator var11 = breakables.iterator();

            Vec3D v;
            float Alpha;
            AxisAlignedBB B;
            while(var11.hasNext()) {
               v = (Vec3D)var11.next();
               Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, 0.019999999552965164D * this.mc.thePlayer.getDistance(v.xCoord, v.yCoord, v.zCoord)));
               B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1.0D, v.yCoord + 1.0D, v.zCoord + 1.0D);
               RenderUtils.boundingESPBox(B, new Color(this.breakColor.getRed(), this.breakColor.getGreen(), this.breakColor.getBlue(), (int)((120.0F + (float)this.breakColor.getAlpha() / 2.0F) * Alpha)));
               Alpha *= 0.8F;
               RenderUtils.boundingESPBoxFilled(B, new Color(this.breakColor.getRed(), this.breakColor.getGreen(), this.breakColor.getBlue(), (int)((120.0F + (float)this.breakColor.getAlpha() / 2.0F) * Alpha)));
            }

            var11 = placeables.iterator();

            while(var11.hasNext()) {
               v = (Vec3D)var11.next();
               Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, 0.019999999552965164D * this.mc.thePlayer.getDistance(v.xCoord, v.yCoord, v.zCoord)));
               B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1.0D, v.yCoord + 1.0D, v.zCoord + 1.0D);
               RenderUtils.boundingESPBox(B, new Color(this.placeColor.getRed(), this.placeColor.getGreen(), this.placeColor.getBlue(), (int)((120.0F + (float)this.placeColor.getAlpha() / 2.0F) * Alpha)));
               Alpha *= 0.8F;
               RenderUtils.boundingESPBoxFilled(B, new Color(this.placeColor.getRed(), this.placeColor.getGreen(), this.placeColor.getBlue(), (int)((120.0F + (float)this.placeColor.getAlpha() / 2.0F) * Alpha)));
            }

            var11 = lava.iterator();

            while(var11.hasNext()) {
               v = (Vec3D)var11.next();
               Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, 0.019999999552965164D * this.mc.thePlayer.getDistance(v.xCoord, v.yCoord, v.zCoord)));
               B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1.0D, v.yCoord + 1.0D, v.zCoord + 1.0D);
               RenderUtils.boundingESPBox(B, new Color(this.lavaColor.getRed(), this.lavaColor.getGreen(), this.lavaColor.getBlue(), (int)((120.0F + (float)this.lavaColor.getAlpha() / 2.0F) * Alpha)));
               Alpha *= 0.8F;
               RenderUtils.boundingESPBoxFilled(B, new Color(this.lavaColor.getRed(), this.lavaColor.getGreen(), this.lavaColor.getBlue(), (int)((120.0F + (float)this.lavaColor.getAlpha() / 2.0F) * Alpha)));
            }
         } catch (Exception var14) {
         }
      }

   }

   public CopyOnWriteArrayList<Vec3D> findLava(int x, int y, int z) {
      int[] facingDir = this.facingDir();
      CopyOnWriteArrayList<Vec3D> lava = new CopyOnWriteArrayList();

      for(int i = -1; i < 2; ++i) {
         for(int j = -2; j < 2; ++j) {
            for(int k = 1; k < 4; ++k) {
               Vec3D blockPos;
               if (facingDir[0] == 0 && (this.mc.theWorld.getBlockId(x + i, y + j, z + facingDir[1] * k) == 10 || this.mc.theWorld.getBlockId(x + i, y + j, z + facingDir[1] * k) == 11)) {
                  blockPos = new Vec3D((double)(x + i), (double)(y + j), (double)(z + facingDir[1] * k));
                  lava.add(blockPos);
               }

               if (facingDir[1] == 0 && (this.mc.theWorld.getBlockId(x + facingDir[0] * k, y + j, z + i) == 10 || this.mc.theWorld.getBlockId(x + facingDir[0] * k, y + j, z + i) == 11)) {
                  blockPos = new Vec3D((double)(x + facingDir[0] * k), (double)(y + j), (double)(z + i));
                  lava.add(blockPos);
               }
            }
         }
      }

      return lava;
   }

   public CopyOnWriteArrayList<Vec3D> findPlace(int x, int y, int z) {
      int[] facingDir = this.facingDir();
      CopyOnWriteArrayList<Vec3D> placeable = new CopyOnWriteArrayList();
      Vec3D top;
      if (this.mc.theWorld.getBlockMaterial(x, y - 2, z).getIsLiquid() || this.mc.theWorld.isAirBlock(x, y - 2, z)) {
         top = new Vec3D((double)x, (double)(y - 2), (double)z);
         placeable.add(top);
      }

      if (this.getSet("Surround").getValBoolean()) {
         if (this.mc.theWorld.isAirBlock(x + facingDir[0], y - 2, z + facingDir[1])) {
            top = new Vec3D((double)(x + facingDir[0]), (double)(y - 2), (double)(z + facingDir[1]));
            placeable.add(top);
         }

         if (this.mc.theWorld.isAirBlock(x + facingDir[0], y + 1, z + facingDir[1])) {
            top = new Vec3D((double)(x + facingDir[0]), (double)(y + 1), (double)(z + facingDir[1]));
            placeable.add(top);
         }

         Vec3D right;
         int i;
         for(i = -1; i < 1; ++i) {
            if (this.mc.theWorld.isAirBlock(x + facingDir[0] + facingDir[1], y + i, z + (facingDir[1] - facingDir[0]))) {
               right = new Vec3D((double)(x + facingDir[0] + facingDir[1]), (double)(y + i), (double)(z + (facingDir[1] - facingDir[0])));
               placeable.add(right);
            }
         }

         for(i = -1; i < 1; ++i) {
            if (this.mc.theWorld.isAirBlock(x + (facingDir[0] - facingDir[1]), y + i, z + facingDir[1] + facingDir[0])) {
               right = new Vec3D((double)(x + (facingDir[0] - facingDir[1])), (double)(y + i), (double)(z + facingDir[1] + facingDir[0]));
               placeable.add(right);
            }
         }
      }

      return placeable;
   }

   public CopyOnWriteArrayList<Vec3D> findBreak(int x, int y, int z) {
      int[] facingDir = this.facingDir();
      CopyOnWriteArrayList<Vec3D> breakable = new CopyOnWriteArrayList();

      for(int i = -1; i < 1; ++i) {
         for(int j = 1; (double)j < this.getSet("Distance").getValDouble(); ++j) {
            if (!this.mc.theWorld.isAirBlock(x + facingDir[0] * j, y + i, z + facingDir[1] * j)) {
               CopyOnWriteArrayList<Integer> blacklist = new CopyOnWriteArrayList(new Integer[]{7, 8, 9, 10, 11});
               if (!blacklist.contains(this.mc.theWorld.getBlockId(x + facingDir[0] * j, y + i, z + facingDir[1] * j))) {
                  Vec3D blockPos = new Vec3D((double)(x + facingDir[0] * j), (double)(y + i), (double)(z + facingDir[1] * j));
                  breakable.add(blockPos);
               }
            }
         }
      }

      return breakable;
   }

   public int[] offsets() {
      int[] offsets = new int[2];
      if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ < 0.0D) {
         offsets[0] = -1;
         offsets[1] = -1;
      }

      if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ > 0.0D) {
         offsets[0] = 0;
         offsets[1] = 0;
      }

      if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ < 0.0D) {
         offsets[0] = 0;
         offsets[1] = -1;
      }

      if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ > 0.0D) {
         offsets[0] = -1;
         offsets[1] = 0;
      }

      return offsets;
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

   public int[] facingDir() {
      int[] facing = new int[2];
      int dir = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      switch(dir) {
      case 0:
         facing[0] = 0;
         facing[1] = 1;
         break;
      case 1:
         facing[0] = -1;
         facing[1] = 0;
         break;
      case 2:
         facing[0] = 0;
         facing[1] = -1;
         break;
      case 3:
         facing[0] = 1;
         facing[1] = 0;
      }

      return facing;
   }

   public void silentplace(int X, int Y, int Z) {
      int[] facing = this.facingDir();
      boolean bestSlot;
      int i;
      int prevItem;
      ItemStack stack;
      int[] values;
      if (this.mc.theWorld.isAirBlock(X - facing[0], Y, Z - facing[1])) {
         bestSlot = true;

         for(i = 0; i < 9; ++i) {
            prevItem = this.mc.thePlayer.inventory.currentItem;
            stack = this.mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBlock) {
               this.mc.thePlayer.inventory.currentItem = i;
               values = this.getDir(new Vec3D((double)(X - facing[0]), (double)Y, (double)(Z - facing[1])));
               if (this.mc.thePlayer.posX - Math.floor(this.mc.thePlayer.posX) > 0.3D && this.mc.thePlayer.posX - Math.floor(this.mc.thePlayer.posX) < 0.7D && this.mc.thePlayer.posZ - Math.floor(this.mc.thePlayer.posZ) > 0.3D && this.mc.thePlayer.posZ - Math.floor(this.mc.thePlayer.posZ) < 0.7D) {
                  this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
               }

               this.mc.thePlayer.inventory.currentItem = prevItem;
            }
         }
      }

      if (this.mc.theWorld.isAirBlock(X - facing[0], Y - 1, Z - facing[1])) {
         bestSlot = true;

         for(i = 0; i < 9; ++i) {
            prevItem = this.mc.thePlayer.inventory.currentItem;
            stack = this.mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBlock) {
               this.mc.thePlayer.inventory.currentItem = i;
               values = this.getDir(new Vec3D((double)(X - facing[0]), (double)(Y - 1), (double)(Z - facing[1])));
               if (this.mc.thePlayer.posX - Math.floor(this.mc.thePlayer.posX) > 0.3D && this.mc.thePlayer.posX - Math.floor(this.mc.thePlayer.posX) < 0.7D && this.mc.thePlayer.posZ - Math.floor(this.mc.thePlayer.posZ) > 0.3D && this.mc.thePlayer.posZ - Math.floor(this.mc.thePlayer.posZ) < 0.7D) {
                  this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
               }

               this.mc.thePlayer.inventory.currentItem = prevItem;
            }
         }
      }

   }
}
