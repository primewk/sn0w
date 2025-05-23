package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class Gui {
   protected float zLevel = 0.0F;
   public static final double TWICE_PI = 6.283185307179586D;
   private static Tessellator tessellator;

   static {
      tessellator = Tessellator.instance;
   }

   protected void drawHorizontalLine(int var1, int var2, int var3, int var4) {
      if (var2 < var1) {
         int var5 = var1;
         var1 = var2;
         var2 = var5;
      }

      drawRect((double)var1, (double)var3, (double)(var2 + 1), (double)(var3 + 1), var4);
   }

   protected void drawVerticalLine(int var1, int var2, int var3, int var4) {
      if (var3 < var2) {
         int var5 = var2;
         var2 = var3;
         var3 = var5;
      }

      drawRect((double)var1, (double)(var2 + 1), (double)(var1 + 1), (double)var3, var4);
   }

   public static void drawRoundedRect(int xCoord, int yCoord, int xSize, int ySize, int colour, String text) {
      int width = xCoord + xSize;
      int height = yCoord + ySize;
      drawRect((double)(xCoord + 1), (double)yCoord, (double)(width - 1), (double)height, colour);
      drawRect((double)xCoord, (double)(yCoord + 1), (double)width, (double)(height - 1), colour);
      drawCenteredUnicodeString(text, xCoord + xSize / 2, yCoord, 16777215);
   }

   public static void drawRoundedRectArmor(int xCoord, int yCoord, int xSize, int ySize, int colour, String text) {
      int width = xCoord + xSize;
      int height = yCoord + ySize;
      drawRect((double)(xCoord + 1), (double)yCoord, (double)(width - 1), (double)height, -1289542877);
      drawRect((double)xCoord, (double)(yCoord + 1), (double)width, (double)(height - 1), 1495474979);
      drawCenteredUnicodeString(text, xCoord + xSize / 2, yCoord, 16777215);
   }

   public static void drawCenteredUnicodeString(String text, int xCoord, int yCoord, int colour) {
      FontRenderer font = Minecraft.theMinecraft.fontRenderer;
      font.drawString(text, (double)(xCoord - font.getStringWidth(text) / 2), (double)yCoord, colour);
   }

   public static void drawRect(double var1, double var2, double var3, double var4, int var5) {
      double var6;
      if (var1 < var3) {
         var6 = var1;
         var1 = var3;
         var3 = var6;
      }

      if (var2 < var4) {
         var6 = var2;
         var2 = var4;
         var4 = var6;
      }

      float var11 = (float)(var5 >> 24 & 255) / 255.0F;
      float var7 = (float)(var5 >> 16 & 255) / 255.0F;
      float var8 = (float)(var5 >> 8 & 255) / 255.0F;
      float var9 = (float)(var5 & 255) / 255.0F;
      Tessellator var10 = Tessellator.instance;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(var7, var8, var9, var11);
      var10.startDrawingQuads();
      var10.addVertex(var1, var4, 0.0D);
      var10.addVertex(var3, var4, 0.0D);
      var10.addVertex(var3, var2, 0.0D);
      var10.addVertex(var1, var2, 0.0D);
      var10.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
      float var8 = 1.0F / textureWidth;
      float var9 = 1.0F / textureHeight;
      Tessellator var11 = Tessellator.instance;
      var11.startDrawingQuads();
      var11.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var8), (double)((v + (float)height) * var9));
      var11.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)width) * var8), (double)((v + (float)height) * var9));
      var11.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)width) * var8), (double)(v * var9));
      var11.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var8), (double)(v * var9));
      var11.draw();
   }

   protected void drawGradientRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = (float)(var5 >> 24 & 255) / 255.0F;
      float var8 = (float)(var5 >> 16 & 255) / 255.0F;
      float var9 = (float)(var5 >> 8 & 255) / 255.0F;
      float var10 = (float)(var5 & 255) / 255.0F;
      float var11 = (float)(var6 >> 24 & 255) / 255.0F;
      float var12 = (float)(var6 >> 16 & 255) / 255.0F;
      float var13 = (float)(var6 >> 8 & 255) / 255.0F;
      float var14 = (float)(var6 & 255) / 255.0F;
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glDisable(3008);
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      Tessellator var15 = Tessellator.instance;
      var15.startDrawingQuads();
      var15.setColorRGBA_F(var8, var9, var10, var7);
      var15.addVertex((double)var3, (double)var2, 0.0D);
      var15.addVertex((double)var1, (double)var2, 0.0D);
      var15.setColorRGBA_F(var12, var13, var14, var11);
      var15.addVertex((double)var1, (double)var4, 0.0D);
      var15.addVertex((double)var3, (double)var4, 0.0D);
      var15.draw();
      GL11.glShadeModel(7424);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glEnable(3553);
   }

   public void drawCenteredString(FontRenderer var1, String var2, int var3, int var4, int var5) {
      var1.drawStringWithShadow(var2, (double)(var3 - var1.getStringWidth(var2) / 2), (double)var4, var5);
   }

   public void drawString(FontRenderer var1, String var2, int var3, int var4, int var5) {
      var1.drawStringWithShadow(var2, (double)var3, (double)var4, var5);
   }

   public void drawTexturedModalRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.instance;
      var9.startDrawingQuads();
      var9.addVertexWithUV((double)(var1 + 0), (double)(var2 + var6), (double)this.zLevel, (double)((float)(var3 + 0) * var7), (double)((float)(var4 + var6) * var8));
      var9.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), (double)this.zLevel, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + var6) * var8));
      var9.addVertexWithUV((double)(var1 + var5), (double)(var2 + 0), (double)this.zLevel, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + 0) * var8));
      var9.addVertexWithUV((double)(var1 + 0), (double)(var2 + 0), (double)this.zLevel, (double)((float)(var3 + 0) * var7), (double)((float)(var4 + 0) * var8));
      var9.draw();
   }

   public static void drawRegularPolygon(double x, double y, double radius, int sides, int color) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      tessellator.startDrawing(6);
      tessellator.addVertex(x, y, 0.0D);

      for(int i = 0; i <= sides; ++i) {
         double angle = 6.283185307179586D * (double)i / (double)sides + Math.toRadians(180.0D);
         GL11.glColor4f(red, green, blue, alpha);
         tessellator.addVertex(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0.0D);
      }

      tessellator.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
      float var10 = 1.0F / tileWidth;
      float var11 = 1.0F / tileHeight;
      Tessellator var12 = Tessellator.instance;
      var12.startDrawingQuads();
      var12.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var10), (double)((v + (float)vHeight) * var11));
      var12.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)uWidth) * var10), (double)((v + (float)vHeight) * var11));
      var12.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)uWidth) * var10), (double)(v * var11));
      var12.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var10), (double)(v * var11));
      var12.draw();
   }
}
