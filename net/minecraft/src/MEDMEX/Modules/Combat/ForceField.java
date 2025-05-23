package net.minecraft.src.MEDMEX.Modules.Combat;

import java.util.List;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.ColorUtil;
import net.minecraft.src.de.Hero.settings.Setting;
import org.lwjgl.opengl.GL11;

public class ForceField extends Module {
   public static Entity target;
   double yRender = 0.0D;
   boolean up = false;

   public ForceField() {
      super("ForceField", 0, Module.Category.COMBAT);
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Attack Players", this, true));
      Client.settingsmanager.rSetting(new Setting("Attack Mobs", this, true));
      Client.settingsmanager.rSetting(new Setting("Attack Animals", this, false));
   }

   public void onDisable() {
      target = null;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         List entities = this.mc.theWorld.getLoadedEntityList();

         for(int i = 0; i < entities.size(); ++i) {
            if (entities.get(i) instanceof EntityPlayer && entities.get(i) != this.mc.thePlayer && Client.settingsmanager.getSettingByName("Attack Players").getValBoolean() && this.mc.thePlayer.getDistanceToEntity((Entity)entities.get(i)) <= 6.0F) {
               EntityPlayer p = (EntityPlayer)entities.get(i);
               if (!Client.friends.contains(p.username)) {
                  target = (Entity)entities.get(i);
                  this.mc.playerController.attackEntity(this.mc.thePlayer, (Entity)entities.get(i));
               }
            }

            if (entities.get(i) instanceof EntityMob && entities.get(i) != this.mc.thePlayer && Client.settingsmanager.getSettingByName("Attack Mobs").getValBoolean() && this.mc.thePlayer.getDistanceToEntity((Entity)entities.get(i)) <= 6.0F) {
               target = (Entity)entities.get(i);
               this.mc.playerController.attackEntity(this.mc.thePlayer, (Entity)entities.get(i));
            }

            if (entities.get(i) instanceof EntityAnimal && entities.get(i) != this.mc.thePlayer && Client.settingsmanager.getSettingByName("Attack Animals").getValBoolean() && this.mc.thePlayer.getDistanceToEntity((Entity)entities.get(i)) <= 6.0F) {
               target = (Entity)entities.get(i);
               this.mc.playerController.attackEntity(this.mc.thePlayer, (Entity)entities.get(i));
            }
         }

         if (target != null && this.mc.thePlayer.getDistanceToEntity(target) > 6.0F) {
            target = null;
         }
      }

   }

   public void onRender() {
      if (this.isEnabled() && target != null && this.mc.thePlayer.getDistanceToEntity(target) <= 6.0F) {
         double cX = target.posX;
         double maxY = target.boundingBox.maxY + 0.1D;
         double minY = target.boundingBox.minY;
         if (!this.up) {
            if (this.yRender <= 0.0D) {
               this.up = true;
            }

            this.yRender -= 0.008D;
         }

         if (this.up) {
            if (this.yRender > (double)target.height) {
               this.up = false;
            }

            this.yRender += 0.008D;
         }

         double cZ = target.posZ;
         double renderX = cX - RenderManager.renderPosX;
         double renderY = minY + this.yRender - RenderManager.renderPosY;
         double renderZ = cZ - RenderManager.renderPosZ;
         this.drawCircle(target, this.mc.timer.renderPartialTicks, 0.7D, this.yRender);
      }

   }

   private void drawCircle(Entity entity, float partialTicks, double rad, double yOffset) {
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(3);
      double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - RenderManager.field_1222_l;
      double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - RenderManager.field_1221_m + yOffset;
      double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - RenderManager.field_1220_n;
      double pix2 = 6.283185307179586D;

      for(int j = -20; j < 20; ++j) {
         for(int i = 0; i <= 90; ++i) {
            int color = ColorUtil.getRainbow(4.0F, 0.6F, 1.0F, (long)(-i * 90));
            float red = (float)(color >> 16 & 255) / 255.0F;
            float blue = (float)(color >> 8 & 255) / 255.0F;
            float green = (float)(color & 255) / 255.0F;
            float alpha = (float)(color >> 24 & 255) / 400.0F;
            GL11.glColor4f(red, green, blue, alpha);
            GL11.glVertex3d(x + rad * Math.cos((double)i * 6.283185307179586D / 45.0D), y + (double)j * 0.001D, z + rad * Math.sin((double)i * 6.283185307179586D / 45.0D));
         }
      }

      GL11.glEnd();
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }
}
