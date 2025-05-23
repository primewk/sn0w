package net.minecraft.src.MEDMEX.Modules.Movement;

import java.awt.Color;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Modules.Combat.ForceField;
import net.minecraft.src.de.Hero.settings.Setting;
import org.lwjgl.opengl.GL11;

public class TargetStrafe extends Module {
   public static TargetStrafe instance;
   static int direction = -1;

   public TargetStrafe() {
      super("TargetStrafe", 0, Module.Category.MOVEMENT);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Strafe Speed", this, 0.25D, 0.0D, 1.0D, false));
      Client.settingsmanager.rSetting(new Setting("Strafe Distance", this, 4.0D, 0.0D, 6.0D, false));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (this.mc.thePlayer.isCollidedHorizontally) {
            this.switchDirection();
         }

         if (ForceField.target != null) {
            this.doStrafeAtSpeed(Client.settingsmanager.getSettingByName("Strafe Speed").getValDouble());
         }
      }

   }

   private void switchDirection() {
      if (direction == 1) {
         direction = -1;
      } else {
         direction = 1;
      }

   }

   public void onRender() {
      if (ForceField.target != null) {
         this.drawCircle(ForceField.target, this.mc.timer.renderPartialTicks, Client.settingsmanager.getSettingByName("Strafe Distance").getValDouble());
      }

   }

   public final boolean doStrafeAtSpeed(double moveSpeed) {
      boolean strafe = canStrafe();
      if (strafe) {
         float[] rotations = this.getNeededRotations(ForceField.target);
         if ((double)this.mc.thePlayer.getDistanceToEntity(ForceField.target) <= Client.settingsmanager.getSettingByName("Strafe Distance").getValDouble()) {
            this.setSpeed(moveSpeed, rotations[0], (double)direction, 0.0D);
         } else {
            this.setSpeed(moveSpeed, rotations[0], (double)direction, 1.0D);
         }
      }

      return strafe;
   }

   public void setSpeed(double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
      double forward = pseudoForward;
      double strafe = pseudoStrafe;
      float yaw = pseudoYaw;
      if (pseudoForward == 0.0D && pseudoStrafe == 0.0D) {
         this.mc.thePlayer.motionZ = 0.0D;
         this.mc.thePlayer.motionX = 0.0D;
      } else {
         if (pseudoForward != 0.0D) {
            if (pseudoStrafe > 0.0D) {
               yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
            } else if (pseudoStrafe < 0.0D) {
               yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (pseudoForward > 0.0D) {
               forward = 1.0D;
            } else if (pseudoForward < 0.0D) {
               forward = -1.0D;
            }
         }

         double cos = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
         double sin = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         this.mc.thePlayer.motionX = forward * moveSpeed * cos + strafe * moveSpeed * sin;
         this.mc.thePlayer.motionZ = forward * moveSpeed * sin - strafe * moveSpeed * cos;
      }

   }

   public float[] getNeededRotations(Entity entityIn) {
      double d0 = entityIn.posX - this.mc.thePlayer.posX;
      double d1 = entityIn.posZ - this.mc.thePlayer.posZ;
      double d2 = entityIn.posY + (double)entityIn.getEyeHeight() - (this.mc.thePlayer.boundingBox.minY + (this.mc.thePlayer.boundingBox.maxY - this.mc.thePlayer.boundingBox.minY));
      double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1);
      float f = (float)(Math.atan2(d1, d0) * 180.0D / 3.141592653589793D) - 90.0F;
      float f1 = (float)(-(Math.atan2(d2, d3) * 180.0D / 3.141592653589793D));
      return new float[]{f, f1};
   }

   private void drawCircle(Entity entity, float partialTicks, double rad) {
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(3);
      double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - RenderManager.field_1222_l;
      double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - RenderManager.field_1221_m;
      double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - RenderManager.field_1220_n;
      float r = 0.003921569F * (float)Color.WHITE.getRed();
      float g = 0.003921569F * (float)Color.WHITE.getGreen();
      float b = 0.003921569F * (float)Color.WHITE.getBlue();
      double pix2 = 6.283185307179586D;

      for(int i = 0; i <= 90; ++i) {
         GL11.glColor3f(r, g, b);
         GL11.glVertex3d(x + rad * Math.cos((double)i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double)i * 6.283185307179586D / 45.0D));
      }

      GL11.glEnd();
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static boolean canStrafe() {
      return ForceField.target != null && instance.isEnabled();
   }
}
