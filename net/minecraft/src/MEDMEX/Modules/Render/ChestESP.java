package net.minecraft.src.MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.RenderManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;

public class ChestESP extends Module {
   public static ChestESP instance;
   public Color ChestColor = new Color(40, 40, 220);
   public Color FurnaceColor = new Color(10, 10, 10);

   public ChestESP() {
      super("ChestESP", 0, Module.Category.RENDER);
      instance = this;
   }

   public void onRender() {
      if (this.isEnabled()) {
         try {
            Iterator var2 = this.mc.theWorld.loadedTileEntityList.iterator();

            while(var2.hasNext()) {
               TileEntity o = (TileEntity)var2.next();
               int cX = o.xCoord;
               int cY = o.yCoord;
               int cZ = o.zCoord;
               double renderX = (double)cX - RenderManager.renderPosX;
               double renderY = (double)cY - RenderManager.renderPosY;
               double renderZ = (double)cZ - RenderManager.renderPosZ;
               float Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, 0.019999999552965164D * this.mc.thePlayer.getDistance((double)o.xCoord, (double)o.yCoord, (double)o.zCoord)));
               AxisAlignedBB B;
               if (o instanceof TileEntityChest && this.mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType() != null) {
                  B = this.mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType().getSelectedBoundingBoxFromPool(this.mc.theWorld, cX, cY, cZ);
                  Vec3D adjacentchest = this.findAdjacentChest(cX, cY, cZ);
                  if (adjacentchest != null) {
                     if (adjacentchest.xCoord == 1.0D) {
                        ++B.maxX;
                     }

                     if (adjacentchest.xCoord == -1.0D) {
                        --B.minX;
                     }

                     if (adjacentchest.zCoord == 1.0D) {
                        ++B.maxZ;
                     }

                     if (adjacentchest.zCoord == -1.0D) {
                        --B.minZ;
                     }
                  }

                  if (!this.hasAdjacent(cX, cY, cZ)) {
                     RenderUtils.boundingESPBox(B, new Color(this.ChestColor.getRed(), this.ChestColor.getGreen(), this.ChestColor.getBlue(), (int)((120.0F + (float)this.ChestColor.getAlpha() / 2.0F) * Alpha)));
                     Alpha *= 0.8F;
                     RenderUtils.boundingESPBoxFilled(B, new Color(this.ChestColor.getRed(), this.ChestColor.getGreen(), this.ChestColor.getBlue(), (int)((120.0F + (float)this.ChestColor.getAlpha() / 2.0F) * Alpha)));
                  }
               }

               if (o instanceof TileEntityFurnace && this.mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType() != null) {
                  B = this.mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType().getSelectedBoundingBoxFromPool(this.mc.theWorld, cX, cY, cZ);
                  RenderUtils.boundingESPBox(B, new Color(this.FurnaceColor.getRed(), this.FurnaceColor.getGreen(), this.FurnaceColor.getBlue(), (int)((120.0F + (float)this.FurnaceColor.getAlpha() / 2.0F) * Alpha)));
                  Alpha *= 0.8F;
                  RenderUtils.boundingESPBoxFilled(B, new Color(this.FurnaceColor.getRed(), this.FurnaceColor.getGreen(), this.FurnaceColor.getBlue(), (int)((120.0F + (float)this.FurnaceColor.getAlpha() / 2.0F) * Alpha)));
               }
            }
         } catch (Exception var15) {
         }
      }

   }

   public boolean hasAdjacent(int x, int y, int z) {
      if (this.mc.theWorld.getBlockTileEntity(x - 1, y, z) instanceof TileEntityChest) {
         return true;
      } else {
         return this.mc.theWorld.getBlockTileEntity(x, y, z - 1) instanceof TileEntityChest;
      }
   }

   public Vec3D findAdjacentChest(int x, int y, int z) {
      if (this.mc.theWorld.getBlockTileEntity(x + 1, y, z) instanceof TileEntityChest) {
         return new Vec3D(1.0D, 0.0D, 0.0D);
      } else {
         return this.mc.theWorld.getBlockTileEntity(x, y, z + 1) instanceof TileEntityChest ? new Vec3D(0.0D, 0.0D, 1.0D) : null;
      }
   }
}
