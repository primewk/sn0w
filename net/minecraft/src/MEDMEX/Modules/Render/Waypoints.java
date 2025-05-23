package net.minecraft.src.MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;
import org.lwjgl.opengl.GL11;

public class Waypoints extends Module {
   public ArrayList<Waypoints.WayPoint> wayPointList;
   public static Waypoints instance;
   float tagScale = 4.0F;
   float pillarRadius = 0.5F;
   int closeClip = 0;
   float distanceScaleAmount = 0.5F;

   public Waypoints() {
      super("Waypoints", 0, Module.Category.RENDER);
      instance = this;
      this.wayPointList = new ArrayList();
   }

   public void remove(String s) {
      Iterator var3 = instance.wayPointList.iterator();

      while(var3.hasNext()) {
         Waypoints.WayPoint w = (Waypoints.WayPoint)var3.next();
         if (w.name.equalsIgnoreCase(s)) {
            instance.wayPointList.remove(w);
            Client.addChatMessage("Removed Waypoint §8" + s);
            return;
         }
      }

      Client.addChatMessage("Waypoint §8" + s + "can not be found!");
   }

   public void addPoint(Vec3D p, String n, String s, int d) {
      Iterator var6 = instance.wayPointList.iterator();

      while(var6.hasNext()) {
         Waypoints.WayPoint w = (Waypoints.WayPoint)var6.next();
         if (w.name.equalsIgnoreCase(n)) {
            Client.addChatMessage("The wayPoint §8" + n + "already exists");
            return;
         }
      }

      this.add(p, n, s, d);
      Client.addChatMessage("Added new waypoint!");
      Client.addChatMessage("Name: §8" + n);
      Client.addChatMessage("Coords: §7" + (int)p.xCoord + "/" + (int)p.yCoord + "/" + (int)p.zCoord + "]");
      String dim = "§2overworld";
      if (d == 1) {
         dim = "§6end";
      }

      if (d == -1) {
         dim = "§4nether";
      }

      Client.addChatMessage("Dimension: " + dim);
   }

   public void add(Vec3D p, String n, String s, int d) {
      this.wayPointList.add(new Waypoints.WayPoint(p, n, s, d));
   }

   public void onRender() {
      String currentAddress = this.mc.serverName != null ? this.mc.serverName : "singleplayer";
      if (this.isEnabled()) {
         Iterator var3 = this.wayPointList.iterator();

         while(true) {
            Waypoints.WayPoint w;
            do {
               do {
                  do {
                     if (!var3.hasNext()) {
                        return;
                     }

                     w = (Waypoints.WayPoint)var3.next();
                  } while(!w.server.equalsIgnoreCase(currentAddress));
               } while(w.dim == 1 && this.mc.thePlayer.dimension != 1);
            } while(this.mc.thePlayer.dimension == 1 && w.dim != 1);

            double x = w.pos.xCoord;
            double y = w.pos.yCoord;
            double z = w.pos.zCoord;
            if (w.dim == -1 && this.mc.thePlayer.dimension == 0) {
               x *= 8.0D;
               z *= 8.0D;
            } else if (w.dim == 0 && this.mc.thePlayer.dimension == -1) {
               x /= 8.0D;
               z /= 8.0D;
            }

            double distance = this.mc.thePlayer.getDistance(x, y, z);
            if (!(distance < (double)this.closeClip)) {
               String displayName = String.valueOf(w.name) + " " + Math.round(distance) + "m";
               if (w.dim != this.mc.thePlayer.dimension) {
                  switch(w.dim) {
                  case -1:
                     displayName = String.valueOf(displayName) + "§4Nether";
                     break;
                  case 0:
                     displayName = String.valueOf(displayName) + "§2Overworld";
                  }
               }

               if (distance < 500.0D && distance > 10.0D && this.pillarRadius > 0.0F) {
                  RenderUtils.boundingESPBox(new AxisAlignedBB(x - (double)this.pillarRadius, -50.0D, z - (double)this.pillarRadius, x + (double)this.pillarRadius, 250.0D, z + (double)this.pillarRadius), new Color(240, 20, 20, 80));
               }

               if (distance > 10.0D) {
                  Vec3D pos = this.mc.thePlayer.getPositionEyes(this.mc.timer.renderPartialTicks);
                  double playerPosx = pos.xCoord;
                  double playerPosy = pos.yCoord;
                  double playerPosz = pos.zCoord;
                  Vec3D dir = (new Vec3D(-playerPosx + x, -playerPosy + y, -playerPosz + z)).normalize().scale(10.0D);
                  x = playerPosx + dir.xCoord;
                  y = playerPosy + dir.yCoord;
                  z = playerPosz + dir.zCoord;
               }

               float baseScale = 0.01F * this.tagScale;
               baseScale = (float)((double)baseScale - (double)(0.005F * this.tagScale * this.distanceScaleAmount) * Math.min(1.0D, 9.999999747378752E-6D * distance));
               renderTagString(displayName, baseScale, 0, (float)x, (float)y, (float)z, 0.33F, 0.33F, 0.33F, 1.0F, (new Color(255, 0, 0)).getRGB());
            }
         }
      }
   }

   public float[] getLegitRenderRotations(Vec3D vec, EntityPlayer me) {
      Vec3D eyesPos = this.getEyesPosRender();
      double diffX = vec.xCoord - eyesPos.xCoord;
      double diffY = vec.yCoord - eyesPos.yCoord;
      double diffZ = vec.zCoord - eyesPos.zCoord;
      double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
      float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
      return new float[]{Minecraft.theMinecraft.thePlayer.rotationYaw + MathHelper.wrapDegrees(yaw - Minecraft.theMinecraft.thePlayer.rotationYaw), Minecraft.theMinecraft.thePlayer.rotationPitch + MathHelper.wrapDegrees(pitch - Minecraft.theMinecraft.thePlayer.rotationPitch)};
   }

   private Vec3D getEyesPosRender() {
      double x = RenderManager.field_1222_l;
      double y = RenderManager.field_1221_m;
      double z = RenderManager.field_1220_n;
      return new Vec3D(x, y + (double)Minecraft.theMinecraft.thePlayer.getEyeHeight(), z);
   }

   public static void renderTagString(String name, float size, int height, float x, float y, float z, float r, float g, float b, float a, int textCol) {
      Minecraft mc = Minecraft.theMinecraft;
      if (RenderManager.options != null) {
         float p_189692_6_ = RenderManager.playerViewY;
         float p_189692_7_ = RenderManager.playerViewX;
         float p_189692_2_ = (float)((double)x - RenderManager.field_1222_l);
         float p_189692_3_ = (float)((double)y - RenderManager.field_1221_m);
         float p_189692_4_ = (float)((double)z - RenderManager.field_1220_n);
         FontRenderer p_189692_0_ = mc.fontRenderer;
         GL11.glPushMatrix();
         GL11.glTranslatef(p_189692_2_, p_189692_3_, p_189692_4_);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-p_189692_6_, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(1.0F * p_189692_7_, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-size, -size, size);
         GL11.glDisable(2896);
         GL11.glDepthMask(false);
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         int i = p_189692_0_.getStringWidth(name) / 2;
         GL11.glDisable(3553);
         Tessellator tessellator = Tessellator.instance;
         tessellator.startDrawingQuads();
         tessellator.setColorRGBA_F(r, g, b, a);
         tessellator.addVertex((double)(-i - 1), (double)(-1 + height), 0.0D);
         tessellator.addVertex((double)(-i - 1), (double)(8 + height), 0.0D);
         tessellator.addVertex((double)(i + 1), (double)(8 + height), 0.0D);
         tessellator.addVertex((double)(i + 1), (double)(-1 + height), 0.0D);
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setColorRGBA_F(255.0F, 0.0F, 0.0F, 255.0F);
         tessellator.addVertex((double)(-i - 2), (double)(-1 + height), 0.0D);
         tessellator.addVertex((double)(-i - 2), (double)(8 + height), 0.0D);
         tessellator.addVertex((double)(-i - 1), (double)(8 + height), 0.0D);
         tessellator.addVertex((double)(-i - 1), (double)(-1 + height), 0.0D);
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setColorRGBA_F(255.0F, 0.0F, 0.0F, 255.0F);
         tessellator.addVertex((double)(i + 1), (double)(-1 + height), 0.0D);
         tessellator.addVertex((double)(i + 1), (double)(8 + height), 0.0D);
         tessellator.addVertex((double)(i + 2), (double)(8 + height), 0.0D);
         tessellator.addVertex((double)(i + 2), (double)(-1 + height), 0.0D);
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setColorRGBA_F(255.0F, 0.0F, 0.0F, 255.0F);
         tessellator.addVertex((double)(-i - 1), (double)(-2 + height), 0.0D);
         tessellator.addVertex((double)(-i - 1), (double)(-1 + height), 0.0D);
         tessellator.addVertex((double)(i + 1), (double)(-1 + height), 0.0D);
         tessellator.addVertex((double)(i + 1), (double)(-2 + height), 0.0D);
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setColorRGBA_F(255.0F, 0.0F, 0.0F, 255.0F);
         tessellator.addVertex((double)(-i - 1), (double)(8 + height), 0.0D);
         tessellator.addVertex((double)(-i - 1), (double)(9 + height), 0.0D);
         tessellator.addVertex((double)(i + 1), (double)(9 + height), 0.0D);
         tessellator.addVertex((double)(i + 1), (double)(8 + height), 0.0D);
         tessellator.draw();
         GL11.glEnable(3553);
         GL11.glDepthMask(true);
         p_189692_0_.drawString(name, (double)(-p_189692_0_.getStringWidth(name) / 2), (double)height, textCol);
         GL11.glEnable(2929);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }
   }

   public class WayPoint {
      public Vec3D pos;
      public String name;
      public String server;
      public int dim;

      public WayPoint(Vec3D p, String n, String s, int d) {
         this.pos = p;
         this.name = n;
         this.server = s;
         this.dim = d;
      }
   }
}
