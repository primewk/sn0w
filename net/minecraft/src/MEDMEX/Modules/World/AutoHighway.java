package net.minecraft.src.MEDMEX.Modules.World;

import java.awt.Color;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.InventoryUtils;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;

public class AutoHighway extends Module {
   Color breakColor = new Color(255, 0, 0);
   Color placeColor = new Color(0, 255, 0);
   public static boolean stopMotion = false;
   public static AutoHighway instance;
   int offsetX = 0;
   int offsetZ = 0;
   int fX = 0;
   int fZ = 0;

   public AutoHighway() {
      super("AutoHighway", 0, Module.Category.WORLD);
      instance = this;
   }

   public void onDisable() {
      stopMotion = false;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.offsets();
         this.facingDir();
         CopyOnWriteArrayList<Vec3D> breakVecs = this.findBreakBlocks();
         int tX;
         int tY;
         int tZ;
         if (!breakVecs.isEmpty()) {
            this.mc.thePlayer.movementInput.moveForward = 0.0F;
            stopMotion = true;
            Vec3D target = (Vec3D)breakVecs.get(0);
            int tX = (int)target.xCoord;
            tX = (int)target.yCoord;
            tY = (int)target.zCoord;
            tZ = InventoryUtils.getHotbarslotPickaxe();
            if (tZ != -1) {
               this.mc.thePlayer.inventory.currentItem = tZ;
            }

            Client.sendPacket(new Packet14BlockDig(0, tX, tX, tY, 0));
            Client.sendPacket(new Packet14BlockDig(2, tX, tX, tY, 0));
         }

         CopyOnWriteArrayList<Vec3D> placeVecs = this.findPlaceBlocks();
         if (!placeVecs.isEmpty() && breakVecs.isEmpty()) {
            this.mc.thePlayer.movementInput.moveForward = 0.0F;
            stopMotion = true;
            Vec3D target = (Vec3D)placeVecs.get(0);
            tX = (int)target.xCoord;
            tY = (int)target.yCoord;
            tZ = (int)target.zCoord;
            int block = InventoryUtils.getHotbarslotBlocks();
            if (block != -1) {
               this.mc.thePlayer.inventory.currentItem = block;
            }

            int[] values = this.getPlace(target);
            this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem(), values[0], values[1], values[2], values[3]);
         }

         if (placeVecs.isEmpty() && breakVecs.isEmpty()) {
            stopMotion = false;
         }
      }

   }

   public void offsets() {
      if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ < 0.0D) {
         this.offsetX = -1;
         this.offsetZ = -1;
      }

      if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ > 0.0D) {
         this.offsetX = 0;
         this.offsetZ = 0;
      }

      if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ < 0.0D) {
         this.offsetX = 0;
         this.offsetZ = -1;
      }

      if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ > 0.0D) {
         this.offsetX = -1;
         this.offsetZ = 0;
      }

   }

   public void onRender() {
      if (this.isEnabled()) {
         try {
            CopyOnWriteArrayList<Vec3D> breakVecs = this.findBreakBlocks();
            CopyOnWriteArrayList<Vec3D> placeVecs = this.findPlaceBlocks();
            Iterator var4 = breakVecs.iterator();

            Vec3D v;
            float Alpha;
            AxisAlignedBB B;
            while(var4.hasNext()) {
               v = (Vec3D)var4.next();
               Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, 0.019999999552965164D * this.mc.thePlayer.getDistance(v.xCoord, v.yCoord, v.zCoord)));
               B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1.0D, v.yCoord + 1.0D, v.zCoord + 1.0D);
               RenderUtils.boundingESPBox(B, new Color(this.breakColor.getRed(), this.breakColor.getGreen(), this.breakColor.getBlue(), (int)((120.0F + (float)this.breakColor.getAlpha() / 2.0F) * Alpha)));
               Alpha *= 0.8F;
               RenderUtils.boundingESPBoxFilled(B, new Color(this.breakColor.getRed(), this.breakColor.getGreen(), this.breakColor.getBlue(), (int)((120.0F + (float)this.breakColor.getAlpha() / 2.0F) * Alpha)));
            }

            var4 = placeVecs.iterator();

            while(var4.hasNext()) {
               v = (Vec3D)var4.next();
               Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, 0.019999999552965164D * this.mc.thePlayer.getDistance(v.xCoord, v.yCoord, v.zCoord)));
               B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1.0D, v.yCoord + 1.0D, v.zCoord + 1.0D);
               RenderUtils.boundingESPBox(B, new Color(this.placeColor.getRed(), this.placeColor.getGreen(), this.placeColor.getBlue(), (int)((120.0F + (float)this.placeColor.getAlpha() / 2.0F) * Alpha)));
               Alpha *= 0.8F;
               RenderUtils.boundingESPBoxFilled(B, new Color(this.placeColor.getRed(), this.placeColor.getGreen(), this.placeColor.getBlue(), (int)((120.0F + (float)this.placeColor.getAlpha() / 2.0F) * Alpha)));
            }
         } catch (Exception var7) {
         }
      }

   }

   public CopyOnWriteArrayList<Vec3D> findBreakBlocks() {
      int posX = (int)this.mc.thePlayer.posX;
      int posY = (int)this.mc.thePlayer.posY;
      int posZ = (int)this.mc.thePlayer.posZ;
      int searchX = posX + this.offsetX + this.fX;
      int searchZ = posZ + this.offsetZ + this.fZ;
      CopyOnWriteArrayList<Vec3D> blockVecs = new CopyOnWriteArrayList();

      for(int i = -1; i < 2; ++i) {
         for(int j = -1; j < 2; ++j) {
            Vec3D vec;
            if (this.fX != 0 && !this.mc.theWorld.isAirBlock(searchX, posY + j, searchZ + i) && this.mc.theWorld.getBlockId(searchX, posY + j, searchZ + i) != 7) {
               vec = new Vec3D((double)searchX, (double)(posY + j), (double)(searchZ + i));
               blockVecs.add(vec);
            }

            if (this.fZ != 0 && !this.mc.theWorld.isAirBlock(searchX + i, posY + j, searchZ) && this.mc.theWorld.getBlockId(searchX + i, posY + j, searchZ) != 7) {
               vec = new Vec3D((double)(searchX + i), (double)(posY + j), (double)searchZ);
               blockVecs.add(vec);
            }
         }
      }

      return blockVecs;
   }

   public CopyOnWriteArrayList<Vec3D> findPlaceBlocks() {
      int posX = (int)this.mc.thePlayer.posX;
      int posY = (int)this.mc.thePlayer.posY;
      int posZ = (int)this.mc.thePlayer.posZ;
      int searchX = posX + this.offsetX + this.fX;
      int searchZ = posZ + this.offsetZ + this.fZ;
      CopyOnWriteArrayList<Vec3D> blockVecs = new CopyOnWriteArrayList();

      for(int i = -1; i < 2; ++i) {
         for(int j = -1; j < 2; ++j) {
            if (!this.mc.theWorld.getBlockMaterial(searchX + i, posY - 2, searchZ + j).isSolid()) {
               Vec3D vec = new Vec3D((double)(searchX + i), (double)(posY - 2), (double)(searchZ + j));
               blockVecs.add(vec);
            }
         }
      }

      return blockVecs;
   }

   public void facingDir() {
      int dir = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      switch(dir) {
      case 0:
         this.fX = 0;
         this.fZ = 1;
         break;
      case 1:
         this.fX = -1;
         this.fZ = 0;
         break;
      case 2:
         this.fX = 0;
         this.fZ = -1;
         break;
      case 3:
         this.fX = 1;
         this.fZ = 0;
      }

   }

   public int[] getPlace(Vec3D blockpos) {
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
