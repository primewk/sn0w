package net.minecraft.src.MEDMEX.Utils;

import java.awt.Color;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class RenderUtils {
   static Sphere s = new Sphere();
   protected float zLevel;

   public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawing(3);
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.draw();
      tessellator.startDrawing(1);
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
      tessellator.draw();
   }

   public static void drawBoundingBox(AxisAlignedBB aa) {
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
      tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
      tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
      tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
      tessellator.draw();
   }

   public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(lineWidth);
      GL11.glColor4f(red, green, blue, alpha);
      drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2896);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glLineWidth(lineWidth);
      GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
      drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glLineWidth(lineWdith);
      GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
      drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(lineWdith);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(2);
      GL11.glVertex3d(0.0D, 0.0D, 0.0D);
      GL11.glVertex3d(x, y, z);
      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void boundingESPBox(AxisAlignedBB box, Color c) {
      int r = c.getRed();
      int g = c.getGreen();
      int b = c.getBlue();
      int a = c.getAlpha();
      double x = box.minX - RenderManager.field_1222_l;
      double y = box.minY - RenderManager.field_1221_m;
      double z = box.minZ - RenderManager.field_1220_n;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.15000000596046448D);
      AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x - box.minX + box.maxX, y - box.minY + box.maxY, z - box.minZ + box.maxZ);
      RenderGlobal.drawOutlinedBoundingBox(bb, 0.00390625F * (float)r, 0.00390625F * (float)g, 0.00390625F * (float)b, 0.00390625F * (float)a);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void drawLine3D(float x, float y, float z, float x1, float y1, float z1, float thickness, int hex) {
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glDisable(3008);
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      GL11.glLineWidth(thickness);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glDisable(2929);
      GL11.glEnable(34383);
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawing(1);
      tessellator.setColorRGBA_F(red, green, blue, alpha);
      tessellator.addVertex((double)x, (double)y, (double)z);
      tessellator.addVertex((double)x1, (double)y1, (double)z1);
      tessellator.draw();
      GL11.glShadeModel(7424);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(34383);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void blockESPBox(Vec3D blockPos, Color c) {
      int r = c.getRed();
      int g = c.getGreen();
      int b = c.getBlue();
      int a = c.getAlpha();
      double x = blockPos.xCoord - RenderManager.field_1222_l;
      double y = blockPos.yCoord - RenderManager.field_1221_m;
      double z = blockPos.zCoord - RenderManager.field_1220_n;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.15000000596046448D);
      RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void blockESPBoxFilled(Vec3D blockPos, Color c) {
      int r = c.getRed();
      int g = c.getGreen();
      int b = c.getBlue();
      int a = c.getAlpha();
      double x = blockPos.xCoord - RenderManager.field_1222_l;
      double y = blockPos.yCoord - RenderManager.field_1221_m;
      double z = blockPos.zCoord - RenderManager.field_1220_n;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4d((double)(0.00390625F * (float)r), (double)(0.00390625F * (float)g), (double)(0.00390625F * (float)b), (double)(0.00390625F * (float)a));
      GL11.glBegin(7);
      AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void bedESPBoxFilled(Vec3D blockPos, Color c) {
      int r = c.getRed();
      int g = c.getGreen();
      int b = c.getBlue();
      int a = c.getAlpha();
      double x = blockPos.xCoord - RenderManager.field_1222_l;
      double y = blockPos.yCoord - RenderManager.field_1221_m;
      double z = blockPos.zCoord - RenderManager.field_1220_n;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4d((double)(0.00390625F * (float)r), (double)(0.00390625F * (float)g), (double)(0.00390625F * (float)b), (double)(0.00390625F * (float)a));
      GL11.glBegin(7);
      AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0D, y + 0.5625D, z + 1.0D);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void boundingESPBoxFilled(AxisAlignedBB box, Color c) {
      int r = c.getRed();
      int g = c.getGreen();
      int b = c.getBlue();
      int a = c.getAlpha();
      double x = box.minX - RenderManager.field_1222_l;
      double y = box.minY - RenderManager.field_1221_m;
      double z = box.minZ - RenderManager.field_1220_n;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4d((double)(0.00390625F * (float)r), (double)(0.00390625F * (float)g), (double)(0.00390625F * (float)b), (double)(0.00390625F * (float)a));
      GL11.glBegin(7);
      AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x - box.minX + box.maxX, y - box.minY + box.maxY, z - box.minZ + box.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void renderSphere(double par3, double par5, double par7, float radius, float red, float green, float blue, float alpha) {
      float x = (float)par3;
      float y = (float)par5;
      float z = (float)par7;
      renderSphere(x, y, z, radius, red, green, blue, alpha);
   }

   private static void renderSphere(float x, float y, float z, float radius, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glTranslatef(x, y + 1.0F, z);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glEnable(3042);
      GL11.glDepthMask(true);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glLineWidth(1.0F);
      s.setDrawStyle(100011);
      s.draw(radius, 64, 64);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }
}
