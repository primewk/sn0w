package net.minecraft.src.MEDMEX.Modules.Render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;

public class Tracers extends Module {
   public static Tracers instance;

   public Tracers() {
      super("Tracers", 0, Module.Category.RENDER);
      instance = this;
   }

   public static Vec3D interpolateEntity(Entity entity, float time) {
      return new Vec3D(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)time);
   }

   public void onRender() {
      if (this.isEnabled()) {
         double size = 0.45D;
         double ytSize = 0.35D;

         for(int x = 0; x < this.mc.theWorld.playerEntities.size(); ++x) {
            EntityPlayer entity = (EntityPlayer)this.mc.theWorld.playerEntities.get(x);
            double X = entity.posX;
            double Y = entity.posY;
            double Z = entity.posZ;
            double RenderX = X - RenderManager.renderPosX;
            double RenderY = Y - RenderManager.renderPosY;
            double RenderZ = Z - RenderManager.renderPosZ;
            if (!entity.username.contains(" ") && entity instanceof EntityPlayer && !entity.username.equals(this.mc.thePlayer.username)) {
               Vec3D pos = interpolateEntity(entity, this.mc.timer.renderPartialTicks).subtract(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
               if (pos != null) {
                  boolean bobbing = this.mc.gameSettings.viewBobbing;
                  this.mc.gameSettings.viewBobbing = false;
                  this.mc.entityRenderer.setupCameraTransform(this.mc.timer.renderPartialTicks, 0);
                  Vec3D forward = (new Vec3D(0.0D, 0.0D, 1.0D)).rotatePitch(-((float)Math.toRadians((double)Minecraft.theMinecraft.thePlayer.rotationPitch))).rotateYaw(-((float)Math.toRadians((double)Minecraft.theMinecraft.thePlayer.rotationYaw)));
                  if (Client.friends.contains(entity.username)) {
                     RenderUtils.drawLine3D((float)forward.xCoord, (float)forward.yCoord, (float)forward.zCoord, (float)pos.xCoord, (float)pos.yCoord, (float)pos.zCoord, 1.0F, (new Color(25, 204, 25)).getRGB());
                  } else {
                     RenderUtils.drawLine3D((float)forward.xCoord, (float)forward.yCoord, (float)forward.zCoord, (float)pos.xCoord, (float)pos.yCoord, (float)pos.zCoord, 1.0F, (new Color(181, 147, 219)).getRGB());
                  }

                  this.mc.gameSettings.viewBobbing = bobbing;
                  this.mc.entityRenderer.setupCameraTransform(this.mc.timer.renderPartialTicks, 0);
               }
            }
         }
      }

   }
}
