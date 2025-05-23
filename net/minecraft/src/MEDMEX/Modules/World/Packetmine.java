package net.minecraft.src.MEDMEX.Modules.World;

import java.awt.Color;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Modules.Render.BreakProgress;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;

public class Packetmine extends Module {
   boolean ESP = true;
   boolean swing = false;
   boolean hasStarted = false;
   Vec3D pos;
   float progress = 0.0F;
   public static Packetmine instance;

   public Packetmine() {
      super("Packetmine", 0, Module.Category.WORLD);
      instance = this;
   }

   public void onRender() {
      if (this.isEnabled() && this.ESP && this.pos != null) {
         int bID = this.mc.theWorld.getBlockId((int)this.pos.xCoord, (int)this.pos.yCoord, (int)this.pos.zCoord);
         Material b = this.mc.theWorld.getBlockMaterial(this.pos);
         if (b != Material.air && b != Material.air && BreakProgress.instance.isEnabled()) {
            float i = Math.min(1.0F, this.progress);
            this.progress += Block.blocksList[bID].blockStrength(this.mc.thePlayer) / 5.0F;
            Vec3D v = new Vec3D((double)PlayerControllerMP.instance.currentBlockX, (double)PlayerControllerMP.instance.currentBlockY, (double)PlayerControllerMP.instance.currentblockZ);
            AxisAlignedBB B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1.0D, v.yCoord + 1.0D, v.zCoord + 1.0D);
            float j = i / 2.0F + 0.5F;
            B = B.expand((double)(-j), (double)(-j), (double)(-j));
            RenderUtils.boundingESPBoxFilled(B, new Color((int)(40.0F + 100.0F * i), (int)(160.0F - 140.0F * i), 40, 130));
         }
      }

   }

   public boolean onBreak(Vec3D posBlock, int directionFacing) {
      if (!this.isEnabled()) {
         return true;
      } else if (!this.can_break(posBlock)) {
         return true;
      } else {
         if (this.pos == null || !this.pos.equals(posBlock)) {
            this.progress = 0.0F;
            this.hasStarted = false;
         }

         this.pos = posBlock;
         int bID = this.mc.theWorld.getBlockId((int)posBlock.xCoord, (int)posBlock.yCoord, (int)posBlock.zCoord);
         this.progress += Block.blocksList[bID].blockStrength(this.mc.thePlayer);
         this.mc.getSendQueue().addToSendQueue(new Packet14BlockDig(0, (int)posBlock.xCoord, (int)posBlock.yCoord, (int)posBlock.zCoord, directionFacing));
         this.mc.getSendQueue().addToSendQueue(new Packet14BlockDig(2, (int)posBlock.xCoord, (int)posBlock.yCoord, (int)posBlock.zCoord, directionFacing));
         return false;
      }
   }

   private boolean can_break(Vec3D pos) {
      return this.mc.theWorld.getBlockId((int)pos.xCoord, (int)pos.yCoord, (int)pos.zCoord) != 7;
   }
}
