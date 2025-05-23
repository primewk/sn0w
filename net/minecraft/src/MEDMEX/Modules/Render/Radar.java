package net.minecraft.src.MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.Gui;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.MEDMEX.Modules.Module;
import org.lwjgl.opengl.GL11;

public class Radar extends Module {
   public static Radar instance;

   public Radar() {
      super("Radar", 0, Module.Category.RENDER);
      instance = this;
   }

   public void onRenderGUI() {
      if (this.isEnabled()) {
         GameSettings var10000 = this.mc.gameSettings;
         if (GameSettings.showDebugInfo) {
            return;
         }

         int miX = -22;
         int miY = 8;
         int maX = miX + 100;
         int maY = miY + 100;
         Gui.drawRegularPolygon(42.0D, 60.0D, 41.0D, 360, -2143095477);
         Gui.drawRegularPolygon(42.0D, 60.0D, 40.0D, 360, -2145180893);
         Gui.drawRegularPolygon(42.0D, 60.0D, 1.0D, 360, -12389045);
         GL11.glPushMatrix();
         int scale = (new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight)).scaleFactor;
         Iterator var7 = this.mc.theWorld.loadedEntityList.iterator();

         while(var7.hasNext()) {
            Entity e = (Entity)var7.next();
            if (e instanceof EntityPlayer) {
               EntityPlayer p = (EntityPlayer)e;
               if (e != this.mc.thePlayer && p.username != this.mc.thePlayer.username && this.mc.thePlayer.getDistanceToEntity(e) < 90.0F) {
                  double dist_sq = this.mc.thePlayer.getDistanceSqToEntity(e);
                  double x = e.posX - this.mc.thePlayer.posX;
                  double z = e.posZ - this.mc.thePlayer.posZ;
                  double calc = Math.atan2(x, z) * 57.295780181884766D;
                  double angle = ((double)this.mc.thePlayer.rotationYaw + calc) % 360.0D * 0.01745329238474369D;
                  double hypotenuse = dist_sq / 200.0D;
                  double x_shift = hypotenuse * Math.sin(angle);
                  double y_shift = hypotenuse * Math.cos(angle);
                  Gui.drawRegularPolygon((double)(maX / 2 + 3) - x_shift, (double)(miY + 52) - y_shift, 2.0D, 4, Color.red.getRGB());
                  GL11.glScalef(0.6F, 0.6F, 1.0F);
                  this.mc.fontRenderer.drawCenteredString(this.mc.fontRenderer, p.username + " [§b" + (int)this.mc.thePlayer.getDistanceToEntity(e) + "§r]", ((double)(maX / 2 + 3) - x_shift) * 1.6D, ((double)(miY + 57) - y_shift) * 1.6D, -1);
               }
            }
         }

         GL11.glPopMatrix();
      }

   }
}
