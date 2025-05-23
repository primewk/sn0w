package net.minecraft.src.MEDMEX.Modules.Render;

import java.awt.Color;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;

public class BreakProgress extends Module {
   public static BreakProgress instance;

   public BreakProgress() {
      super("BreakProgress", 0, Module.Category.RENDER);
      instance = this;
   }

   public void onRender() {
      if (this.isEnabled() && PlayerControllerMP.instance.curBlockDamageMP != 0.0F && PlayerControllerMP.instance.currentBlockX != -1) {
         float i = Math.min(1.0F, PlayerControllerMP.instance.curBlockDamageMP);
         Vec3D v = new Vec3D((double)PlayerControllerMP.instance.currentBlockX, (double)PlayerControllerMP.instance.currentBlockY, (double)PlayerControllerMP.instance.currentblockZ);
         AxisAlignedBB B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1.0D, v.yCoord + 1.0D, v.zCoord + 1.0D);
         float j = i / 2.0F + 0.5F;
         B = B.expand((double)(-j), (double)(-j), (double)(-j));
         RenderUtils.boundingESPBoxFilled(B, new Color((int)(40.0F + 100.0F * i), (int)(160.0F - 140.0F * i), 40, 130));
      }

   }
}
